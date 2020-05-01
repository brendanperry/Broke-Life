import java.io.Externalizable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * UserProfile holds all the data pertaining to a particular user. Instances created
 * within ProfileCreation and utilized throughout all other program classes. 
 * Drew Albert
 * 02/29/2020
 */
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 14L;
	private String name, password;
	private double balance;
	private ArrayList<Event> events = new ArrayList<Event>();
	private ArrayList<Income> monthlyIncome = new ArrayList<Income>();
	private Date creationDate;
	public UserProfile() {}
	
	public double goal;
	public double need;
	
	
	public UserProfile(String name, String password, Double balance, Date date) {
		this.name = name;
		this.balance = balance;
		this.password = password;
		events = new ArrayList<Event>();
		this.creationDate = date;
	}
	
	public void setGoal(double goal) {
		this.goal = goal;
	}
	
	public double getGoal() {
		return this.goal;
	}
	
	public int getNumberOfEvents() {
		return events.size();
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
	
	public void removeEvent(int i) {
		events.remove(i);
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
		Collections.sort(events);
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
		
		return compileEvents(list);
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
		
		return compileEvents(list);
	}
	
	/**
	 * Generate a list of recurring events from a given list of events
	 * @param events - The list of events being searched
	 * @return - A list of recurring events from the given array
	 */
	public Event[] getRecurringEvents(Event[] events) {
		ArrayList<Event> list = new ArrayList<Event>();
		for(int i = 0; i < events.length; i ++) {
			if(events[i].getRecurPeriod() > 0)
				list.add(events[i]);
		}
		
		return compileEvents(list);
	}
	
	/**
	 * Generate a list of non-recurring events from a given list of events
	 * @param events - The list of events being searched
	 * @return - A list of recurring events from the given array
	 */
	public Event[] getNonRecuringEvents(Event[] events) {
		ArrayList<Event> list = new ArrayList<Event>();
		for(int i = 0; i < events.length; i ++) {
			if(events[i].getRecurPeriod() == 0)
				list.add(events[i]);
		}
		
		return compileEvents(list);
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
	 * Find the total amount of all events within a given period. Accounts for events which may reapeat during a month
	 * @param events - The list of events being totaled
	 * @return - The combined amount of all events listed
	 */
	public double sumEvents(Event[] events, int month) {
		double total = 0.0;
		int endDay = 0;
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			// last day is 31st
			endDay = 31;
		}
		else if(month == 2) {
			// last day is 28th
			endDay += 28;
		}
		else {
			// last day is 30th
			endDay += 30;
		}
		
		for(int i=0; i < events.length; i++) {
			if(events[i].getRecurPeriod() == 0)
				total += events[i].getAmount();
			else if(events[i].getRecurPeriod() == 1) //Daily
				total += events[i].getAmount() * endDay;
			else if(events[i].getRecurPeriod() == 7) //Weekly
				total += (events[i].getAmount() * 4);
			else if(events[i].getRecurPeriod() == 14) //Biweekly
				total += (events[i].getAmount() * 2);
		}
		
		return total;
	}

	public void addIncome(Income income) {
		monthlyIncome.add(income);
	}
	
	public boolean isNewMonth(int year, int month) {
		Income income = new Income(year, month);

		for(int i = 0; i < monthlyIncome.size(); i++) {
			if(monthlyIncome.get(i).equals(income))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Retrieve the user's income from a given month
	 * @param year - The specified year
	 * @param month - the specified month
	 */
	public Income getIncome(int year, int month) {
		Income income = new Income(year, month);

		for(int i = 0; i < monthlyIncome.size(); i++) {
			if(monthlyIncome.get(i).equals(income))
				return monthlyIncome.get(i);
		}
		
		addIncome(income);
		return income;
	}
	
	/**
	 * Calculate the sum of income from a given year
	 * @param year - The specified year of earnings
	 * @return - The total amount of income gained during the year
	 */
	public double annualIncome(int year) {
		double sum = 0.0;
		for(int i = 0; i < monthlyIncome.size(); i++) {
			if(monthlyIncome.get(i).getYear() == year)
				sum += monthlyIncome.get(i).sumIncome();
		}
		
		return sum;
	}
	
	/**
	 * Helper method which copies the values of an ArrayList of events to a statically-sized Array
	 * @param list - The ArrayList of events being condensed
	 * @return - The compiled array of events
	 */
	private Event[] compileEvents(ArrayList<Event> list) {
		Event[] events = new Event[list.size()];
		for(int i = 0; i < list.size(); i++) {
			events[i] = list.get(i);
		}
		
		return events;
	}
	
	/**
	 * Saves all current UserProfile information to a ".bl" file
	 * @throws FileNotFoundException - Issue that occurs while choosing the path of ".bl" file
	 * @throws IOException - Issue that occur while saving to the file
	 */
	public void saveProfile() throws FileNotFoundException, IOException {
		String filename =  this.name + ".bl";
		FileOutputStream file = new FileOutputStream(new File(".").getCanonicalPath() + "/Profiles/" + filename);
		ObjectOutputStream out = new ObjectOutputStream(file);
		
		out.writeObject(this);
		file.close();
		out.close();
	}

	public void setNeeded(double need) {
		this.need = need;
	}
	public double getNeeded() {
		return this.need;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
}