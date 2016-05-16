package main.comparators;

import java.util.Comparator;

import main.Item;

public class PriceComparator implements Comparator<Item>{
	@Override
	public int compare(Item arg0, Item arg1){
		return arg0.getTopBid().getValue() - arg1.getTopBid().getValue();
	}
}