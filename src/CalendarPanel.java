/*
 * Contains the GUI for the calendar panel
 */

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {
	
	public CalendarPanel() {
		JLabel calendarText = new JLabel("Calendar");
		add(calendarText);
	}
}
