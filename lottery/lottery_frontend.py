# frontend.py - Lottery 合约前端
from web3 import Web3
import json

# 连接以太坊节点
w3 = Web3(Web3.HTTPProvider("http://127.0.0.1:8545"))
assert w3.is_connected()

print("✅ 已连接到以太坊节点")
print(f"Chain ID: {w3.eth.chain_id}")
print(f"可用账户: {w3.eth.accounts}")

# 加载 Lottery ABI
with open("build/Lottery.abi") as f:
    abi = json.load(f)

# Lottery 合约地址（从 deploy.ipynb 部署的地址）
contract_address = Web3.to_checksum_address("d4c9c103617c7f1367b7671d2f341283277fcc504657b1d7ea7d2a6d023e1763")

# 创建合约实例
lottery = w3.eth.contract(address=contract_address, abi=abi)

# 使用第一个账户
account = w3.eth.accounts[0]
print(f"\n使用账户: {account}")

# 检查账户余额
balance = w3.eth.get_balance(account)
print(f"账户余额: {w3.from_wei(balance, 'ether')} ETH")

# ============================================
# 1. 查看合约状态
# ============================================
print("\n" + "="*50)
print("1. 查看 Lottery 合约状态")
print("="*50)

try:
    entry_fee = lottery.functions.entryFee().call()
    participant_count = lottery.functions.getParticipantCount().call()
    is_active = lottery.functions.isLotteryActive().call()
    prize_pool = lottery.functions.prizePool().call()
    owner = lottery.functions.owner().call()
    contract_balance = lottery.functions.getContractBalance().call()
    
    print(f"合约拥有者: {owner}")
    print(f"入場費: {w3.from_wei(entry_fee, 'ether')} ETH ({entry_fee} wei)")
    print(f"參與者數量: {participant_count}")
    print(f"抽獎狀態: {'✅ 進行中' if is_active else '❌ 已暫停'}")
    print(f"獎金池: {w3.from_wei(prize_pool, 'ether')} ETH")
    print(f"合約餘額: {w3.from_wei(contract_balance, 'ether')} ETH")
    
    # 查看上一輪獲勝者（如果有）
    last_winner = lottery.functions.lastWinner().call()
    last_prize = lottery.functions.lastPrize().call()
    if last_winner != "0x0000000000000000000000000000000000000000":
        print(f"\n上一輪獲勝者: {last_winner}")
        print(f"上一輪獎金: {w3.from_wei(last_prize, 'ether')} ETH")
    
except Exception as e:
    print(f"❌ 读取合约状态失败: {e}")
    exit(1)

# ============================================
# 2. 查看參與者列表
# ============================================
print("\n" + "="*50)
print("2. 查看參與者列表")
print("="*50)

try:
    participants = lottery.functions.getParticipants().call()
    if len(participants) > 0:
        print(f"參與者列表 ({len(participants)} 人):")
        for i, participant in enumerate(participants):
            is_current = " (當前用戶)" if participant.lower() == account.lower() else ""
            print(f"  {i+1}. {participant}{is_current}")
    else:
        print("目前沒有參與者")
except Exception as e:
    print(f"❌ 读取参与者列表失败: {e}")

# ============================================
# 3. 參與抽獎
# ============================================
print("\n" + "="*50)
print("3. 參與抽獎")
print("="*50)

try:
    # 檢查是否已經參與
    already_participated = lottery.functions.isParticipant(account).call()
    
    if already_participated:
        print("⚠️  您已經參與過本輪抽獎")
    elif not is_active:
        print("⚠️  抽獎目前暫停中，無法參與")
    else:
        # 檢查餘額是否足夠
        if balance < entry_fee:
            print(f"❌ 餘額不足！需要 {w3.from_wei(entry_fee, 'ether')} ETH，但只有 {w3.from_wei(balance, 'ether')} ETH")
        else:
            print(f"準備參與抽獎，需要支付 {w3.from_wei(entry_fee, 'ether')} ETH...")
            
            # 發送交易參與抽獎
            tx_hash = lottery.functions.enter().transact({
                "from": account,
                "value": entry_fee,
                "gas": 100_000
            })
            
            print(f"交易哈希: {tx_hash.hex()}")
            
            # 等待交易確認
            receipt = w3.eth.wait_for_transaction_receipt(tx_hash)
            
            if receipt.status == 1:
                print(f"✅ 參與成功！交易已確認，區塊: {receipt.blockNumber}")
                
                # 再次查看參與者數量
                participant_count = lottery.functions.getParticipantCount().call()
                prize_pool = lottery.functions.prizePool().call()
                print(f"現在參與者數量: {participant_count}")
                print(f"現在獎金池: {w3.from_wei(prize_pool, 'ether')} ETH")
            else:
                print("❌ 交易失敗")
                
