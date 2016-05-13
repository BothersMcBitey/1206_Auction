package server;

import java.util.Map;
import java.util.Random;

import exceptions.AuctionNotOverException;
import exceptions.AuctionTooShortException;
import exceptions.EmptyStringException;
import exceptions.ItemAlreadyExistsException;
import exceptions.ItemDoesNotExistException;
import exceptions.PasswordTooShortException;
import exceptions.ReserveTooLowException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import main.Bid;
import main.Category;
import main.Item;
import main.User;

public class DataManager {

	private DataPersistence storage; //TODO: Construct Storage
	private PrintStream log;
	
	//Map of UIID to Item, for quick look up of objects by ID
	private Map<Integer, Item> activeItems, closedItems;
	private List<User> users;
	
	public DataManager(PrintStream log) {
		activeItems = new HashMap<Integer, Item>();
		closedItems = new HashMap<Integer, Item>();
		users = new ArrayList<User>();
		this.log = log;
		storage = new DataPersistence(log);
	}
	
	public void saveData(){
		Map<Integer, Item> store = new HashMap<Integer, Item>();
		store.putAll(activeItems);
		store.putAll(closedItems);
		storage.WriteOutData(store, users);
	}
	
	public void closeItem(int UIID) throws AuctionNotOverException{
		closeItem(activeItems.get(UIID));
	}
	
	public void closeItem(Item item) throws AuctionNotOverException{
		if(item.getEndTime().after(new Date())) throw new AuctionNotOverException();
		
		synchronized(activeItems){
			activeItems.remove(item.getUIID());
		}
		synchronized(closedItems){
			closedItems.put(item.getUIID(), item);
		}
	}
	
	public Item createNewAuction(String title, String description, Category category, User vendor, Date endTime, int reservePrice) throws EmptyStringException, AuctionTooShortException, ReserveTooLowException{
		int UIID = genUIID();
		return new Item(UIID, title, description, category, vendor, endTime, reservePrice);
	}
	
	public void addItem(Item i) throws ItemAlreadyExistsException{
		if(activeItems.containsKey(i.getUIID())) throw new ItemAlreadyExistsException();
		activeItems.put(i.getUIID(), i);
		i.getVendor().addSellingItem(i.getUIID());
	}
	
	public List<Item> getCategory(Category cat){
		List<Item> allInCategory = getAllItems();
		//use toArray so that removing items from the list doesn't cause problems with iteration
		for(Item i : allInCategory.toArray(new Item[1])){
			if(!i.getCategory().equals(cat)) allInCategory.remove(i);
		}
		return allInCategory;
	}
	
	public List<Item> getAllItemsSince(Date date){
		List<Item> allSince = getAllItems();
		//use toArray so that removing items from the list doesn't cause problems with iteration
		for(Item i : allSince.toArray(new Item[1])){
			if(i.getStartTime().before(date)) allSince.remove(i);
		}
		return allSince;
	}
	
	public List<Item> getAllActiveItems(){
		List<Item> all = new ArrayList<>();
		all.addAll(activeItems.values());
		return all;
	}
	
	public List<Item> getAllItems(){
		List<Item> all = getAllActiveItems();
		all.addAll(closedItems.values());
		return all;
	}
	
	public Item getItem(Integer UIID) throws ItemDoesNotExistException{
		if(activeItems.containsKey(UIID)) return activeItems.get(UIID);
		if(closedItems.containsKey(UIID)) return closedItems.get(UIID);
		throw new ItemDoesNotExistException(UIID);
	}
	
	public User getUser(int UUID) throws UserDoesNotExistException{
		for(User u: users){
			if(u.getUUID() == UUID) return u;
		}
		throw new UserDoesNotExistException(Integer.toString(UUID));
	}
	
	public User getUser(String username) throws UserDoesNotExistException{
		for(User u: users){
			if(u.getScreenName().equals(username)) return u;
		}
		throw new UserDoesNotExistException(username);
	}
	
