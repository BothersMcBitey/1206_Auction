package communication.messages;

import main.Item;

public class NotificationMessage extends Message {

	public enum NotifyType{
		Closed, Won, Impending, Shutdown
	}
	
	private static final long serialVersionUID = 1L;
	private final NotifyType notificationType;
	private final Item item;
	private final long timeLeft;
	
	public NotificationMessage(String clientIP, int sessionNo, NotifyType notificationType, Item item,
			long timeLeft) {
		super(clientIP, sessionNo, MessageType.Notification);
		this.notificationType = notificationType;
		this.item = item;
		this.timeLeft = timeLeft;
	}

	public NotifyType getNotificationType() {
		return notificationType;
	}

	public Item getItem() {
		return item;
	}

	public long getTimeLeft() {
		return timeLeft;
	}
}
