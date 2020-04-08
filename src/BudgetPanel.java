import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.util.ArrayList;
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
	
	public BudgetPanel(UserProfile user) {
		setLayout(new BorderLayout(0, 0));
		
		data = new ArrayList<String[]>();
		us = NumberFormat.getCurrencyInstance(Locale.US);
		
		// These are the two classes that handle user input 
		ActionHandler actionHandler = new ActionHandler();
		FocusHandler focusHandler = new FocusHandler();
		
		// EXPENSES PANEL
		
		JPanel expensesPanel = new JPanel();
		expensesPanel.setSize(new Dimension(300, 300));
		expensesPanel.setLayout(new BorderLayout(0, 0));
		expensesPanel.setBackground(Color.GRAY);
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
		model.addColumn("Item");
		model.addColumn("Cost");
		model.addColumn("Percentage");
		model.addColumn("Day");
		model.addColumn("Repeating");
		model.addColumn("Category");
		
		// The table is in a scroll pane so more events than can fit in the pane can be added
		JScrollPane scrollPane = new JScrollPane(table);
		
		expensesPanel.add(scrollPane, BorderLayout.CENTER);
		
		// RIGHT PANEL
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		// INCOME PANEL
		
		JPanel incomePanel = new JPanel();
		incomePanel.setBackground(Color.GRAY);
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
		incomePanel.add(tipsText);
		
		tips = new JTextField();
		tips.addActionListener(actionHandler);
		tips.addFocusListener(focusHandler);
		
		incomePanel.add(tips);
		
		// REVIEW PANEL
		
		JPanel reviewPanel = new JPanel();
		reviewPanel.setPreferredSize(new Dimension(300,300));
		reviewPanel.setLayout(new GridLayout(7, 1));
		reviewPanel.setBackground(Color.GRAY);
		reviewPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel reviewText = new JLabel("Review", SwingConstants.CENTER);
		reviewText.setForeground(Color.WHITE);
		reviewText.setFont(new Font("Arial", Font.BOLD, 20));
		
		JLabel totalIncomeText = new JLabel("Total Income");
		totalIncomeText.setForeground(Color.WHITE);
		totalIncome = new JTextField("$0.00");
		totalIncome.setEditable(false);
		
		JLabel totalBudgetedText = new JLabel("Total Budgeted");
		totalBudgetedText.setForeground(Color.WHITE);
		totalBudgeted = new JTextField("$0.00");
		totalBudgeted.setEditable(false);
		
		JLabel leftToBudgetText = new JLabel("Left to Budget");
		leftToBudgetText.setForeground(Color.WHITE);
		leftToBudget = new JTextField("$0.00");
		leftToBudget.setEditable(false);
		
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
		eventPanel.setLayout(new BorderLayout());
		
		String[] interactions = {"Add Event", "Modify Event", "Delete Event"};
		eventComboBox = new JComboBox<String>(interactions);
		eventComboBox.addActionListener(actionHandler);
		eventPanel.add(eventComboBox, BorderLayout.NORTH);
		
		JPanel centerEventPanel = new JPanel();
		
		name = new JTextField();
		name.setPreferredSize(new Dimension(100,20));
		cost = new JTextField("0");
		cost.setPreferredSize(new Dimension(100,20));
		percentage = new JTextField("0");
		percentage.setPreferredSize(new Dimension(100,20));
		day = new JTextField("1");
		day.setPreferredSize(new Dimension(100,20));
		String[] interactionsRepeating = {"None", "Weekly", "Biweekly", "Monthly", "Yearly"};
		repeating = new JComboBox<String>(interactionsRepeating);
		category = new JTextField("default");
		category.setPreferredSize(new Dimension(100,20));
		
		JLabel nameText = new JLabel("Name");
		JLabel costText = new JLabel("Cost");
		JLabel percentageText = new JLabel("Percentage");
		JLabel dayText = new JLabel("Day");
		JLabel repeatingText = new JLabel("Repeating");
		JLabel categoryText = new JLabel("Category");
		
		centerEventPanel.add(nameText);
		centerEventPanel.add(name);
		centerEventPanel.add(costText);
		centerEventPanel.add(cost);
		centerEventPanel.add(percentageText);
		centerEventPanel.add(percentage);
		centerEventPanel.add(dayText);
		centerEventPanel.add(day);
		centerEventPanel.add(repeatingText);
		centerEventPanel.add(repeating);
		centerEventPanel.add(categoryText);
		centerEventPanel.add(category);
				
		eventPanel.add(centerEventPanel, BorderLayout.CENTER);
		
		submitChanges = new JButton("Add");
		submitChanges.addActionListener(actionHandler);
		eventPanel.add(submitChanges, BorderLayout.SOUTH);
		
		add(expensesPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		add(eventPanel, BorderLayout.SOUTH);
	}
	
	private void loadUserData(UserProfile userProfile) {
		Date startDate = new Date();
		Date endDate = new Date();
		Event[] events = userProfile.getEvents(startDate, endDate);
		
		for(int i = 0; i < events.length; i++) {
			String title = events[i].getTitle();
			Double cost = events[i].getAmount();
			int repeating = events[i].getRecurPeriod();
			String category = events[i].getTag();	
			String day = Integer.toString(events[i].getDate().getDay());
			
			String[] newData = {title, us.format(cost), "10", day, category};
			
			data.add(newData);
			model.addColumn(newData);
		}
	}
	
	private void clearData() {
		for(int i = 0; i < data.size(); i++) {
			data.remove(i);
			model.removeRow(i);
		}
	}
	
	private void saveUserData(UserProfile userProfile, String[] info) {
		Date date = new Date();
		// convert cost to double
		// convert day to date
		Event event = new Event(info[0], info[1], info[2], info[3], info[4]);
		userProfile.addEvent(event);
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