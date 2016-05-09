package communication.messages;

import main.Item;

public class ViewAuctionDataMessage extends Message {
	
	private static final long serialVersionUID = 1L;
	private final Item item;

	public ViewAuctionDataMessage(String clientIP, int sessionNo, Item item) {
		super(clientIP, sessionNo, MessageType.ViewAuctionData);
		this.item = item;
	}

	public Item getItem() {
		return item;
	}
}
