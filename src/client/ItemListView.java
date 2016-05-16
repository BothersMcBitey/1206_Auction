package client;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.ItemDisplayData;

public class ItemListView extends JPanel {
	
	private List<ItemDisplayData> items;

	public ItemListView(ActionListener listener, List<ItemDisplayData> items, boolean searchBar) {
		super();
		this.items = items;
		init(listener, searchBar);
	}

	private void init(ActionListener listener, boolean searchBar) {
		//add the search bar (optional)
		
		
		//add the scrolly bit (The search results)
		JScrollPane pane = new JScrollPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//add items to panel
		for(ItemDisplayData item : items){
			panel.add(new ItemPanel(listener, item));
		}
		
		//add panel to scrollpane
		pane.setViewportView(panel);
		
		//set scrollbars
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(pane);
	}
}
