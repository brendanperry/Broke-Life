import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
		ArrayList<String> profiles = new ArrayList<String>();
		File profileDir = new File(new File(".").getCanonicalPath() + "/Profiles/");
		listFilesForFolder(profileDir, profiles);
		InitialWindow start = new InitialWindow(profiles);  
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
            	if(!profiles.contains(fileEntry.getName()))
            		profiles.add(fileEntry.getName());
        }
	}
}