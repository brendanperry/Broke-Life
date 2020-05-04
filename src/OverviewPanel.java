/*
 * Contains the GUI for the overview panel
 */


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

/*
 * This class holds all of the important
 */

@SuppressWarnings("serial")
public class OverviewPanel extends JPanel implements KeyListener {
	
	//The following are all the panel components 
	private Container pane;
	private JTextField asset, debt, netWorth, netGoal, need, ratio;
	private JLabel assetL, debtL, netWorthL, netGoalL, needL, ratioL, yourMoney, graphL;
	private JPanel innerMoney, innerWorth, outerMoney, outerWorth, buttonPanel;
	private GraphPanel graph;
	private Color bgColor = Color.decode("#3e92cc");
	private Color fgColor = Color.decode("#25ced1");
		
	//Used to format the text boxes
	private DecimalFormat dFormat = new DecimalFormat("$###,##0.00");
	private DecimalFormat needFormat = new DecimalFormat("#####0.00");

	//This is used to verify that the Net Goal textfield is filled
	private boolean isFilled = true;

	//These are used to bring in information from the needed classes
	UserProfile profile;
	BudgetPanel bp;
	
	//This is used as a start date for the profile.
	private int selectedMonth = 1;
	private int selectedYear = 2020;

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
		setBackground(bgColor);
	
		//Sets the profile to the passed into the class
		profile = userProfile;
				
