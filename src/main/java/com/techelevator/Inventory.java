package com.techelevator;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Inventory {
	// attributes
	protected HashMap<String, Snack> inventoryAccess = new HashMap<String, Snack>();
	private double totalSales;
	// constructor
	public Inventory() {
		this.totalSales = 0.0;
	}
	// methods
	
	// getters and setters
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double purchaseAmount) {
		this.totalSales += purchaseAmount;
	}
	
	public void displayInventory() {
		Set<String> keySet = this.inventoryAccess.keySet();
		
		for (String slot : keySet) {
			System.out.print(slot + ") " + this.inventoryAccess.get(slot).toString() + "\n");
		}
	}
	
	public void FillInventory() {

		try (Scanner fileScanner = new Scanner(new File("vendingmachine.csv"))) { // try block for executing while loop that reads
													// file
			while (fileScanner.hasNextLine()) { // loop through file while able to read input
				
				String textLine = fileScanner.nextLine(); // read input and store in String variable
				String[] lineSplit = textLine.split("\\|"); // split textLine into array of strings
				Snack newFill = new Snack(lineSplit[1], new BigDecimal(lineSplit[2]), lineSplit[3]);
				this.inventoryAccess.put(lineSplit[0], newFill);
			}
			fileScanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	public void addAudit(String Action, double customerMoney, double moneyLeft, File log) {
		PrintWriter writer = new PrintWriter(log);
		writer.println();
		
	}
	*/
}
