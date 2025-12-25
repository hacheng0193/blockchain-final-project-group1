package messageApp;

import java.util.Scanner;

import ethSC.MessageHandler;

public class Messenger {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to the Messenger App !");
		System.out.println("What do you like to do today ?");
		System.out.println("1. Create New Message");
		System.out.println("2. Read an Existing Message");
		System.out.println("Your chioce ? (1/2)");
		
		int choice = Integer.parseInt(sc.nextLine());
		MessageFileIO mfio = new MessageFileIO();
		String[] existingMessages = mfio.getAllMessages();
		
		if (choice == 1) {
			System.out.println("Sure ! What's your message ?");
			String newMessage = sc.nextLine();
			MessageHandler mh = new MessageHandler(newMessage, true);
			mfio.addMessage(mh.getAddress());
		} else if (choice == 2) {
			if (existingMessages.length < 1) {
				System.out.println("You have no existing messages");
				return;
			}
			System.out.println("Sure ! You have " + existingMessages.length + " message(s)");
			System.out.println("Which message are you going to read ?");
			for (int i = 0; i < existingMessages.length; i++) {
				System.out.println(i + " " + existingMessages[i]);
			}
			System.out.println("Your choice ?");
			
			int mChoice = Integer.parseInt(sc.nextLine());
			
			if (mChoice > existingMessages.length -1 || mChoice < 0) {
				System.err.println("There's no such option");
				return;
			}
			
			MessageHandler mh = new MessageHandler(existingMessages[mChoice], false);
			System.out.println("The message is " + mh.getMessage());
			
			
		} else {
			System.err.println("Option not existed!");
			System.exit(1);
		}
		
	}
}