		//The following gets the date and uses it to get the information from the profile
		//for the selected month
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());			
		selectedMonth = cal.get(Calendar.MONTH);
		selectedYear = cal.get(Calendar.YEAR);

		//This allows the information to be pulled in from the BudgetPanel.
		//This was the easiest way to get the updated information needed to do the 
		//Calculations this panel does
		bp = new BudgetPanel(userProfile);
		
		//The pane holds everything
		pane = new Container();
		setSize(470,375);
		pane.setBackground(bgColor);
		pane.setForeground(fgColor);
		pane.setLayout(new BorderLayout(10,10));
		pane.setPreferredSize(new Dimension(300,300));

		//Calls the buildPanel where all the magic happens
		buildPanel();

		//Adds the pane to the main overview panel
		add(pane, BorderLayout.CENTER);

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

		/*
		 *The following is all the panels, buttons and textfields used 
		 *in the panel. If anything visual related needs to be altered it will be here.
		 */
		innerMoney = new JPanel();
		innerMoney.setLayout(new GridLayout(2,4,5,5));
		innerMoney.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		innerMoney.setBackground(bgColor);

		outerMoney = new JPanel();
		outerMoney.setLayout(new BorderLayout(10,10));
		outerMoney.setBackground(bgColor);

		asset = new JTextField(10);
		asset.setPreferredSize(new Dimension(100,30));
		asset.addKeyListener(this);
		asset.setEditable(false);

		assetL = new JLabel("MONTHLY BALANCE");
		assetL.setForeground(Color.WHITE);
		assetL.setFont(new Font("Arial", Font.BOLD, 13));

		debt = new JTextField(10);
		debt.setPreferredSize(new Dimension(100,30));
		debt.addKeyListener(this);
		debt.setEditable(false);

		debtL = new JLabel("DEBT");
		debtL.setForeground(Color.WHITE);
		debtL.setFont(new Font("Arial", Font.BOLD, 13));

		netWorth = new JTextField(10);
		netWorth.setPreferredSize(new Dimension(100,30));
		netWorth.addKeyListener(this);
		netWorth.setEditable(false);

		netWorthL = new JLabel("NET WORTH");
		netWorthL.setForeground(Color.WHITE);
		netWorthL.setFont(new Font("Arial", Font.BOLD, 13));

		netGoal = new JTextField("$0.00");
		netGoal.setPreferredSize(new Dimension(100,30));
		netGoal.setText(needFormat.format(profile.getGoal()));
		netGoal.addKeyListener(this);
		
		//Boolean for netGoal textfield initially set to false since it will default be
		//empty.
		isFilled = false;

		netGoalL = new JLabel("NET WORTH GOAL");
		netGoalL.setForeground(Color.WHITE);
		netGoalL.setFont(new Font("Arial", Font.BOLD, 13));

		need = new JFormattedTextField(10);
		need.setPreferredSize(new Dimension(100,30));
		need.addKeyListener(this);
		need.setEditable(false);

		needL = new JLabel("NEEDED");
		needL.setForeground(Color.WHITE);
		needL.setFont(new Font("Arial", Font.BOLD, 13));

		ratio = new JTextField(10);
		ratio.setPreferredSize(new Dimension(100,30));
		ratio.addKeyListener(this);
		ratio.setEditable(false);

		ratioL = new JLabel("DEBT RATIO");
		ratioL.setForeground(Color.WHITE);
		ratioL.setFont(new Font("Arial", Font.BOLD, 13));
		
		//Calls the yourMoneyMethod that does all the calculations for the textfields
		yourMoneyMethod(selectedMonth, selectedYear);
		
		innerMoney.add(assetL);
		innerMoney.add(asset);
		innerMoney.add(netGoalL);
		innerMoney.add(netGoal);
		innerMoney.add(netWorthL);
		innerMoney.add(netWorth);
		innerMoney.add(needL);
		innerMoney.add(need);
		
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBackground(bgColor);
		
		yourMoney = new JLabel("Your Money", SwingConstants.CENTER);
		yourMoney.setForeground(Color.WHITE);
		yourMoney.setFont(new Font("Arial", Font.BOLD, 20));
		outerMoney.add(yourMoney,BorderLayout.NORTH);
		outerMoney.add(innerMoney, BorderLayout.CENTER);
		outerMoney.add(buttonPanel, BorderLayout.SOUTH);
		setLayout(new BorderLayout(0, 0));
	
		innerWorth = new JPanel();
		innerWorth.setLayout(new BorderLayout(10,10));
		innerWorth.setBackground(bgColor);
		
		outerWorth = new JPanel();
		outerWorth.setLayout(new BorderLayout(10,10));
		outerWorth.setBackground(bgColor);
		outerWorth.setSize(450,500);
		
		//This call is for the GraphPanel class. Its a subclass in the Overview Panel class
		//that builds the graph.
		graph = new GraphPanel(profile);
		outerWorth.add(graph, BorderLayout.CENTER);
		outerWorth.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		graphL = new JLabel("Net Worth Over Time", SwingConstants.CENTER);
		graphL.setForeground(Color.WHITE);
		graphL.setFont(new Font("Arial", Font.BOLD, 20));
		graph.setBackground(bgColor);
		graph.repaint();
		
		outerWorth.add(graphL, BorderLayout.NORTH);
		pane.add(outerWorth, BorderLayout.CENTER);

		pane.add(outerMoney, BorderLayout.NORTH);
		setVisible(true);


	}//End of buildPanel

	
	
	
	//This is just for net worth goal textfield only.
	//The other textfields are not editable since they are 
	//computed from profile information
	@Override
	public void keyPressed(KeyEvent e) {
		netWorthGoalTest(e);
	}//End of keyPressed method

	@Override
	public void keyReleased(KeyEvent e) {
		netWorthGoalTest(e);
	}//End of keyRelease method

	@Override
	public void keyTyped(KeyEvent e) {
		netWorthGoalTest(e);
	}//End of keyTyped method
	
	
	/*
	 * Updates the panel to reflect new data with the current month and year
	 * @author brendanperry
	 */
	public void updateInfo() {
		yourMoneyMethod(selectedMonth, selectedYear);
		graph.repaint();
	}//End of updateInfo() method
	
	
	
	/*
	 * Updates the panel with a new month and year
	 * @params month - the new month, year - the new year
	 * @author brendanperry
	 */
	public void updateInfo(int month, int year) {
		yourMoneyMethod(month, year);
		graph.repaint();
	}//End of updateInfo(month, year) method

	
	//This method will update the different text fields based off of the data from 
	//user profile
	@SuppressWarnings({ "deprecation" })
	private void yourMoneyMethod(int month, int year) {
		
		//This allows a passed in month an year to figure out which month's information
		// to display
		selectedMonth = month;
		selectedYear = year;
		
		//Sets the passed dates in to set up a new Date value
		Date date = new GregorianCalendar(year, month, 1).getTime();
		Date creationDate = profile.getCreationDate();
		
		//Gets the monthly balance from the current profile
		double monthlyBalance = profile.getIncome(year, month + 1).getBalance();
		double totalBalance = profile.getBalance();
		
		Date tempDate = date;
		
		// getting the balance from every month before the current date
		while(creationDate.equals(tempDate) || creationDate.before(tempDate)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(tempDate);			
			int monthInt = cal.get(Calendar.MONTH);
			int yearInt = cal.get(Calendar.YEAR);
			
			totalBalance += profile.getIncome(yearInt, monthInt + 1).getBalance();
			
			if(tempDate.getMonth() == 0) {
				tempDate.setMonth(11);
				tempDate.setYear(tempDate.getYear() - 1);
			}//End of if statement
			else {
				tempDate.setMonth(tempDate.getMonth() - 1);
			}//End of else statement
			
		}//End of while loop

		asset.setText(dFormat.format(monthlyBalance));
		netWorth.setText(dFormat.format(totalBalance));
		need.setText(Double.toString(profile.getNeeded()));
		
		
		//Just checks to make sure the net goal textfield is filled to prevent
		//null being passed in as a value
		if(!netGoal.getText().isEmpty()) {
			if(Double.parseDouble(netGoal.getText()) != 0) {
				double needed = Double.parseDouble(netGoal.getText()) - Double.parseDouble(netWorth.getText().replaceAll("[$,]", ""));
				profile.setNeeded(needed);
				
				dFormat.format(needed);
				need.setText(dFormat.format(needed));
				
			}//End of inner if statement
		}//End of outer if statement
		
	}//End of yourMoneyMethod

	
	
	
	//This method is for the net worth goal text field.
	//Checks to see if it is emtpy. If it is sets a boolean that will be used in
	//the yourMoneyMethod for the needed text field.
	private void netWorthGoalTest(KeyEvent e) {

		if(e.getSource().equals(netGoal)) {
			if(netGoal.getText().length() > 0) {
				//Used to make sure that valid characters are being passed in along with
				//limiting the size of string to a reasonable value
				try {
					
					if(Double.parseDouble(netGoal.getText()) > 1000000.00)
						isFilled = false;
					yourMoneyMethod(selectedMonth, selectedYear);
				}//End of try
				catch(Exception i){
					JOptionPane.showMessageDialog(null, "Invalid character only integers are accepted");
					try {
						netGoal.setText(netGoal.getText(0, netGoal.getText().length()-1));
						yourMoneyMethod(selectedMonth, selectedYear);
					}//end of try
					catch(Exception j) {
						JOptionPane.showMessageDialog(null, "null");
					}//End of catch
				}//End of catch
			}//end of if statement
		}//End of outer if statement
		
		if(netGoal.getText().isEmpty()) 
			isFilled = false;

		else {
			profile.setGoal(Double.parseDouble(netGoal.getText()));
			isFilled = true;
		}//end of else statement
	}//End of netWorthGoalTest


	
	/*
	 * This class creates the graphs used in the overview panel.
	 * Made to keep everything for it together so that the overview panel class
	 * didnt become too big and convoluted.
	 */
	class GraphPanel extends JPanel {
		
		//Components for the graph
	    private int padding = 25;
	    private int labelPadding = 25;
	    private Color lineColor = new Color(44, 102, 230, 180);
	    private Color pointColor = new Color(100, 100, 100, 180);
	    private Color gridColor = new Color(200, 200, 200, 200);
	    private final Stroke GRAPH_STROKE = new BasicStroke(2f);
	    private int pointWidth = 4;
	    private int numberYDivisions = 10;
	    
	    //Loads all the income for the year into an ArrayList that is used for
	    //data points
	    private ArrayList<Double> income = new ArrayList<Double>();
	    
	    //Used to get needed information from UserProfile
	    UserProfile userProfile;
	
	    //Constructor that instantiates the class profile with the passed in profile
	    public GraphPanel(UserProfile user) { 
	    	userProfile = user;
	    }//End of constructor
	    
	    /*
	     * This function updates the information that is to be put into the graph. This includes the monthly balances.
	     * @author brendanperry
	     */
	    @SuppressWarnings("deprecation")
		public void updateGraph() {
	    	income.clear();
	    	Date date = new GregorianCalendar(selectedYear, selectedMonth, 1).getTime();
			Date creationDate = profile.getCreationDate();
			
			Date tempDate = date;
						
			int count = 0;
									
			// find how many months to go back up to one year
			while(creationDate.equals(tempDate) || creationDate.before(tempDate) && count <= 12) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(tempDate);
				
				if(tempDate.getMonth() == 0) {
					tempDate.setMonth(11);
					tempDate.setYear(tempDate.getYear() - 1);
				}//End of if statement
				else
					tempDate.setMonth(tempDate.getMonth() - 1);
				
				count++;
			}//End of while loop
	        
			
			// roll back temp date to the furthers date with data
	        tempDate = new GregorianCalendar(selectedYear, selectedMonth, 1).getTime();
	        
	        for(int i = 1; i < count; i++) {
	        	if(tempDate.getMonth() == 0) {
					tempDate.setMonth(11);
					tempDate.setYear(tempDate.getYear() - 1);
				}//End of if statement
				else 
					tempDate.setMonth(tempDate.getMonth() - 1);
	        }//End of for loop
	        
	        // now we go forwards to and add all the balances up
	        double totalBalance = profile.getBalance();
			
	        //This for loop sums up the balance as the year goes on and adds
	        //it into the income arraylist to represent each months total increase or 
	        //decrease
	        for (int i = 0; i < count; i++) {
	        	Calendar cal = Calendar.getInstance();
				cal.setTime(tempDate);			
				int monthInt = cal.get(Calendar.MONTH);
				int yearInt = cal.get(Calendar.YEAR);
				
				totalBalance += profile.getIncome(yearInt, monthInt + 1).getBalance();
	        	income.add(totalBalance);
	        	
	        	if(tempDate.getMonth() == 11) {
					tempDate.setMonth(0);
					tempDate.setYear(tempDate.getYear() + 1);
				}//End of if statement
				else 
					tempDate.setMonth(tempDate.getMonth() + 1);
				
	        }//End of for loop
	        
	        int size = income.size();
	        
	        // if the graph is too small, we make sure that there is at least two points
			if(size < 12) 
				income.add(0, 0.0);
	    }//end of updateGraph method
	
	    
	    //This method is responsible for making the graph and painting the panel
	    @SuppressWarnings("deprecation")
		@Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        //Used to make sure the points are up to date
	        updateGraph();
	        
	        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (income.size() - 1);
	        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxValue() - getMinValue());
	
	        ArrayList<Point> graphPoints = new ArrayList<>();
	        
	        //Loads the graphPoints ArrayList with data points
	        for (int i = 0; i < income.size(); i++) {
	            int x1 = (int) (i * xScale + padding + labelPadding);
	            int y1 = (int) ((getMaxValue() - income.get(i)) * yScale + padding);
	            graphPoints.add(new Point(x1, y1));
	        }//End of for loop
	
	        // draw white background
	        g2.setColor(Color.WHITE);
	        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
	        g2.setColor(Color.BLACK);
	
	        // create hatch marks and grid lines for y axis.
	        for (int i = 0; i < numberYDivisions + 1; i++) {
	            int x0 = padding + labelPadding;
	            int x1 = pointWidth + padding + labelPadding;
	            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
	            int y1 = y0;
	            
	            if (income.size() > 0) {
	                g2.setColor(gridColor);
	                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
	                g2.setColor(Color.BLACK);
	                
	                //Y axis label
	                String yLabel = "$0.00";
	                if(getMaxValue() > 1) {
	                	yLabel = "$" + (int) ((getMinValue() + (getMaxValue() - getMinValue()) * ((i * 1.0) / numberYDivisions)));
	                }//End of if statement
	                
	                FontMetrics metrics = g2.getFontMetrics();
	                int labelWidth = metrics.stringWidth(yLabel);
	                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
	            }//End of if statement
	            
	            g2.drawLine(x0, y0, x1, y1);
	        }//End of for loop
	        
	        Date tempDate = new GregorianCalendar(selectedYear, selectedMonth, 1).getTime();
	        
	        int size = income.size();
	        
	        for(int i = 1; i < size; i++) {
	        	if(tempDate.getMonth() == 0) {
					tempDate.setMonth(11);
					tempDate.setYear(tempDate.getYear() - 1);
				}//End of if statement
				else 
					tempDate.setMonth(tempDate.getMonth() - 1);
				
	        }//End of for loop
	        
	        
	        // and for x axis
	        for (int i = 0; i < income.size(); i++) {
	        	
	            if (income.size() > 1) {
	                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (income.size() - 1) + padding + labelPadding;
	                int x1 = x0;
	                int y0 = getHeight() - padding - labelPadding;
	                int y1 = y0 - pointWidth;
	                
	                
	                if ((i % ((int) ((income.size() / 20.0)) + 1)) == 0) {
	                    g2.setColor(gridColor);
	                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
	                    g2.setColor(Color.BLACK);
	                    
	                    Calendar cal = Calendar.getInstance();
	                    cal.setTime(tempDate);
	                    String m = new SimpleDateFormat("MMM").format(cal.getTime());
	                    String y = Integer.toString(cal.get(Calendar.YEAR)).substring(2, 4);
	                    
	                    String xLabel = " " + m + " " + y;
	                    if(tempDate.getMonth() == 11) {
	    					tempDate.setMonth(0);
	    					tempDate.setYear(tempDate.getYear() + 1);
	    				}//End of if statement
	    				else 
	    					tempDate.setMonth(tempDate.getMonth() + 1);
	    				
	         
	                    FontMetrics metrics = g2.getFontMetrics();
	                    int labelWidth = metrics.stringWidth(xLabel);
	                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
	                }//End of inner if statement
	                
	                g2.drawLine(x0, y0, x1, y1);
	            }//End of outer if statement
	        }//End of for loop
	
	        // create x and y axes 
	        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
	        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);
	
	        Stroke oldStroke = g2.getStroke();
	        g2.setColor(lineColor);
	        g2.setStroke(GRAPH_STROKE);
	        
	        
	        for (int i = 0; i < graphPoints.size() - 1; i++) {
	            int x1 = graphPoints.get(i).x;
	            int y1 = graphPoints.get(i).y;
	            int x2 = graphPoints.get(i + 1).x;
	            int y2 = graphPoints.get(i + 1).y;
	            g2.drawLine(x1, y1, x2, y2);
	        }//End of for loop
	
	        g2.setStroke(oldStroke);
	        g2.setColor(pointColor);
	        for (int i = 0; i < graphPoints.size(); i++) {
	            int x = graphPoints.get(i).x - pointWidth / 2;
	            int y = graphPoints.get(i).y - pointWidth / 2;
	            int ovalW = pointWidth;
	            int ovalH = pointWidth;
	            g2.fillOval(x, y, ovalW, ovalH);
	        }//End of for loop
	        
	        g2.drawLine(0, 0, 1, 1);
	        setVisible(true);
	    }//End of paintComponent method
	
	
	    //The following helper methods are getters and setters for values and income
	    private double getMinValue() {
	        double minIncome = Double.MAX_VALUE;
	        for (Double value : income) {
	            minIncome = Math.min(minIncome, value);
	        }//End of for loop
	        return minIncome;
	    }//End of getMinValue method
	
	    private double getMaxValue() {
	        double maxIncome = Double.MIN_VALUE;
	        for (Double value : income) {
	            maxIncome = Math.max(maxIncome, value);
	        }//End of for loop
	        return maxIncome;
	    }//End of getMaxValue method
	
	    public void setIncome(ArrayList<Double> income) {
	        this.income = income;
	        invalidate();
	        this.repaint();
	    }//End of setIncome method
	
	    public ArrayList<Double> getIncome() {
	        return income;
	    }//End of getIncome method
	}//End of GraphPanel Class
}//End of OverviewPanel Class