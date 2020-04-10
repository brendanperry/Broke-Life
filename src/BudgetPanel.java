import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/*
 * This class contains the GUI for the Budget Panel and allows 
 * users to enter in transactions and income. From here, the user
 * can add, modify, and delete events. The event data will be loaded
 * from the user profile.
 * @author brendanperry
 * 03/20/20
 */

@SuppressWarnings("serial")
public class BudgetPanel extends JPanel {
		
	UserProfile user;
	
	DefaultTableModel model;
	JTable table;
	ArrayList<String[]> data;
	NumberFormat us;
	
	JTextField totalIncome;
	JTextField totalBudgeted;
	JTextField leftToBudget;
	
	JTextField payOne;
	JTextField payTwo;
	JTextField payThree;
	JTextField payFour;
	JTextField payFive;
	JTextField tips;
	
	double oldPayOne = 0.0;
	double oldPayTwo = 0.0;
	double oldPayThree = 0.0;
	double oldPayFour = 0.0;
	double oldTips = 0.0;
	double sum = 0.0;
	
	JTextField name;
	JTextField cost;
	JTextField day;
	JTextField percentage;
	@SuppressWarnings("rawtypes")
	JComboBox repeating;
	JTextField category;
	
	JButton submitChanges;
	
	JComboBox<String> eventComboBox;
	
	int tableYear = 2020;
	int tableMonth = 4;
	
	public BudgetPanel(UserProfile userProfile) {
		setLayout(new BorderLayout(0, 0));
		
		user = userProfile;
		
		data = new ArrayList<String[]>();
		us = NumberFormat.getCurrencyInstance(Locale.US);
		
		Color bgColor = Color.decode("#3e92cc");
		Color foregroundColor = Color.decode("#25ced1");
		
		// These are the two classes that handle user input 
		ActionHandler actionHandler = new ActionHandler();
		FocusHandler focusHandler = new FocusHandler();
		
		// EXPENSES PANEL
		
		JPanel expensesPanel = new JPanel();
		expensesPanel.setSize(new Dimension(300, 300));
		expensesPanel.setLayout(new BorderLayout(0, 0));
		expensesPanel.setBackground(bgColor);
		expensesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel expensesText = new JLabel("Expenses", SwingConstants.CENTER);
		expensesText.setForeground(Color.WHITE);
		expensesText.setFont(new Font("Arial", Font.BOLD, 20));
		expensesPanel.add(expensesText, BorderLayout.NORTH);
						
		// Disables the users ability to edit individual cells
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};	
		
		table = new JTable(model);
		table.getTableHeader().setBackground(foregroundColor);
		model.addColumn("Item");
		model.addColumn("Cost");
		model.addColumn("Percentage");
		model.addColumn("Day");
		model.addColumn("Repeating");
		model.addColumn("Category");
		
		// The table is in a scroll pane so more events than can fit in the pane can be added
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		expensesPanel.add(scrollPane, BorderLayout.CENTER);
		
		// RIGHT PANEL
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		// INCOME PANEL
		
		JPanel incomePanel = new JPanel();
		incomePanel.setBackground(bgColor);
		incomePanel.setPreferredSize(new Dimension(300, 300));
		incomePanel.setLayout(new GridLayout(8, 1));
		incomePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel incomeText = new JLabel("Income", SwingConstants.CENTER);
		incomeText.setForeground(Color.WHITE);
		incomeText.setFont(new Font("Arial", Font.BOLD, 20));
		incomePanel.add(incomeText);
		
		JLabel payText = new JLabel("PAY");
		incomePanel.add(payText);
		payText.setForeground(Color.WHITE);
		payText.setFont(new Font("Arial", Font.BOLD, 13));
		
		payOne = new JTextField();
		payOne.addActionListener(actionHandler);
		payOne.addFocusListener(focusHandler);
		
		payTwo = new JTextField();
		payTwo.addActionListener(actionHandler);
		payTwo.addFocusListener(focusHandler);
		
