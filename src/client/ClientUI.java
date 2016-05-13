package client;

import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	
	private LogIn logIn = null;	
	private CreateAuction createAuction = null;	
	private ItemPanel itemViewerPanel = null;

	public ClientUI(ActionListener listener) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		frame = new JFrame("Client v0.1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		this.listener = listener;
		
		displayLogIn();
		
		frame.setVisible(true);
	}
	
	public void displayLogIn(){	
		if(logIn == null) logIn = new LogIn(listener);
		frame.setContentPane(logIn);
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
		display(new JPanel());
	}
	
	private void display(JPanel panel){
		if(!templateDisplayed) displayTemplate();
		targetPanel.removeAll();
		targetPanel.add(panel);
		frame.revalidate();
	}
	
	public void displayItem(ItemDisplayData i){
		itemViewerPanel = new ItemPanel(listener, i);
		display(itemViewerPanel);
	}
	
	public void displayCreateAuction(){
		if(createAuction == null) createAuction = new CreateAuction(listener);
		display(createAuction);
	}
	
	public LogIn getLogIn() {
		return logIn;
	}

	public void showNotification(String notification) {
		JOptionPane.showMessageDialog(frame, notification);
	}
	
	public CreateAuction getCreateAuction() {
		return createAuction;
	}
}
