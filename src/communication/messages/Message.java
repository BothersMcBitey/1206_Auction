package communication.messages;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;

	private final Date timestamp;
	private final String clientIP;
	private final int sessionNo;
	private final MessageType type;
	
	public Message(String clientIP, int sessionNo, MessageType type) {
		this.timestamp = Date.from(Instant.now());
		this.clientIP = clientIP.trim();
		this.sessionNo = sessionNo;
		this.type = type;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getClientIP() {
		return clientIP;
	}

	public int getSessionNo() {
		return sessionNo;
	}

	public MessageType getType() {
		return type;
	}
}
