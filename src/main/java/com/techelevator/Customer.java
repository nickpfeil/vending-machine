package com.techelevator;

import java.math.BigDecimal;

public class Customer {
	
	
	public Customer() {
		this.userFunds = new BigDecimal("0.00");
	}


	public BigDecimal getUserFunds() {
		return userFunds;
	}

	
	public void feedMoney(BigDecimal money) {
		this.userFunds.add(money);
	}
	
	
}
