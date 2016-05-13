package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Category;

public class CreateAuction extends JPanel {

	private JTextField title, description, reserve;
	private JComboBox<Category> category;
	private JComboBox<Integer> noDaysTillClose;
	
	public CreateAuction(ActionListener listener) {
		super(new GridBagLayout());
		init(listener);
	}
	
	private void init(ActionListener listener){
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		
		JLabel titlelbl = new JLabel("Title:");
		add(titlelbl, c);
		
		JLabel descriptionlbl = new JLabel("Description:");
		c.gridy = 1;
		add(descriptionlbl, c);
		
		JLabel reservelbl = new JLabel("Reserve:");
		c.gridy = 2;
		add(reservelbl, c);
		
		JLabel catlbl = new JLabel("Category:");
		c.gridy = 3;
		add(catlbl, c);
		
		JLabel datelbl = new JLabel("Length of Auction (Days):");
		c.gridy = 4;
		add(datelbl, c);
		
		title = new JTextField(20);
		c.gridx = 1;
		c.gridy = 0;
		add(title, c);
		
		description = new JTextField(20);
		c.gridy = 1;
		add(description, c);
		
		reserve = new JTextField(20);
		c.gridy = 2;
		add(reserve, c);
		
		category = new JComboBox<>(Category.values());
		c.gridy = 3;
		add(category, c);
		
		noDaysTillClose = new JComboBox<>(new Integer[] {1,2,3,4,5,6,7});
		c.gridy = 4;
		add(noDaysTillClose, c);
		
		JButton create = new JButton("Create Auction");
		create.addActionListener(listener);
		c.gridy = 5;
		add(create, c);
	}
	
	public String getTitle(){
		return title.getText();
	}
	
	public String getDescription(){
		return description.getText();
	}
	
	public Integer getReserve(){
		Integer i = -1;
		boolean valid = true;
		try{
			i = Integer.parseInt(reserve.getText());
		} catch (NumberFormatException e){
			do{
				i = Integer.parseInt(JOptionPane.showInputDialog("Invalid reserve, please enter an integer value:"));
				if(i == -1) valid = false;
			} while (!valid);
		}		
		return i;
	}
	
	public Category getCategory(){
		return (Category)category.getSelectedItem();
	}
	
	public Integer getDays(){
		return (Integer) noDaysTillClose.getSelectedItem();
	}
}
