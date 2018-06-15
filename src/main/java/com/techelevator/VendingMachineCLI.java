package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import com.techelevator.view.Menu;

public class VendingMachineCLI {
	
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
				this.userFunds = userFunds.add(new BigDecimal(money));
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
						this.userFunds = this.userFunds.subtract(machine.inventoryAccess.get(item).getCost());
						// Output item purchased
						System.out.print("You Purchased: " + machine.inventoryAccess.get(item).getName() + " ");
						// Delete 1 item from quantity value
						machine.inventoryAccess.get(item).setQuantity(machine.inventoryAccess.get(item).getQuantity() -1);
						// Output consumeSound
						consumeSounds.add(machine.inventoryAccess.get(item).getConsumeSound());
						//System.out.println(machine.inventoryAccess.get(item).getConsumeSound());
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
		System.exit(0);
		
	}
	
	public static void main(String[] args) {

		Menu menu = new Menu(System.in, System.out); // instantiate menu object(input-source, output-source)
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();

		
	}
}
