package communication.messages;

import java.util.ArrayList;

import main.Bid;
import main.Item;

public class ViewUserDataMessage extends Message {

	private static final long serialVersionUID = 1L;
	private final String screenName, forename, surname;
	private final ArrayList<Item> auctions;
	private final ArrayList<Bid> bids;
	
	public ViewUserDataMessage(String clientIP, int sessionNo, String screenName, String forename,
			String surname, ArrayList<Item> auctions, ArrayList<Bid> bids) {
		super(clientIP, sessionNo, MessageType.ViewUserData);
		this.screenName = screenName.trim();
		this.forename = forename.trim();
		this.surname = surname.trim();
		this.auctions = auctions;
		this.bids = bids;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public ArrayList<Item> getAuctions() {
		return auctions;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}
}
