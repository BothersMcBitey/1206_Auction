package main.comparators;

import java.util.Comparator;

import main.Item;

public class CategoryComparator implements Comparator<Item>{
	@Override
	public int compare(Item arg0, Item arg1) {
		return arg0.getCategory().compareTo(arg1.getCategory());
	}
}