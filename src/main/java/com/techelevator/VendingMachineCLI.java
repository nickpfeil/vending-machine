package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {
	
	private BigDecimal userFunds;
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items"; // define a constant																	// for menu text
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase"; // define a constant for menu text
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, // define an array with menu options
													   MAIN_MENU_OPTION_PURCHASE, 
													   MAIN_MENU_OPTION_EXIT };
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS); // invoke the getChoiceFromOptions
			Inventory machineInventory = new Inventory();
			machineInventory.FillInventory();																		// method
			// with array of options
			//System.out.print(LocalDateTime.now());
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				machineInventory.displayInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				return;
			}
			
			
		}
	}

	public void feedMoney() {
		Scanner moneySlot = new Scanner(System.in);
		System.out.println("*****| Machine accepts $1, $2, $5, $10 |*****");
		System.out.println("Please insert money (enter 'Q' when finished): ");
		while (true) {
			String money = moneySlot.nextLine();
			if (money.equals("Q")) {
				return;
			} else if (money.equals("1") || money.equals("2") || money.equals("5") || money.equals("10")) {
				this.userFunds.add(new BigDecimal(money));
			} else {
				continue;
			}
			moneySlot.close();
		}
	}
	
	public static void main(String[] args) {

		Menu menu = new Menu(System.in, System.out); // instantiate menu object(input-source, output-source)
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();

		
	}
}
