package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ItemView extends JPanel {
	
	private ItemDisplayData i;

	public ItemView(ActionListener listener, ItemDisplayData i) {
		super(new GridBagLayout());
		this.i = i;
		init(listener);
	}

	private void init(ActionListener listener) {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		
		JLabel title = new JLabel(i.title);
		c.anchor = GridBagConstraints.NORTHWEST;
		add(title, c);
		
		JLabel desc = new JLabel(i.description);//.substring(0, DESCRIPTION_MAX_LENGTH) + "...");
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		c.gridheight = 3;
		add(desc, c);
		
		String priceStr = "£" + ((float)i.topBid)/100;
		JLabel price = new JLabel(priceStr);
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		add(price, c);
		
		String bidStr = "£" + ((float)i.userTopBid)/100;
		JLabel yourBid = new JLabel(bidStr);
		c.gridy = 1;
		add(yourBid, c);
		
		String time = new Date(i.endTime.getTime() - (new Date().getTime())).toString();
		JLabel timeLeft = new JLabel(time);
		c.gridy = 2;
		add(timeLeft, c);
		
		JButton view = new JButton("view " + i.UIID);
		view.addActionListener(listener);
		c.gridy = 3;
		add(view, c);
	}

}
