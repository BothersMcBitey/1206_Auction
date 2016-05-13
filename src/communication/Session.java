package communication;

import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import communication.messages.Message;
import communication.messages.Message.NotifyType;
import communication.messages.MessageType;
import exceptions.AuctionCreationFailedException;
import exceptions.AuctionTooShortException;
import exceptions.BidTooLowException;
import exceptions.EmptyStringException;
import exceptions.IncorrectPasswordException;
import exceptions.ItemAlreadyExistsException;
import exceptions.ItemDoesNotExistException;
import exceptions.PasswordTooShortException;
import exceptions.RegistrationFailedException;
import exceptions.ReserveTooLowException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserAlreadyTopBidderException;
import exceptions.UserDoesNotExistException;
import javafx.util.Pair;
import main.Bid;
import main.Item;
import main.User;
import server.DataManager;
import server.DataPersistence;

public class Session extends Thread{	
	
	//session info
	private Comms comms;
	private String clientIP;
	private final int sessionNo;
	private PrintStream log;
	private DataManager data;
	
	private boolean loggedIn = false;	
	private User user;
	
	private volatile boolean terminate = false;
	
	public Session(Comms commsModule, int sessionNo, PrintStream log, DataManager data) {
		this.comms = commsModule;
		this.comms.setLog(log);
		this.sessionNo = sessionNo;
		clientIP = commsModule.getIP();
		this.log = log;
		this.data = data;
	}
	
