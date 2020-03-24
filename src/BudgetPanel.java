/*
 * Contains the GUI for the budget panel
 */

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BudgetPanel extends JPanel {
	
	public BudgetPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel expensesPanel = new JPanel();
		JLabel expensesText = new JLabel("Expenses");
		expensesPanel.add(expensesText);
		expensesPanel.setBackground(Color.RED);
		expensesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel incomePanel = new JPanel();
		JLabel incomeText = new JLabel("Income");
		incomePanel.add(incomeText);
		incomePanel.setBackground(Color.YELLOW);
		incomePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel reviewPanel = new JPanel();
		JLabel reviewText = new JLabel("review");
		reviewPanel.add(reviewText);
		reviewPanel.setBackground(Color.BLUE);
		reviewPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		rightPanel.add(incomePanel, BorderLayout.CENTER);
		rightPanel.add(reviewPanel, BorderLayout.SOUTH);
		rightPanel.setSize(300, 300);
		
		add(expensesPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
}