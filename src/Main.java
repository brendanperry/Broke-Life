import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

// main class
/**
 * Main class acts as the starting point of the program. Profile selection occurs before launching the main program
 * @author Drew Albert
 */
public class Main {
	
	/**
	 * Main method creates an InitialWindow given the list of profiles retrieved from the specified directory
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		// sets the look and feel of the GUI
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		ArrayList<String> profiles = new ArrayList<String>();
		File currentDir = new File(new File(".").getCanonicalPath());
		File profileDir = new File(currentDir.getCanonicalPath() + "/Profiles/");
		
		if(!profileDir.isDirectory()) {
			profileDir.mkdirs();
		} else {
			listFilesForFolder(profileDir, profiles);
		}
		new InitialWindow(profiles);  
    }
    
	/**
     * Helper method utilized within InitialWindow. Lists all user profiles saved within a given directory
     * @param folder - A directory containing ".bl" files, the file extension given to serialized UserProfiles
     * 		  profiles - A list of the names of valid files
     * @author Drew Albert
     */
	public static void listFilesForFolder(final File folder, ArrayList<String> profiles) {
        for (final File fileEntry : folder.listFiles()) {
            if(fileEntry.getName().contains(".bl"))
            	profiles.add(fileEntry.getName());
        }
	}
}