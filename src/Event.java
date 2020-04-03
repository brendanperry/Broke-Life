import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Events represent transactions performed by the user. Events will have a direct effect on 
 * the balance of UserProfile and they may also be used to generate balance projections.
 * Drew Albert
 * 02/29/2020
 */
public class Event implements Serializable{
	private static final long serialVersionUID = 1L;
	public String title;
	private double amount;
	private Date date;
	private String tag;
	private int recurPeriod = 0;
	
	/**
	 * Constructor creates a new Event given user input. A tag value of "Misc." is given to 
	 * Events created without user-specified tags 
	 * @param title - A name given to the event
	 * @param amount - The amount of money being transfered during the event
	 * @param date - The date where an event takes place. For recurring events, this is the first date of occurrence
	 * @param recurring - The number of days between event repetition. Value of 0 used for one-time events
	 */
	public Event(String title, double amount, Date date, int recurPeriod) {
		this.title = title;
		this.tag = "Misc.";
		this.amount = amount;
		this.date = date;
		this.recurPeriod = recurPeriod;
	}
	
	/**
	 * Constructor creates a new Event given user input
	 * @param title - A name given to the event
	 * @param tag - A one-word descriptor for the event
	 * @param amount - The amount of money being transfered during the event
	 * @param date - The date where an event takes place. For recurring events, this is the first date of occurrence
	 * @param recurring - The number of days between event repetition. Value of 0 used for one-time events
	 */
	public Event(String title, double amount, Date date, String tag) {
		this.title = title;
		this.tag = tag;
		this.amount = amount;
		this.date = date;
		this.tag = tag;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void setRecurPeriod(int period) {
		this.recurPeriod = period;
	}
	
	public int getRecurPeriod() {
		return this.recurPeriod;
	}
	
	public String dateString(Date day) {
		return day.getMonth() + " " + day.getDay() + " " + day.getYear();
	}
	
	public String toString() {
		String output = "Event: " + title + 
				"\nDate: " + dateString(date) +
				"\nAmount: " + amount;
		return output;
	}
	
}
