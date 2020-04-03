import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;

/**
 * GUI class used to create new UserProfiles, prompted by InitialWindow
 * Drew Albert
 * 04/02/2020
 */

public class ProfileCreation extends JFrame {
	private static final long serialVersionUID = 1L;
	private static boolean nameEntry, balanceEntry, passwordEntry;
	private JTextField name, balance, password;
	private static JButton create;
	
	/**
	 * Constructor method. Draws GUI window containing text fields to input
	 * initializing data for a new User Profile
	 * @throws IOException - Resulting from issues in saving profile
	 * @author Drew Albert
	 */
	public ProfileCreation(ArrayList<String> profileList) {
		setAlwaysOnTop(true);
		setForeground(Color.WHITE);
		setSize(299, 208);
		setTitle("Create a new profile");
		getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 293, 179);
		getContentPane().add(panel);
		panel.setLayout(null);

		JTextArea startingBalance = new JTextArea();
		startingBalance.setBounds(10, 90, 136, 22);
		panel.add(startingBalance);
		startingBalance.setText("Starting Balance :");
		startingBalance.setBackground(null);
		startingBalance.setEditable(false);
		startingBalance.setHighlighter(null);

		JTextArea profileName = new JTextArea();
		profileName.setBounds(10, 10, 102, 22);
		panel.add(profileName);
		profileName.setText("Profile Name :");
		profileName.setBackground(null);
		profileName.setEditable(false);
		profileName.setHighlighter(null);

		JTextArea passwordField = new JTextArea();
		passwordField.setText("Password :");
		passwordField.setEditable(false);
		passwordField.setBackground((Color) null);
		passwordField.setBounds(10, 50, 136, 22);
		passwordField.setHighlighter(null);

		panel.add(passwordField);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		cancel.setBounds(186, 130, 100, 23);
		panel.add(cancel);

		create = new JButton("Create");
		create.setBounds(10, 130, 100, 23);
		panel.add(create);
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String filename = name.getText() + ".bl";
					System.out.println(new File(".").getCanonicalPath() + "/Profiles/" + filename);
					FileOutputStream file = new FileOutputStream(new File(".").getCanonicalPath() + "/Profiles/" + filename);
					ObjectOutputStream out = new ObjectOutputStream(file);

					UserProfile profile = new UserProfile(name.getText(), password.getText(), Double.parseDouble(balance.getText()));

					out.writeObject(profile);
					file.close();
					out.close();
					
					JOptionPane.showMessageDialog(null, "Profile " + name.getText() + " has been created!" );
					profileList.add(name.getText() + ".bl");
					InitialWindow iw = new InitialWindow(profileList);
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Profile initialization failed");
				}

			}
		});
		
		name = new JTextField();
		name.setBounds(200, 10, 85, 20);
		panel.add(name);
		name.setColumns(10);
		name.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (name.getText().trim() != "" && !name.getText().contains(".bl")) {
					nameEntry = true;
					if (balanceEntry == true && passwordEntry == true)
						create.setEnabled(true);
				} else {
					nameEntry = false;
					create.setEnabled(false);
				}
			}
		});

		balance = new JTextField();
		balance.setBounds(200, 90, 85, 20);
		panel.add(balance);
		balance.setColumns(10);
		balance.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (balance.getText().matches("^[0-9]+$")) {
					balanceEntry = true;

					if (nameEntry == true && passwordEntry == true)
						create.setEnabled(true);
				} else {
					balanceEntry = false;
					create.setEnabled(false);
				}
			}
		});

		password = new JTextField();
		password.setBounds(200, 50, 86, 20);
		panel.add(password);
		password.setColumns(10);
		password.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (password.getText().trim() != "") {
					passwordEntry = true;

					if (balanceEntry == true && nameEntry == true)
						create.setEnabled(true);
				} else {
					passwordEntry = false;
					create.setEnabled(false);
				}
			}
		});

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		create.setEnabled(false);

		JTextArea dollarSign = new JTextArea();
		dollarSign.setText("$");
		dollarSign.setBounds(186, 90, 11, 22);
		dollarSign.setEditable(false);
		dollarSign.setBackground(null);

		panel.add(dollarSign);

		setVisible(true);

	}
}
