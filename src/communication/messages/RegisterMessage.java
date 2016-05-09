package communication.messages;

public class RegisterMessage extends Message {

	private static final long serialVersionUID = 1L;	
	private final String forename, surname, userName;
	private final char[] password; 
	
	public RegisterMessage(String clientIP, int sessionNo, String forename, String surname, String userName, char[] password) {
		super(clientIP, sessionNo, MessageType.Register);
		this.forename = forename.trim();
		this.surname = surname.trim();
		this.userName = userName.trim();
		this.password = password;
	}

	public String getForename() {
		return forename;
	}
	
	public String getSurname() {
		return surname;
	}

	public String getUserName() {
		return userName;
	}

	public char[] getPassword() {
		return password;
	}
}
