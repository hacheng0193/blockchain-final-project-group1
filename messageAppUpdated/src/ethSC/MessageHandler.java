package ethSC;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import ethInfo.EthBasis;

public class MessageHandler {
	// 2. Why do we need handler?
	private static String address;
	private static Web3j web3j;
	private static Credentials credential;
	private static ContractGasProvider contractGasProvider;
	private static Message msg;
	private static TransactionManager transactionManager;

	public MessageHandler(String data, boolean mode){
		web3j = Web3j.build(new HttpService(EthBasis.pipeLine)); //Docker

		try {
			credential = WalletUtils.loadCredentials(EthBasis.password, EthBasis.credential);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		contractGasProvider = new DefaultGasProvider();
		transactionManager = new RawTransactionManager(web3j, credential, EthBasis.chainID);


		try {
			if(mode){
				System.out.println("Message Create Mode...");
				createMsg(data);
			}
			else{
				System.out.println("Message Load Mode...");
				loadMsg(data);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void createMsg(String message) throws Exception {
		msg = Message.deploy(web3j, transactionManager, contractGasProvider).send();
		address = msg.getContractAddress();
		System.out.println("Message deployed to: " + address);
		msg.set(message).send();
		System.out.println("Message sent: " + message);
	}

	private void loadMsg(String address) throws Exception {
		msg = Message.load(address, web3j, transactionManager, contractGasProvider);
		this.address = address;
		System.out.println("Message loaded from: " + address);
	}

	public String getMessage() throws Exception {
		return msg.get().send();
	}

	public String getAddress() {
		return address;
	}
}

