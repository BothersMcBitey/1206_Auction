package communication.messages;

public class LogInMessage extends Message {

	private static final long serialVersionUID = 1L;
	
	private final String username, password;

	public LogInMessage(String clientIP, int sessionNo, String username, String password) {
		super(clientIP, sessionNo, MessageType.LogIn);
		this.username = username.trim();
		this.password = password.trim();
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
}
