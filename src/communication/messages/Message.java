package communication.messages;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import main.Category;
import main.ItemDisplayData;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;

	private final Date timestamp;
	private final String clientIP;
	private final int sessionNo;
	private final MessageType type;
	
	private int UUID, UIID, vendorID, reservePrice, userBidValue, topBidValue;
	private String forename, surname, screenName, title, description, vendorScreenName, errorMessage;
	private char[] password;
	private Category category;
	private NotifyType noteType;
	private SortField sortField;
	private boolean searchActive, searchSold, searchTimeStarted, searchByPrice, startedBefore, priceIsGreater, onlyUserIsSelling, onlyUserHasBidOn, lowToHigh;
	private long startTime, endTime, timeLeft;
	private List<ItemDisplayData> searchResults;
	
	public enum NotifyType{
		Closed, Won, Impending, Shutdown
	}
	
	public enum SortField{
		Category, Seller, Title, Price, Time, Sold
	}
	
	public Message(String clientIP, int sessionNo, MessageType type) {
		this.timestamp = Date.from(Instant.now());
		this.clientIP = clientIP.trim();
		this.sessionNo = sessionNo;
		this.type = type;
		searchActive = true;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getClientIP() {
		return clientIP;
	}

	public int getSessionNo() {
		return sessionNo;
	}

	public MessageType getType() {
		return type;
	}
	
	@Override
	public String toString(){
		String s ="";
		s += timestamp.toString() + ", " +
			clientIP + ", " + sessionNo + 
			", " + type.toString();
		return s;
	}

	public int getUUID() {
		return UUID;
	}

	public void setUUID(int uUID) {
		UUID = uUID;
	}

	public int getUIID() {
		return UIID;
	}

	public void setUIID(int uIID) {
		UIID = uIID;
	}

	public int getVendorID() {
		return vendorID;
	}

	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}

	public int getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(int reservePrice) {
		this.reservePrice = reservePrice;
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

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVendorScreenName() {
		return vendorScreenName;
	}

	public void setVendorScreenName(String vendorScreenName) {
		this.vendorScreenName = vendorScreenName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public NotifyType getNoteType() {
		return noteType;
	}

	public void setNoteType(NotifyType noteType) {
		this.noteType = noteType;
	}

	public SortField getSortField() {
		return sortField;
	}

	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}

	public boolean isSearchSold() {
		return searchSold;
	}

	public void setSearchSold(boolean searchSold) {
		this.searchSold = searchSold;
	}

	public boolean isStartedBefore() {
		return startedBefore;
	}

	public void setStartedBefore(boolean startedBefore) {
		this.startedBefore = startedBefore;
	}

	public boolean isPriceIsGreater() {
		return priceIsGreater;
	}

	public void setPriceIsGreater(boolean priceIsGreater) {
		this.priceIsGreater = priceIsGreater;
	}

	public boolean isOnlyUserIsBidding() {
		return onlyUserHasBidOn;
	}

	public void setOnlyUserIsBidding(boolean userHasBidOn) {
		this.onlyUserHasBidOn = userHasBidOn;
	}

	public boolean isLowToHigh() {
		return lowToHigh;
	}

	public void setLowToHigh(boolean lowToHigh) {
		this.lowToHigh = lowToHigh;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(long timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getUserBidValue() {
		return userBidValue;
	}

	public void setUserBidValue(int userBidValue) {
		this.userBidValue = userBidValue;
	}

	public int getTopBidValue() {
		return topBidValue;
	}

	public void setTopBidValue(int topBidValue) {
		this.topBidValue = topBidValue;
	}

	public List<ItemDisplayData> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<ItemDisplayData> searchResults) {
		this.searchResults = searchResults;
	}

	public boolean isSearchActive() {
		return searchActive;
	}

	public void setSearchActive(boolean searchActive) {
		this.searchActive = searchActive;
	}

	public boolean isOnlyUserIsSelling() {
		return onlyUserIsSelling;
	}

	public void setOnlyUserIsSelling(boolean userIsSelling) {
		this.onlyUserIsSelling = userIsSelling;
	}

	public boolean isSearchTimeStarted() {
		return searchTimeStarted;
	}

	public void setSearchTimeStarted(boolean searchTimeStarted) {
		this.searchTimeStarted = searchTimeStarted;
	}

	public boolean isSearchByPrice() {
		return searchByPrice;
	}

	public void setSearchByPrice(boolean searchByPrice) {
		this.searchByPrice = searchByPrice;
	}
}
