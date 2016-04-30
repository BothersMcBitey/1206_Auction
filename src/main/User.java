package main;

public class User {

	private final int UUID;	
	private String forename;
	private String surname;
	private byte[] passwordHash;
	
	public User(int UUID, String forename, String surname, byte[] passwordHash){
		this.UUID = UUID;
		this.forename = forename;
		this.surname = surname;
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
}
