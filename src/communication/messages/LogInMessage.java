package communication.messages;

public class LogInMessage extends Message {

	private static final long serialVersionUID = 1L;
	
	private final String username;
	private final char[] password;

	public LogInMessage(String clientIP, int sessionNo, String username, char[] password) {
		super(clientIP, sessionNo, MessageType.LogIn);
		this.username = username.trim();
		this.password = password;
	}

	public char[] getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
}
