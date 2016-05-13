package server;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Item;
import main.User;

public class DataManager {

	//Map of UIID to Item, for quick look up of objects by ID
	private Map<Integer, Item> activeItems, closedItems;
	private List<User> users;
	
	public DataManager() {
		activeItems = new HashMap<Integer, Item>();
		closedItems = new HashMap<Integer, Item>();
		users = new ArrayList<User>();
	}
	
	public List<Item> getActiveBidOnItems(User u){
		for(Integer i : u.getBidOnItemIDs()){
			
		}
		return null;
	}
}
