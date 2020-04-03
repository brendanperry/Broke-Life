import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author drew
 *
 */
public class UserProfile implements Serializable {
	private String name, password;
	private double balance;
	private ArrayList<Event> events;
	
	public UserProfile(String name, String password, double balance) {
		this.name = name;
		this.balance = balance;
		this.password = password;
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
	
	public Event getEvent(int i) {
		return events.get(i);
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double amount) {
		this.balance = amount;
	}
	
	public String toString() {
		String output = "User: " + name
				+ "\nBalance: " + balance 
				+ "\n" + listEvents();
		return output;
	}
}
