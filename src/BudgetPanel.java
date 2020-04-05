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
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/*
 * This class contains the GUI for the Budget Panel and allows 
 * users to enter in transactions and income.
 * @author brendanperry
 * 03/20/20
 */

@SuppressWarnings("serial")
public class BudgetPanel extends JPanel {
		
	DefaultTableModel model;
	ArrayList<String[]> data;
	
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
	@SuppressWarnings("rawtypes")
	JComboBox repeating;
	JTextField category;
	
	JTextField nameModified;
	JTextField costModified;
	JTextField dayModified;
	@SuppressWarnings("rawtypes")
	JComboBox repeatingModified;
	JTextField categoryModified;
	
	JPanel centerBottomPanel;
	JLabel modifiedText;
	JPanel bottomPanel;
	
	JButton submitChanges;
	JPanel centerAddPanel;
	
	JComboBox<String> comboBox;
	
	public BudgetPanel(UserProfile user) {
		setLayout(new BorderLayout(0, 0));
		data = new ArrayList<String[]>();
		
		ActionHandler actionHandler = new ActionHandler();
		
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
		
		
		
		JTable table = new JTable(model);
		model.addColumn("Item");
		model.addColumn("Cost");
		model.addColumn("Day");
		model.addColumn("Repeating");
		model.addColumn("Category");
		
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
		
		FocusHandler focusHandler = new FocusHandler();
		
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
		
		// Event Panel
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		
		// ADD EVENT TAB
		
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new BorderLayout());
		
		String[] interactions = {"Add Event", "Modify Event", "Delete Event"};
		comboBox = new JComboBox<String>(interactions);
		comboBox.addActionListener(actionHandler);
		eventPanel.add(comboBox, BorderLayout.NORTH);
		
		centerAddPanel = new JPanel();
		centerAddPanel.setLayout(new BorderLayout());
		JPanel centerTopPanel = new JPanel();
		centerBottomPanel = new JPanel();
		
		name = new JTextField();
		name.setPreferredSize(new Dimension(100,20));
		cost = new JTextField();
		cost.setPreferredSize(new Dimension(100,20));
		day = new JTextField();
		day.setPreferredSize(new Dimension(100,20));
		String[] interactionsRepeating = {"None", "Weekly", "Biweekly", "Monthly", "Yearly"};
		repeating = new JComboBox<String>(interactionsRepeating);
		category = new JTextField();
		category.setPreferredSize(new Dimension(100,20));
		
		JLabel nameText = new JLabel("Name");
		JLabel costText = new JLabel("Cost");
		JLabel dayText = new JLabel("Day");
		JLabel repeatingText = new JLabel("Repeating");
		JLabel categoryText = new JLabel("Category");
		
		centerTopPanel.add(nameText);
		centerTopPanel.add(name);
		centerTopPanel.add(costText);
		centerTopPanel.add(cost);
		centerTopPanel.add(dayText);
		centerTopPanel.add(day);
		centerTopPanel.add(repeatingText);
		centerTopPanel.add(repeating);
		centerTopPanel.add(categoryText);
		centerTopPanel.add(category);
		
		// Only needed if modify event is selected
				
		nameModified = new JTextField();
		nameModified.setPreferredSize(new Dimension(100,20));
		costModified = new JTextField();
		costModified.setPreferredSize(new Dimension(100,20));
		dayModified = new JTextField();
		dayModified.setPreferredSize(new Dimension(100,20));
		String[] interactionsRepeatingModified = {"None", "Weekly", "Biweekly", "Monthly", "Yearly"};
		repeatingModified = new JComboBox<String>(interactionsRepeatingModified);
		categoryModified = new JTextField();
		categoryModified.setPreferredSize(new Dimension(100,20));
		
		modifiedText = new JLabel("Updated Information");
		JLabel nameTextModified = new JLabel("Name");
		JLabel costTextModified = new JLabel("Cost");
		JLabel dayTextModified = new JLabel("Day");
		JLabel repeatingTextModified = new JLabel("Repeating");
		JLabel categoryTextModified = new JLabel("Category");
		
		centerBottomPanel.add(nameTextModified);
		centerBottomPanel.add(nameModified);
		centerBottomPanel.add(costTextModified);
		centerBottomPanel.add(costModified);
		centerBottomPanel.add(dayTextModified);
		centerBottomPanel.add(dayModified);
		centerBottomPanel.add(repeatingTextModified);
		centerBottomPanel.add(repeatingModified);
		centerBottomPanel.add(categoryTextModified);
		centerBottomPanel.add(categoryModified);
		
		centerAddPanel.add(centerTopPanel, BorderLayout.NORTH);
		centerAddPanel.add(modifiedText, BorderLayout.CENTER);
		centerAddPanel.add(centerBottomPanel, BorderLayout.SOUTH);
		
		centerBottomPanel.setVisible(false);
		modifiedText.setVisible(false);
		
		eventPanel.add(centerAddPanel, BorderLayout.CENTER);
		
		submitChanges = new JButton("Add");
		submitChanges.addActionListener(actionHandler);
		eventPanel.add(submitChanges, BorderLayout.SOUTH);
		
