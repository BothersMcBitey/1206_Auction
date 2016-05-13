import java.io.IOException;
import java.io.PrintStream;

import communication.SessionManager;
import server.DataManager;

public class Server {

	public static void main(String[] args){
		new Server();
	}
	
	private SessionManager sessionManager;
	private DataManager dataManager;
	private PrintStream log = System.out;
	
	public Server() {
		dataManager = new DataManager(System.out);
		dataManager.loadDataFromDisk();
		try {
			sessionManager = new SessionManager(7254, 10, dataManager, log);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        dataManager.saveData();
		    }
		});
	}
}
