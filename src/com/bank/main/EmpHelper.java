package com.bank.main;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.bo.EmpBo;
import com.bank.bo.EmpBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public class EmpHelper {

	final static Logger log = Logger.getLogger(EmpHelper.class);

	Scanner input = new Scanner(System.in);
	EmpBo empBo = new EmpBoImpl();

	public void employeeEntry() throws BusinessException {
		log.info("Enter task number according to the below criteria: ");
		log.info("1. View Customer Account");
		log.info("2. Pending user account");
		log.info("3. Pending customer account");
		log.info("4. Transaction Log");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			viewCustomerAccount();
			employeeEntry();
			break;
		case "2":
			pendindAccount();
			employeeEntry();
			break;
		case "3":
			pendindCard();
			employeeEntry();
			break;
		case "4":
			viewTransactionLog();
			employeeEntry();
			break;
		default:
			logout();
		}
	}

	public void viewCustomerAccount() throws BusinessException {
		List<Account> accountInfoList = empBo.accountInfoList();
		if (accountInfoList.size() == 0) {
			log.info("This list is empty!");
		} else {
			int i = 1;
			for (Account a : accountInfoList) {
				log.info(i + ". Username: " + a.getUsername() + "\tUser Type: " + a.getUsertype() + "\tFull Name: "
						+ a.getFullname() + "\tEmail: " + a.getEmail() + "\t\tCard Number: "
						+ a.getCard().getCardNumber() + "\tCard Type: " + a.getCard().getCardType() + "\tBalance: "
						+ a.getCard().getBalance());
				i++;
			}
		}
	}

	public void pendindAccount() throws BusinessException {
		List<Account> pendingAccountList = empBo.pendingAccountList();
		if (pendingAccountList.size() == 0) {
			log.info("Approval list is empty!");
		} else {
			int i = 1;
			for (Account a : pendingAccountList) {
				log.info(i + ". Username: " + a.getPd_username() + "\tPassword: " + a.getPd_password() + "\tUser Type: "
						+ a.getPd_usertype() + "\tFull Name: " + a.getPd_fullname() + "\tEmail: " + a.getPd_email());
				i++;
			}

			log.info("Enter username for details: ");
			String pendingUsername = input.nextLine();

			log.info("Enter 1: Approve");
			log.info("Enter 2: Deny");
			log.info("Enter any key to exit!");
			String option = input.nextLine();
			switch (option) {
			case "1":
				if (empBo.approveAccount(pendingUsername)) {
					log.info("Account Approved.");
				} else {
					log.info("Invalid input. Please try again.");
					pendindAccount();
				}
				break;
			case "2":
				if (empBo.removeAccount(pendingUsername)) {
					log.info("Account Removed.");
				} else {
					log.info("Invalid input. Please try again.");
					pendindAccount();
				}
				break;
			default:
				logout();
			}
		}
	}

	public void pendindCard() throws BusinessException {
		List<Card> pendingCardList = empBo.pendingCardList();

		int i = 1;
		for (Card c : pendingCardList) {
			if (c.getApplicator() != null) {
				log.info(i + ". Applicator: " + c.getApplicator() + "\tCard Number: " + c.getPd_cardNumber()
						+ "\tCard Type: " + c.getPd_cardType() + "\tStarting amount: " + c.getPd_balance());
				i++;
			}
		}

		log.info("Enter applicator name for details: ");
		String pendingApplicator = input.nextLine();

		log.info("Enter 1: Approve");
		log.info("Enter 2: Deny");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			if (empBo.approveAccount(pendingApplicator)) {
				log.info("Card Approved.");
			} else {
				log.info("Invalid input. Please try again.");
				pendindCard();
			}
			break;
		case "2":
			if (empBo.removeCard(pendingApplicator)) {
				log.info("Card Removed.");
			} else {
				log.info("Invalid input. Please try again.");
				pendindCard();
			}
			break;
		default:
			logout();
		}
	}

	public void viewTransactionLog() throws BusinessException {
		List<Transaction> logList = empBo.logList();
		if (logList.size() == 0) {
			log.info("This list is empty!");
		} else {
			int i = 1;
			for (Transaction t : logList) {
				log.info(i + ". " + t.getPerson_1() + "\t" + t.getAction() + "\t" + t.getPerson_2() + "\tat "
						+ t.getTime());
				i++;
			}
		}
	}

	public void logout() {
		log.info("Thank you for banking with us. See you again!");
		System.exit(0);
	}

}
