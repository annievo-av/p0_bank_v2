package com.bank.to;

public class Transaction {

	private String sender;
	private double amount;
	private String receiver;

	private String person_1;
	private String action;
	private String person_2;
	private String time;

	public Transaction() {
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getPerson_1() {
		return person_1;
	}

	public void setPerson_1(String person_1) {
		this.person_1 = person_1;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPerson_2() {
		return person_2;
	}

	public void setPerson_2(String person_2) {
		this.person_2 = person_2;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
