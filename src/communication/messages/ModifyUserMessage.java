package communication.messages;

public class ModifyUserMessage extends Message {

	private static final long serialVersionUID = 1L;	
	private final String forename, surname, password, screenName;
	
	public ModifyUserMessage(String clientIP, int sessionNo, String forename, String surname,
			String password, String screenName) {
		super(clientIP, sessionNo, MessageType.ModifyUser);
		this.surname = surname.trim();
		this.forename = forename.trim();
		this.password = password.trim();
		this.screenName = screenName.trim();
	}

	public String getPassword() {
		return password;
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
}
