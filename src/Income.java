import java.io.Serializable;
import java.util.Date;

/**
 * The Income class acts as a representation of all money the user receives within a month 
 * DREW ALBERT 
 * 04/10/2020
 */
public class Income implements Serializable {
	private static final long serialVersionUID = 1L;
	private int month, year;
	private Double[] weeks = new Double[4];
	private double tips;
	private double misc;

	/**
	 * Constructor method which initializes an Income object that represents a certain month
	 * @param month
	 */
	public Income(int month, int year) {
		this.month = month;
		this.year = year;
	}

	/**
	 * Designate the amount that was paid to the user within a given week
	 * @param pay - The amount paid to the user
	 * @param index - The particular week out of the month that pay was given
	 */
	public void setWeek(double pay, int index) {
		if(index <= 3 && index >= 0)
			weeks[index] = pay;
		//Do nothing if index is invalid
	}

	public double getWeek(int index) {
		if(!(index > 3) && index > 0)
			return weeks[index];
		else return -1; //Invalid week selection
	}
	
	public void setTips(double tips) {
		this.tips = tips;
	}
	
	public double getTips() {
		return this.tips;
		
	}
	
	public void setMisc(double misc) {
		this.misc = misc;
	}
	
	public double getMisc(double misc) {
		return misc;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public double sumIncome() {
		double sum = 0.0;
		
		for(int i = 0; i < 4; i++) {
			sum += weeks[i];
		}
		
		return sum + tips + misc;
	}
	
	/**
	 * Compares two income objects to see if they occur during the same period
	 * @param income - The Income object being compared with the calling object 
	 * @return - A boolean indicating whether or not the two Income values share a date
	 */
	public boolean equals(Income income) {
		if(income.month == this.month && income.year == this.year)		
			return true;
		else return false;
	}
	
}

