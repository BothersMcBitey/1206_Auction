package communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;

import main.SynchronizedList;

public class ServerCommsManager {

	private ServerSocket socket;
	private SynchronizedList<Comms> connections;
	private SynchronizedList<Message> messages;
 	
	private Thread newConnectionListener;
	private Thread messageListener;
	
	public ServerCommsManager(int port, int backlog) throws IOException {
		socket = new ServerSocket(port, backlog);
		newConnectionListener = new Thread(new ConnectionListener());
		newConnectionListener.start();
		messageListener = new Thread(new MessageListener());
		messageListener.start();			
	}
	
	class ConnectionListener implements Runnable {
		
		@Override
		public void run(){
			while(true){
				try {
					connections.add(new Comms(socket.accept()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class MessageListener implements Runnable {

		@Override
		public void run() {
			Iterator<Comms> it = connections.iterator();
			while(it.hasNext()){
				try {
					messages.add(it.next().receiveMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}