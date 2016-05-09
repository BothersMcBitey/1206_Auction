package communication.messages;

public class RegisterMessage extends Message {

	private static final long serialVersionUID = 1L;	
	private final String forename, surname, screenName, password; 
	
	public RegisterMessage(String clientIP, int sessionNo, String forename, String surname, String screenName, String password) {
		super(clientIP, sessionNo, MessageType.Register);
		this.forename = forename.trim();
		this.surname = surname.trim();
		this.screenName = screenName.trim();
		this.password = password.trim();
	}

	public String getForename() {
		return forename;
	}
	
	public String getSurname() {
		return surname;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getPassword() {
		return password;
	}
}
