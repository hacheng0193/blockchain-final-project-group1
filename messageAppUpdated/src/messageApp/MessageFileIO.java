package messageApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessageFileIO {
	private static final String msgRecordFileName = "message.dat";
	private static File msgRecordFile;
	private static String[] address;
	
	MessageFileIO() {
		loadMessage();
	}
	
	public void addMessage(String newAddress) {
		for (String singleAddress : address) {
			if (singleAddress.contentEquals(newAddress)) {
				System.out.println("It's already in the list !");
				return;
			}
		}
		String[] tempAddress = address;
		address = new String[address.length + 1];
		for (int i = 0; i < address.length; i++) {
			if (i == address.length - 1) {
				address[i] = newAddress;
			} else {
				address[i] = tempAddress[i];
			}
		}
		updateMessage();
	}
	
	public String[] getAllMessages() {
		return address;
	}
	
	
	private void updateMessage() {
		msgRecordFile = new File(msgRecordFileName);
		if (msgRecordFile.exists()) {
			msgRecordFile.delete();
		}
		try {
			msgRecordFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(msgRecordFile));
			
			for (int i = 0; i < address.length; i++) {
				bw.write(address[i] + "\n");
			}
			bw.flush();
			bw.close();
		} catch (IOException ioe) {
			System.out.println("Cannot update message record file");
			ioe.printStackTrace();
		}
	}
	
	private void loadMessage() {
		msgRecordFile = new File(msgRecordFileName);
		if (msgRecordFile.exists()) {
			try {
				FileInputStream fis = new FileInputStream(msgRecordFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				int lineCount = 0;
				
				while (br.ready()) {
					br.readLine();
					lineCount++;
				}
				br.close();
				fis.close();
				
				if (lineCount < 1) {
					System.out.println("There's no existing message record !");
					address = new String[0];
				} else {
					fis = new FileInputStream(msgRecordFile);
					br = new BufferedReader(new InputStreamReader(fis));
					address = new String[lineCount];
					for (int i = 0; i < lineCount; i++) {
						address[i] = br.readLine();
					}
				}
			} catch (IOException ioe) {
				System.err.println("Cannot read message record file !");
				ioe.printStackTrace();
				System.exit(1);
			}
		} else {
			try {
				msgRecordFile.createNewFile();
				System.out.println("Message record file does not exist.");
				System.out.println("Create new message record file.");
			} catch (IOException ioe) {
				System.err.println("Cannot create message record file !");
				ioe.printStackTrace();
				System.exit(1);
			}
			address = new String[0];
		}
	}

}
