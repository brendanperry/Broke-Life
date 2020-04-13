/**
 /**
 * GUI class providing a calendar view of Events associated with a given UserProfile
 * Displays Events scheduled for a selected date as well as upcoming Events 
 * 04/03/2020
 * Zach Barrow
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {
	private JLabel lblCurrentMonth;
	private JButton btnNextMonth;
	private JButton btnPreviousMonth;
	private JLabel lblSelectedDay;
	private DefaultListModel<String> listUpcoming = new DefaultListModel<String>();
	private DefaultListModel<String> listSelected = new DefaultListModel<String>();
	private JList<String> listUpcomingEvents;
	private JList<String> listSelectedDayEvents;
	private DefaultTableModel monthTable;
	private JTable tblCalendar;
	private JScrollPane scrlCalendar;
	private GregorianCalendar gc;
	private int daysInCurrentMonth;
	private int realDay;
	private int realMonth;
	private int realYear;
	private int currentMonth;
	private int currentYear = 2020;
	private String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private UserProfile userProfile;
	private Event monthEvents[];
	
	public CalendarPanel(UserProfile user) {
		this.userProfile = user;
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
        		int selectedDay = Integer.parseInt(tblCalendar.getValueAt(row, column).toString());
        	try {
        		lblSelectedDay.setText(months[currentMonth] + " " + selectedDay + ", " + currentYear);
        		for(Event event : user.getEvents(new Date(selectedDay, currentMonth, currentYear), new Date(selectedDay, currentMonth, currentYear))) {
        			listSelected.addElement(event.toString());
        		}
        	} catch(NullPointerException n) {
        		lblSelectedDay.setText("Today: " + months[realMonth] + " " + realDay + ", " + realYear);
        		for(Event event : user.getEvents(new Date(realDay, realMonth, realYear), new Date(realDay+3, realMonth, realYear))) {
        			listUpcoming.addElement(event.toString());
        		}
        		
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
		listSelectedDayEvents = new JList<String>(listSelected);
		listSelectedDayEvents.setBorder(new LineBorder(new Color(0, 0, 0)));
		selectedDayPanel.add(listSelectedDayEvents);
		
		//Create panel used to display upcoming events
		JPanel upcomingEventsPanel = new JPanel();
		upcomingEventsPanel.setBounds(853, 306, 338, 283);
		add(upcomingEventsPanel, BorderLayout.CENTER);
		upcomingEventsPanel.setLayout(new BorderLayout(0, 0));
		upcomingEventsPanel.setBackground(Color.decode("#3e92cc"));
		//List that will eventually hold String values of upcoming Events
		listUpcomingEvents = new JList<String>(listUpcoming);
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
        daysInCurrentMonth = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int weekdayOfMonthBeginning = gc.get(GregorianCalendar.DAY_OF_WEEK);
        
        //Fill table with days
        
        for (int i=1; i <= daysInCurrentMonth; i++){
            int row = new Integer((i + weekdayOfMonthBeginning-2)/7);
            int column  =  (i + weekdayOfMonthBeginning-2)%7;
            tblCalendar.setValueAt(i, row, column);
            
            
            
        }
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());

	}
	
	private class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            setBackground(new Color(255, 255, 255));
            if(value != null) {
            	if((Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear)) {
        			setBackground(new Color(120, 220, 255));
        		}
            }
            
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
            int month = currentMonth + 1;
			String m = Integer.toString(month);
			String y = Integer.toString(currentYear);
			
			String startDay = y + "-" + m + "-" + "1";
			String endDay = y + "-" + m + "-";
			
			if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				// last day is 31st
				endDay += "31";
			}
			else if(month == 2) {
				// last day is 28th
				endDay += "28";
			}
			else {
				// last day is 30th
				endDay += "30";
			}
			
			Date start = null;
			Date end = null;
			try {
				start = format.parse(startDay);
				end = format.parse(endDay);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            monthEvents = userProfile.getEvents(start, end);
            
            int eventDay = 0;
            for (int i = 0; i <monthEvents.length; i++) {
            	Calendar cal = Calendar.getInstance();
				cal.setTime(monthEvents[i].getDate());			
				eventDay = cal.get(Calendar.DAY_OF_MONTH);
				
            if (value != null){
            	
            		if (Integer.parseInt(value.toString()) == eventDay && currentMonth == realMonth && currentYear == realYear){ //Event Days
            			setBackground(new Color(220, 120, 255));
            		} 	
            	}
        	}
            setForeground(Color.BLACK);
            return this;
        }
    }

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}
	

	public String getCurrentMonthString() {
		return lblCurrentMonth.getText()  + " " + currentYear;
	}
}