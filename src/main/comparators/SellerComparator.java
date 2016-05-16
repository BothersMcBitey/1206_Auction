package main.comparators;

import java.util.Comparator;

import main.Item;

public class SellerComparator implements Comparator<Item>{
	@Override
	public int compare(Item arg0, Item arg1) {
		String seller1Name = arg0.getVendor().getScreenName();
		String seller2Name = arg1.getVendor().getScreenName();
		return seller1Name.compareTo(seller2Name);
	}		
}