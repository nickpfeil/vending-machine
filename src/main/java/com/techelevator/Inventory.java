package com.techelevator;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
	// attributes
	private HashMap<String, ArrayList<Snack>> inventoryAccess = new HashMap<String, ArrayList<Snack>>();
	protected ArrayList<String> auditLog = new ArrayList<String>();
	protected double totalSales;
	// constructor
	public Inventory(String slot, ArrayList<Snack> update) {
		this.inventoryAccess.put(slot, update.);
		
	}
	// methods
	
	// getters and setters
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	public void addAudit(String Action, double customerMoney, double moneyLeft, File log) {
		PrintWriter writer = new PrintWriter(log);
		writer.println(LocalDateTime.now());
	}
}
