import java.io.Serializable;
import java.util.Date;

/**
 * The Balance class represents the monthly change that occurs to the user's initial balance
 * @author Drew Albert
 */
public class Balance implements Serializable{
	private static final long serialVersionUID = 1L;
	private double balance;
	private int month;
	private int year;
	
	public Balance(double balance, Date date) {
		this.balance = balance ;
		this.month = date.getMonth();
		this.year = date.getYear();
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
}
