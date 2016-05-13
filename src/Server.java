import java.io.IOException;

import communication.SessionManager;
import server.DataManager;

public class Server {

	private SessionManager sessionManager;
	private DataManager dataManager;
	
	public Server() {
		dataManager = new DataManager();
		try {
			sessionManager = new SessionManager(7253, 10, dataManager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
