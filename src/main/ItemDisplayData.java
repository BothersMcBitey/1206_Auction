package main;

import java.io.Serializable;
import java.util.Date;

public class ItemDisplayData implements Serializable {

	public final int UIID;
	public String title;
	public String description;
	public Category category;
	public int vendorID; 
	public Date endTime; 
	public int topBid, userTopBid;	
	
	public ItemDisplayData(int uIID, String title, String description, Category category, int vendorID, Date endTime,
			int topBid, int userTopBid) {
		UIID = uIID;
		this.title = title;
		this.description = description;
		this.category = category;
		this.vendorID = vendorID;
		this.endTime = endTime;
		this.topBid = topBid;
		this.userTopBid = userTopBid;
	}
}
