docker run --rm \
  -v $(pwd):/root/.ethereum \
  -v $(pwd)/genesis.json:/genesis.json \
  ethereum/client-go:v1.10.26 \
  init /genesis.json
