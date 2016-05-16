package main.comparators;

import java.util.Comparator;

import main.Item;

public class StartTimeComparator implements Comparator<Item>{
	@Override
	public int compare(Item arg0, Item arg1){
		return arg0.getStartTime().compareTo(arg1.getStartTime());
	}
}