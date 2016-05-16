package main.comparators;

import java.util.Comparator;

import main.Item;

public class SoldComparator implements Comparator<Item>{
	@Override
	public int compare(Item arg0, Item arg1){
		if(arg0.isOpen()){
			if(arg1.isOpen()) return 0;
			else return 1;
		} else{
			if(arg1.isOpen()) return -1;
			else return 0;
		}
	}
}