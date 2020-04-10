/**
 /**
 * GUI class providing a calendar view of Events associated with a given UserProfile
 * Displays Events scheduled for a selected date as well as upcoming Events 
 * 04/03/2020
 * Zach Barrow
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {
	static JLabel lblCurrentMonth;
	static JButton btnNextMonth;
	static JButton btnPreviousMonth;
	static JLabel lblSelectedDay;
	static JList listUpcomingEvents;
	static JList listSelectedDayEvents;
	static DefaultTableModel monthTable;
	static JTable tblCalendar;
	static JScrollPane scrlCalendar;
	static GregorianCalendar gc;
	static int realDay;
	static int realMonth;
	static int realYear;
	static int currentMonth;
	static int currentYear = 2020;
	static String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	static String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

	
	public CalendarPanel(UserProfile user) {
		setLayout(null);
		setSize(1250,650);
		setLayout(null);
		setBackground(Color.decode("#3e92cc"));
		
		lblCurrentMonth = new JLabel("January");
		lblCurrentMonth.setForeground(Color.WHITE);
		lblCurrentMonth.setFont(new Font("Arial", Font.BOLD, 16));
		lblCurrentMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentMonth.setBounds(34, 0, 717, 63);
		add(lblCurrentMonth);
		
		//Creation of the calendar
		JPanel monthlyCalendarPanel = new JPanel();
		monthlyCalendarPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		monthlyCalendarPanel.setBounds(34, 61, 717, 528);
		monthTable = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int columnIndex){return false;}};
        monthlyCalendarPanel.setLayout(new BorderLayout(0, 0));
        monthlyCalendarPanel.setBackground(Color.decode("#3e92cc"));
        tblCalendar = new JTable(monthTable);
        scrlCalendar = new JScrollPane(tblCalendar);
        scrlCalendar.setBorder(null);
        scrlCalendar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        monthlyCalendarPanel.add(scrlCalendar);
        
        //The GregorianCalendar is used to get the date.
        //currentMonth and currentYear are initialized to the actual date and
        //are used to index the calendar over time.
        gc = new GregorianCalendar(); 
        realDay = gc.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = gc.get(GregorianCalendar.MONTH); 
        realYear = gc.get(GregorianCalendar.YEAR); 
        currentMonth = realMonth; 
        currentYear = realYear;
        
        for (int i = 0; i < days.length; i++){
            monthTable.addColumn(days[i]);
        }
        
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Used to select a certain day in order to display the Events scheduled
        tblCalendar.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		int row = tblCalendar.rowAtPoint(e.getPoint());
        		int column = tblCalendar.columnAtPoint(e.getPoint());
        	try {
        		lblSelectedDay.setText(months[currentMonth] + " " + tblCalendar.getValueAt(row, column).toString() + ", " + currentYear);
        	} catch(NullPointerException n) {
        		lblSelectedDay.setText("Today: " + months[realMonth] + " " + realDay + ", " + realYear);
        	}
    	}
        });
        
        
        tblCalendar.setRowHeight(83);
        monthTable.setColumnCount(7);
        monthTable.setRowCount(6);
     
		add(monthlyCalendarPanel, BorderLayout.CENTER);
		
		refreshCalendar(realMonth, realYear);
		
		//Create panel used to display events for the selected day
		JPanel selectedDayPanel = new JPanel();
		selectedDayPanel.setBounds(853, 5, 338, 283);
		add(selectedDayPanel);
		selectedDayPanel.setLayout(new BorderLayout(0, 0));
		selectedDayPanel.setBackground(Color.decode("#3e92cc"));
		
		//Selected day defaults to current date
		lblSelectedDay = new JLabel("Today: " + months[realMonth] + " " + realDay + ", " + realYear);
		lblSelectedDay.setForeground(Color.WHITE);
		lblSelectedDay.setFont(new Font("Arial", Font.BOLD, 18));
		lblSelectedDay.setHorizontalAlignment(SwingConstants.CENTER);
		selectedDayPanel.add(lblSelectedDay, BorderLayout.NORTH);
		
		//List that will eventually hold String values of Events concerned with selected day
		listSelectedDayEvents = new JList();
		listSelectedDayEvents.setBorder(new LineBorder(new Color(0, 0, 0)));
		selectedDayPanel.add(listSelectedDayEvents);
		
		//Create panel used to display upcoming events
		JPanel upcomingEventsPanel = new JPanel();
		upcomingEventsPanel.setBounds(853, 306, 338, 283);
		add(upcomingEventsPanel, BorderLayout.CENTER);
		upcomingEventsPanel.setLayout(new BorderLayout(0, 0));
		upcomingEventsPanel.setBackground(Color.decode("#3e92cc"));
		//List that will eventually hold String values of upcoming Events
		listUpcomingEvents = new JList();
		listUpcomingEvents.setBorder(new LineBorder(new Color(0, 0, 0)));
		upcomingEventsPanel.add(listUpcomingEvents);
		
		JLabel lblUpcomingEvents = new JLabel("Upcoming Events");
		lblUpcomingEvents.setForeground(Color.WHITE);
		lblUpcomingEvents.setFont(new Font("Arial", Font.BOLD, 18));
		
		upcomingEventsPanel.add(lblUpcomingEvents, BorderLayout.NORTH);
		lblUpcomingEvents.setHorizontalAlignment(SwingConstants.CENTER);
		show();
	}
	
	//Cycles through months as next and previous buttons are clicked, correctly formatting the calendar for each month
	public void refreshCalendar(int month, int year){
        //Update month labels
        lblCurrentMonth.setText(months[month]);
        
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                tblCalendar.setValueAt(null, i, j);
            }
        }
        
        //Determine month length and weekday of first of the month
        gc = new GregorianCalendar(year, month, 1);
        int daysInCurrentMonth = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int weekdayOfMonthBeginning = gc.get(GregorianCalendar.DAY_OF_WEEK);
        
        //Fill table with days
        for (int i=1; i <= daysInCurrentMonth; i++){
            int row = new Integer((i + weekdayOfMonthBeginning-2)/7);
            int column  =  (i + weekdayOfMonthBeginning-2)%7;
            tblCalendar.setValueAt(i, row, column);
        }

	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		CalendarPanel.currentMonth = currentMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		CalendarPanel.currentYear = currentYear;
	}
	

	public String getCurrentMonthString() {
		return lblCurrentMonth.getText()  + " " + currentYear;
	}
}