		payThree = new JTextField();
		payThree.addActionListener(actionHandler);
		payThree.addFocusListener(focusHandler);
		
		payFour = new JTextField();
		payFour.addActionListener(actionHandler);
		payFour.addFocusListener(focusHandler);
		
		incomePanel.add(payOne);
		incomePanel.add(payTwo);
		incomePanel.add(payThree);
		incomePanel.add(payFour);
		
		JLabel tipsText = new JLabel("TIPS");
		tipsText.setForeground(Color.WHITE);
		tipsText.setFont(new Font("Arial", Font.BOLD, 13));
		incomePanel.add(tipsText);
		
		tips = new JTextField();
		tips.addActionListener(actionHandler);
		tips.addFocusListener(focusHandler);
		
		incomePanel.add(tips);
		
		// REVIEW PANEL
		
		JPanel reviewPanel = new JPanel();
		reviewPanel.setPreferredSize(new Dimension(300,300));
		reviewPanel.setLayout(new GridLayout(7, 1));
		reviewPanel.setBackground(bgColor);
		reviewPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel reviewText = new JLabel("Review", SwingConstants.CENTER);
		reviewText.setForeground(Color.WHITE);
		reviewText.setFont(new Font("Arial", Font.BOLD, 20));
		
		JLabel totalIncomeText = new JLabel("TOTAL INCOME");
		totalIncomeText.setFont(new Font("Arial", Font.BOLD, 13));
		totalIncomeText.setForeground(Color.WHITE);
		totalIncome = new JTextField("$0.00");
		totalIncome.setEditable(false);
		totalIncome.setBackground(Color.WHITE);
		
		JLabel totalBudgetedText = new JLabel("TOTAL BUDGETED");
		totalBudgetedText.setFont(new Font("Arial", Font.BOLD, 13));
		totalBudgetedText.setForeground(Color.WHITE);
		totalBudgeted = new JTextField("$0.00");
		totalBudgeted.setEditable(false);
		totalBudgeted.setBackground(Color.WHITE);
		
		JLabel leftToBudgetText = new JLabel("LEFT TO BUDGET");
		leftToBudgetText.setFont(new Font("Arial", Font.BOLD, 13));
		leftToBudgetText.setForeground(Color.WHITE);
		leftToBudget = new JTextField("$0.00");
		leftToBudget.setEditable(false);
		leftToBudget.setBackground(Color.WHITE);
		
		reviewPanel.add(reviewText);
		reviewPanel.add(totalIncomeText);
		reviewPanel.add(totalIncome);
		reviewPanel.add(totalBudgetedText);
		reviewPanel.add(totalBudgeted);
		reviewPanel.add(leftToBudgetText);
		reviewPanel.add(leftToBudget);
		
		rightPanel.add(incomePanel, BorderLayout.CENTER);
		rightPanel.add(reviewPanel, BorderLayout.SOUTH);
		rightPanel.setSize(300, 300);
		
		// EVENT PANEL
		
		JPanel eventPanel = new JPanel();
						
		name = new JTextField();
		name.setPreferredSize(new Dimension(100,30));
		cost = new JTextField("0");
		cost.setPreferredSize(new Dimension(100,30));
		percentage = new JTextField("0");
		percentage.setPreferredSize(new Dimension(100,30));
		day = new JTextField("1");
		day.setPreferredSize(new Dimension(100,30));
		String[] interactionsRepeating = {"None", "Daily", "Weekly", "Biweekly", "Monthly", "Yearly"};
		repeating = new JComboBox<String>(interactionsRepeating);
		category = new JTextField("Misc");
		category.setPreferredSize(new Dimension(100,30));
		
