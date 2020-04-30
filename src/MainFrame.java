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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import com.itextpdf.awt.geom.Rectangle;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.List;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.ColumnText;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.parser.Line;

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
						user.saveProfile();
						System.exit(0);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Save failed!");
					}
				}
				else if(input == 1){
					System.exit(0);
				}
				else {
					// do nothing
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
		
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        if(sourceTabbedPane.getSelectedIndex() == 1) {
		        	calendarPanel.updateLists();
		        }
		      }
		    };
		    centerPanel.addChangeListener(changeListener);
		
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
					user.saveProfile();
					JOptionPane.showMessageDialog(null, "Profile has been saved!" );
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Save failed!");
			}
			}
			
		});
		
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Save changes to current profile before "
						+ "loading a new one?", "Save changes?", JOptionPane.YES_NO_OPTION);
				
				if (choice == JOptionPane.YES_OPTION) {
				    try {
						user.saveProfile();
					} catch (IOException e1) {}
				}
				
				try {
					ArrayList<String> profiles = new ArrayList<String>();
					File folder = new File(new File(".").getCanonicalPath() + "/Profiles/");
					Main.listFilesForFolder(folder, profiles);
					new InitialWindow(profiles);
					dispose();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Failed to load files");
				}
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
		
		JMenu report = new JMenu("Report");
		report.setMnemonic(KeyEvent.VK_R);
		JMenuItem create = new JMenuItem("Create Report for Current Month");
		report.add(create);
		menuBar.add(report);
		
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					createReport(user);
				} catch (IOException e) {
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
				budgetPanel.loadFromHeader(calendarPanel.getCurrentMonth() + 1, calendarPanel.getCurrentYear());
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
				budgetPanel.loadFromHeader(calendarPanel.getCurrentMonth() + 1, calendarPanel.getCurrentYear());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
    		monthHeading.setText(calendarPanel.getCurrentMonthString());
        }
    }
     
    
    public void createReport(UserProfile user) throws MalformedURLException, IOException {
    	Document document = new Document();
    	user.saveProfile();
    	user.sortEvents();
    	Event[] events = user.getEvents(new Date(BudgetPanel.tableYear - 1900, BudgetPanel.tableMonth - 1, 1), new Date(BudgetPanel.tableYear - 1900,BudgetPanel.tableMonth - 1, 31));
    	
    	int year = BudgetPanel.tableYear;
    	int month = BudgetPanel.tableMonth;
    	
    	if(events.length == 0) {
    		JOptionPane.showMessageDialog(null, "There are no events to list in report");
    		return;
    	}
    	
        try
        {
           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(user.getName() + "_" +
        		   		BudgetPanel.tableMonth + "-" + BudgetPanel.tableYear +".pdf"));
        
           document.open();
           
           PdfContentByte canvas = writer.getDirectContent();
           ColumnText ct = new ColumnText(canvas);
           ct.setSimpleColumn(700f,48f, 180f, 800f);
           BaseFont bfBold = BaseFont.createFont(BaseFont.TIMES_BOLD, "" , BaseFont.EMBEDDED);
           com.itextpdf.text.Font f = new com.itextpdf.text.Font(bfBold, 16);
           ct.addElement(new Paragraph("BrokeLife Activity for " + user.getName() + " in the month of " + month + "/" + year, f));
           ct.go();
           Image logo = Image.getInstance("logo-full.png");
           logo.scaleAbsolute(150, 75);
           logo.setAbsolutePosition(20f, 750f);
           document.add(logo);
           
           canvas.setRGBColorStroke(0, 0, 0);
           canvas.moveTo(20, 750);
           canvas.lineTo(570, 750);
           
           canvas.closePathStroke();
           
           f = new com.itextpdf.text.Font(bfBold, 10);
           ct.setSimpleColumn(700f, 50f, 20f, 750f);
           ct.addElement(new Paragraph("Expenses from the month of " + month + "/" + year, f));
           ct.go();
        
           PdfPTable expenses = new PdfPTable(6);
           expenses.setTotalWidth(540);
           //expenses.setWidthPercentage(100);
           
           expenses.addCell(new PdfPCell(new Paragraph("Event", f)));
           expenses.addCell(new PdfPCell(new Paragraph("Amount", f)));
           expenses.addCell(new PdfPCell(new Paragraph("Percentage", f)));
           expenses.addCell(new PdfPCell(new Paragraph("Tag", f)));
           expenses.addCell(new PdfPCell(new Paragraph("Date", f)));
           expenses.addCell(new PdfPCell(new Paragraph("Recur Period", f)));
           
           int row;
           
           BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, "" , BaseFont.EMBEDDED);
           f = new com.itextpdf.text.Font(bf, 10);
           
           for(row = 0; row < events.length; row ++) {
        	   PdfPCell c1 = new PdfPCell(new Paragraph(events[row].getTitle(),f));
        	   expenses.addCell(c1);
        	   
        	   PdfPCell c2 = new PdfPCell(new Paragraph("$" + events[row].getAmount(),f));
        	   expenses.addCell(c2);
        	   
        	   PdfPCell c3 = new PdfPCell(new Paragraph(events[row].getPercentage() + "%",f));
        	   expenses.addCell(c3);
        	   
        	   PdfPCell c4 = new PdfPCell(new Paragraph(events[row].getTag(),f));
        	   expenses.addCell(c4);
        	   
        	   PdfPCell c5 = new PdfPCell(new Paragraph(events[row].dateString(),f));
        	   expenses.addCell(c5);
        	   
        	   PdfPCell c6 = new PdfPCell(new Paragraph(""+ events[row].getRecurPeriod(),f));
        	   expenses.addCell(c6);
        	   
           }
  
           float nextPos = expenses.writeSelectedRows(0, -1, 20, 730, canvas);
           
           f = new com.itextpdf.text.Font(bfBold, 10);
           ct.setSimpleColumn(700f, 50f, 20f, (nextPos));
           ct.addElement(new Paragraph("Total Spent: $" + user.sumEvents(events, month), f));
           ct.go();
           PdfPTable income = new PdfPTable(3);
           income.setTotalWidth(300);
           
           income.addCell(new PdfPCell(new Paragraph("Week", f)));
           income.addCell(new PdfPCell(new Paragraph("Pay", f)));
           income.addCell(new PdfPCell(new Paragraph("Misc.", f)));
           
           Income monthIncome = user.getIncome(year, month);
           
           f = new com.itextpdf.text.Font(bfBold, 10);
           
           ct.setSimpleColumn(700f, 50f, 140f, (nextPos - 13));
           ct.addElement(new Paragraph("Income for the month of " + month + "/" + year, f));
           ct.go();
           
           f = new com.itextpdf.text.Font(bf, 10);
           
           for(row = 0; row < 4; row ++) {
        	   PdfPCell c1 = new PdfPCell(new Paragraph("Week " + (row + 1), f));
        	   income.addCell(c1);
        	   
        	   PdfPCell c2 = new PdfPCell(new Paragraph("$" + monthIncome.getWeek(row),f));
        	   income.addCell(c2);
        	   
        	   PdfPCell c3 = new PdfPCell(new Paragraph("$" + monthIncome.getMisc(row),f));
        	   income.addCell(c3);
        	   
        	   
           }
           
           PdfPCell tips = new PdfPCell(new Paragraph("Tips for the Month: $" + monthIncome.getTips(), f));
           tips.setColspan(3);
           income.addCell(tips);
           
           nextPos = income.writeSelectedRows(0, -1, 140, nextPos - 32, canvas);
           
           f = new com.itextpdf.text.Font(bfBold, 10);
           ct.setSimpleColumn(700f, 50f, 140f, (nextPos));
           ct.addElement(new Paragraph("Total Income: $" + monthIncome.sumIncome(), f));
           ct.go();
           
           f = new com.itextpdf.text.Font(bfBold, 10);
           nextPos = nextPos - 12;
           ct.setSimpleColumn(700f, 50f, 20f, (nextPos));
           ct.addElement(new Paragraph("Overview", f));
           ct.go();
           
           nextPos = nextPos - 12;
           f= new com.itextpdf.text.Font(bf, 10);
           ct.setSimpleColumn(700f, 50f, 20f, (nextPos));
           ct.addElement(new Paragraph("Money Left to Budget: $" + BudgetPanel.left + "\nNet Gain/Loss: $" + (monthIncome.sumIncome() - user.sumEvents(events, month)), f));
           ct.go();
           
           document.close();
           writer.close();
        } catch (DocumentException e)
        {
           e.printStackTrace();
        } catch (FileNotFoundException e)
        {
           e.printStackTrace();
        }
        
        JOptionPane.showMessageDialog(null, "Report has been generated!");
    }
}

