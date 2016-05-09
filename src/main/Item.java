package main;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Item holds all the data for the auction of a single item.
 * 
 * All fields in Item are final, to allow Items to be sent to clients without them being able to modify fields.
 * The downside of this is every time a bid is made or the owner modifies a filed, every session holding that item
 * has to request the new data from the database and re-construct the object. 
 * @author Callum
 *
 */
public class Item implements Serializable{
		
	private static final long serialVersionUID = 1L;
	public static final long MinimumAuctionLength = 1000 * 60 * 60; //1hour
	
	private final int UIID; //Actually never changes
	private final String title;
	private final String description;
	private final Category category;
	private final int vendorID; //also never changes
	private final Date startTime; //never changes
	private final Date endTime; //never changes
	private final int reservePrice; // can only be lowered
	private final ArrayList<Bid> bids;
	
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
