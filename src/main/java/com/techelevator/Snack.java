package com.techelevator;

import java.math.BigDecimal;

public class Snack {
	private String name;
	private BigDecimal cost;
	private String type;
	private String consumeSound;
	private int quantity;

	public Snack(String name, BigDecimal cost, String type) {
		this.name = name;
		this.cost = cost;
		this.type = type;
		this.quantity = 5;
		if (this.type.equals("Chip")) {
			this.consumeSound = "Crunch Crunch Yum!";
		} else if (this.type.equals("Candy")) {
			this.consumeSound = "Munch Munch, Yum!";
		} else if (this.type.equals("Drink")) {
			this.consumeSound = "Glug Glug, Yum!";
		} else if (this.type.equals("Gum")) {
			this.consumeSound = "Chew Chew, Yum!";
		}
	}

	@Override
	public String toString() {
		return this.getName() + this.getCost() + "  remaining: " + this.getQuantity();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConsumeSound() {
		return consumeSound;
	}

}
