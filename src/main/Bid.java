package main;

import java.io.Serializable;

public class Bid implements Serializable{

	private static final long serialVersionUID = 1L;
	private final int UUID;
	private final int value;
	
	public Bid(int UUID, int value) throws IllegalArgumentException{
		this.UUID = UUID;
		if(value < 0) throw new IllegalArgumentException("bid cannot be less than 0");
		this.value = value;
	}

	public int getUserID() {
		return UUID;
	}

	public int getValue() {
		return value;
	}
}
