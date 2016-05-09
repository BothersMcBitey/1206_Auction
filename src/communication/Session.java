package communication;

import java.util.Date;

public class Session extends Thread{

	private Comms commsModule;
	
	public Session(Comms commsModule) {
		this.commsModule = commsModule;
	}
	
	@Override
	public void run(){
		
	}
	
	/**
	 * Ends the session in a clean way, making sure the session is in a state where
	 * ending won't cause any security/consistency issues.
	 */
	public void endSession(){
		
	}
	
	/**
	 * Used to notify connected users of impending session shutdown, so they can
	 * finish what they're doing beforehand
	 * @param time
	 */
	public void notifyEndSession(Date time){
		
	}
}
