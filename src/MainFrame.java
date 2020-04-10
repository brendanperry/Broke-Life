import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class is responsible for the display of the program.
 * It contains the GUI JFrame that holds all other JPanels
 * @author brendanperry
 * 03/20/20
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JMenuItem save, load;
	

	public MainFrame(UserProfile user) {
		getContentPane().setBackground(Color.decode("#25ced1"));
		setTitle("BrokeLife - " + user.getName());
		setSize(1250, 800);
		setResizable(false);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/logo.png"));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// TOP PANEL
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.decode("#25ced1"));
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JButton leftButton = new JButton();
		leftButton.setText("\u2190");
		topPanel.add(leftButton);
		
		JLabel monthHeading = new JLabel("April 2020");
		monthHeading.setFont(new Font("Arial", Font.BOLD, 30));
		monthHeading.setForeground(Color.WHITE);
		topPanel.add(monthHeading);
		
		JButton rightButton = new JButton();
		rightButton.setText("\u2192");
		topPanel.add(rightButton);
		
		// CENTER PANEL
		JTabbedPane centerPanel = new JTabbedPane();
		centerPanel.setForeground(Color.RED);
		centerPanel.setBackground(Color.decode("#25ced1"));
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		
		JPanel budgetPanel = new BudgetPanel(user);		
		JPanel calendarPanel = new CalendarPanel(user);
		JPanel overviewPanel = new OverviewPanel(user);
		
		centerPanel.addTab("Budget", budgetPanel);
		centerPanel.addTab("Calendar", calendarPanel);
		centerPanel.addTab("Overview", overviewPanel);
		
		// MENU BAR
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem save = new JMenuItem("Save Profile");
		JMenuItem load = new JMenuItem("Load Profile");
		
		menuBar.add(file);
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String filename =  user.getName() + ".bl";
					FileOutputStream file = new FileOutputStream(new File(".").getCanonicalPath() + "/Profiles/" + filename);
					ObjectOutputStream out = new ObjectOutputStream(file);
					
					out.writeObject(user);
					file.close();
					out.close();

					JOptionPane.showMessageDialog(null, "Profile has been saved!" );
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Save failed!");
			}
			}
			
		});
		
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			});
		
		
		file.add(save);
		file.add(load);
	}

}

