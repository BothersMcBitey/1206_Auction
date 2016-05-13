package communication;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import main.SynchronizedList;
import server.DataManager;

public class SessionManager {

	private ServerSocket socket;
	private SynchronizedList<Session> connectedSessions;
 	
	private Thread newConnectionListener;
	
	private DataManager data;
	private PrintStream log;
	
	/**
	 * 
	 * @param port - The port to listen to
	 * @param backlog - The number of connections that can be queued at once
	 * @param data - The data manager to redirect information requests to
	 * @throws IOException
	 */
	public SessionManager(int port, int backlog, DataManager data, PrintStream log) throws IOException {
		socket = new ServerSocket(port, backlog);
		newConnectionListener = new Thread(new ConnectionListener());
		newConnectionListener.start();
		connectedSessions = new SynchronizedList<>();
		this.data = data;
		this.log = log;
	}
	
	/**
	 * notifies all connected users of impending server shutdown
	 * @param time - time of shutdown
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
			try {
				s.endSession();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ConnectionListener implements Runnable {
		
		@Override
		public void run(){
			int sessionID = 0;
			while(true){
				try {
					Socket sock = socket.accept();
					Comms c = new Comms(sock, log);
					Session s = new Session(c, sessionID, log, data);
					s.start();
					connectedSessions.add(s);
					sessionID++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}