		JLabel nameText = new JLabel("Name");
		nameText.setFont(new Font("Arial", Font.BOLD, 13));
		JLabel costText = new JLabel("Cost");
		costText.setFont(new Font("Arial", Font.BOLD, 13));
		JLabel percentageText = new JLabel("Percentage");
		percentageText.setFont(new Font("Arial", Font.BOLD, 13));
		JLabel dayText = new JLabel("Day");
		dayText.setFont(new Font("Arial", Font.BOLD, 13));
		JLabel repeatingText = new JLabel("Repeating");
		repeatingText.setFont(new Font("Arial", Font.BOLD, 13));
		JLabel categoryText = new JLabel("Category");
		categoryText.setFont(new Font("Arial", Font.BOLD, 13));
		
		eventPanel.setBackground(foregroundColor);
		
		eventPanel.add(nameText);
		eventPanel.add(name);
		eventPanel.add(costText);
		eventPanel.add(cost);
		eventPanel.add(percentageText);
		eventPanel.add(percentage);
		eventPanel.add(dayText);
		eventPanel.add(day);
		eventPanel.add(repeatingText);
		eventPanel.add(repeating);
		eventPanel.add(categoryText);
		eventPanel.add(category);
				
		String[] interactions = {"Add Event", "Modify Event", "Delete Event"};
		eventComboBox = new JComboBox<String>(interactions);
		eventComboBox.addActionListener(actionHandler);
		eventComboBox.setBackground(foregroundColor);
		eventPanel.add(eventComboBox);
		
		submitChanges = new JButton("Add Row");
		submitChanges.setForeground(Color.WHITE);
		submitChanges.setBackground(bgColor);
		submitChanges.addActionListener(actionHandler);		
		eventPanel.add(submitChanges);
		
		add(expensesPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		add(eventPanel, BorderLayout.SOUTH);
		
		try {
			LocalDate date = LocalDate.now();
			loadUserData(date.getMonthValue(), date.getYear());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * This function loads data about from the user profile and inserts it into the table
	 * @params userProfile is the profile of the current user
	 * @author brendanperry
	 */
	public void loadUserData(int month, int year) throws ParseException {
		clearData();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(Integer.toString(year) + "-" + Integer.toString(month) + "-01");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);		
		String m = Integer.toString(month);
		String y = Integer.toString(calendar.get(Calendar.YEAR));
		
		tableMonth = month;
		tableYear = calendar.get(Calendar.YEAR);
		
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
		
		Date start = format.parse(startDay);
		Date end = format.parse(endDay);

		Event[] events = user.getEvents(start, end);

		for(int i = 0; i < events.length; i++) {
			String title = events[i].getTitle();
			Double cost = events[i].getAmount();
			
			int repeating = events[i].getRecurPeriod();

			String percent = Integer.toString(events[i].getPercentage());
			String category = events[i].getTag();	
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(events[i].getDate());			
			String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			
			// name, cost, percent, day, repeating, category
			if(repeating == 0) {
				String[] newData = {title, us.format(cost), percent, day, "None", category};
				data.add(newData);
				model.addRow(newData);
			}
			else {
				String recurring = "None";
				
				if(repeating == 1) {
					recurring = "Daily";
				}
				else if(repeating == 7) {
					recurring = "Weekly";
				}
				else if(repeating == 14) {
					recurring = "Biweekly";
				}
				else if(repeating == 30) {
					recurring = "Monthly";
				}
				else if(repeating == 365) {
					recurring = "Yearly";
				}
				
				String[] newData = {title, us.format(cost), percent, day, recurring, category};
				
				data.add(newData);
				model.addRow(newData);
			}
		}
	}
	
	/*
	 * This functions removes all data that is stored and shown in the table.
	 * @author brendanperry
	 */
	private void clearData() {
		int size = data.size();
		
		for(int i = 0; i < size; i++) {
			data.remove(0);
			model.removeRow(0);
		}
	}
	
	/*
	 * This function saves user data that has been added to the table.
	 * @params it takes info which is the list of all event information
	 * @author brendanperry
	 */
	private void saveUserData(String[] info) throws ParseException {
		ActionHandler actionHandler = new ActionHandler();
		
		// need to get month and year from header
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = info[3] + "/" + tableMonth + "/" + tableYear;
		
		Date dateObject = sdf.parse(dateString);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObject);
		
		Event event;
		
		//String title, double amount, Date date, String tag
		if(info[4].equals("None")) {
			// create non repeating event
			event = new Event(info[0], actionHandler.currencyToDouble(info[1]), Integer.parseInt(info[2]), dateObject, 0, info[5]);
		}
		else {
			System.out.println(info[4]);
			// create repeating event
			int recurPeriod = 0;
			if(info[4].equals("Weekly")) {
				System.out.println("Equals");
				recurPeriod = 7;
			}
			else if(info[4].equals("Biweekly")) {
				recurPeriod = 14;
			}
			else if(info[4].equals("Monthly")) {
				recurPeriod = 30;
			}
			else if(info[4].equals("Daily")) {
				recurPeriod = 1;
			}
			else {
				recurPeriod = 365;
			}
			
			System.out.println("RECUR: " + recurPeriod);
			System.out.println(info[0]);
			System.out.println(actionHandler.currencyToDouble(info[1]));
			System.out.println(Integer.parseInt(info[2]));
			System.out.println(dateObject);
			System.out.println(recurPeriod);
			System.out.println(info[5]);
			event = new Event(info[0], actionHandler.currencyToDouble(info[1]), Integer.parseInt(info[2]), dateObject, recurPeriod, info[5]);
		}
		
		user.addEvent(event);
	}
	