	@Override
	public void run(){
		try {
			Message connectMsg = new Message(clientIP, sessionNo, MessageType.Connect);			
			comms.sendMessage(connectMsg);
		} catch (IOException e) {
			//TODO: handle failed connection
		}
		while(!terminate){
			try {
				Message message = comms.receiveMessage();		
				switch (message.getType()) {
					case LogIn:
						LogIn( message);						
						break;
						
					case Register:
						RegisterUser( message);
						break;
					
					case CreateAuction:
						CreateAuction( message);						
						break;	
					
					case Bid:
						BidOnAuction( message);
						break;
						
					case LogOff:
						LogOff(message);
						break;
						
					case ViewAuctionRequest:
						sendAuctionData(message);
						break;
	
					default: 
						log.println("unknown message type " + message.getType().toString());
						//TODO: work out how to respond to this
						break;
				}
				
			} catch (SocketException e) {
				log.println("client " + clientIP + " disconnected unexpectedly");
				terminate = true;
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void sendAuctionData(Message message) throws IOException{
		Item item = null;
		try {
			item = data.getItem(message.getUIID());
			
			Message reply = new Message(clientIP, sessionNo, MessageType.ViewAuctionData);
			reply.setUIID(item.getUIID());
			reply.setTitle(item.getTitle());
			reply.setDescription(item.getDescription());
			reply.setCategory(item.getCategory());
			reply.setVendorID(item.getVendor().getUUID());
			reply.setEndTime(item.getEndTime().getTime());
			reply.setTopBidValue(item.getTopBid().getValue());
			int userBidValue = getUserTopBid(item, user);
			reply.setUserBidValue(userBidValue);
			
			log.println("Sent " + user.getUUID() + " details of item " + item.getUIID());
			comms.sendMessage(reply);		
		} catch (ItemDoesNotExistException e) {
			sendError(" tried to view non-existant auction " + message.getUIID(), "Item doesn't exist");
		}
	}

	private int getUserTopBid(Item item, User user) {
		int bid = 0;
		for(Bid b : item.getBids()){
			if(b.getUser() == user){
				bid = Math.max(b.getValue(), bid);
			}
		}
		return bid;
	}

	private void LogOff(Message message) throws IOException {	
		log.println("Succesfully logged off user " + user.getUUID());
		comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));
		user = null;
		loggedIn = false;
	}

	private void LogIn(Message msg) throws IOException{
		User logInUser = null;
		//get user info
		try{
			logInUser = data.getUser(msg.getScreenName());
			//test password
			try{
				if(logInUser.getPasswordHash().length != msg.getPassword().length){
					throw new Exception();
				}

				for(int i = 0; i < msg.getPassword().length; i++){
					if(msg.getPassword()[i] != logInUser.getPasswordHash()[i])
						throw new Exception();
				}
				
				//log in okay
				user = logInUser;
				log.println("Succesfully logged in user " + user.getUUID());
				comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));		
				loggedIn = true;
			} catch (Exception e){
				log.println(msg.getClientIP() + " tried to log in user " + logInUser.getScreenName() + " with incorrect password");
				Message error = new Message(clientIP, sessionNo, MessageType.Failure);
				error.setErrorMessage("incorrect password");
				comms.sendMessage(error);
			}
		} catch (UserDoesNotExistException e){
			log.println(msg.getClientIP() + " tried to log in a non existent user");
			Message error = new Message(clientIP, sessionNo, MessageType.Failure);
			error.setErrorMessage("user does not exist");
			comms.sendMessage(error);
		}			
	}
	
	private void RegisterUser(Message msg) throws IOException{
		try {
			user = data.registerUser(msg.getForename(), msg.getSurname(), msg.getScreenName(), msg.getPassword());
			log.println("Succesfully registered user " + user.getUUID());
			comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));
			loggedIn = true;
		} catch (EmptyStringException e) {
			log.println(msg.getClientIP() + " tried to register a user with blank fields");
			Message error = new Message(clientIP, sessionNo, MessageType.Failure);
			error.setErrorMessage("username, forename, and surname must not be blank");
			comms.sendMessage(error);
		} catch (PasswordTooShortException e) {
			log.println(msg.getClientIP() + " tried to register user " + msg.getScreenName() + " with a short password");
			Message error = new Message(clientIP, sessionNo, MessageType.Failure);
			error.setErrorMessage("Password too short, must be at least 4 characters long");
			comms.sendMessage(error);
		} catch (UserAlreadyExistsException e) {
			log.println(msg.getClientIP() + " tried to register user " + msg.getScreenName() + ", username already in use");
			Message error = new Message(clientIP, sessionNo, MessageType.Failure);
			error.setErrorMessage("User already exists");
			comms.sendMessage(error);
		}		
	}
	
	private void CreateAuction(Message msg) throws IOException{
		//check logged in, just in case
		//This is because there's no checking that connected users are
		//actually using the client from this project, not just imitating it
		if(loggedIn){		
			try {
				data.addItem(data.createNewAuction(msg.getTitle(), msg.getDescription(), msg.getCategory(), user, new Date(msg.getEndTime()), msg.getReservePrice()));
				//send ok
				log.println("Succesfully created auction " + msg.getTitle());
				comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));
			} catch (ItemAlreadyExistsException e) {
				//this should never be reached, but just in case
				log.println(msg.getClientIP() + " tried to create auction that already exists");
				Message error = new Message(clientIP, sessionNo, MessageType.Failure);
				error.setErrorMessage("Auction already exists");
				comms.sendMessage(error);
			} catch (EmptyStringException e) {
				log.println(msg.getClientIP() + " tried to create auction with empty fields");
				Message error = new Message(clientIP, sessionNo, MessageType.Failure);
				error.setErrorMessage("Fields must not be empty");
				comms.sendMessage(error);
			} catch (AuctionTooShortException e) {
				log.println(msg.getClientIP() + " tried to create auction with insufficient lifetime");
				Message error = new Message(clientIP, sessionNo, MessageType.Failure);
				error.setErrorMessage("Auction must last at least 1 hour");
				comms.sendMessage(error);
			} catch (ReserveTooLowException e) {
				log.println(msg.getClientIP() + " tried to create auction with a negative reserve");
				Message error = new Message(clientIP, sessionNo, MessageType.Failure);
				error.setErrorMessage("reserve must be at least 0");
				comms.sendMessage(error);
			}			
		} else {
			log.println(msg.getClientIP() + " tried to create auction without logging in");
			Message error = new Message(clientIP, sessionNo, MessageType.Failure);
			error.setErrorMessage("Must be logged in to create auction");
			comms.sendMessage(error);
		}		
	}
	
	private void BidOnAuction(Message msg) throws IOException{
		//check logged in, just in case
		//This is because there's no checking that connected users are
		//actually using the client from this project, not just imitating it
		if(loggedIn){		
			//get the item
			Item item = null;
			try {
				item = data.getItem(msg.getUIID());
				//bid on it
				try {
					item.PlaceBid(user, msg.getUserBidValue());
					//send ok
					log.println("Succesfully bid on auction " + msg.getUIID());
					comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));	
				} catch (BidTooLowException e) {
					sendError("tried place a low bid", "bid must be greater than reserve and top bid");
				} catch (UserAlreadyTopBidderException e) {
					sendError("tried to double bid", "You are already the top bidder");
				}
			} catch (ItemDoesNotExistException e) {
				log.println(msg.getClientIP() + " tried to bid on nonexistant auction");
				Message error = new Message(clientIP, sessionNo, MessageType.Failure);
				error.setErrorMessage("Item does not exist");
				comms.sendMessage(error);
			}			
		} else {
			sendError("tried to bid on auction without loggin in", "must be logged in to bid");
		}
	}
	
	private void ViewAuction(Message msg)throws IOException{
		
	}
	
	//TODO: replace error messages with this method call
	private void sendError(String logErr, String clientErr) throws IOException{
		log.println(new Date().toString() + ", " + sessionNo + ", " + logErr);
		Message error = new Message(clientIP, sessionNo, MessageType.Failure);
		error.setErrorMessage(clientErr);
		comms.sendMessage(error);
	}
	
	/**
	 * Ends the session in a clean way, making sure the session is in a state where
	 * ending won't cause any security/consistency issues.
	 * @throws IOException 
	 */
	public void endSession() throws IOException{
		Message msg = new Message(clientIP, sessionNo, MessageType.Notification);
		msg.setNoteType(NotifyType.Shutdown);
		msg.setTimeLeft(0);
		comms.sendMessage(msg);
		comms.close();
		terminate = true;
	}
	
	/**
	 * Used to notify connected users of impending session shutdown, so they can
	 * finish what they're doing beforehand
	 * @param time
	 * @throws IOException 
	 */
	public void notifyEndSession(Date time) throws IOException{
		long timeLeft = time.getTime() - Date.from(Instant.now()).getTime();
		Message msg = new Message(clientIP, sessionNo, MessageType.Notification);
		msg.setNoteType(NotifyType.Shutdown);
		msg.setTimeLeft(timeLeft);
		comms.sendMessage(msg);
	}
	
	public int getUserID(){
		return user.getUUID();
	}
	
	public int getSessionNo(){
		return sessionNo;
	}

	public PrintStream getLog() {
		return log;
	}

	public void setLog(PrintStream log) {
		this.log = log;
	}
}
