package exceptions;

public class ItemDoesNotExistException extends Exception {

	private int uIID;
	
	public ItemDoesNotExistException(Integer uIID) {
		this.uIID = uIID;
	}

	public int getUIID(){
		return uIID;
	}
}
