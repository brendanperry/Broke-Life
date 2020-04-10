import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;

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
	JLabel monthHeading;
	BudgetPanel budgetPanel;		
	CalendarPanel calendarPanel;
	OverviewPanel overviewPanel;

	public MainFrame(UserProfile user) {
		getContentPane().setBackground(Color.decode("#25ced1"));
		setTitle("BrokeLife - " + user.getName());
		setSize(1250, 800);
		setResizable(false);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/logo.png"));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				Object[] options = {"Save and exit", "Exit without saving"};
				
				int input = JOptionPane.showOptionDialog(null,
		                 "Do you want to save your work?",
		                 "Exit",
		                 JOptionPane.OK_CANCEL_OPTION,
		                 JOptionPane.QUESTION_MESSAGE,
		                 null,
		                 options,
		                 null);	
				if(input == JOptionPane.OK_OPTION) {
					try {
						String filename =  user.getName() + ".bl";
						FileOutputStream file = new FileOutputStream(new File(".").getCanonicalPath() + "/Profiles/" + filename);
						ObjectOutputStream out = new ObjectOutputStream(file);
						
						out.writeObject(user);
						file.close();
						out.close();
						
						System.exit(0);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Save failed!");
					}
				}
				else {
					System.exit(0);
				}
			}
		});
		
		// TOP PANEL
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.decode("#25ced1"));
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JButton btnPreviousMonth = new JButton();
		btnPreviousMonth.setText("\u2190");
		btnPreviousMonth.setFont(new Font("Arial", Font.PLAIN, 18));
		topPanel.add(btnPreviousMonth);
		
		monthHeading = new JLabel("January");
		monthHeading.setFont(new Font("Arial", Font.BOLD, 30));
		monthHeading.setForeground(Color.WHITE);
		topPanel.add(monthHeading);
		
		JButton btnNextMonth = new JButton();
		btnNextMonth.setText("\u2192");
		btnNextMonth.setFont(new Font("Arial", Font.PLAIN, 18));
		topPanel.add(btnNextMonth);
		
		btnPreviousMonth.addActionListener(new btnPreviousMonthClicked());
		btnNextMonth.addActionListener(new btnNextMonthClicked());
		
		// CENTER PANEL
		JTabbedPane centerPanel = new JTabbedPane();
		centerPanel.setForeground(Color.RED);
		centerPanel.setBackground(Color.decode("#25ced1"));
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		
		budgetPanel = new BudgetPanel(user);		
		calendarPanel = new CalendarPanel(user);
		overviewPanel = new OverviewPanel(user);
		
		centerPanel.addTab("Budget", budgetPanel);
		centerPanel.addTab("Calendar", calendarPanel);
		centerPanel.addTab("Overview", overviewPanel);
		
		monthHeading.setText(calendarPanel.getCurrentMonthString());
		
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
		
		JMenu education = new JMenu("Education");
		education.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem tutorial = new JMenuItem("Tutorial");
		JMenuItem learning = new JMenuItem("Learning");
		
		education.add(tutorial);
		education.add(learning);
		
		menuBar.add(education);
		
		learning.addActionListener(new ActionListener() {
      		@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					EducationPanel educationPanel = new EducationPanel(1);
					educationPanel.setVisible(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Load Failed");
				}
			}
		});
		
		tutorial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					EducationPanel educationPanel = new EducationPanel(0);
					educationPanel.setVisible(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Load Failed");
				}
			}
		});
	}

	//If currentMonth is January, decrement current year and change current month to December
    //Otherwise, change currentMonth to previous month
	//Refresh calendar
	 class btnPreviousMonthClicked implements ActionListener{
        public void actionPerformed (ActionEvent e){
        	if (calendarPanel.getCurrentMonth() == 0){
        		calendarPanel.setCurrentMonth(11);
        		calendarPanel.setCurrentYear(calendarPanel.getCurrentYear()-1);
            } else{
                calendarPanel.setCurrentMonth(calendarPanel.getCurrentMonth()-1);
            }
            calendarPanel.refreshCalendar(calendarPanel.getCurrentMonth(), calendarPanel.getCurrentYear());
            
            try {
				budgetPanel.loadUserData(calendarPanel.getCurrentMonth() + 1, calendarPanel.getCurrentYear());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
    		monthHeading.setText(calendarPanel.getCurrentMonthString());
        }
    }
	
	//If currentMonth is December, increment current year and change current month to January
    //Otherwise, change currentMonth to next month
	//Refresh calendar
     class btnNextMonthClicked implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (calendarPanel.getCurrentMonth() == 11){
        		calendarPanel.setCurrentMonth(0);
        		calendarPanel.setCurrentYear(calendarPanel.getCurrentYear()+1);
            } else{
                calendarPanel.setCurrentMonth(calendarPanel.getCurrentMonth()+1);
            }
            calendarPanel.refreshCalendar(calendarPanel.getCurrentMonth(), calendarPanel.getCurrentYear());
            
            try {
				budgetPanel.loadUserData(calendarPanel.getCurrentMonth() + 1, calendarPanel.getCurrentYear());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
    		monthHeading.setText(calendarPanel.getCurrentMonthString());
        }
    }
}

