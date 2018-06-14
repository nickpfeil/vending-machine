package com.techelevator;

import java.io.File;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	/*
	public void addAudit(String Action, double customerMoney, double moneyLeft, File log) {
		PrintWriter writer = new PrintWriter(log);
		writer.println();
		
	}
	*/
}
