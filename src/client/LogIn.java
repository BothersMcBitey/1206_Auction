package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LogIn extends JPanel {
	
	private JTextField logInUsername, firstname, lastname, registerUsername;
	private JPasswordField registerPassword, logInPassword;

	public LogIn(ActionListener listener) {
		super(new GridBagLayout());
		init(listener);
	}
	
	private void init(ActionListener listener){
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		
		//Add logo
		JLabel logo = new JLabel(new ImageIcon("AuctionLogo.png"));
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth =  GridBagConstraints.REMAINDER;
		add(logo, c);
		
		//Add Log-in UI
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;	
		JLabel logInUsernamelbl = new JLabel("Username:");
		c.gridy = 1;
		add(logInUsernamelbl, c);
		
		JLabel logInPasswordlbl = new JLabel("Password:");
		c.gridy = 2;
		add(logInPasswordlbl, c);
		
		logInUsername = new JTextField(20);
		c.gridy = 1;
		c.gridx = 1;
		add(logInUsername, c);
		
		logInPassword = new JPasswordField(20);
		c.gridy = 2;
		add(logInPassword, c);
		
		JButton login = new JButton("Log in");
		login.addActionListener(listener);
		c.gridy = 3;
		add(login, c);
		
		//add separator
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 4;
		c.fill = GridBagConstraints.VERTICAL;
		add(sep, c);
		
		//add register UI
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		
		JLabel firstNamelbl = new JLabel("First Name:");
		c.gridx = 3;
		add(firstNamelbl, c);
		
		JLabel lastNamelbl = new JLabel("Last Name:");
		c.gridy = 2;
		add(lastNamelbl, c);
		
		JLabel registerUserNamelbl = new JLabel("User Name:");
		c.gridy = 3;
		add(registerUserNamelbl, c);	
		
		JLabel registerPasswordlbl = new JLabel("Password:");
		c.gridy = 4;
		add(registerPasswordlbl, c);
		
		firstname = new JTextField(20);
		c.gridy = 1;
		c.gridx = 4;
		add(firstname, c);
		
		lastname = new JTextField(20);
		c.gridy = 2;
		add(lastname, c);
		
		registerUsername = new JTextField(20);
		c.gridy = 3;
		add(registerUsername, c);
		
		registerPassword = new JPasswordField(20);
		c.gridy = 4;
		add(registerPassword, c);
		
		JButton register = new JButton("Register");
		register.addActionListener(listener);
		c.gridy = 5;
		add(register, c);
		
		//add exit button
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		c.gridx = 0;
		c.gridy = 5;
		add(exit, c);
	}
	
	public String getLogInUsername(){
		return logInUsername.getText().trim();
	}
	
	public char[] getLogInPassword(){
		return logInPassword.getPassword();
	}
	
	public String getFirstName(){
		return firstname.getText().trim();
	}
	
	public String getLastName(){
		return lastname.getText().trim();
	}
	
	public String getRegisterUsername(){
		return registerUsername.getText().trim();
	}
	
	public char[] getRegisterPassword(){
		return registerPassword.getPassword();
	}
}
