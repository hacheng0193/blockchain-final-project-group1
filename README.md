# Group 1 DAPP -- lottery on blockchain

## Project intro
lottery.sol
    - solidity code for the smart contract
lottery_deploy.ipynb
    - deploy contract to chain
lottery_frontend.ipynb
    - the frontend to interact with the DAPP
## Step
1. put your code lottery.sol under the folder lottery
2. execute the following cmd, and you will see the .bin, .abi under a folder named build
```
solc --bin --abi --evm-version=london lottery.sol -o build
```
lottery
├── build
│   ├── Lottery.abi
│   └── Lottery.bin
└── lottery.sol
3. open your chain, and run the lottery_deploy.ipynb to deploy the contract to the chain, remeber the contractAddress from step 3 output
![alt text](/image/image-1.png)
4. create several user account to simulate multiple lottery buyer, and paste the contractAddress to lottery_frontend.ipynb
![alt text](/image/image-4.png)
5. Then you can start running your onchain lottery businiess


![alt text](/image/image.png)
![alt text](/image/image-2.png)
![alt text](/image/image-3.png)

## Contribution
111511198 鄭恆安(hacheng0193) - project idea, lottery.sol, lottery_deploy.ipynb, lottery_frontend.ipynb
李博凱 - project idea, lottery.sol, lottery_frontend.ipynb
