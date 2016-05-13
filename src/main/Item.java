package main;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Item holds all the data for the auction of a single item.
 * 
 * @author Callum
 *
 */
public class Item implements Serializable{
		
	private static final long serialVersionUID = 1L;
	public static final long MinimumAuctionLength = 1000 * 60 * 60; //1hour
	
	private final int UIID;
	private final String title;
	private String description;
	private final Category category;
	private final int vendorID; 
	private final Date startTime; 
	private final Date endTime;
	private final int reservePrice; 
	private ArrayList<Bid> bids;
	
	public Item(int UIID, String title, String description, Category category, int vendorID, Date startTime, Date endTime, int reservePrice, ArrayList<Bid> bids) {
		this.UIID = UIID;
		this.title = title;
		this.description = description;
		this.category = category;
		this.vendorID = vendorID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reservePrice = reservePrice;
		this.bids = bids;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public int getVendorID() {
		return vendorID;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public int getReservePrice() {
		return reservePrice;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}

	public int getUIID() {
		return UIID;
	}
	
	public boolean isOpen(){
		return (endTime.getTime() > Date.from(Instant.now()).getTime());
	}
}
