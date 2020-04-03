/*
 * Contains the GUI for the budget panel
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/*
 * This class contains the GUI for the Budget Panel and allows 
 * users to enter in transactions and income.
 * @author brendanperry
 * 03/20/20
 */

@SuppressWarnings("serial")
public class BudgetPanel extends JPanel {
	
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
		
		String[] columnNames = {"Item", "Cost", "Day", "Repeating", "Category"};
		
		String[][] data = {{"Toast", "$5.99", "15", "No", "Food"}, {"Tea", "$1.99", "15", "No", "Food"}};
		
		JTable expensesTable = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(expensesTable);
		
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
		
		add(expensesPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
}