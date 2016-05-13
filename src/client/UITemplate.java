package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UITemplate extends JPanel {
	
	private JPanel targetPanel;

	public UITemplate(ActionListener listener) {
		super(new GridBagLayout());
		init(listener);
	}
	
	private void init(ActionListener listener){
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.2, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		
		JLabel logo = new JLabel(new ImageIcon("AuctionLogo.png"));
		c.anchor = GridBagConstraints.NORTHWEST;
		add(logo, c);
		
		//add menu buttons
		c.anchor = GridBagConstraints.CENTER;
		
		JButton browseAuctions = new JButton("Browse Auctions");
		browseAuctions.addActionListener(listener);
		c.gridx = 1;
		add(browseAuctions, c);
		
		JButton createAuction = new JButton("Create Auction");
		createAuction.setActionCommand("Show Auction Create");
		createAuction.addActionListener(listener);
		c.gridx = 2;
		add(createAuction, c);
		
		JButton viewBids = new JButton("View Bids");
		viewBids.addActionListener(listener);
		c.gridx = 3;
		add(viewBids, c);
		
		JButton viewSelling = new JButton("View Selling");
		viewSelling.addActionListener(listener);
		c.gridx = 4;
		add(viewSelling, c);
		
		JButton viewInfo = new JButton("Log off");
		viewInfo.addActionListener(listener);
		c.gridx = 5;
		add(viewInfo, c);
		
		//add targetPanel
		targetPanel = new JPanel();
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.8;
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(targetPanel, c);
	}
	
	public JPanel getTargetPanel(){
		return targetPanel;
	}
}