except Exception as e:
    print(f"❌ 參與抽獎失敗: {e}")
    print("提示: 請確保賬戶已解鎖（在 geth console 中執行: personal.unlockAccount(eth.accounts[0], 'nycu', 60)）")

# ============================================
# 4. 抽獎（僅合約擁有者）
# ============================================
print("\n" + "="*50)
print("4. 執行抽獎（僅合約擁有者）")
print("="*50)

try:
    owner = lottery.functions.owner().call()
    participant_count = lottery.functions.getParticipantCount().call()
    
    if account.lower() != owner.lower():
        print(f"⚠️  只有合約擁有者可以執行抽獎")
        print(f"   合約擁有者: {owner}")
        print(f"   當前賬戶: {account}")
    elif participant_count == 0:
        print("⚠️  目前沒有參與者，無法抽獎")
    elif not is_active:
        print("⚠️  抽獎已暫停，無法執行")
    else:
        print(f"準備執行抽獎，目前有 {participant_count} 位參與者...")
        
        tx_hash = lottery.functions.drawLottery().transact({
            "from": account,
            "gas": 200_000
        })
        
        print(f"交易哈希: {tx_hash.hex()}")
        
        receipt = w3.eth.wait_for_transaction_receipt(tx_hash)
        
        if receipt.status == 1:
            print(f"✅ 抽獎完成！交易已確認，區塊: {receipt.blockNumber}")
            
            # 查看獲勝者
            winner = lottery.functions.lastWinner().call()
            prize = lottery.functions.lastPrize().call()
            print(f"\n🎉 獲勝者: {winner}")
            print(f"💰 獎金: {w3.from_wei(prize, 'ether')} ETH")
            
            # 查看新的狀態
            participant_count = lottery.functions.getParticipantCount().call()
            prize_pool = lottery.functions.prizePool().call()
            print(f"\n新一輪狀態:")
            print(f"  參與者數量: {participant_count}")
            print(f"  獎金池: {w3.from_wei(prize_pool, 'ether')} ETH")
        else:
            print("❌ 抽獎失敗")
            
except Exception as e:
    print(f"❌ 執行抽獎失敗: {e}")

# ============================================
# 5. 管理功能（僅合約擁有者）
# ============================================
print("\n" + "="*50)
print("5. 管理功能（僅合約擁有者）")
print("="*50)

try:
    owner = lottery.functions.owner().call()
    
    if account.lower() != owner.lower():
        print(f"⚠️  只有合約擁有者可以使用管理功能")
    else:
        print("可用管理功能:")
        print("  - setLotteryActive(bool): 暫停/啟動抽獎")
        print("  - setEntryFee(uint256): 修改入場費（僅在沒有參與者時）")
        print("  - manualReset(): 手動重置抽獎")
        print("  - emergencyWithdraw(): 緊急提取合約餘額")
        
        # 示例：暫停/啟動抽獎
        current_status = lottery.functions.isLotteryActive().call()
        print(f"\n當前抽獎狀態: {'進行中' if current_status else '已暫停'}")
        # 如果需要修改狀態，可以取消下面的註釋
        # new_status = not current_status
        # tx_hash = lottery.functions.setLotteryActive(new_status).transact({
        #     "from": account,
        #     "gas": 100_000
        # })
        # receipt = w3.eth.wait_for_transaction_receipt(tx_hash)
        # print(f"狀態已更改為: {'進行中' if new_status else '已暫停'}")
        
except Exception as e:
    print(f"❌ 讀取管理信息失敗: {e}")

print("\n" + "="*50)
print("完成！")
print("="*50)