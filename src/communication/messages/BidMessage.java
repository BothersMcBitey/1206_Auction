package communication.messages;

public class BidMessage extends Message {

	private static final long serialVersionUID = 1L;
	private final int itemID;
	private final int price;
	
	public BidMessage(String clientIP, int sessionNo, int itemID, int price) throws IllegalArgumentException{
		super(clientIP, sessionNo, MessageType.Bid);
		this.itemID = itemID;
		if(price < 0) throw new IllegalArgumentException("Bid can't be less than 0");
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public int getItemID() {
		return itemID;
	}
}