	/*
	 * The FocusHandler is used to see when the user has entered in text into the
	 * income section of the GUI. This is used to format the text when they are done
	 * and update other values.
	 * @author brendanperry
	 */
	public class FocusHandler implements FocusListener {
		
		/*
		 * focusLost fires when the user clicks out of one of the text boxes on the 
		 * income panel
		 * @params e is the focus event that can be used to see which text box called
		 * the function
		 * @author brendanperry
		 */
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource() == payOne) {				
				updateStats(payOne, 1);
			}
			else if(e.getSource() == payTwo) {
				updateStats(payTwo, 2);
			}
			else if(e.getSource() == payThree) {
				updateStats(payThree, 3);
			}
			else if(e.getSource() == payFour) {
				updateStats(payFour, 4);
			}
			else if(e.getSource() == tips) {
				updateStats(tips, 5);
			}
		}
		
		/*
		 * updateStats is used to update the the total income and left to budget text fields.
		 * It is called when the user adds new pay information.
		 * @params textField is the text field that the data should be updated from, num is the 
		 * paycheck number (1 - 5) with 5 being tips.
		 * @author brendanperry
		 */
		public void updateStats(JTextField textField, int num) {
			String text = textField.getText();
			Double temp = 0.00;
					
			try {
				if(!text.isEmpty()) {
					if(text.toCharArray()[0] == '$') {
						temp = Double.parseDouble(text.substring(1, text.length()));
					}
					else {
						temp = Double.parseDouble(text);
					}
					
					if(temp < 0) {
						temp = 0.0;
					}
					
					textField.setText(us.format(temp));
				}
			
				if(num == 1) {
					sum -= oldPayOne;
					sum += temp;
					oldPayOne = temp;
				}
				else if(num == 2) {
					sum -= oldPayTwo;
					sum += temp;
					oldPayTwo = temp;
				}
				else if(num == 3) {
					sum -= oldPayThree;
					sum += temp;
					oldPayThree = temp;
				}
				else if(num == 4) {
					sum -= oldPayFour;
					sum += temp;
					oldPayFour = temp;
				}
				else if(num == 5) {
					sum -= oldTips;
					sum += temp;
					oldTips = temp;
				}
				
				totalIncome.setText(us.format(sum));

				ActionHandler actionHandler = new ActionHandler();
				actionHandler.updatePercentages();
				
				double left = sum - actionHandler.currencyToDouble(totalBudgeted.getText());
				if(left < 0) {
					leftToBudget.setText("$0.00");
				}
				else {
					leftToBudget.setText(us.format(sum - actionHandler.currencyToDouble(totalBudgeted.getText())));
				}			
			}
			catch (Exception e) {
				totalIncome.setText("NaN");
				leftToBudget.setText("NaN");
				
				if(num == 1) {
					sum -= oldPayOne;
					sum += temp;
					oldPayOne = temp;
				}
				else if(num == 2) {
					sum -= oldPayTwo;
					sum += temp;
					oldPayTwo = temp;
				}
				else if(num == 3) {
					sum -= oldPayThree;
					sum += temp;
					oldPayThree = temp;
				}
				else if(num == 4) {
					sum -= oldPayFour;
					sum += temp;
					oldPayFour = temp;
				}
				else if(num == 5) {
					sum -= oldTips;
					sum += temp;
					oldTips = temp;
				}
			}
		}
		
		/*
		 * focusGained is not used but required by Java
		 */
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/*
	 * ActionHandler is responsible for the Add, Modify, and Delete event button.
	 * From here, the data will be updated based on the value of the button at the time.
	 * It is also used for the combo boxes for swapping between add, modify, and delete and for
	 * changing the repeating frequency.
	 * @author brendanperry
	 */
	public class ActionHandler implements ActionListener {
		
		/*
		 * actionPerformed is called when the button or a combo box is clicked.
		 * @params e provides action event data that can be used to see what called the function.
		 * @author brendanperry
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submitChanges) {
				
				if(eventComboBox.getSelectedIndex() == 0) {
					// add event
					String[] newData = {name.getText(), cost.getText(), percentage.getText(), day.getText(), repeating.getSelectedItem().toString(), category.getText()};
					String[] checkedData = checkDataValidity(newData);
					
					if(checkedData != null) {						
						double num = currencyToDouble(checkedData[1]);
						totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) + num));
						double left = sum - currencyToDouble(totalBudgeted.getText());
						
						if(left < 0) {
							leftToBudget.setText("$0.00");
						}
						else {
							leftToBudget.setText(us.format(sum - currencyToDouble(totalBudgeted.getText())));
						}
						
						data.add(checkedData);
						model.addRow(checkedData);
						
						try {
							saveUserData(checkedData);
							System.out.println("Data save initiated");
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						name.setText("");
						cost.setText("0");
						percentage.setText("0");
						day.setText("1");
						repeating.setSelectedIndex(0);
						category.setText("default");
					}
				}
				else if(eventComboBox.getSelectedIndex() == 1) {
					// modify event
					if(model.getRowCount() == 0) {
						return;
					}
					
					String[] dataToRemove = {name.getText(), cost.getText(), percentage.getText(), day.getText(), repeating.getSelectedItem().toString(), category.getText()};
					String[] checkedData = checkDataValidity(dataToRemove);
					
					if(checkedData != null) {
						int row = table.getSelectedRow();
						
						if(row != -1) {
							double oldCost = currencyToDouble(data.get(row)[1]);
							
							for(int i = 0; i < 6; i++) {
								data.get(row)[i] = checkedData[i];
								model.setValueAt(checkedData[i], row, i);
							}
							
							totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) - oldCost));
							double numToAdd = currencyToDouble(checkedData[1]);
							totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) + numToAdd));
							double left = sum - currencyToDouble(totalBudgeted.getText());
							
							if(left < 0) {
								leftToBudget.setText("$0.00");
							}
							else {
								leftToBudget.setText(us.format(sum - currencyToDouble(totalBudgeted.getText())));
							}
							
							name.setText("");
							cost.setText("0");
							percentage.setText("0");
							day.setText("1");
							repeating.setSelectedIndex(0);
							category.setText("default");
						}
					}
				}
				else {
					// delete event
					int row = table.getSelectedRow();
					
					if(row != -1) {
						totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) - currencyToDouble(data.get(row)[1])));
						leftToBudget.setText(us.format(currencyToDouble(totalIncome.getText()) - currencyToDouble(totalBudgeted.getText())));
						
						data.remove(row);
						model.removeRow(row);
					}
				}
			}
			
			// changes the text on the button
			if(e.getSource() == eventComboBox) {
				if(eventComboBox.getSelectedIndex() == 0) {
					// add event
					submitChanges.setText("Add Row");
				}
				else if(eventComboBox.getSelectedIndex() == 1) {
					// modify event
					submitChanges.setText("Modify Selected Row");
				}
				else {
					// delete event
					submitChanges.setText("Delete Selected Row");
				}
			}
			
			FocusHandler focusHandler = new FocusHandler();
			
			if(e.getSource() == payOne) {				
				focusHandler.updateStats(payOne, 1);
			}
			else if(e.getSource() == payTwo) {
				focusHandler.updateStats(payTwo, 2);
			}
			else if(e.getSource() == payThree) {
				focusHandler.updateStats(payThree, 3);
			}
			else if(e.getSource() == payFour) {
				focusHandler.updateStats(payFour, 4);
			}
			else if(e.getSource() == tips) {
				focusHandler.updateStats(tips, 5);
			}
		}
		
		/*
		 * This function is used to update the cost of items that depend on a certain percentage
		 * of the users income. It will be called whenever the total income has changed.
		 * @author brendanperry
		 */
		public void updatePercentages() {
			for(int i = 0; i < data.size(); i++) {
				if(Integer.parseInt(data.get(i)[2]) > 0) {
					double oldValue = currencyToDouble(data.get(i)[1]);
					double newValue = currencyToDouble(data.get(i)[2]) / 100.0 * currencyToDouble(totalIncome.getText());
					
					totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) - oldValue));
					totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) + newValue));
					data.get(i)[1] = us.format(newValue);
					model.setValueAt(data.get(i)[1], i, 1);
				}
			}
		}
		
		/*
		 * This function is used to convert text that is formatted using NumberFormat to a double value.
		 * @params numberText is the formatted text to be converted
		 * @return returns a decimal value with no '$' or ','
		 * @author brendanperry
		 */
		public double currencyToDouble(String numberText) {
			String noFormatting = numberText.replaceAll("[$,]", "");
			return Double.parseDouble(noFormatting);
		}
		
		/*
		 * This method checks the data to see if its valid and properly formats it for the table
		 * @author brendanperry
		 * @params String[] data is an array of all the event data
		 * @return returns the newly formatted string array
		 */
		private String[] checkDataValidity(String[] data) {
			String[] returnData = data;
			
			for(int i = 0; i < 5; i++) {
				if(data[i].isEmpty()) {
					JOptionPane.showMessageDialog(null, "Missing Data");
					return null;
				}
			}
			
			try {
				int percent = Integer.parseInt(data[2]);
				if(percent < 0 || percent > 100) {
					JOptionPane.showMessageDialog(null, "Invalid Percentage");
					return null;
				}
				else {
					double cost = -1;
					
					if(percent > 0) {
						cost = (percent / 100.0) * currencyToDouble(totalIncome.getText());
						returnData[1] = us.format(cost);
					}
					else {
						try {
							cost = currencyToDouble(data[1]);
							
							if(cost < 0) {
								JOptionPane.showMessageDialog(null, "Invalid Cost");
								return null;
							}
							
							returnData[1] = us.format(cost);
						}
						catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Invalid Cost");
							return null;
						}
					}
				}
			}
			catch(Exception per) {
				JOptionPane.showMessageDialog(null, "Invalid Percentage");
				return null;
			}
			
			int day = -1;
			
			try {
				day = Integer.parseInt(data[3]);
				if(day < 0 || day > 31) {
					JOptionPane.showMessageDialog(null, "Invalid Day");
					return null;
				}
			}
			catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid Day");
				return null;
			}
			
			return returnData;
		}
	}
}