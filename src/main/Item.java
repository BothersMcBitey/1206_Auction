package main;

import java.util.ArrayList;
import java.util.Date;

public class Item {
	
	private final int UIID;
	private String title;
	private String description;
	private Category category;
	private int vendorID;
	private Date startTime;
	private Date endTime;
	private float reservePrice;
	private ArrayList<Bid> bids;
	
	public Item(int UIID, String title, String description, Category category, int vendorID, Date startTime, Date endTime, float reservePrice, ArrayList<Bid> bids) {
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

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getVendorID() {
		return vendorID;
	}

	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public float getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(float reservePrice) {
		this.reservePrice = reservePrice;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}

	public int getUIID() {
		return UIID;
	}
}