	public List<Item> getActiveSellingItems(User u){
		List<Item> items = new ArrayList<Item>();
		for(Integer i : u.getSellingItemIDs()){
			Item item = activeItems.get(i);
			if(item != null) items.add(item);
		}
		return items;
	}
	
	public List<Item> getClosedSellingItems(User u){
		List<Item> items = new ArrayList<Item>();
		for(Integer i : u.getSellingItemIDs()){
			Item item = closedItems.get(i);
			if(item != null) items.add(item);
		}
		return items;
	}
	
	public List<Item> getAllSellingItems(User u){
		List<Item> items = getActiveSellingItems(u);
		items.addAll(getClosedSellingItems(u));
		return items;
	}
	
	public List<Item> getActiveBidOnItems(User u){
		List<Item> items = new ArrayList<Item>();
		for(Integer i : u.getBidOnItemIDs()){
			Item item = activeItems.get(i);
			if(item != null) items.add(item);
		}
		return items;
	}
	
	public List<Item> getClosedBidOnItems(User u){
		List<Item> items = new ArrayList<Item>();
		for(Integer i : u.getBidOnItemIDs()){
			Item item = closedItems.get(i);
			if(item != null) items.add(item);
		}
		return items;
	}
	
	public List<Item> getAllBidOnItems(User u){
		List<Item> items = getActiveBidOnItems(u);
		items.addAll(getClosedBidOnItems(u));
		return items;
	}
	
	public synchronized User registerUser(String forename,  String surname, String screenName, char[] passwordHash) throws EmptyStringException, PasswordTooShortException, UserAlreadyExistsException{
		if(forename.trim().equals("") ||surname.trim().equals("") || screenName.trim().equals("")) throw new EmptyStringException();
		if(passwordHash.length < 4) throw new PasswordTooShortException();
		
		for(User u : users){
			if(u.getScreenName().equals(screenName)) throw new UserAlreadyExistsException();
		}
		
		int UUID = genUUID();
		
		User u = new User(UUID, forename, surname, screenName, passwordHash);
		users.add(u);
		return u;
	}
	
	/*
	 * This method gets the persistence class to read in the old session data, and then organises it into the
	 * form required for the server. The reason that users are added first from the items map, and then from the list,
	 * is that each item contains a reference to its vendor and any user that bid on it, and those references don't point
	 * to the corresponding user objects in the user list, so we read the users from the item map first to prevent breaking
	 *  references, and then read in the few users who have no auctions or bids from the users list
	 *  
	 *  I know that this is a ridiculous method, but I ran out of time 
	 */
	public void loadDataFromDisk(){
		Map<Integer, Item> items = storage.ReadInItemData();
		List<User> users = storage.ReadInUserData();
		
		if(items != null){
			//run through the items and:
			for(Item i : items.values()){
				//split items into active and closed
				if(i.getEndTime().after(new Date())){
					activeItems.put(i.getUIID(), i);
				} else {
					closedItems.put(i.getUIID(), i);
				}
				
				//add users who have bid/vendors to this.users
				if(!this.users.contains(i.getVendor())) this.users.add(i.getVendor());
				for(Bid b : i.getBids()){
					if(!this.users.contains(b.getUser())){
						this.users.add(b.getUser());
					}
				}
			}
		}
		
		if(users != null){
			//run through local users and check if any users still need to be added
			for(User u : users){
				if(!this.users.contains(u))
					this.users.add(u);
			}
		}
	}
	
	private int genUUID(){
		Random r = new Random();
		boolean unique = true;
		int i = 0;
		
		do{
			i = r.nextInt(Integer.MAX_VALUE);
			
			for(User u : users){
				unique = i == u.getUUID();
			}
		} while (!unique);
		
		return i;
	}
	
	private int genUIID(){
		Random r = new Random();
		boolean unique = true;
		int i = 0;
		
		do{
			i = r.nextInt(Integer.MAX_VALUE);
			
			for(Item item : getAllItems()){
				unique = i == item.getUIID();
			}
		} while (!unique);
		
		return i;
	}
}
