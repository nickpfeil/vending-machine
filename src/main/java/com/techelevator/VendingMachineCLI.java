package com.techelevator;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import com.techelevator.view.Menu;

public class VendingMachineCLI {
	
	private File auditLog;
	private PrintWriter diskFile;
	private BigDecimal userFunds = new BigDecimal("0");
	private ArrayList<String> consumeSounds = new ArrayList<String>();
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items"; // define a constant																	// for menu text
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase"; // define a constant for menu text
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, // define an array with menu options
													   MAIN_MENU_OPTION_PURCHASE, 
													   MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money"; // define a constant																	// for menu text
	private static final String PURCHASE_MENU_OPTION_PURCHASE_ITEM = "Purchase Item"; // define a constant for menu text
	private static final String PURCHASE_MENU_OPTION_MAKE_CHANGE = "Gimme my change";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, // define an array with menu options
													   PURCHASE_MENU_OPTION_PURCHASE_ITEM,
													   PURCHASE_MENU_OPTION_MAKE_CHANGE,
													   MAIN_MENU_OPTION_EXIT };
	
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		auditLog = new File("audit_log.txt");
		try {
			diskFile = new PrintWriter(auditLog);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.menu = menu;
	}

	public void run() {
		
		// Create and fill inventory for vending machine
		Inventory machineInventory = new Inventory();
		machineInventory.FillInventory();	
		
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS); // invoke the getChoiceFromOptions
																			// method
			// with array of options
			//System.out.print(LocalDateTime.now());
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				machineInventory.displayInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchaseMenu(machineInventory);
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				diskFile.close();
				return;
			}
		}
	}

	@SuppressWarnings("resource")
	public void feedMoney() {
		Scanner moneySlot = new Scanner(System.in);
		
		while (true) {
			System.out.println("*****| Machine accepts $1, $2, $5, $10 |*****");
			System.out.println("Please insert money (enter 'Q' when finished): ");
			String money = moneySlot.next();
			// check to see if their money is accepted
			if (money.equals("Q")) {
				return;
			} else if (money.equals("1") || money.equals("2") || money.equals("5") || money.equals("10")) {
				BigDecimal previousFunds = this.userFunds;
				this.userFunds = userFunds.add(new BigDecimal(money));
				audit("FEED MONEY", previousFunds);
			} else {
				continue;
			}
		}
	}
	
	public void purchaseMenu(Inventory machine) {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS); // invoke the getChoiceFromOptions																		// method
			// with array of options
			//System.out.print(LocalDateTime.now());
			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				feedMoney();
			} else if (choice.equals(PURCHASE_MENU_OPTION_PURCHASE_ITEM)) {
				purchaseItem(machine);
			} else if (choice.equals(PURCHASE_MENU_OPTION_MAKE_CHANGE)) {
				makeChange();
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				return;
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void purchaseItem(Inventory machine) {
		Scanner itemPurchase = new Scanner(System.in);
		Set<String> keySet = machine.inventoryAccess.keySet();
		
		while (true) {
			System.out.println("***| Which item would you like to purchase? |***");
			System.out.println("Enter item slot or enter 'Q' when finished): ");
			String item = itemPurchase.next();
			
			// loop to check valid slot
			for (String slot : keySet) {
				// if the slot is valid input
				if(slot.equals(item)) {
					if ((this.userFunds.compareTo(machine.inventoryAccess.get(item).getCost()) > 0) 
							&& (machine.inventoryAccess.get(item).getQuantity() > 0)) {
						// subtract snack price from userFunds
						BigDecimal previousFunds = this.userFunds;
						this.userFunds = this.userFunds.subtract(machine.inventoryAccess.get(item).getCost());
						//send report to audit log
						audit(machine.inventoryAccess.get(item).getName() + " " + slot, previousFunds);
						// Output item purchased
						System.out.print("You Purchased: " + machine.inventoryAccess.get(item).getName() + " ");
						// Delete 1 item from quantity value
						machine.inventoryAccess.get(item).setQuantity(machine.inventoryAccess.get(item).getQuantity() -1);
						// Output consumeSound
						consumeSounds.add(machine.inventoryAccess.get(item).getConsumeSound());
						// Print quantity of snacks left
						System.out.println("and there are " + machine.inventoryAccess.get(item).getQuantity() + " " +
											machine.inventoryAccess.get(item).getName() + "'s remaining");
						// Show userFunds balance
						System.out.println("Your balance is: " + this.userFunds.toString());
						System.out.println(" ");
					} else if(this.userFunds.compareTo(machine.inventoryAccess.get(item).getCost()) < 0) {
						System.out.println("User funds insufficient!");
						System.out.println(" ");
					} else {
						System.out.println("Invalid item slot!");
						System.out.println(" ");
						continue;
					}
				} else if (item.equals("Q")) {
					return;
				} 
			}
		}
	}
	
	public void makeChange() {
		BigDecimal previousFunds = this.userFunds;
		this.userFunds = this.userFunds.multiply(new BigDecimal("100"));
		double parseFunds = Double.parseDouble(this.userFunds.toString());
		int totalFunds = (int)parseFunds;
		int dollars = 0;
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		// make change loop to determine coin amounts
		while (totalFunds > 0) {
			if (totalFunds > 100) {
				dollars = totalFunds / 100;
				totalFunds = totalFunds % 100;
			} else if (totalFunds > 25) {
				quarters = totalFunds / 25;
				totalFunds = totalFunds % 25;
			} else if (totalFunds > 10) {
				dimes = totalFunds / 10;
				totalFunds = totalFunds % 10;
			} else if (totalFunds > 5) {
				nickels = totalFunds / 5;
				totalFunds = totalFunds % 5;
			}
		}
		// output change to user
		System.out.println("");
		System.out.println("Your change is: " + dollars + " dollars "
							+ quarters + " quarters " 
							+ dimes + " dimes "
							+ nickels + " nickels");
		for(String sound : consumeSounds) {
			System.out.println(sound);
		}
		this.userFunds = new BigDecimal("0");
		audit("GIVE CHANGE", previousFunds);
		return;
	}
	
	public void audit(String action, BigDecimal previousFunds) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		String dateLine = dateFormat.format(new Date()).toString();
		try {
			diskFile.printf("%1$-20s %2$-18s %3$-9s %4$5s", dateLine, action, "$" + String.format("%.2f", previousFunds), "$" + String.format("%.2f", this.userFunds) + "\n");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		Menu menu = new Menu(System.in, System.out); // instantiate menu object(input-source, output-source)
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();

		
	}
}
