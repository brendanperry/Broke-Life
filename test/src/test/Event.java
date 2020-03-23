package test;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Serializable{
	public String title;
	public String desc;
	private double amount;
	private Date date;
	
	public Event(String title, String desc, double amount, Date date) {
		this.title = title;
		this.desc = desc;
		this.amount = amount;
		this.date = date;
	}
	
	
	public Date getDate() {
		return date;
	}
	
	public String getTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String dateString(Date day) {
		return day.getMonth() + " " + day.getDay() + " " + day.getYear();
	}
	
	public String toString() {
		String output = "Event: " + title + 
				"\nDate: " + dateString(date) +
				"\nAmount: " + amount;
		if(desc != null) {
			output += "\nDescription: " + desc;
		}
		
		return output;
	}
	
}
