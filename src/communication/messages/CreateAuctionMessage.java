package communication.messages;

import java.time.Instant;
import java.util.Date;

import main.Category;

public class CreateAuctionMessage extends Message {

	private static final long serialVersionUID = 1L;
	private final String title, desription;
	private final Category category;
	private final Date endTime;
	private final int reservePrice;
	
	public CreateAuctionMessage(String clientIP, int sessionNo, String title, String desription, Category category, Date endTime, int reservePrice) throws IllegalArgumentException{
		super(clientIP, sessionNo, MessageType.CreateAuction);
		this.title = title;
		this.desription = desription;
		this.category = category;
		if(!endTime.after(Date.from(Instant.now()))) throw new IllegalArgumentException("Endtime must be in the future");
		this.endTime = endTime;
		if(reservePrice < 0) throw new IllegalArgumentException("Reserve Price cannot be less than 0");
		this.reservePrice = reservePrice;
	}
	
	public String getTitle() {
		return title;
	}

	public String getDesription() {
		return desription;
	}

	public Category getCategory() {
		return category;
	}

	public Date getEndTime() {
		return endTime;
	}

	public int getReservePrice() {
		return reservePrice;
	}
}
