// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.8.2 <0.9.0;

contract Lottery {
    // 合約擁有者（管理員）
    address public owner;
    
    // 入場費（單位：wei）
    uint public entryFee;
    
    // 參與者列表
    address[] public participants;
    
    // 當前獎金池總額
    uint public prizePool;
    
    // 是否正在進行抽獎
    bool public isLotteryActive;
    
    // 上一輪的獲勝者
    address public lastWinner;
    
    // 上一輪的獎金
    uint public lastPrize;
    
    // 事件
    event ParticipantJoined(address indexed participant, uint entryFee);
    event LotteryDrawn(address indexed winner, uint prize);
    event LotteryReset();
    
    // 修飾符：只有合約擁有者可以執行
    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can call this function");
        _;
    }
    
    // 修飾符：確保抽獎正在進行
    modifier lotteryActive() {
        require(isLotteryActive, "Lottery is not active");
        _;
    }
    
    // 建構函數：設定合約擁有者和入場費
    constructor() {
        owner = msg.sender;
        entryFee = 100; // 預設入場費為100 wei
        isLotteryActive = true;
    }
    
    // 參與者支付入場費加入抽獎
    function enter() public payable lotteryActive {
        require(msg.value == entryFee, "Must pay exact entry fee");
        require(!isParticipant(msg.sender), "Already participated in this round");
        
        participants.push(msg.sender);
        prizePool += msg.value;
        
        emit ParticipantJoined(msg.sender, msg.value);
    }
    
    // 檢查地址是否已經參與
    function isParticipant(address _address) public view returns (bool) {
        for (uint i = 0; i < participants.length; i++) {
            if (participants[i] == _address) {
                return true;
            }
        }
        return false;
    }
    
    // 獲取參與者數量
    function getParticipantCount() public view returns (uint) {
        return participants.length;
    }
    
    // 獲取所有參與者
    function getParticipants() public view returns (address[] memory) {
        return participants;
    }
    
    // 抽獎：隨機選擇獲勝者並發放獎金
    function drawLottery() public onlyOwner lotteryActive {
        require(participants.length > 0, "No participants");
        setLotteryActive(false);
        // 使用區塊信息生成隨機數
        uint randomNumber = uint(keccak256(abi.encodePacked(
            block.timestamp,
            block.difficulty,
            block.number,
            participants.length
        )));
        
        // 選擇獲勝者
        uint winnerIndex = randomNumber % participants.length;
        address winner = participants[winnerIndex];
        
        // 記錄獲勝者信息
        lastWinner = winner;
        lastPrize = prizePool;
        
        // 發放獎金給獲勝者
        payable(winner).transfer(prizePool);
        
        emit LotteryDrawn(winner, prizePool);
        
        // 重置抽獎狀態
        resetLottery();
    }
    
    // 重置抽獎（清空參與者列表和獎金池）
    function resetLottery() internal {
        delete participants;
        prizePool = 0;
        setLotteryActive(true);
        emit LotteryReset();
    }
    
    // 管理員手動重置抽獎
    function manualReset() public onlyOwner {
        // 如果有參與者，先退還入場費
        setLotteryActive(false);
        if (participants.length > 0) {
            for (uint i = 0; i < participants.length; i++) {
                payable(participants[i]).transfer(entryFee);
            }
        }
        resetLottery();
    }
    
    // 暫停/啟動抽獎
    function setLotteryActive(bool _active) public onlyOwner {
        isLotteryActive = _active;
    }
    
    // 修改入場費（僅在沒有參與者時）
    function setEntryFee(uint _newEntryFee) public onlyOwner {
        require(participants.length == 0, "Cannot change fee while lottery is active");
        setLotteryActive(false);
        entryFee = _newEntryFee;
        resetLottery();
    }
    
    // 獲取合約餘額
    function getContractBalance() public view returns (uint) {
        return address(this).balance;
    }
    
    // 緊急提取（僅合約擁有者）
    function emergencyWithdraw() public onlyOwner {
        setLotteryActive(false);
        payable(owner).transfer(address(this).balance);
        resetLottery();
    }
}