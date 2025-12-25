docker run -it \
  -v $(pwd):/root/.ethereum \
  -p 8545:8545 -p 30303:30303 \
  ethereum/client-go:v1.10.26 \
  --networkid 11330023 \
  --http --http.port 8545 --http.addr 0.0.0.0 \
  --http.api "eth,net,web3,personal,miner" \
  --http.corsdomain=* \
  --allow-insecure-unlock \
  console

