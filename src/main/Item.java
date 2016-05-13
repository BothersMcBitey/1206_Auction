package main;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import exceptions.AuctionTooShortException;
import exceptions.BidTooLowException;
import exceptions.EmptyStringException;
import exceptions.ReserveTooLowException;
import exceptions.UserAlreadyTopBidderException;

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
	private final User vendor; 
	private final Date startTime; 
	private final Date endTime;
	private final int reservePrice; 
	private ArrayList<Bid> bids;
	
	public Item(int UIID, String title, String description, Category category, User vendor, Date endTime, int reservePrice) throws EmptyStringException, AuctionTooShortException, ReserveTooLowException {
		this.UIID = UIID;		
		if(title.trim().equals("")) throw new EmptyStringException();
		this.title = title;
		if(description.trim().equals("")) throw new EmptyStringException();
		this.description = description;
		this.category = category;
		this.vendor = vendor;
		this.startTime = new Date();
		long length = endTime.getTime() - startTime.getTime();
		if(length < MinimumAuctionLength) throw new AuctionTooShortException();
		this.endTime = endTime;
		if(reservePrice < 0) throw new ReserveTooLowException();
		this.reservePrice = reservePrice;
		this.bids = new ArrayList<>();;		
	}
	
	public synchronized void PlaceBid(User user, int value) throws BidTooLowException, UserAlreadyTopBidderException{		
		if(value < reservePrice) throw new BidTooLowException();
		
		Bid topBid = getTopBid();
		
		if(value <= topBid.getValue()) throw new BidTooLowException();
		
		if(topBid.getUser() == user) throw new UserAlreadyTopBidderException(); 
		
		bids.add(new Bid(user, value));
		user.addBidOnItem(UIID);
	}
	
	public synchronized Bid getTopBid(){
		int value = 0;
		User user = null;
		for(Bid b : bids){
			if(value < b.getValue()){
				value = b.getValue();
				user = b.getUser();
			}
		}
		return new Bid(user, value);
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

	public User getVendor() {
		return vendor;
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
