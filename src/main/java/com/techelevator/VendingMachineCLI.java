package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";  //Define a constant for menu test
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";							// Define a constant for menu text
	private static final String MAIN_MENU_OPTION_EXIT = "EXIT";									// Define a constant for menu text
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,			//Define an array with menu choices
													   MAIN_MENU_OPTION_PURCHASE,
													   MAIN_MENU_OPTION_EXIT};
	
	private Menu menu;
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	
	public void run() {
		while(true) {
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // invoke the getChoiceFromOptions method with array of options
			
			if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
			} else if(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			}
			else if (choice.equals(MAIN_MENU_OPTION_EXIT)){
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);				// Instantiate menu object (input-source, output source)
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
