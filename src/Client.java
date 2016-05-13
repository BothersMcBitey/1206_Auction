import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import client.ClientUI;
import communication.Comms;

public class Client implements ActionListener{
	
	public static void main(String[] args) {
		new Client();		
	}

	private ClientUI ui;
	private Comms comms;
	
	public Client() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					ui = new ClientUI(Client.this);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			comms = new Comms("localhost", 7253);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		ui.displayHome();
	}
	
}
