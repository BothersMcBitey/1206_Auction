package main;

import java.io.Serializable;

public class Stats implements Serializable{

	private static final long serialVersionUID = 1L;
	private final int totalMoneyRaised, currentTotalBidValue, HighestCostItem, activeAuctions, totalAuctions;

	public Stats(int totalMoneyRaised, int currentTotalBidValue, int highestCostItem, int activeAuctions, int totalAuctions) {
		this.totalMoneyRaised = totalMoneyRaised;
		this.currentTotalBidValue = currentTotalBidValue;
		HighestCostItem = highestCostItem;
		this.activeAuctions = activeAuctions;
		this.totalAuctions = totalAuctions;
	}

	public int getTotalMoneyRaised() {
		return totalMoneyRaised;
	}

	public int getCurrentTotalBidValue() {
		return currentTotalBidValue;
	}

	public int getHighestCostItem() {
		return HighestCostItem;
	}

	public int getActiveAuctions() {
		return activeAuctions;
	}

	public int getTotalAuctions() {
		return totalAuctions;
	}
}
