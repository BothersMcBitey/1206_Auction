package exceptions;

public class UserDoesNotExistException extends Exception {

	private static final long serialVersionUID = 1L;
	private String username;
	
	public UserDoesNotExistException(String username) {
		this.username = username;
	}
	
	public String getUserName(){
		return username;
	}

}
