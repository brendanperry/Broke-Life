package test;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author drew
 *
 */
public class UserProfile implements Serializable {
	private String name;
	private double balance;
	private ArrayList<Event> events;
	
	public UserProfile(String name, double balance) {
		this.name = name;
		this.balance = balance;
		events = new ArrayList<Event>();
	}
	
	
	public String listEvents() {
		String output = "";
		
		for(int i=0; i< events.size(); i++) {
			output += (i + ". " + events.get(i).toString()+ "\n\n");
		}
		
		return output;
	}
	
	
	public void addEvent(Event e) {
		events.add(e);
	}
	
	
	public String toString() {
		String output = "User: " + name
				+ "\nBalance: " + balance 
				+ "\n" + listEvents();
		return output;
	}
}
