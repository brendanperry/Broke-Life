package test;

import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class main {
	private static Date today = new Date(2020, 2, 29);
	private static Event e1 = new Event("Rent", "Some BS", -500, today);
	private static Scanner input = new Scanner(System.in);
	private static UserProfile profile;

	public static void main(String[] args) {
		int selection = 0;
		String username = "";
		double balance = 0;
		while (selection != 6) {
			System.out.println("1. Create Profile\n" + "2. Load Profile\n" + "3. Save Profile\n" + "4. Add Event\n"
					+ "5. View Events\n" + "6. Quit");
			selection = input.nextInt();
			
			if (selection == 1) {
				input.next();
				System.out.println("\nEnter a name");
					username = input.next();
				System.out.println("\nEnter a starting balance");
					balance = input.nextDouble();
					
				profile = new UserProfile(username, balance);	
				System.out.println("Profile Initialized!");	
				
			} else if (selection == 2) {
				System.out.println("Enter a username");
				username = input.next();
				
				try {
					FileInputStream file = new FileInputStream(username + ".bl"); 
		            ObjectInputStream in = new ObjectInputStream(file); 
		             
		            profile = (UserProfile)in.readObject(); 
		            System.out.println("\nProfile " + username + " loaded\n");
		            
		            in.close(); 
		            file.close(); 
				} catch (IOException e) {
					System.out.println("Could not load file");
				} catch (ClassNotFoundException ex) {}
				
			} else if (selection == 3) {
				try {
					FileOutputStream file = new FileOutputStream(username + ".bl");
					ObjectOutputStream out = new ObjectOutputStream(file);

					out.writeObject(profile);
					System.out.println("File " + username + ".bl saved!");

					file.close();
					out.close();
				} catch (IOException e) {
					System.out.println("Could not save");
				}

			} else if (selection == 4) {
				String title, desc;
				int day,month,year;
				double amount;
				
				System.out.println("Enter event title");
					title = input.next();
				System.out.println("Enter event date\nDay:");
					day = input.nextInt();
				System.out.println("Month:");
					month = input.nextInt();
				System.out.println("Year:");
					year = input.nextInt();
				System.out.println("Event event amount");
					amount = input.nextDouble();
				System.out.println("Enter description:");
					desc = input.nextLine();
					input.next();
				Date eventDate = new Date(year, month, day);	
				Event e = new Event(title, desc, amount, eventDate);
				profile.addEvent(e);
				
				
			} else if (selection == 5) {
				System.out.println(profile.toString());
			}
		}
	}

}
