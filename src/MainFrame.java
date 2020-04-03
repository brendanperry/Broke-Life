import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author brendanperry
 *
 */
public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuItem save, load;
	

	public MainFrame(UserProfile user) {
		setTitle("BrokeLife");
		setSize(1000, 700);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		
		
		JPanel budgetPanel = new BudgetPanel(user);		
		JPanel calendarPanel = new CalendarPanel(user);
		JPanel overviewPanel = new OverviewPanel(user);
		
		centerPanel.addTab("Budget", budgetPanel);
		centerPanel.addTab("Calendar", calendarPanel);
		centerPanel.addTab("Overview", overviewPanel);
		
		// MENU BAR

		JMenuItem file = new JMenuItem("File");
		menuBar.add(file);
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem save = new JMenuItem("Save Profile");
		JMenuItem load = new JMenuItem("Load Profile");
		
		
		
		save.addActionListener(this);
		load.addActionListener(this);
		
		file.add(save);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			
		} else if(e.getSource() == load) {
			
		}
		
	}
	
}
