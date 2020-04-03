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
	ArrayList<String[]> data;
	
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
	
	JPanel centerBottomPanel;
	JLabel modifiedText;
	JPanel bottomPanel;
	
	JButton submitChanges;
	JPanel centerAddPanel;
	
	JComboBox<String> comboBox;
	
	public BudgetPanel(UserProfile user) {
		setLayout(new BorderLayout(0, 0));
		data = new ArrayList<String[]>();
		
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
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		
		// ADD EVENT TAB
		
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new BorderLayout());
		
		String[] interactions = {"Add Event", "Modify Event", "Delete Event"};
		comboBox = new JComboBox<String>(interactions);
		ActionHandler actionHandler = new ActionHandler();
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
		repeating = new JTextField();
		repeating.setPreferredSize(new Dimension(100,20));
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
		repeatingModified = new JTextField();
		repeatingModified.setPreferredSize(new Dimension(100,20));
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
	
	public class ActionHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submitChanges) {
				
				if(comboBox.getSelectedIndex() == 0) {
					// add event
					String[] newData = {name.getText(), cost.getText(), day.getText(), repeating.getText(), category.getText()};
					data.add(newData);
					model.addRow(newData);
				}
				else if(comboBox.getSelectedIndex() == 1) {
					// modify event
					
				}
				else {
					// delete event
					String[] dataToRemove = {name.getText(), cost.getText(), day.getText(), repeating.getText(), category.getText()};

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
					
					System.out.println(row);
					
					if(row == -1) {
						System.out.println("Not found");
						// Add a pop up
					}
					else {
						model.removeRow(row);
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
		}
	}
}