package ethSC;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class Message extends Contract {
    public static final String BINARY = "6080604052348015600f57600080fd5b5033600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506106d6806100606000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80634ed3885e1461003b5780636d4ce63c14610057575b600080fd5b610055600480360381019061005091906102ce565b610075565b005b61005f6100e2565b60405161006c9190610396565b60405180910390f35b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146100cf57600080fd5b80600090816100de91906105ce565b5050565b6060600080546100f1906103e7565b80601f016020809104026020016040519081016040528092919081815260200182805461011d906103e7565b801561016a5780601f1061013f5761010080835404028352916020019161016a565b820191906000526020600020905b81548152906001019060200180831161014d57829003601f168201915b5050505050905090565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6101db82610192565b810181811067ffffffffffffffff821117156101fa576101f96101a3565b5b80604052505050565b600061020d610174565b905061021982826101d2565b919050565b600067ffffffffffffffff821115610239576102386101a3565b5b61024282610192565b9050602081019050919050565b82818337600083830152505050565b600061027161026c8461021e565b610203565b90508281526020810184848401111561028d5761028c61018d565b5b61029884828561024f565b509392505050565b600082601f8301126102b5576102b4610188565b5b81356102c584826020860161025e565b91505092915050565b6000602082840312156102e4576102e361017e565b5b600082013567ffffffffffffffff81111561030257610301610183565b5b61030e848285016102a0565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610351578082015181840152602081019050610336565b60008484015250505050565b600061036882610317565b6103728185610322565b9350610382818560208601610333565b61038b81610192565b840191505092915050565b600060208201905081810360008301526103b0818461035d565b905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806103ff57607f821691505b602082108103610412576104116103b8565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b60006008830261047a7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261043d565b610484868361043d565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b60006104cb6104c66104c18461049c565b6104a6565b61049c565b9050919050565b6000819050919050565b6104e5836104b0565b6104f96104f1826104d2565b84845461044a565b825550505050565b600090565b61050e610501565b6105198184846104dc565b505050565b5b8181101561053d57610532600082610506565b60018101905061051f565b5050565b601f8211156105825761055381610418565b61055c8461042d565b8101602085101561056b578190505b61057f6105778561042d565b83018261051e565b50505b505050565b600082821c905092915050565b60006105a560001984600802610587565b1980831691505092915050565b60006105be8383610594565b9150826002028217905092915050565b6105d782610317565b67ffffffffffffffff8111156105f0576105ef6101a3565b5b6105fa82546103e7565b610605828285610541565b600060209050601f8311600181146106385760008415610626578287015190505b61063085826105b2565b865550610698565b601f19841661064686610418565b60005b8281101561066e57848901518255600182019150602085019450602081019050610649565b8683101561068b5784890151610687601f891682610594565b8355505b6001600288020188555050505b50505050505056fea2646970667358221220267977cfb68b3f7f10767ec8aae4c1d2c3eea350ac18ce723d511d873458227264736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_GET = "get";

    public static final String FUNC_SET = "set";

    @Deprecated
    protected Message(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Message(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Message(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Message(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> get() {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> set(String set_message) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(set_message)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Message load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Message(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Message load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Message(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Message load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Message(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Message load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Message(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Message> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Message.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<Message> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Message.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Message> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Message.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<Message> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Message.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
