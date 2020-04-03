import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * GUI class providing a form for selecting the desired UserProfile. MainFrame is initiated upon profile selection.
 * 04/02/2020
 * Drew Albert
 */
public class InitialWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	private static DefaultListModel<String> list = new DefaultListModel<String>();
	static boolean selected = false;
	private static UserProfile profile = null;
	
	ArrayList<String> profileNames = new ArrayList<String>();
	
	/**
	 * Constructor method that draws the GUI window
	 * @throws IOException - Resulting from issues that may arise in loading of profile
	 * 
	 */
	public InitialWindow(ArrayList<String> profileNames) throws IOException {
    	JTextArea text;
    	JList<String> profileList = new JList<String>(list);
    	JButton okay, close, newProfile;
    	
        	setAlwaysOnTop(true);
        	setForeground(Color.WHITE);
        	setSize(400,310);
        	setTitle("Select a Profile");
        	getContentPane().setLayout(null);
        	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        	setResizable(false);
        	
        	JPanel panel = new JPanel();
        	panel.setSize(400, 600);
        	getContentPane().add(panel);
        	panel.setVisible(true);
        	panel.setLayout(null);
        	
        	profileList.setBounds(20, 40, 350, 200);
        	profileList.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        	profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        	panel.add(profileList);
        	
    		for(int i = 0; i < profileNames.size(); i++) 
    			list.addElement(profileNames.get(i));
    		
        	text = new JTextArea("Select a User Profile from the list below");
        	text.setEditable(false);
        	text.setBackground(null);
        	text.setBounds(24, 11, 291, 22);
        	text.setColumns(10);
        	text.setHighlighter(null);
        	panel.add(text);
        	
        	close = new JButton("Close");
        	close.setBounds(279, 250, 90, 23);
        	close.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent arg0) {
        			System.exit(0);
        		}
        	});
        	
        	okay = new JButton("Okay");
        	okay.setBounds(20, 250, 90, 23);
        	okay.setEnabled(false);
        	okay.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent arg0) {
        			try {
        				String filename = profileList.getSelectedValue() + ".bl";
						File file = new File(new File(".").getCanonicalPath() + "/Profiles/" + filename);
						FileInputStream fileInput = new FileInputStream(file); 
			            ObjectInputStream decode = new ObjectInputStream(fileInput); 
			             
			            profile = (UserProfile)decode.readObject(); 
			            
			            decode.close(); 
			            fileInput.close();
			            MainFrame mf = new MainFrame(profile);
			            dispose();
					} catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Profile selection invalid");
					}
        		}
        	});
        	
        	newProfile = new JButton("Create a New Profile");
        	newProfile.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent arg0) {
        			ProfileCreation pc = new ProfileCreation(profileNames);
        			dispose();
        		}
        	});
        	
        	newProfile.setBounds(120, 250, 150, 23);
        	
        	ListSelectionModel listModel = profileList.getSelectionModel();
        	listModel.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					okay.setEnabled(true);
				}
        		
        	});
        	
        	panel.add(okay);
        	panel.add(close);
        	panel.add(newProfile);
        	
        	setVisible(true);
    }
}

