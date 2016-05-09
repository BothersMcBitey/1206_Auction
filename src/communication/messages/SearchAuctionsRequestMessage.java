package communication.messages;

import main.Category;

public class SearchAuctionsRequestMessage extends Message {
	
	public enum SortField{
		Category, Seller, Title, Price, Time, Sold
	}

	private static final long serialVersionUID = 1L;
	//search for
	private final Category category;
	private final String seller, title, description;
	private final int itemID;
	private final long timeLeft;
	private final int price;
	private final boolean isSold, timeIsGreater, priceIsGreater, userHasBidOn;
	//sort by
	private final boolean isLowToHigh;
	private final SortField sortBy;
	
	public SearchAuctionsRequestMessage(String clientIP, int sessionNo, Category category, String seller,
			String title, String description, int iD, long timeLeft, int price, boolean isSold, boolean timeIsGreater,
			boolean priceIsGreater, boolean userHasBidOn, boolean isLowToHigh, SortField sortBy) throws IllegalArgumentException{
		super(clientIP, sessionNo, MessageType.SearchAuctionsRequest);
		this.category = category;
		this.seller = seller.trim();
		this.title = title.trim();
		this.description = description.trim();
		this.itemID = iD;
		if(timeLeft < 0) throw new IllegalArgumentException("Time left cannot be less than 0");
		this.timeLeft = timeLeft;
		if(price < 0) throw new IllegalArgumentException("Price cannot be less than 0");
		this.price = price;
		this.isSold = isSold;
		this.timeIsGreater = timeIsGreater;
		this.priceIsGreater = priceIsGreater;
		this.userHasBidOn = userHasBidOn;
		this.isLowToHigh = isLowToHigh;
		this.sortBy = sortBy;
	}

	public String getSeller() {
		return seller;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public int getID() {
		return itemID;
	}

	public long getTimeLeft() {
		return timeLeft;
	}

	public int getPrice() {
		return price;
	}

	public Category getCategory() {
		return category;
	}

	public boolean isSold() {
		return isSold;
	}

	public boolean isTimeIsGreater() {
		return timeIsGreater;
	}

	public boolean isPriceIsGreater() {
		return priceIsGreater;
	}

	public boolean isUserHasBidOn() {
		return userHasBidOn;
	}

	public boolean isLowToHigh() {
		return isLowToHigh;
	}

	public SortField getSortBy() {
		return sortBy;
	}
}
