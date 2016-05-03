package main;

import java.util.ArrayList;
import java.util.Iterator;

public class SynchronizedList<E>{

	private ArrayList<E> list;
	
	public SynchronizedList() {
		list = new ArrayList<E>();
	}
	
	/**
	 * Nothing will be added if e is null
	 * @param e
	 */
	public synchronized void add(E e){
		if(e != null) list.add(e);
	}
	
	public synchronized E get(int i){
		return list.get(i);
	}
	
	public synchronized E removeNext(){
		return list.remove(0);
	}
	
	public synchronized Iterator<E> iterator(){
		return list.iterator();
	}		
}
