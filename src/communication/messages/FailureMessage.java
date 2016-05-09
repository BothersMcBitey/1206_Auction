package communication.messages;

public class FailureMessage extends Message {

	private static final long serialVersionUID = 1L;
	private final String msg;
	
	public FailureMessage(String clientIP, int sessionNo, String msg) {
		super(clientIP, sessionNo, MessageType.Failure);
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}

}
