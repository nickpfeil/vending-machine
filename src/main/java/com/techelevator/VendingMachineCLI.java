package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";	// define a constant for menu text
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";							// define a constant for menu text
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,			// define an array with menu choices
													   MAIN_MENU_OPTION_PURCHASE, 
													   MAIN_MENU_OPTION_EXIT };
	
	private Menu menu;
	
	public static void FillInventory(Inventory machineInventory) {

		try (Scanner fileScanner = new Scanner("vendingmachine.csv")) { // try block for executing while loop that reads
																		// file
			while (fileScanner.hasNextLine()) { // loop through file while able to read input

				String textLine = fileScanner.nextLine(); // read input and store in String variable
				String[] lineSplit = textLine.split("\\|"); // split textLine into array of strings
				Snack newFill = new Snack(lineSplit[1], Double.parseDouble(lineSplit[2]), lineSplit[3]);
				machineInventory.inventoryAccess.put(lineSplit[0], newFill);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	
	public void run() {
		while(true) {
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);	// invoke the getChoiceFromOptions method
																				// with array of options 
			System.out.print(LocalDateTime.now());
			if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
			} else if(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)){
				return;
			}
		}
	}
	
	public static void main(String[] args) {

		//Inventory machineInventory = new Inventory();
		//FillInventory(machineInventory);
		Menu menu = new Menu(System.in, System.out);			// instantiate menu object(input-source, output-source)
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
