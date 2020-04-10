import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * This class is a pop up window that gives valuable educational information to the user. This includes the tutorial and learning material
 * @author brendanperry
 */
public class EducationPanel extends JFrame {
	private static final long serialVersionUID = 1L;

	/*
	 * The GUI for the education panel.
	 * @params type determines what information is loaded into the panel.
	 * @author brendanperry
	 */
	public EducationPanel(int type) {
		setSize(500, 500);
		setTitle("Education");
		setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/logo.png"));
		JLabel title;
		JTextArea information;
		
		// Loads tutorial
		if(type == 0) {
			title = new JLabel("Tutorial");
			information = new JTextArea(
					"Welcome to BrokeLife! \n\n"
					+ "We are going to walk you through a few quick and easy steps to get you up and running. \n\n"
					+ "There are three main sections within this app: Budget, Calendar, and Overview. We will cover each one here so you know what they do and how to use them. \n\n"
					+ "Before that, lets go over how things work from a user level.\n\n"
					+ "Since you have made it this far, you already have a user profile. All of your data is saved to this profile. If you want to access it the next time, make sure you select it and enter your password. Your previous work will be loaded.\n\n"
					+ "You can add new profiles if you have other people that will be using the application. All of their information will be saved to their profile and your information to yours.\n\n"
					
					+ "HEADING \n\n"
					+ "\u2022 At the top of the application you can see the current month. This can be changed by selecting the back or forward arrow. \n"
					+ "\u2022 The menu bar allows you to save work you have done to your profile, load a new profile, access this window again, or view educational information. \n\n"
					
					+ "BUDGET \n\n"
					+ "The budgeting section is the heart of BrokeLife. This is where you will be putting in your information.\n"
					+ "To begin, you can add your income under the income heading. \n\n"
					+ "After this, you can add new expenses using the bottom bar at the bottom. \n\n"
					+ "Each event has several attributes: \n"
					+ "\u2022 The name is whatever you want to call this event. \n"
					+ "\u2022 The cost is what you paid for it. \n"
					+ "\u2022 The percentage is used if you want the cost to be calculated as a percent of your total income. This overrides the cost field. It automatically updates as you enter in new income.\n"
					+ "\u2022 The day field is whatever day in the month that the event occured.\n"
					+ "\u2022 The repeating drop down lets you select a recurrence period for the event if it will happen consistently in the future.\n"
					+ "\u2022 The category field is used to group similar events together. \n\n"
					+ "All of the entered events can be seen in the table.\n\n"
					+ "You can also modify or delete events by selecting the drop down next to the add new event button.\n\n"
					
					+ "CALENDAR \n\n"
					+ "The calendar section is here to help you keep your financial life in order. It will show your upcoming events and lets you see when things are due.\n\n"
					+ "\u2022 You can select a day to see what is happening on that date.\n"
					+ "\u2022 The upcoming events tab shows the events that will be occuring the soonest.\n\n"
					
					+ "OVERVIEW \n\n"
					+ "The overview section will help you see the big picture. It shows you your net worth (your total money minus your total debts)\n\n"
					+ "\u2022 Here you can add goals and track your progress."
					);
		}
		else {
			// Loads learning
			title = new JLabel("Learning");
			information = new JTextArea(
					"Financial Philosophy\n\n" + 
					"BrokeLife is here to help you grow and learn how to handle your money.\n\n" + 
					"\u2022 By entering in every expense, you are forced to see the money go, and also you are able to see how it impacts the rest of your financial health.\n" + 
					"\u2022Having all of your events listed out can also help you avoid overdraft fees by making sure you have enough money in your account to pay for everything that you need.\n" + 
					"\u2022We believe in saving money. Using BrokeLife, you can set aside money to save in your budget and watch your net worth grow.\n\n" + 
					
					"What is a budget? \r\n" + 
					"\u2022A budget is just a plan for your money. You decide where your money should be spent before you spend it. This helps you see how much money you have for things, helps you avoid unnecessary spending, and helps you save more for your goals.\n" + 
					"\u2022We recommend that you give each dollar a job so that your total amount budgeted is equal to the total amount that you have made. Budgets are usually month to month so that you can plan according to your most current pay and life situations.\n\n" + 
					
					"Financial Steps\n\n" + 
					"\u2022 The first step to any good financial plan is to have a safety net. Anything can happen including job loss.\n" + 
					"\u2022 We recommend the first step people take is to save $1,000 as an emergency fund. This is only used when absolutely necessary and hopefully it never needs to be.\n" + 
					"\u2022 The next step we recommend is to begin paying off your debts starting with the debts that have the highest interest rate. This will help save you the most money.\n" + 
					"\u2022 Once you are debt free (besides a mortgage), you should boost the emergency fund up to 3 to 6 months worth of expenses. This will help protect you and provide peace of mind.\n" + 
					"\u2022 We recommend beginning to save money for retirement after this step; however, this is beyond the scope of BrokeLife. The main goal of this application is to help you get your financial life in order and learn how to handle your money.\n"  
					);
		}
		
		information.setLineWrap(true);
		information.setWrapStyleWord(true);
		
		add(title, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(information);
		add(scrollPane, BorderLayout.CENTER);
	}
}