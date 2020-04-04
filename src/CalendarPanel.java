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
	static JLabel lblCalendarMonth;
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
		getContentPane().setLayout(null);
		setSize(470,375);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JLabel calendarText = new JLabel("Calendar");
		calendarText.setHorizontalAlignment(SwingConstants.CENTER);
		calendarText.setBounds(168, 5, 114, 14);
		getContentPane().add(calendarText);
		
		lblCurrentMonth = new JLabel("January");
		lblCurrentMonth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurrentMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentMonth.setBounds(168, 28, 114, 23);
		getContentPane().add(lblCurrentMonth);
		
		btnNextMonth = new JButton("\u2192");
		btnNextMonth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNextMonth.setBounds(376, 30, 55, 23);
		getContentPane().add(btnNextMonth);
		
		btnPreviousMonth = new JButton("\u2190");
		btnPreviousMonth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPreviousMonth.setBounds(23, 30, 55, 23);
		getContentPane().add(btnPreviousMonth);
		
		btnPreviousMonth.addActionListener(new btnPreviousMonthClicked());
        btnNextMonth.addActionListener(new btnNextMonthClicked());
        
		lblCalendarMonth = new JLabel("January");
		lblCalendarMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblCalendarMonth.setBounds(23, 55, 235, 23);
		getContentPane().add(lblCalendarMonth);		
		
		//Creation of the calendar
		JPanel monthlyCalendarPanel = new JPanel();
		monthlyCalendarPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		monthlyCalendarPanel.setBounds(23, 77, 235, 248);
		monthTable = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int columnIndex){return false;}};
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
        
        
        tblCalendar.setRowHeight(38);
        monthTable.setColumnCount(7);
        monthTable.setRowCount(6);
     
		getContentPane().add(monthlyCalendarPanel);
		monthlyCalendarPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		refreshCalendar(realMonth, realYear);
		
		//Create panel used to display events for the selected day
		JPanel selectedDayPanel = new JPanel();
		selectedDayPanel.setBounds(276, 55, 155, 155);
		getContentPane().add(selectedDayPanel);
		selectedDayPanel.setLayout(null);
		
		//Selected day defaults to current date
		lblSelectedDay = new JLabel("Today: " + months[realMonth] + " " + realDay + ", " + realYear);
		lblSelectedDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedDay.setBounds(0, 0, 155, 23);
		selectedDayPanel.add(lblSelectedDay);
		
		//List that will eventually hold String values of Events concerned with selected day
		listSelectedDayEvents = new JList();
		listSelectedDayEvents.setBounds(10, 23, 135, 121);
		selectedDayPanel.add(listSelectedDayEvents);
		
		//Create panel used to display upcoming events
		JPanel upcomingEventsPanel = new JPanel();
		upcomingEventsPanel.setBounds(276, 215, 155, 110);
		getContentPane().add(upcomingEventsPanel);
		upcomingEventsPanel.setLayout(null);
		
		JLabel lblUpcomingEvents = new JLabel("Upcoming Events");
		lblUpcomingEvents.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpcomingEvents.setBounds(0, 0, 155, 19);
		upcomingEventsPanel.add(lblUpcomingEvents);
		
		//List that will eventually hold String values of upcoming Events
		listUpcomingEvents = new JList();
		listUpcomingEvents.setBounds(10, 21, 135, 89);
		upcomingEventsPanel.add(listUpcomingEvents);
		
		//Display CalendarPanel
		setVisible(true);
	}
	
	//Cycles through months as next and previous buttons are clicked, correctly formatting the calendar for each month
	public static void refreshCalendar(int month, int year){
        //Update month labels
        lblCurrentMonth.setText(months[month] + " " + currentYear); 
        lblCalendarMonth.setText(months[month]);
        
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
	
	//If currentMonth is January, decrement current year and change current month to December
    //Otherwise, change currentMonth to previous month
	//Refresh calendar
	static class btnPreviousMonthClicked implements ActionListener{
        public void actionPerformed (ActionEvent e){
        	if (currentMonth == 0){
                currentMonth = 11;
                currentYear--;
            } else{
                currentMonth--;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
	
	//If currentMonth is December, increment current year and change current month to January
    //Otherwise, change currentMonth to next month
	//Refresh calendar
    static class btnNextMonthClicked implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){
                currentMonth = 0;
                currentYear++;
            } else {
                currentMonth++;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    
//	public static void main(String[] args) {
//		CalendarPanel cp = new CalendarPanel();
//	}
}
