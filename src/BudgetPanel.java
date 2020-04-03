/*
 * Contains the GUI for the budget panel
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
	
	JTextField name;
	JTextField cost;
	JTextField day;
	JTextField repeating;
	JTextField category;
	
	JTextField nameModified;
	JTextField costModified;
	JTextField dayModified;
	JTextField repeatingModified;
	JTextField categoryModified;
	
	JPanel modifiedEventPanel;
	
	JButton submitChanges;
	JPanel centerAddPanel;
	
	JComboBox<String> comboBox;
	
	public BudgetPanel(UserProfile user) {
		setLayout(new BorderLayout(0, 0));
		
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
		
		JTextField payOne = new JTextField();
		JTextField payTwo = new JTextField();
		JTextField payThree = new JTextField();
		JTextField payFour = new JTextField();
		
		incomePanel.add(payOne);
		incomePanel.add(payTwo);
		incomePanel.add(payThree);
		incomePanel.add(payFour);
		
		JLabel tipsText = new JLabel("TIPS");
		tipsText.setForeground(Color.WHITE);
		incomePanel.add(tipsText);
		
		JTextField tips = new JTextField();
		
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
		JTextField totalIncome = new JTextField("$500.00");
		totalIncome.setEditable(false);
		
		JLabel totalBudgetedText = new JLabel("Total Budgeted");
		totalBudgetedText.setForeground(Color.WHITE);
		JTextField totalBudgeted = new JTextField("$400.00");
		totalBudgeted.setEditable(false);
		
		JLabel leftToBudgetText = new JLabel("Left to Budget");
		leftToBudgetText.setForeground(Color.WHITE);
		JTextField leftToBudget = new JTextField("$100.00");
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
		
		JTabbedPane eventPanel = new JTabbedPane();
		
		// ADD EVENT TAB
		
		JPanel addEventPanel = new JPanel();
		addEventPanel.setLayout(new BorderLayout());
		
		String[] interactions = {"Add", "Modify", "Delete"};
		comboBox = new JComboBox<String>(interactions);
		ActionHandler actionHandler = new ActionHandler();
		comboBox.addActionListener(actionHandler);
		addEventPanel.add(comboBox, BorderLayout.NORTH);
		centerAddPanel = new JPanel();
		
		name = new JTextField();
		name.setPreferredSize(new Dimension(100,20));
		cost = new JTextField();
		cost.setPreferredSize(new Dimension(100,20));
		day = new JTextField();
		day.setPreferredSize(new Dimension(100,20));
		repeating = new JTextField();
		repeating.setPreferredSize(new Dimension(100,20));
		category = new JTextField();
		category.setPreferredSize(new Dimension(100,20));
		
		JLabel nameText = new JLabel("Name");
		JLabel costText = new JLabel("Cost");
		JLabel dayText = new JLabel("Day");
		JLabel repeatingText = new JLabel("Repeating");
		JLabel categoryText = new JLabel("Category");
		
		centerAddPanel.add(nameText);
		centerAddPanel.add(name);
		centerAddPanel.add(costText);
		centerAddPanel.add(cost);
		centerAddPanel.add(dayText);
		centerAddPanel.add(day);
		centerAddPanel.add(repeatingText);
		centerAddPanel.add(repeating);
		centerAddPanel.add(categoryText);
		centerAddPanel.add(category);
		
		// Only needed if modify event is selected
		
		modifiedEventPanel = new JPanel();
		
		nameModified = new JTextField();
		nameModified.setPreferredSize(new Dimension(100,20));
		costModified = new JTextField();
		costModified.setPreferredSize(new Dimension(100,20));
		dayModified = new JTextField();
		dayModified.setPreferredSize(new Dimension(100,20));
		repeatingModified = new JTextField();
		repeatingModified.setPreferredSize(new Dimension(100,20));
		categoryModified = new JTextField();
		categoryModified.setPreferredSize(new Dimension(100,20));
		
		JLabel modifiedText = new JLabel("Updated Information");
		JLabel nameTextModified = new JLabel("Name");
		JLabel costTextModified = new JLabel("Cost");
		JLabel dayTextModified = new JLabel("Day");
		JLabel repeatingTextModified = new JLabel("Repeating");
		JLabel categoryTextModified = new JLabel("Category");
		
		
		modifiedEventPanel.add(nameTextModified);
		modifiedEventPanel.add(nameModified);
		modifiedEventPanel.add(costTextModified);
		modifiedEventPanel.add(costModified);
		modifiedEventPanel.add(dayTextModified);
		modifiedEventPanel.add(dayModified);
		modifiedEventPanel.add(repeatingTextModified);
		modifiedEventPanel.add(repeatingModified);
		modifiedEventPanel.add(categoryTextModified);
		modifiedEventPanel.add(categoryModified);
		
		addEventPanel.add(centerAddPanel, BorderLayout.CENTER);
		
		submitChanges = new JButton("Add Event");
		submitChanges.addActionListener(actionHandler);
		addEventPanel.add(submitChanges, BorderLayout.SOUTH);
		
		eventPanel.addTab("ADD", addEventPanel);
		
		
		
		
		
		
		
		
		add(eventPanel, BorderLayout.SOUTH);
		add(expensesPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
	
	public class ActionHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submitChanges) {
				
				if(comboBox.getSelectedIndex() == 0) {
					// add event
					model.addRow(new Object[] {name.getText(), cost.getText(), day.getText(), repeating.getText(), category.getText()});
				}
				else if(comboBox.getSelectedIndex() == 1) {
					// modify event
					
				}
				else {
					// delete event
				}
				
				name.setText("");
				cost.setText("");
				day.setText("");
				repeating.setText("");
				category.setText("");
			}
			
			if(e.getSource() == comboBox) {
				if(comboBox.getSelectedIndex() == 0) {
					// add event
					submitChanges.setText("Add Event");
				}
				else if(comboBox.getSelectedIndex() == 1) {
					// modify event
					submitChanges.setText("Modify Event");
				}
				else {
					// delete event
					submitChanges.setText("Delete Event");
				}
			}
		}
	}
}