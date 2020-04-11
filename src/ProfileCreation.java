import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
/**
 * GUI class used to create new UserProfiles, prompted by InitialWindow
 * Drew Albert
 * 04/02/2020
 */

public class ProfileCreation extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private static boolean nameEntry, balanceEntry, passwordEntry;
	private JTextField name, balance, password;
	private JLabel nameLabel, balanceLabel, passwordLabel;
	private static JButton create, cancel;
	
	
	
	
	private static String title = "Create A User Profile";
	
	private Container pane;
	private JPanel mainPanel, buttonPanel;
	
	private ArrayList<String> pList;
	
	/**
	 * Constructor method. Draws GUI window containing text fields to input
	 * initializing data for a new User Profile
	 * @throws IOException - Resulting from issues in saving profile
	 * @author Drew Albert
	 */
	
	/**
	 * Altered By Andrew Hansel 4/8/2020
	 * -fixed bug that allowed empty fields to pass into profiles.
	 * -reorganized code to try to implement the dry method.
	 * -pulled from original code commented out at the bottom
	 */
	public ProfileCreation(ArrayList<String> profileList) {
		super(title);

		pList = profileList;

		pane = new Container();
		pane.setBackground(Color.GREEN);
		pane.setLayout(new BorderLayout(10,10));

		setSize(350, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);

		buildWindow();

		add(pane);

		setVisible(true);
	}

	private void buildWindow() {

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,2,5,10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		mainPanel.setBounds(0, 0, 293, 179);


		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,2));

		name = new JTextField(10);
		name.setBounds(200, 10, 85, 20);
		nameLabel = new JLabel("Name:");
		name.addKeyListener(this);



		balance = new JTextField();
		balance.setBounds(200, 90, 85, 20);
		balanceLabel = new JLabel("Balance:");
		balance.addKeyListener(this);

		password = new JTextField(10);
		password.setBounds(200, 50, 86, 20);
		passwordLabel = new JLabel("Password:");
		password.addKeyListener(this);


		create = new JButton("Create");
		create.setEnabled(false);
		create.setBounds(10, 130, 100, 23);
		create.addActionListener(this);


		cancel = new JButton("Cancel");
		cancel.setBounds(186, 130, 100, 23);
		cancel.addActionListener(this);


		setVisible(true);

		mainPanel.add(nameLabel);
		mainPanel.add(name);
		mainPanel.add(balanceLabel);
		mainPanel.add(balance);
		mainPanel.add(passwordLabel);
		mainPanel.add(password);
		mainPanel.add(create);
		mainPanel.add(cancel);


		pane.add(mainPanel, BorderLayout.CENTER);



	}//End of buildWindow

	//ActionListener for buttons
	public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(create)) {

				try {
					String filename = name.getText() + ".bl";
					System.out.println(new File(".").getCanonicalPath() + "/Profiles/" + filename);
					FileOutputStream file = new FileOutputStream(new File(".").getCanonicalPath() + "/Profiles/" + filename);
					ObjectOutputStream out = new ObjectOutputStream(file);

					UserProfile profile = new UserProfile(name.getText(), password.getText(), Double.parseDouble(balance.getText()), new Date());

					out.writeObject(profile);
					file.close();
					out.close();

					JOptionPane.showMessageDialog(null, "Profile " + name.getText() + " has been created!" );
					pList.add(name.getText() + ".bl");
					new InitialWindow(pList);
					dispose();
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Profile initialization failed");
				}//End of Catch

			}//End of if statement for create



		if(e.getSource().equals(cancel))
			dispose();



	}//End of actionPerformed method

	//ActionListener for Text fields
	public void keyReleased(KeyEvent e) {
		fieldsFilled();
		textFieldTest(e);

	}//End of keyReleased method

	public void keyPressed(KeyEvent e) {
		fieldsFilled();
		textFieldTest(e);
	}//End of keyPressed method


	public void keyTyped(KeyEvent e) {
		fieldsFilled();
		textFieldTest(e);
	}//End of keyTyped method

	//Helper method that checks the input of the text field inputs to make sure they are not empty
	//and that the input is valid
	//For these we can implement more rigorous tests to check if the input is valid.
	public void textFieldTest(KeyEvent e) {
		if(e.getSource().equals(name)) {
			fieldsFilled();
			if(name.getText().isEmpty()) 
				nameEntry = false;
			else if(name.getText().contains(".bl"))
				nameEntry = false;
			else if(name.getText().length() > 30)
				nameEntry = false;
			else
				nameEntry = true;
		}//End of if statement

		if(e.getSource().equals(balance)) {
			if(balance.getText().length() > 0) {
				try {
					if(Double.parseDouble(balance.getText()) > 1000000.00)
						balanceEntry = false;
				}
				catch(Exception i){
					JOptionPane.showMessageDialog(null, "Invalid character");
					try {
						balance.setText(balance.getText(0, balance.getText().length()-1));
					}
					catch(Exception j) {
						JOptionPane.showMessageDialog(null, "null");
					}
				}
			}

			if(balance.getText().isEmpty()) 
				balanceEntry = false;

			else
				balanceEntry = true;
			fieldsFilled();
		}//End of if statement

		if(e.getSource().equals(password)) {
			fieldsFilled();
			if(password.getText().isEmpty())
				passwordEntry = false;
			else if(password.getText().length() <= 3)
				passwordEntry = false;

			else
				passwordEntry = true;
		}//End of if statement
	}//End of textFieldTest method

	//This method checks if the fields are full
	//If they are the create button is active
	private void fieldsFilled() {
		if(nameEntry == true && balanceEntry == true && passwordEntry == true)
			create.setEnabled(true);
		else
			create.setEnabled(false);
	}//End of fieldsFilled
	
}