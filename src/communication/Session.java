package communication;

import java.io.IOException;
import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import communication.messages.BidMessage;
import communication.messages.CreateAuctionMessage;
import communication.messages.FailureMessage;
import communication.messages.LogInMessage;
import communication.messages.Message;
import communication.messages.MessageType;
import communication.messages.NotificationMessage;
import communication.messages.NotificationMessage.NotifyType;
import communication.messages.RegisterMessage;
import exceptions.AuctionCreationFailedException;
import exceptions.IncorrectPasswordException;
import exceptions.RegistrationFailedException;
import exceptions.UserDoesNotExistException;
import javafx.util.Pair;
import main.Item;
import main.User;
import server.DataPersistence;

public class Session extends Thread{	
	
	//session info
	private Comms comms;
	private String clientIP;
	private final int sessionNo;
	private PrintStream log;
	private DataPersistence dataAccess;
	
	//user info
	private User user;
	private boolean loggedIn = false;
	/*
	 * The maps below are used to limit database access. By storing the information requested with a time stamp,
	 * the session can simply check the data persistence class to see if the data has been modified since the last 
	 * database access. If not, the session simply returns the currently stored data, preventing unnecessary calls to
	 * the database, and hopefully speeding up the system (albeit at the cost of memory use). The two ItemListUpdate
	 * objects are to keep track of when the session last checked for additional entries to the maps; 
	 */
	//items being sold by user
	private HashMap<Item, Date> userItems; //mapping representing when each item was last updated from db
	private Date userItemListUpdate; // represents when the list of the user's items was last updated, needed in case the user has 2 sessions active
	//items viewed by the user
	private HashMap<Item, Date> recentItems; //mapping representing when each item was last updated from db
	//items user bid on
	private HashMap<Item, Date> bidItems; //mapping representing when each item was last updated from db
	private Date bidItemListUpdate; //represents when the list of the user's bids was last updated, needed in case the user has 2 sessions active
	
	
	
	public Session(Comms commsModule, int sessionNo, PrintStream log, DataPersistence dataAccess) {
		this.comms = commsModule;
		this.comms.setLog(log);
		this.sessionNo = sessionNo;
		clientIP = commsModule.getIP();
		this.log = log;
		this.dataAccess = dataAccess;
		userItems = new HashMap<Item, Date>();
		recentItems = new HashMap<Item, Date>();
		bidItems = new HashMap<Item, Date>();
		userItemListUpdate = null;
		bidItemListUpdate = null;
	}
	
