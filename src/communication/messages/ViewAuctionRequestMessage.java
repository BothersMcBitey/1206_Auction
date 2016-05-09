package communication.messages;

public class ViewAuctionRequestMessage extends Message {
	
	private static final long serialVersionUID = 1L;	
	private final int ID;
	
	public ViewAuctionRequestMessage(String clientIP, int sessionNo,int iD) {
		super(clientIP, sessionNo, MessageType.ViewAuctionRequest);
		this.ID = iD;
	}

	public int getID() {
		return ID;
	}
}
