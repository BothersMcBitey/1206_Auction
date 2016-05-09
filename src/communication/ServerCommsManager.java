package communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

import main.SynchronizedList;

public class ServerCommsManager {

	private ServerSocket socket;
	private SynchronizedList<Session> connectedSessions;
 	
	private Thread newConnectionListener;
	
	public ServerCommsManager(int port, int backlog) throws IOException {
		socket = new ServerSocket(port, backlog);
		newConnectionListener = new Thread(new ConnectionListener());
		newConnectionListener.start();			
	}
	
	/**
	 * notifies all connected users of impending server shutdown
	 * @param time
	 */
	public void notifyAllConnections(Date time){
		for(Session s : connectedSessions){
			s.notifyEndSession(time);
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
			while(true){
				try {
					connectedSessions.add(new Session(new Comms(socket.accept())));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}