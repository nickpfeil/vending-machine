package com.techelevator;

public class Snack {
	private String name;
	private double cost;
	private String type;
	private String consumeSound;

	public Snack(String name, double cost, String type) {
		this.name = name;
		this.cost = cost;
		this.type = type;
		if (this.type.equals("chip")) {
			this.consumeSound = "Crunch Crunch Yum!";
		} else if (this.type.equals("Candy")) {
			this.consumeSound = "Munch Munch, Yum!";
		} else if (this.type.equals("Drink")) {
			this.consumeSound = "Glug Glug, Yum!";
		} else if (this.type.equals("Gum")) {
			this.consumeSound = "Chew Chew, Yum!";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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

	public void setConsumeSound(String consumeSound) {
		this.consumeSound = consumeSound;

	}

}
