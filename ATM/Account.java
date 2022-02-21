import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {
	// variables
	private int customerNumber, pinNumber;
	private double currentBalance = 0, overdraft = 0, balWithOverdraft = 0;

	Scanner input = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");

	// The Constant Notes.
	protected static final int[] currDenom = { 50, 20, 10, 5 };
	// The Number of notes of each type
	protected static int[] currNo = { 10, 30, 30, 20 };
	// Count
	protected int[] count = { 0, 0, 0, 0, 0 };
	protected static int totalCorpus;
	static {
		calcTotalCorpus();
	}

	public static void calcTotalCorpus() {
		for (int i = 0; i < currDenom.length; i++) {
			totalCorpus = totalCorpus + currDenom[i] * currNo[i];
		}
	}

//	public Account() {
//	}

	public Account(int customerNumber, int pinNumber) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
	}

	public Account(int customerNumber, int pinNumber, double currentBalance, double overdraft) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
		this.currentBalance = currentBalance;
		this.overdraft = overdraft;
	}

	public int setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
		return customerNumber;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public double calcCurrentWithdraw(double amount) {
		currentBalance = (currentBalance - amount);
		return currentBalance;
	}

	public void getCurrentWithdrawInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Currents Account Balance: " + moneyFormat.format(currentBalance));
				System.out.print("\nAmount you want to withdraw from Currents Account: ");
				int amount = input.nextInt();
				if (amount <= totalCorpus) {
					for (int i = 0; i < currDenom.length; i++) {
						if (currDenom[i] <= amount) {
							int noteCount = amount / currDenom[i];
							if (currNo[i] > 0) {
								count[i] = amount >= currNo[i] ? currNo[i] : amount;
								currNo[i] = amount >= currNo[i] ? 0 : currNo[i] - amount;
								totalCorpus = totalCorpus - (count[i] * currDenom[i]);
								amount = amount - (count[i] * currDenom[i]);
								balWithOverdraft = currentBalance + overdraft;
								if ((balWithOverdraft - amount) >= 0 && amount >= 0) {
									calcCurrentWithdraw(amount);
									end = true;
								}
							}
						}
					}
					displayNotes();
					displayLeftNotes();
				} else {
					System.out.println("Unable to dispense cash at this moment for this big amount");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	private void displayNotes() {
		for (int i = 0; i < count.length; i++) {
			if (count[i] != 0) {
				System.out.println(currDenom[i] + " * " + count[i] + " = " + (currDenom[i] * count[i]));
			}
		}
	}

	private void displayLeftNotes() {
		for (int i = 0; i < currDenom.length; i++) {
			System.out.println("Notes of " + currDenom[i] + " left are " + currNo[i]);
		}

	}
}
