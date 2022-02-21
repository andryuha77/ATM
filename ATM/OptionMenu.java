import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<Integer, Account> data = new HashMap<Integer, Account>();

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		while (!end) {
			try {
				System.out.print("\nPlease enter your account number: ");
				customerNumber = menuInput.nextInt();
				System.out.print("\nPlease enter your PIN: ");
				pinNumber = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					Account acc = (Account) pair.getValue();
					if (data.containsKey(customerNumber) && pinNumber == acc.getPinNumber()) {
						getCurrent(acc);
						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nWrong Customer Number or Pin Number");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Character(s). Only Numbers.");
			}
		}
	}

	public void getCurrent(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					System.out.println("\nCurrent Account Balance: " + moneyFormat.format(acc.getCurrentBalance()) + " Overdraft: " + moneyFormat.format(acc.getOverdraft()));
					break;
				case 2:
					acc.getCurrentWithdrawInput();
					break;
				case 3:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void initAccounts() throws IOException {
		data.put(123456789, new Account(123456789, 1234, 800, 200));
		data.put(987654321, new Account(987654321, 4321, 1230, 150));
		getLogin();
	}
}
