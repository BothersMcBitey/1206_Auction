package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import main.Category;

public class Home extends JPanel {

	public Home(ActionListener listener) {
		super(new GridBagLayout());
		init(listener);
	}
	
	private void init(ActionListener listener){
		GridBagConstraints c = new GridBagConstraints(0, 0, 2, 2, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 0), 0, 0);
		
		ItemDisplayData dat = new ItemDisplayData(50, "an item", "ashfiafowfohwafo", Category.Clothes, 124, new Date(new Date().getTime() + 1234567), 1000, 800);
		
		//add highlight items
		ItemPanel itemH1 = new ItemPanel(listener, dat);
		add(itemH1, c);
		
		ItemPanel itemH2 = new ItemPanel(listener, dat);
		c.gridy = 2;
		c.gridheight = 3;
		add(itemH2, c);
		
		//seperator
		JSeparator sep1 = new JSeparator(SwingConstants.VERTICAL);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 5;
		c.gridwidth = 1;
		add(sep1, c);
		
		//add bids list
		ItemPanel bid1 = new ItemPanel(listener, dat);
		c.gridheight = 1;
		c.gridx = 3;
		c.gridy = 0;
		add(bid1, c);
		
		ItemPanel bid2 = new ItemPanel(listener, dat);
		c.gridy = 1;
		add(bid2, c);
		
		ItemPanel bid3 = new ItemPanel(listener, dat);
		c.gridy = 2;
		add(bid3, c);
		
		//seperator
		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		c.gridx = 4;
		c.gridy = 0;
		c.gridheight = 3;
		add(sep2, c);
		
		//add selling list
		ItemPanel sell1 = new ItemPanel(listener, dat);
		c.gridx = 5;
		c.gridheight = 1;
		add(sell1, c);
		
		ItemPanel sell2 = new ItemPanel(listener, dat);
		c.gridy = 1;
		add(sell2, c);
		
		ItemPanel sell3 = new ItemPanel(listener, dat);
		c.gridy = 2;
		add(sell3, c);
		
		//seperator
		JSeparator sep3 = new JSeparator(SwingConstants.HORIZONTAL);
		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 3;
		add(sep3, c);
		
		//add stats
		ItemPanel stats = new ItemPanel(listener, dat);
		c.gridx = 3;
		c.gridy = 4;		
		add(stats, c);
	}
}
