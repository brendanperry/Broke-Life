import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Events represent transactions performed by the user. Events will have a direct effect on 
 * the balance of UserProfile and they may also be used to generate balance projections.
 * Drew Albert
 * 02/29/2020
 */
public class Event implements Serializable, Comparable<Event>{
	private static final long serialVersionUID = 1L;
	public String title;
	private Double amount;
	private Date date;
	private String tag;
	private Integer recurPeriod = 0;
	private Integer percentage;
	/**
	 * Constructor creates a new Event given user input. A tag value of "Misc." is given to 
	 * Events created without user-specified tags 
	 * @param title - A name given to the event
	 * @param amount - The amount of money being transfered during the event
	 * @param date - The date where an event takes place. For recurring events, this is the first date of occurrence
	 * @param recurring - The number of days between event repetition. Value of 0 used for one-time events
	 */
	public Event(String title, double amount, int percentage, Date date, int recurPeriod, String tag) {
		this.title = title;
		this.tag = tag;
		this.amount = amount;
		this.date = date;
		this.recurPeriod = recurPeriod;
		this.percentage = percentage;
	}
	
	/**
	 * Constructor creates a new Event given user input
	 * @param title - A name given to the event
	 * @param tag - A one-word descriptor for the event
	 * @param amount - The amount of money being transfered during the event
	 * @param date - The date where an event takes place. For recurring events, this is the first date of occurrence
	 * @param recurring - The number of days between event repetition. Value of 0 used for one-time events
	 */
	public Event(String title, double amount, int percentage, Date date, String tag) {
		this.title = title;
		this.tag = tag;
		this.amount = amount;
		this.date = date;
		this.tag = tag;
		this.percentage = percentage;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public int getRecurPeriod() {
		return this.recurPeriod;
	}
	
	public void setRecurPeriod(int period) {
		this.recurPeriod = period;
	}
	
	public int getPercentage() {
		return this.percentage;
	}
	
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public String dateString() {
		return (date.getMonth() + 1) + "/" + date.getDate() + "/" + (date.getYear() + 1900);
	}

	@Override
	public int compareTo(Event e) {
		if(date.getDate() < e.date.getDate())
			return -1;
		else if(date.getDate() > e.date.getDate())
			return 1;
		else return 0;
	}
	

}
