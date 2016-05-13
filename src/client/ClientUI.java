package client;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientUI {

	private JFrame frame;
	private ActionListener listener;
	
	//all screens except the log in/register screen use the same template, which
	//simplifies switching between them.
	private boolean templateDisplayed = false;
	//the space on the template used to display each screens unique content 
	private JPanel targetPanel;
	
	public ClientUI(ActionListener listener) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		frame = new JFrame("Client v0.1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1920, 1080);
		
		this.listener = listener;
		
		displayLogIn();
		
		frame.setVisible(true);
	}
	
	public void displayLogIn(){		
		frame.setContentPane(new LogIn(listener));
		templateDisplayed = false;
		frame.revalidate();
	}
	
	private void displayTemplate(){
		UITemplate temp = new UITemplate(listener);
		targetPanel = temp.getTargetPanel();
		frame.setContentPane(temp);
		templateDisplayed = true;
	}
	
	public void displayHome(){
		if(!templateDisplayed) displayTemplate();
		targetPanel.removeAll();
		targetPanel.add(new Home(listener));
		frame.revalidate();
	}
	
}
