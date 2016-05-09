package communication.messages;

public class ModifyUserMessage extends Message {

	private static final long serialVersionUID = 1L;	
	private final String forename, surname, screenName;
	private final char[] password;
	
	public ModifyUserMessage(String clientIP, int sessionNo, String forename, String surname,
			char[] password, String screenName) {
		super(clientIP, sessionNo, MessageType.ModifyUser);
		this.surname = surname.trim();
		this.forename = forename.trim();
		this.password = password;
		this.screenName = screenName.trim();
	}

	public char[] getPassword() {
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
