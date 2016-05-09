package main;

public class User {

	private final int UUID;	
	private String forename;
	private String surname;
	private String screenName;
	private byte[] passwordHash;
	
	public User(int UUID, String forename, String surname, String screenName, byte[] passwordHash){
		this.UUID = UUID;
		this.forename = forename;
		this.surname = surname;
		this.screenName = screenName;
		this.passwordHash = passwordHash;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public byte[] getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}

	public int getUUID() {
		return UUID;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
