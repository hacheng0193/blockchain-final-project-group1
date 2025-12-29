from web3 import Web3

w3 = Web3(Web3.HTTPProvider("http://127.0.0.1:8545"))

assert w3.is_connected(), "❌ 無法連線到 geth RPC"

print("✅ Connected to chain")
print("Chain ID:", w3.eth.chain_id)
print("Accounts:", w3.eth.accounts)
