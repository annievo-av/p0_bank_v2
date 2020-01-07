package com.bank.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.bo.UserBo;
import com.bank.bo.UserBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.Account;

public class UserHelper {

	final static Logger log = Logger.getLogger(UserHelper.class);

	Scanner input = new Scanner(System.in);
	UserBo userBo = new UserBoImpl();
	CustHelper custHelper = new CustHelper();
	EmpHelper empHelper = new EmpHelper();

	public void loginEntry() throws BusinessException {
		Account account = new Account();
		log.info("Enter username: ");
		String username = input.nextLine();
		log.info("Enter password: ");
		String password = input.nextLine();

		account = userBo.validLogin(username, password);
		while (account.getUsername() == null) {
			log.error("Your enter values are not matched with any account in our system. Please try again!");
			loginEntry();
		}

		if (userBo.isEmployee(account)) {
			empHelper.employeeEntry();
		} else {
			custHelper.customerEntryLogic(account);
		}
	}

	public void signupEntry() throws BusinessException {
		Account account = new Account();
		log.info("Enter username: ");
		String username = input.nextLine();

		while (userBo.isUsernameExist(username)) {
			log.error("Taken. Please try again.");
			signupEntry();
		}
		account.setPd_username(username);

		log.info("Enter password: ");
		account.setPd_password(input.nextLine());
		log.info("Enter your full name: ");
		account.setPd_fullname(input.nextLine());
		log.info("Enter your email: ");
		account.setPd_email(input.nextLine());
		account.setPd_usertype("Customer");

		account = new Account(account.getPd_username(), account.getPd_password(), account.getPd_usertype(),
				account.getPd_fullname(), account.getPd_email());
		userBo.signup(account);
		log.info("Congratulation, your account has been created. Currently pending for approval!");
	}

}
