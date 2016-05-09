package communication.messages;

import java.util.ArrayList;

import main.Item;

public class SearchAuctionsDataMessage extends Message {

	private static final long serialVersionUID = 1L;	
	private final ArrayList<Item> results;
	
	public SearchAuctionsDataMessage(String clientIP, int sessionNo, ArrayList<Item> results) {
		super(clientIP, sessionNo, MessageType.SearchAuctionData);
		this.results = results;
	}

	public ArrayList<Item> getResults() {
		return results;
	}
}
