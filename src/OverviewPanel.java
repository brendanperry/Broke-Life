/*
 * Contains the GUI for the overview panel
 */


import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

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
	private Color bgColor = Color.decode("#3e92cc");
	private Color fgColor = Color.decode("#25ced1");

	
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
		setBackground(Color.BLUE);
		//JLabel overviewText = new JLabel("Overview");
		//add(overviewText);
		
		profile = userProfile;

		pane = new Container();
		setSize(470,375);
		pane.setBackground(bgColor);
		pane.setForeground(fgColor);
		pane.setLayout(new BorderLayout(10,10));
		pane.setPreferredSize(new Dimension(300,300));

		buildPanel();

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

		innerMoney = new JPanel();
		innerMoney.setLayout(new GridLayout(3,4,5,5));
		innerMoney.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		innerMoney.setBackground(bgColor);

		outerMoney = new JPanel();
		outerMoney.setLayout(new BorderLayout(10,10));
		outerMoney.setBackground(bgColor);

		asset = new JTextField(10);
		asset.setPreferredSize(new Dimension(100,30));
		asset.addKeyListener(this);
		asset.setEditable(false);

		assetL = new JLabel("ASSETS");
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

		netGoal = new JTextField(10);
		netGoal.setPreferredSize(new Dimension(100,30));
		netGoal.addKeyListener(this);
		//Boolean for netGoal textfield
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
		yourMoneyMethod();
		innerMoney.add(assetL);
		innerMoney.add(asset);
		innerMoney.add(netGoalL);
		innerMoney.add(netGoal);
		innerMoney.add(debtL);
		innerMoney.add(debt);
		innerMoney.add(needL);
		innerMoney.add(need);
		innerMoney.add(netWorthL);
		innerMoney.add(netWorth);
		innerMoney.add(ratioL);
		innerMoney.add(ratio);
		yourMoney = new JLabel("Your Money", SwingConstants.CENTER);
		yourMoney.setForeground(Color.WHITE);
		yourMoney.setFont(new Font("Arial", Font.BOLD, 20));
		outerMoney.add(yourMoney,BorderLayout.NORTH);
		outerMoney.add(innerMoney, BorderLayout.CENTER);
		setLayout(new BorderLayout(0, 0));
	
		innerWorth = new JPanel();
		innerWorth.setLayout(new BorderLayout(10,10));
		innerWorth.setBackground(bgColor);
		
//		JLabel test = new JLabel("Just to add something");
//		innerWorth.add(test, BorderLayout.CENTER);
		
		outerWorth = new JPanel();
		outerWorth.setLayout(new BorderLayout(10,10));
		outerWorth.setBackground(bgColor);
		
				
				
		outerWorth.setSize(450,500);
		
		//TODO
		
		GraphPanel graph = new GraphPanel(profile);
		outerWorth.add(graph, BorderLayout.CENTER);
		
		graphL = new JLabel("OVERVIEW", SwingConstants.CENTER);
		graphL.setForeground(Color.WHITE);
		graphL.setFont(new Font("Arial", Font.BOLD, 20));
		
		outerWorth.add(graphL, BorderLayout.NORTH);
		outerWorth.add(innerWorth, BorderLayout.CENTER);
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
	@SuppressWarnings("static-access")
	private void yourMoneyMethod() {
		GregorianCalendar calendar = new GregorianCalendar();
		int month = calendar.MONTH;
		int year = calendar.YEAR;
		
		
		DecimalFormat dFormat = new DecimalFormat("$###,##0.00");
		DecimalFormat ratioF = new DecimalFormat("%#00.00");
		//String temp;
		double balance = profile.getBalance();
		//temp = dFormat.format(balance);
		
		Income income = profile.getIncome(year, month);
		asset.setText(dFormat.format(balance));
		double sum = 0.00;
		int i = 0;
		
		while(i < profile.getNumberOfEvents()) {
			sum += profile.getEvent(i).getAmount();
			i++;
		}
		
		
		double debtCalc = balance - sum;
		debt.setText(dFormat.format(sum));
		netWorth.setText(dFormat.format(debtCalc));
		double debtRatio = (sum/balance);
		dFormat.format(debtRatio);
		System.out.println(debtRatio);
		ratio.setText(ratioF.format(debtRatio));
		if(isFilled) {
			if(Double.parseDouble(netGoal.getText()) != 0) {
				double needed = Double.parseDouble(netGoal.getText()) - debtCalc;
				dFormat.format(needed);
				need.setText(dFormat.format(needed));
				
			}
		}
		else {
			need.setText("$0.00");
		}
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
					yourMoneyMethod();
				}//End of try
				catch(Exception i){
					JOptionPane.showMessageDialog(null, "Invalid character");
					try {
						netGoal.setText(netGoal.getText(0, netGoal.getText().length()-1));
						yourMoneyMethod();
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

@SuppressWarnings("serial")
class GraphPanel extends JPanel {

    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    
    private GregorianCalendar calendar = new GregorianCalendar();
    private int currentYear;
    private Income currentMonthIncome;
    
    private ArrayList<Integer> income = new ArrayList<Integer>();
    
    //private List<Double> scores;

    public GraphPanel(UserProfile user) { 
    	
        currentYear = calendar.get(GregorianCalendar.YEAR); 
        
        for(int i = 0; i > 12; i++) {
        	currentMonthIncome = user.getIncome(currentYear, i);
        	income.add(currentMonthIncome.getMonth());
        }
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (income.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        ArrayList<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < income.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - income.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

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
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

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
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

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
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
        
        setVisible(true);
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(width, heigth);
//    }
    private int getMinScore() {
        int minScore = Integer.MAX_VALUE;
        for (Integer score : income) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxScore() {
        int maxScore = Integer.MIN_VALUE;
        for (Integer score : income) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.income = scores;
        invalidate();
        this.repaint();
    }

    public ArrayList<Integer> getScores() {
        return income;
    }


}