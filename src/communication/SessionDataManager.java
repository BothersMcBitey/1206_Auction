package communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javafx.util.Pair;
import main.Item;
import main.User;
import server.DataPersistence;

public class SessionDataManager {

	private DataPersistence dataAccess;
	private User user;
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
	
	public SessionDataManager(DataPersistence dataAccess) {
		this.dataAccess = dataAccess;
		userItems = new HashMap<Item, Date>();
		recentItems = new HashMap<Item, Date>();
		bidItems = new HashMap<Item, Date>();
		userItemListUpdate = null;
		bidItemListUpdate = null;
	}
	
	public void setUser(User user)
	
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
}
