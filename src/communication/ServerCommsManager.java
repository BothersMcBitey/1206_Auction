package communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

import main.SynchronizedList;
import server.DataPersistence;

public class ServerCommsManager {

	private ServerSocket socket;
	private SynchronizedList<Session> connectedSessions;
 	
	private Thread newConnectionListener;
	
	private DataPersistence dataAccess;
	
	public ServerCommsManager(int port, int backlog, DataPersistence dataAccess) throws IOException {
		socket = new ServerSocket(port, backlog);
		newConnectionListener = new Thread(new ConnectionListener());
		newConnectionListener.start();
		this.dataAccess = dataAccess; 
	}
	
	/**
	 * notifies all connected users of impending server shutdown
	 * @param time
	 */
	public void notifyAllConnections(Date time){
		for(Session s : connectedSessions){
			try {
				s.notifyEndSession(time);
			} catch (IOException e) {
				System.err.println("Unable to notify user " + s.getUserID() + " in session " + s.getSessionNo() + " of impending closure");
			}
		}
	}
	
	/**
	 * Ends all connected sessions in a clean, secure way
	 */
	public void closeAllConnections(){
		for(Session s : connectedSessions){
			s.endSession();
		}
	}
	
	class ConnectionListener implements Runnable {
		
		@Override
		public void run(){
			int sessionID = 0;
			while(true){
				try {
					connectedSessions.add(new Session(new Comms(socket.accept()), sessionID, System.out, dataAccess));
					sessionID++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}