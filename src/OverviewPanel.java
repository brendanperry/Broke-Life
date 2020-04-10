/*
 * Contains the GUI for the overview panel
 */

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OverviewPanel extends JPanel {
	
	public OverviewPanel(UserProfile user) {
		JLabel overviewText = new JLabel("Overview");
		add(overviewText);
	}
}
