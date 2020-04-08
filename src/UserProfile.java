import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * UserProfile holds all the data pertaining to a particular user. Instances created
 * within ProfileCreation and utilized throughout all other program classes. 
 * Drew Albert
 * 02/29/2020
 */
public class UserProfile implements Serializable {
	private String name, password;
	private double balance;
	private ArrayList<Event> events;
	private ArrayList<Event> recurring;
	
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
		if(e.getRecurPeriod() > 0) {
			recurring.add(e);
		} else
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
	
	public void sortEvents() {
		
	}
	
	/**
	 * Generates a list of events which occur during a specified time period
	 * @param start - The starting date of the search space
	 * @param end - The ending date of the search space
	 * @return - A list of events that inclusively fall between the start and end dates
	 */
	public Event[] getEvents(Date start, Date end){
		ArrayList<Event> list = new ArrayList<Event>();
		for(int i = 0; i < events.size(); i++) {
			if((events.get(i).getDate().compareTo(start) > 0 || events.get(i).getDate().compareTo(start) == 0)
					&& (events.get(i).getDate().compareTo(end) < 0 || events.get(i).getDate().compareTo(end) == 0)) {
				list.add(events.get(i));
			}
		}
		
		return (Event[])list.toArray();
		
	}
	
	/**
	 * Generates a list of events with a given tag value
	 * @param events - The list of events being searched
	 * @param tag - The specified event tag being searched for
	 * @return - The compiled list of events having the specified tag value
	 */
	public Event[] getEvents(Event[] events, String tag) {
		ArrayList<Event> list = new ArrayList<Event>();
		for(int i = 0; i < events.length; i ++) {
			if(events[i].getTag().equals(tag))
				list.add(events[i]);
		}
		
		return (Event[])list.toArray();
	}
	
	/**
	 * Counts the number of unique tags for a given list of events
	 * @param events - The list of events being counted
	 * @return - The count of unique tags present within a list
	 */
	public int countTags(Event[] events) {
		int count = 0;
		ArrayList<String> tags = new ArrayList<String>();
		
		for(int i = 0; i < events.length; i++) {
			if(!tags.contains(events[i].getTag())) {
				tags.add(events[i].getTag());
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Find the total amount of all events within a given period
	 * @param events - The list of events being totaled
	 * @return - The combined amount of all events listed
	 */
	public double sumEvents(Event[] events) {
		double total = 0.0;
		
		for(int i=0; i < events.length; i++) {
			total += events[i].getAmount();
		}
		
		return total;
	}
}