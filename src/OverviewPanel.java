/*
 * Contains the GUI for the overview panel
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * @author Andrew Hansel
 * - So far a basic framework has been created.
 * I need some of the other methods to work before I can tweak the methods and
 * sizes of the different components. 4/8/2020
 */


@SuppressWarnings("serial")
public class OverviewPanel extends JPanel implements KeyListener{
	private Container pane;
	private JTextField asset, debt, netWorth, netGoal, need, ratio;
	private JLabel assetL, debtL, netWorthL, netGoalL, needL, ratioL, yourMoney, graphL;
	private JPanel innerMoney, innerWorth, outerMoney, outerWorth;

	private boolean isFilled;

	private UserProfile profile;

	/*
	 * The purpose of this class is to take information from the profile class and
	 * show calculations such as debt, net worth, debt ratio, and how much is needed 
	 * reach a savings goal.
	 */

	/*
	 * This is the constructor that is just creating a container, calling
	 * the buildPanel method and making sure you can see all the cool bits.
	 */
	public OverviewPanel(UserProfile userProfile) {
		JLabel overviewText = new JLabel("Overview");
		add(overviewText);

		profile = userProfile;

		pane = new Container();
		setSize(470,375);
		pane.setBackground(Color.GREEN);
		pane.setLayout(new BorderLayout(10,10));

		buildPanel();

		add(pane);

		setVisible(true);


	}//End of Constructor

	/*
	 * This is where all the goodies are for the GUI
	 * If you want to edit a button or a textfield they are found here
	 * 
	 * Inner money is the panel that holds all the labels and text fields to allow for
	 * a nice gridlayout.
	 * Outer money is set to borderlayout so you can have a title of sorts and put the 
	 * inner money panel right under it without compromising the look
	 * 
	 * Same layout for the worth panel. It will hold the graph and has a place for a 
	 * title.
	 */

	private void buildPanel() {

		innerMoney = new JPanel();
		innerMoney.setLayout(new GridLayout(3,4,5,5));
		innerMoney.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		innerMoney.setBackground(Color.white);

		outerMoney = new JPanel();
		outerMoney.setLayout(new BorderLayout(10,10));
		outerMoney.setBackground(Color.MAGENTA);

		innerWorth = new JPanel();
		innerWorth.setLayout(null);
		innerWorth.setBackground(Color.white);

		outerWorth = new JPanel();
		outerWorth.setLayout(new BorderLayout(10,10));
		outerWorth.setBackground(Color.MAGENTA);

		asset = new JTextField(10);
		asset.addKeyListener(this);
		asset.setEditable(false);

		assetL = new JLabel("Assets");

		debt = new JTextField(10);
		debt.addKeyListener(this);
		debt.setEditable(false);

		debtL = new JLabel("Debt");

		netWorth = new JTextField(10);
		netWorth.addKeyListener(this);
		netWorth.setEditable(false);

		netWorthL = new JLabel("Net Worth");

		netGoal = new JTextField(10);
		netGoal.addKeyListener(this);
		//Boolean for netGoal textfield
		isFilled = false;

		netGoalL = new JLabel("Net Worth Goal");

		need = new JTextField(10);
		need.addKeyListener(this);
		need.setEditable(false);

		needL = new JLabel("Needed");

		ratio = new JTextField(10);
		ratio.addKeyListener(this);
		ratio.setEditable(false);

		ratioL = new JLabel("Debt Ratio");

		innerMoney.add(assetL);
		innerMoney.add(asset);
		innerMoney.add(netGoalL);
		innerMoney.add(netGoal);
		innerMoney.add(debtL);
		innerMoney.add(debt);
		innerMoney.add(needL);
		innerMoney.add(need);
		innerMoney.add(netWorthL);
		innerMoney.add(netWorthL);
		innerMoney.add(ratioL);
		innerMoney.add(ratio);

		yourMoney = new JLabel("Your Money");
		outerMoney.add(yourMoney,BorderLayout.NORTH);
		outerMoney.add(innerMoney, BorderLayout.CENTER);

		graphL = new JLabel("Net Worth");
		outerWorth.add(graphL, BorderLayout.NORTH);
		outerWorth.add(innerWorth, BorderLayout.CENTER);



	}//End of buildPanel

	//This is just for net worth goal textfield only.
	//The other textfields are not editable since they are 
	//computed from profile information
	@Override
	public void keyPressed(KeyEvent e) {
		netWorthGoalTest(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		netWorthGoalTest(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		netWorthGoalTest(e);
	}


	//TODO
	//For whenever we get the methods going for pulling info from save profiles
	//This method will update the different text fields based off of the data from 
	//user profile
	private void yourMoneyMethod() {

	}//End of yourMoneyMethod


	//This method is for the net worth goal text field.
	//Checks to see if it is emtpy. If it is sets a boolean that will be used in
	//the yourMoneyMethod for the needed text field.
	private void netWorthGoalTest(KeyEvent e) {

		if(e.getSource().equals(netGoal)) {
			if(netGoal.getText().length() > 0) {
				try {
					if(Double.parseDouble(netGoal.getText()) > 1000000.00)
						isFilled = false;
				}//End of try
				catch(Exception i){
					JOptionPane.showMessageDialog(null, "Invalid character");
					try {
						netGoal.setText(netGoal.getText(0, netGoal.getText().length()-1));
					}//end of try
					catch(Exception j) {
						JOptionPane.showMessageDialog(null, "null");
					}//End of catch
				}//End of catch
			}//end of if statement
		}//End of outer if statement

		if(netGoal.getText().isEmpty()) 
			isFilled = false;

		else
			isFilled = true;
	}//End of netWorthGoalTest

}//End of OverviewPanel Class