package communication.messages;

public class ViewUserRequestMessage extends Message {

	private static final long serialVersionUID = 1L;	
	private final String screenName;
	
	public ViewUserRequestMessage(String clientIP, int sessionNo, String screenName) {
		super(clientIP, sessionNo, MessageType.ViewUserRequest);
		this.screenName = screenName.trim();
	}
	
	public String getScreenName() {
		return screenName;
	}
}
