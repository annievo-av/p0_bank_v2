package com.bank.to;

public class Card {
	
	private int cardNumber;
	private String cardType;
	private double balance;
	private String username;

	private int pd_cardNumber;
	private String pd_cardType;
	private double pd_balance;
	private String applicator;

	public Card() {
	}

	public Card(int cardNumber, String cardType, double balance, String username) {
		super();
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.balance = balance;
		this.username = username;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPd_cardNumber() {
		return pd_cardNumber;
	}

	public void setPd_cardNumber(int pd_cardNumber) {
		this.pd_cardNumber = pd_cardNumber;
	}

	public String getPd_cardType() {
		return pd_cardType;
	}

	public void setPd_cardType(String pd_cardType) {
		this.pd_cardType = pd_cardType;
	}

	public double getPd_balance() {
		return pd_balance;
	}

	public void setPd_balance(double pd_balance) {
		this.pd_balance = pd_balance;
	}

	public String getApplicator() {
		return applicator;
	}

	public void setApplicator(String applicator) {
		this.applicator = applicator;
	}

}
