package main;

public class Bid {

	private final User user;
	private final int value;
	
	public Bid(User user, int value) throws IllegalArgumentException{
		this.user = user;
		if(value < 0) throw new IllegalArgumentException("bid cannot be less than 0");
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public int getValue() {
		return value;
	}
}
