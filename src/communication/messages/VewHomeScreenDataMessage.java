package communication.messages;

import java.util.ArrayList;

import main.Item;
import main.Stats;

public class VewHomeScreenDataMessage extends Message {

	private static final long serialVersionUID = 1L;
	private final Item primeHighlightItem, secondHighlightItem;
	private final ArrayList<Item> usersAuctions;
	private final ArrayList<Item> usersBids;
	private final Stats siteStats;
	
	public VewHomeScreenDataMessage(String clientIP, int sessionNo, Item primeHighlightItem,
			Item secondHighlightItem, ArrayList<Item> usersAuctions, ArrayList<Item> usersBids, Stats siteStats) {
		super(clientIP, sessionNo, MessageType.ViewHomeScreenData);
		this.primeHighlightItem = primeHighlightItem;
		this.secondHighlightItem = secondHighlightItem;
		this.usersAuctions = usersAuctions;
		this.usersBids = usersBids;
		this.siteStats = siteStats;
	}

	public Item getPrimeHighlightItem() {
		return primeHighlightItem;
	}

	public Item getSecondHighlightItem() {
		return secondHighlightItem;
	}

	public ArrayList<Item> getUsersAuctions() {
		return usersAuctions;
	}

	public ArrayList<Item> getUsersBids() {
		return usersBids;
	}

	public Stats getSiteStats() {
		return siteStats;
	}
}
