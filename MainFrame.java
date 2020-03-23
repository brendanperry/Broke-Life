import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author brendanperry
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		setSize(1000, 700);
		JMenuBar menuBar = new JMenuBar();
		JMenuItem file = new JMenuItem("File");
		JMenuItem edit = new JMenuItem("Edit");
		menuBar.add(file);
		menuBar.add(edit);
		setJMenuBar(menuBar);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// TOP PANEL
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.gray);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JButton leftButton = new JButton();
		leftButton.setText("Left");
		topPanel.add(leftButton);
		
		JLabel monthHeading = new JLabel("MARCH");
		monthHeading.setFont(new Font("Arial", Font.BOLD, 30));
		topPanel.add(monthHeading);
		
		JButton rightButton = new JButton();
		rightButton.setText("Right");
		topPanel.add(rightButton);
		
		// CENTER PANEL
		
		JTabbedPane centerPanel = new JTabbedPane();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		JPanel budgetPanel = new JPanel();
		JLabel budgetText = new JLabel("Budget");
		budgetPanel.add(budgetText);
		
		JPanel calendarPanel = new JPanel();
		JLabel calendarText = new JLabel("Calendar");
		calendarPanel.add(calendarText);
		
		JPanel overviewPanel = new JPanel();
		JLabel overviewText = new JLabel("Overview");
		overviewPanel.add(overviewText);
		
		centerPanel.addTab("Budget", budgetPanel);
		centerPanel.addTab("Calendar", calendarPanel);
		centerPanel.addTab("Overview", overviewPanel);
		
	}
}
