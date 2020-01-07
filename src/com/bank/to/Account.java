package com.bank.to;

public class Account {
	
	private String username;
	private String password;
	private String usertype;
	private String fullname;
	private String email;

	private String pd_username;
	private String pd_password;
	private String pd_usertype;
	private String pd_fullname;
	private String pd_email;

	private Card card;

	public Account() {
	}
	
	public Account(String pd_username, String pd_password, String pd_usertype, String pd_fullname, String pd_email) {
		super();
		this.pd_username = pd_username;
		this.pd_password = pd_password;
		this.pd_usertype = pd_usertype;
		this.pd_fullname = pd_fullname;
		this.pd_email = pd_email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPd_username() {
		return pd_username;
	}

	public void setPd_username(String pd_username) {
		this.pd_username = pd_username;
	}

	public String getPd_password() {
		return pd_password;
	}

	public void setPd_password(String pd_password) {
		this.pd_password = pd_password;
	}

	public String getPd_usertype() {
		return pd_usertype;
	}

	public void setPd_usertype(String pd_usertype) {
		this.pd_usertype = pd_usertype;
	}

	public String getPd_fullname() {
		return pd_fullname;
	}

	public void setPd_fullname(String pd_fullname) {
		this.pd_fullname = pd_fullname;
	}

	public String getPd_email() {
		return pd_email;
	}

	public void setPd_email(String pd_email) {
		this.pd_email = pd_email;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

}
