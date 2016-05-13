package main;

import java.util.ArrayList;

public class User {

	private final int UUID;	
	private String forename;
	private String surname;
	private String screenName;
	private char[] passwordHash;
	private ArrayList<Integer> bidOnItemIDs;
	private ArrayList<Integer> sellingItemIDs;
	
	public User(int UUID, String forename, String surname, String screenName, char[] passwordHash){
		this.UUID = UUID;
		this.forename = forename;
		this.surname = surname;
		this.screenName = screenName;
		this.passwordHash = passwordHash;
		bidOnItemIDs = new ArrayList<>();
		sellingItemIDs = new ArrayList<>();
	}
	
	public void addSellingItem(Integer UIID){
		if(!sellingItemIDs.contains(UIID)) sellingItemIDs.add(UIID);
	}
	
	public void addBidOnItem(Integer UIID){
		if(!bidOnItemIDs.contains(UIID)) bidOnItemIDs.add(UIID);
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public char[] getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(char[] passwordHash) {
		this.passwordHash = passwordHash;
	}

	public int getUUID() {
		return UUID;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public ArrayList<Integer> getBidOnItemIDs() {
		return bidOnItemIDs;
	}

	public void setBidOnItemIDs(ArrayList<Integer> bidOnItemIDs) {
		this.bidOnItemIDs = bidOnItemIDs;
	}

	public ArrayList<Integer> getSellingItemIDs() {
		return sellingItemIDs;
	}

	public void setSellingItemIDs(ArrayList<Integer> sellingItemIDs) {
		this.sellingItemIDs = sellingItemIDs;
	}
}
