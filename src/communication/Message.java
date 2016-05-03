package communication;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;

	private String s;
	
	public Message(String s){
		this.s = s;
	}
	
	public String getS(){
		return s;
	}
}