	@Override
	public void run(){
		try {
			Message connectMsg = new Message(clientIP, sessionNo, MessageType.Connect);			
			comms.sendMessage(connectMsg);
		} catch (IOException e) {
			//TODO: handle failed connection
		}
		while(true){
			try {
				Message message = comms.receiveMessage();		
				switch (message.getType()) {
					case LogIn:
						LogIn((LogInMessage) message);						
						break;
						
					case Register:
						RegisterUser((RegisterMessage) message);
						break;
					
					case CreateAuction:
						CreateAuction((CreateAuctionMessage) message);						
						break;	
					
					case Bid:
						BidOnAuction((BidMessage) message);
						break;
	
					default: 
						log.println("unknown message type " + message.getType().toString());
						//TODO: work out how to respond to this
						break;
				}
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void LogIn(LogInMessage msg) throws IOException{
		char[] password = null; 
		try {
			//try retrieve the password(hash) from the database
			password = dataAccess.getPassword(msg.getUsername());
			try{
				//check passwords match
				if(password.length != msg.getPassword().length){
					throw new IncorrectPasswordException();
				} else {
					for(int i = 0; i < password.length; i++){
						if(password[i] != msg.getPassword()[i]){
							throw new IncorrectPasswordException();
						}
					}
				}
				try {
					//try to retrieve user info
					user = dataAccess.getUser(msg.getUsername());
					log.println("Succesfully logged in user " + user.getUUID());
					comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));
					loggedIn = true;
				} catch (UserDoesNotExistException e) {
					log.println("error logging in user " + msg.getUsername() + ", user was in database but now isn't");
					comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Error logging in, try again later or contact support"));
				}
			} catch (IncorrectPasswordException e){
				log.println(msg.getClientIP() + " tried to log in user " + msg.getUsername() + " with incorrect password");
				comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Incorrect password"));
			}
		} catch (UserDoesNotExistException e) {
			log.println(msg.getClientIP() + " tried to log in with unregistered user " + msg.getUsername());					
			comms.sendMessage(new FailureMessage(clientIP, sessionNo, "User does not exist"));
		}	
	}
	
	private void RegisterUser(RegisterMessage msg) throws IOException{
		//check user doesn't already exist
		if(!dataAccess.userExists(msg.getUserName())){
			//hash the password
			char[] password = Security.hashPassword(msg.getPassword());
			try {
				//try registering the user
				user = dataAccess.registerUser(msg.getForename(), msg.getSurname(), msg.getUserName(), password);
				log.println("Succesfully registered user " + user.getUUID());
				comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));
				loggedIn = true;
			} catch (RegistrationFailedException e) {
				log.println("Error registering user " + msg.getUserName() + ", " + e.getMessage());
				comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Error registering user, try again later or contact support"));
			}
		} else {
			log.println(msg.getClientIP() + " tried to register user " + msg.getUserName() + ", username already in use");
			comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Username already in use"));
		}
		
	}
	
	private void CreateAuction(CreateAuctionMessage msg) throws IOException{
		if(loggedIn){		
			//check auction end time is sufficiently far in the future
			if((msg.getEndTime().getTime() - Date.from(Instant.now()).getTime()) >= Item.MinimumAuctionLength){
				//check reserve is valid
				if(msg.getReservePrice() >= 0){
					//check title and description not blank
					if(!msg.getTitle().trim().equals("") && !msg.getDesription().trim().equals("")){
						try {
							//add auction to database and session's userAuction list
							Item item = dataAccess.createAuction(user.getUUID(), msg.getTitle().trim(), msg.getDesription().trim(), msg.getCategory(), msg.getEndTime(), msg.getReservePrice());
							userItems.put(item, Date.from(Instant.now()));
							log.println("Successfully created auction " + msg.getTitle().trim() + " for user " + user.getUUID());
							comms.sendMessage(new Message(clientIP, sessionNo, MessageType.Success));
						} catch (AuctionCreationFailedException e) {
							log.println("Failed to create auction " + msg.getTitle() + " for user " + user.getUUID());
							comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Error creating auction, try again later or contact support"));
						}						
					} else {
						log.println(msg.getClientIP() + " tried to create auction with blank title or description");
						comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Title and Description must not be blank (whitespace doesn't count)"));
					}
				} else {
					log.println(msg.getClientIP() + " tried to create auction with negative reserve");
					comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Reserve must be 0 or greater"));
				}
			} else {
				log.println(msg.getClientIP() + " tried to create auction ending to soon");
				long minEndDate = Date.from(Instant.now()).getTime() + Item.MinimumAuctionLength;
				comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Auction must end after " + new Date(minEndDate).toString()));
			}
		} else {
			log.println(msg.getClientIP() + " tried to create auction without loggin in");
			comms.sendMessage(new FailureMessage(clientIP, sessionNo, "Must be logged in to create Auction"));
		}
	}
	
	private void BidOnAuction(BidMessage msg){
		//get item
//		Item item =Item 
//		//check auction is still open
//		if()
	}
	
	/**
	 * checks the list of user items is up to date and checks for new entries
	 */
	private void checkUserItemsUpToDate(){
		checkItemsUpToDate(userItems);
		long lastUpdate = dataAccess.getSellerUpdateTime(user.getUUID()).getTime();
		if(userItemListUpdate.getTime() < lastUpdate){
			ArrayList<Item> newItems = dataAccess.getSellerItems(user.getUUID());
			for(Item i : newItems){
				if(!userItems.containsKey(i)){
					userItems.put(i, new Date(lastUpdate));
				}
			}
		}
	}
	
	/**
	 * Checks the list of bids is up to date and checks for new entries
	 */
	private void checkUserBidsUpToDate(){
		checkItemsUpToDate(bidItems);
		long lastUpdate = dataAccess.getBidderUpdateTime(user.getUUID()).getTime();
		if(bidItemListUpdate.getTime() < lastUpdate){
			ArrayList<Item> newItems = dataAccess.getBidderItems(user.getUUID());
			for(Item i : newItems){
				if(!bidItems.containsKey(i)){
					bidItems.put(i, new Date(lastUpdate));
				}
			}
		}
	}
	
	/**
	 * runs through a map of items to dates and checks each one is up to date
	 * @param items
	 */
	private void checkItemsUpToDate(HashMap<Item, Date> items){
		for(Item i : items.keySet()){
			Pair<Item, Date> entry = UpdateItem(i, items.get(i));
			items.put(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * This method separated from checkItemsUpToDate in case you need to only check
	 * one item is up to date.
	 * @param item
	 * @param date
	 * @return
	 */
	private Pair<Item, Date> UpdateItem(Item item, Date date){		
		long localUpdate = date.getTime();
		long lastUpdate = dataAccess.getItemUpdateTime(item.getUIID()).getTime();
		
		if(localUpdate < lastUpdate){
			item = dataAccess.getItem(item.getUIID());
		}
		
		return new Pair<Item, Date>(item, new Date(lastUpdate));
	}
	
	/**
	 * Ends the session in a clean way, making sure the session is in a state where
	 * ending won't cause any security/consistency issues.
	 */
	public void endSession(){
		//TODO: write end session method
	}
	
	/**
	 * Used to notify connected users of impending session shutdown, so they can
	 * finish what they're doing beforehand
	 * @param time
	 * @throws IOException 
	 */
	public void notifyEndSession(Date time) throws IOException{
		long timeLeft = time.getTime() - Date.from(Instant.now()).getTime();
		comms.sendMessage(new NotificationMessage(clientIP, sessionNo, NotifyType.Shutdown, null, timeLeft));
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
