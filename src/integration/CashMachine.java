package integration;

import java.util.ArrayList;
import java.util.List;

import util.enums.Type;

/**
 * This class is a simplified version of a cash machine in a store. The main
 * tasks performed in this class are to calculate how much the customer will
 * receive after a cash payment and to deposit money in the class paid for a
 * purchase.
 * 
 * @author ozsan
 *
 */
public class CashMachine implements Transaction {
	private List<MoneyObserver> listOfMoneyObservers = new ArrayList<>();
	private List<Money> runningLow = new ArrayList<>();
	private Money[] inRegister = new Money[10];
	Money dummy = new Money(0, 0, Type.DUMMY);
	Money fivehundred = new Money(500, 1, Type.FIVEHUNDRED);
	Money twohundred = new Money(200, 4, Type.TWOHUNDRED);
	Money hundred = new Money(100, 1, Type.HUNDRED);
	Money fifty = new Money(50, 21, Type.FIFTY);
	Money twenty = new Money(20, 50, Type.TWENTY);
	Money ten = new Money(10, 50, Type.TEN);
	Money five = new Money(5, 50, Type.FIVE);
	Money two = new Money(2, 50, Type.TWO);
	Money one = new Money(1, 100, Type.ONE);

	/**
	 * Constructor
	 */
	public CashMachine() {
		inRegister[0] = dummy;
		inRegister[1] = fivehundred;
		inRegister[2] = twohundred;
		inRegister[3] = hundred;
		inRegister[4] = fifty;
		inRegister[5] = twenty;
		inRegister[6] = ten;
		inRegister[7] = five;
		inRegister[8] = two;
		inRegister[9] = one;

	}

	/**
	 * used for update cashregister 
	 * @return amount money in machine
	 */
	public int getAmountMoneyInMachine() {
		int amount = 0;
		for (Money money : inRegister)
			amount += (money.getValue() * money.getQuantity());
		return amount;
	}

	/**
	 * creates a list of banknotes that the customer will receive.
	 * 
	 * uses addToChange to add the list and manipulates the Money object's
	 * attributes for quantity to the customer and what is left in the cash
	 * register.
	 * 
	 * addToChange uses findPivot to find the highest banknote
	 * 
	 * findPivot uses existInRegister to check if the highest banknote is in the
	 * cash register
	 * 
	 * 
	 * @return list of change back with quantity
	 */
	private List<Money> amountBack(double amountToPayCustomer) {
		int change = (int) Math.round(amountToPayCustomer);
		List<Money> changeBack = new ArrayList<>();
		addToChange(changeBack, change);
		balanceControl();
		return changeBack;
	}

	/**
	 * adds money obj to an list and adds amount money to give customer with money
	 * attribute setQuantityInChange and remove amount money from machine with money
	 * attribute removeFromQuantity
	 * 
	 * uses findPivot uses regulatedQuantity uses balanceControl
	 * 
	 * 
	 * @param list   list of money
	 * @param change
	 */
	private void addToChange(List<Money> list, int change) {
		int pivot = findPivot(change);
		int quantity;
		for (; pivot < inRegister.length; pivot++) {
			quantity = change / inRegister[pivot].getValue();
			quantity = regulatedQuantity(quantity, inRegister[pivot]);
			if (quantity != 0) {
				Money moneyToChance = new Money(inRegister[pivot], quantity);
				inRegister[pivot].removeFromQuantity(quantity);
				list.add(moneyToChance);
				change -= quantity * inRegister[pivot].getValue();
			}
			
		}
	}

	/**
	 * checks if amount money exist in machine if amount pay back is greater then
	 * amount money in machine sets quantity to amount money exist in machine
	 * 
	 * @param quantity number of
	 * @param money    the money who amount checked
	 * @return controlled quantity
	 */
	private int regulatedQuantity(int quantity, Money money) {
		return (quantity > money.getQuantity()) ? money.getQuantity() : quantity;

	}

	private void balanceControl() {
		for(int i = 1; i < inRegister.length; i++) 
			if(inRegister[i].getQuantity() < 2) {
				runningLow.add(inRegister[i]);
			}
		
			notifyMoneyObserver();
	}

	/**
	 * 
	 * @param change
	 * @return
	 */
	private int findPivot(int change) {
		int pivot = 0;
		for (int i = 1; i < inRegister.length; i++)
			if (change / inRegister[i].getValue() >= 1 && existInRegister(inRegister[i])) {
				pivot = i;
				break;
			}
		return pivot;
	}

	/**
	 * Controls if the specific money exist in machine
	 * 
	 * @param moneyValue controlled money
	 * @return true / false
	 */
	private boolean existInRegister(Money moneyValue) {
		for (Money money : inRegister)
			if (money.equals(moneyValue) && money.getQuantity() != 0)
				return true;
		return false;
	}

	/**
	 * get amount money found highest value banknote adds the banknotes quantity in
	 * machine subtract value of banknote from amount and keep doing algorithm until
	 * amount is zero.
	 * 
	 * uses findCurrentHigestBanknote uses updateAmount uses
	 * updateQuantityOfBanknotes uses chooseBanknotes
	 * 
	 * @param amount amount money paid
	 */
	private void paidMoneyInPayment(double amount) {
		int paid = (int) Math.round(amount);
		while (paid != 0) {
			int highValue = findCurrentHigestBanknote(paid);
			updateQuantityOfBanknotes(highValue, paid);
			paid = updateAmount(highValue, paid);
		}

	}

	private int findCurrentHigestBanknote(int amount) {
		int banknote = 0;
		for (int i = 1; i < inRegister.length; i++) {
			if (amount / inRegister[i].getValue() == 1) {
				banknote = i;
				break;
			}
		}
		return --banknote;
	}

	private int updateAmount(int highValue, int amount) {
		return amount - inRegister[chooseBanknotes(highValue, amount)].getValue();
	}

	private void updateQuantityOfBanknotes(int highValue, int amount) {
		inRegister[chooseBanknotes(highValue, amount)].addQuantity(1);
	}

	private int chooseBanknotes(int highValue, int amount) {
		int lowValue = highValue + 1;
		return (amount > inRegister[highValue].getValue()) ? highValue : lowValue;
	}
	/**
	 * Transaction interface method who called from Payment 
	 */
	@Override
	public List<Money> moneyFlow(double paid, double change) {
		paidMoneyInPayment(paid);
		return amountBack(change);
	}

	/**
	 * adds list of money observer to the money observer list
	 * 
	 * @param moneyObservers list
	 */
	public void addMoneyObservers(List<MoneyObserver> moneyObservers) {
		listOfMoneyObservers.addAll(moneyObservers);
	}

	private void notifyMoneyObserver() {
		for (MoneyObserver moneyObserver : listOfMoneyObservers)
			moneyObserver.moneyWarning(runningLow);
	}
	
	public List<Money> getRunninLow(){
		return this.runningLow;
	}

}