		bottomPanel.add(eventPanel, BorderLayout.CENTER);
		
		add(bottomPanel, BorderLayout.SOUTH);
		add(expensesPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
	
	public class FocusHandler implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

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
		
		public void updateStats(JTextField textField, int num) {
			
			String text = textField.getText();
			Double temp = 0.00;
			
			NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
			
			try {
				if(!text.isEmpty()) {
					if(text.toCharArray()[0] == '$') {
						temp = Double.parseDouble(text.substring(1, text.length()));
					}
					else {
						temp = Double.parseDouble(text);
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
				leftToBudget.setText(us.format(sum - Double.parseDouble(totalBudgeted.getText().substring(1, 4))));
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
	}
	
	public class ActionHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submitChanges) {
				
				if(comboBox.getSelectedIndex() == 0) {
					// add event
					String[] newData = {name.getText(), cost.getText(), day.getText(), repeating.getSelectedItem().toString(), category.getText()};
					
					String[] checkedData = checkDataValidity(newData);
					
					if(checkedData != null) {
					
						NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
						
						double num = currencyToDouble(checkedData[1]);
						
						totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) + num));
						
						data.add(checkedData);
						model.addRow(checkedData);
					}
				}
				else if(comboBox.getSelectedIndex() == 1) {
					// modify event
					if(model.getRowCount() == 0) {
						return;
					}
					
					String[] dataToRemove = {name.getText(), cost.getText(), day.getText(), repeating.getSelectedItem().toString(), category.getText()};
					String[] dataToAdd = {nameModified.getText(), costModified.getText(), dayModified.getText(), repeatingModified.getSelectedItem().toString(), categoryModified.getText()};
					
					String[] checkedData = checkDataValidity(dataToAdd);
					String[] removeCheckedData = checkDataValidity(dataToRemove);
					
					if(checkedData != null) {
						int row = removeData(data, removeCheckedData);
						
						if(row != -1) {
							data.add(checkedData);
							model.addRow(checkedData);
							
							data.remove(row);
							model.removeRow(row);
							
							NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
							double num = currencyToDouble(removeCheckedData[1]);
							totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) - num));
						
							double numToAdd = currencyToDouble(checkedData[1]);
							totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) + numToAdd));
							
						}
					}
				}
				else {
					// delete event
					if(model.getRowCount() == 0) {
						return;
					}
					
					String[] dataToRemove = {name.getText(), cost.getText(), day.getText(), repeating.getSelectedItem().toString(), category.getText()};
					
					String[] removeCheckedData = checkDataValidity(dataToRemove);
					
					if(removeCheckedData != null) {
						int row = removeData(data, removeCheckedData);
						
						if(row != -1) {
							data.remove(row);
							model.removeRow(row);
							
							NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
							
							double num = currencyToDouble(removeCheckedData[1]);
							
							totalBudgeted.setText(us.format(currencyToDouble(totalBudgeted.getText()) - num));
						}
					}
				}
			}
			
			if(e.getSource() == comboBox) {
				if(comboBox.getSelectedIndex() == 0) {
					// add event
					submitChanges.setText("Add");
					centerBottomPanel.setVisible(false);
					modifiedText.setVisible(false);
				}
				else if(comboBox.getSelectedIndex() == 1) {
					// modify event
					submitChanges.setText("Modify");
					centerBottomPanel.setVisible(true);
					modifiedText.setVisible(true);
				}
				else {
					// delete event
					submitChanges.setText("Delete");
					centerBottomPanel.setVisible(false);
					modifiedText.setVisible(false);
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
		
		public double currencyToDouble(String numberText) {
			String noFormatting = numberText.replaceAll("[$,]", "");
			return Double.parseDouble(noFormatting);
		}
		
		/*
		 * This method removes data from the users events and is reflected in the table.
		 * @author brendanperry
		 * @params String[] data is an array of all the event data
		 * @return int of row number to remove, -1 if not found
		 */
		private int removeData(ArrayList<String[]> data, String[] dataToRemove) {
			int row = -1;
			for(int i = 0; i < data.size(); i++) {
				int count = 0;
				for(int j = 0; j < 5; j++) {
					if(!data.get(i)[j].equals(dataToRemove[j])) {
						break;
					}
					else {
						count++;
					}
				}
				
				if(count == 5) {
					row = i;
					break;
				}
			}
								
			if(row == -1) {
				System.out.println("Not found");
				// Add a pop up
			}
			
			return row;
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
					System.out.println("Missing Data");
					return null;
				}
			}
						
			double cost = -1;
			try {
				cost = currencyToDouble(data[1]);
				
				NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
				returnData[1] = us.format(cost);
			}
			catch (Exception ex) {
				// pop up here
				System.out.println("Invalid Cost");
				return null;
			}
			
			int day = -1;
			
			try {
				day = Integer.parseInt(data[2]);
				if(day < 0 || day > 31) {
					System.out.println("Invalid Day");
					return null;
				}
			}
			catch (Exception exc) {
				System.out.println("Invalid Day");
				return null;
			}
			
			return returnData;
		}
	}
}