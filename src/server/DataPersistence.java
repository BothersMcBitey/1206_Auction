package server;

import java.util.ArrayList;
import java.util.Date;

import exceptions.AuctionCreationFailedException;
import exceptions.RegistrationFailedException;
import exceptions.UserDoesNotExistException;
import main.Category;
import main.Item;
import main.User;

public class DataPersistence {

	public DataPersistence() {
		// TODO Auto-generated constructor stub
	}
	
	public char[] getPassword(String userName) throws UserDoesNotExistException{
		char[] pass = new char[] {};
		
		return pass;
	}
	
	public User getUser(String userName) throws UserDoesNotExistException{
		User u = null;
		
		return u;
	}
	
	public boolean userExists(String userName){
		return false;
	}
	
	public User registerUser(String forename, String surname, String userName, char[] password) throws RegistrationFailedException{
		
		return null;
	}
	
	public Item createAuction(int vendorID, String title, String description, Category category, Date endTime, int reservePrice) throws AuctionCreationFailedException{
		return null;
	}
	
	public Item getItem(int UIID){
		return null;
	}
	
	public Date getItemUpdateTime(int UIID){
		return null;
	}

	public Date getSellerUpdateTime(int UUID) {
		return null;
	}

	public ArrayList<Item> getSellerItems(int uuid) {
		return null;
	}

	public Date getBidderUpdateTime(int uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Item> getBidderItems(int uuid) {
		// TODO Auto-generated method stub
		return null;
	}
}
