import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.swing.SwingUtilities;

import client.ClientUI;
import client.CreateAuction;
import client.ItemDisplayData;
import client.LogIn;
import communication.Comms;
import communication.messages.Message;
import communication.messages.MessageType;
import main.Category;

public class Client implements ActionListener{
	
	public static void main(String[] args) {
		new Client();		
	}

	private ClientUI ui;
	private Thread notifyListener;
	private Comms comms;
	private String serverIP;
	private int sessionNo = -1;
	
	public Client() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					ui = new ClientUI(Client.this);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			comms = new Comms("localhost", 7254, System.out);
			sessionNo = comms.receiveMessage().getSessionNo();
			serverIP = comms.getIP();
			System.out.println(serverIP);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		switch(e.getActionCommand()){
			case "Log in" :
				logIn();
				break;
				
			case "Register" :
				register();
				break;
				
			case "Log off" :
				logOff();
				break;
				
			case "Show Auction Create" :
				ui.displayCreateAuction();
				break;
				
			case "Create Auction" :
				createAuction();
				break;
				
			default :
				if(e.getActionCommand().matches("view \\d*")){
					int UIID = Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf(' ') + 1));
					viewItem(UIID);
				}
				break;
		}
	}

	private void viewItem(int uIID) {
		Message msg = new Message(serverIP, sessionNo, MessageType.ViewAuctionRequest);
		msg.setUIID(uIID);
		try {
			comms.sendMessage(msg);
			Message reply = comms.receiveMessage();
			if(reply.getType() == MessageType.Failure){
				ui.showNotification("Failed to retrieve data: " + reply.getErrorMessage());
			} else if(reply.getType() == MessageType.ViewAuctionData){
				ItemDisplayData i = new ItemDisplayData(reply.getUIID(), reply.getTitle(), reply.getDescription(), 
						reply.getCategory(), reply.getVendorID(), new Date(reply.getEndTime()), reply.getTopBidValue(), 
						reply.getUserBidValue());
				ui.displayItem(i);
			} else {
				ui.showNotification("Error communicating with server");
			}
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void createAuction() {
		CreateAuction auc = ui.getCreateAuction();
		String title = auc.getTitle();
		String desc =auc.getDescription();
		int reserve = auc.getReserve();
		Category cat = auc.getCategory();
		int length = auc.getDays();
		Message msg = new Message(serverIP, sessionNo, MessageType.CreateAuction);
		msg.setTitle(title);
		msg.setDescription(desc);
		msg.setReservePrice(reserve);
		msg.setCategory(cat);
		long endTime = new Date().getTime() + 1000 * 60 * 60 * 24 * length;
		msg.setEndTime(endTime);
		try {
			comms.sendMessage(msg);
			Message reply = comms.receiveMessage();
			if(reply.getType() == MessageType.Failure){
				ui.showNotification("Auction creation Failed: " + reply.getErrorMessage());
			} else if(reply.getType() == MessageType.Success){
				ui.showNotification("Auction creation successful");
				ui.displayHome();
			} else {
				ui.showNotification("Error communicating with server");
			}
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void logOff() {
		Message msg = new Message(serverIP, sessionNo, MessageType.LogOff);
		try {
			comms.sendMessage(msg);
			Message reply = comms.receiveMessage();
			if(reply.getType() == MessageType.Failure){
				ui.showNotification("Log off Failed: " + reply.getErrorMessage());
			} else if(reply.getType() == MessageType.Success){
				ui.displayLogIn();
			} else {
				ui.showNotification("Error communicating with server");
			}
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void register() {
		LogIn logIn = ui.getLogIn();
		String name = logIn.getRegisterUsername();
		char[] pass = logIn.getRegisterPassword();
		String fname = logIn.getFirstName();
		String lname = logIn.getLastName();
		Message msg = new Message(serverIP, sessionNo, MessageType.Register);
		msg.setScreenName(name);
		msg.setPassword(pass);
		msg.setForename(fname);
		msg.setSurname(lname);
		try {
			comms.sendMessage(msg);
			Message reply = comms.receiveMessage();
			if(reply.getType() == MessageType.Failure){
				ui.showNotification("Registration failed: " + reply.getErrorMessage());
			} else if(reply.getType() == MessageType.Success){
				ui.displayHome();
			} else {
				ui.showNotification("Error communicating with server");
			}
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void logIn() {
		LogIn logIn = ui.getLogIn();
		String name = logIn.getLogInUsername();
		char[] pass = logIn.getLogInPassword();
		Message msg = new Message(serverIP, sessionNo, MessageType.LogIn);
		msg.setScreenName(name);
		msg.setPassword(pass);
		try {
			comms.sendMessage(msg);
			Message reply = comms.receiveMessage();
			if(reply.getType() == MessageType.Failure){
				ui.showNotification("Login failed: " + reply.getErrorMessage());
			} else if(reply.getType() == MessageType.Success){
				ui.displayHome();
			} else {
				ui.showNotification("Error communicating with server");
			}
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
}
