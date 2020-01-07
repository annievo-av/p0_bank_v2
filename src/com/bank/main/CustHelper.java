package com.bank.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.bo.CustBo;
import com.bank.bo.CustBoImpl;
import com.bank.bo.EmpBo;
import com.bank.bo.EmpBoImpl;
import com.bank.bo.Validator;
import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public class CustHelper {

	final static Logger log = Logger.getLogger(CustHelper.class);

	Scanner input = new Scanner(System.in);
	CustBo custBo = new CustBoImpl();
	Validator v = new Validator();
	EmpBo empBo = new EmpBoImpl();

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	Date date = new Date(System.currentTimeMillis());

	public void customerEntryLogic(Account a) throws BusinessException {
		log.info("Enter task number according to the below criteria:");
		log.info("1. View Account Balance");
		log.info("2. Deposit");
		log.info("3. Withdraw");
		log.info("4. Transfer");
		log.info("5. Pending money");
		log.info("6. Apply for a new bank account");
		log.info("Enter any key to exit!");
		String option = input.nextLine();

		switch (option) {
		case "1":
			viewAccountBalance(a);
			customerEntryLogic(a);
			break;
		case "2":
			deposit(a);
			customerEntryLogic(a);
			break;
		case "3":
			withdraw(a);
			customerEntryLogic(a);
			break;
		case "4":
			transfer(a);
			customerEntryLogic(a);
			break;
		case "5":
			moneyPendingInfo(a);
			customerEntryLogic(a);
			break;
		case "6":
			applyNewCard(a);
			customerEntryLogic(a);
			break;
		default:
			logout();
		}
	}

	public void viewAccountBalance(Account a) throws BusinessException {
		List<Card> cardInfoList = custBo.cardInfoList(a);
		log.info("Your card(s) details are: ");
		if (cardInfoList.size() == 0) {
			log.info("This list is empty!");
		}
		for (Card c : cardInfoList) {
			log.info("Card Number: " + c.getCardNumber() + "\tCard Type: " + c.getCardType() + "\tBalance: "
					+ c.getBalance());
		}
	}

	public void deposit(Account a) throws BusinessException {
		List<Card> cList = custBo.cardInfoList(a);
		Transaction t = new Transaction();
		int cardNumberForDeposit = 0;
		double depositAmount = 0;

		if (cList.size() == 0) {
			log.info("You currently don't own any card!");
		} else if (cList.size() == 1) {
			Card card = cList.get(0);
			log.info("Enter amount: ");
			try {
				depositAmount = v.validAmount(input.nextLine());
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			}
			card.setBalance(card.getBalance() + depositAmount);
			custBo.updateBalance(card);
			log.info("Succeeded." + " Your new balance is: $" + card.getBalance() + ".");

			t.setPerson_1(a.getUsername());
			t.setAction("Deposit $" + depositAmount);
			t.setPerson_2(Integer.toString(card.getCardNumber()));
			t.setTime(formatter.format(date));
			custBo.insertLogProc(t);
		} else {
			log.info("You have more than one card, please enter the card number that you want to deposit to: ");
			try {
				cardNumberForDeposit = v.validCardNumber(input.nextLine());
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			}

			Card card = new Card();
			double currentBalance = 0;
			for (Card c : cList) {
				if (c.getCardNumber() == cardNumberForDeposit) {
					currentBalance = c.getBalance();
					card = c;
				}
			}

			if (card.getCardNumber() != cardNumberForDeposit) {
				log.error("Invalid input. Please try again!");
				deposit(a);
			} else {
				log.info("Enter amount: ");
				try {
					depositAmount = v.validAmount(input.nextLine());
				} catch (BusinessException e) {
					throw new BusinessException(e.getMessage());
				}
				card.setBalance(currentBalance + depositAmount);
				custBo.updateBalance(card);
				log.info("Succeeded." + " Your new balance is: $" + card.getBalance() + ".");

				t.setPerson_1(a.getUsername());
				t.setAction("Deposit $" + depositAmount);
				t.setPerson_2(Integer.toString(card.getCardNumber()));
				t.setTime(formatter.format(date));
				custBo.insertLogProc(t);
			}
		}
	}

	public void withdraw(Account a) throws BusinessException {
		List<Card> cList = custBo.cardInfoList(a);
		Transaction t = new Transaction();
		int cardNumberForWithdraw = 0;
		double withdrawAmount = 0, newBalance = 0;

		if (cList.size() == 0) {
			log.info("You currently don't own any card!");
		} else if (cList.size() == 1) {
			Card card = cList.get(0);
			log.info("Enter amount: ");
			try {
				withdrawAmount = v.validAmount(input.nextLine());
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			}
			newBalance = card.getBalance() - withdrawAmount;
			if (newBalance < 0) {
				log.info("Pleaser enter a valid number to continue." + " Your new balance is: $" + card.getBalance());
				withdraw(a);
			}
			card.setBalance(newBalance);
			custBo.updateBalance(card);
			log.info("Succeeded." + " Your new balance is: $" + card.getBalance() + ".");

			t.setPerson_1(a.getUsername());
			t.setAction("Withdraw $" + withdrawAmount);
			t.setPerson_2(Integer.toString(card.getCardNumber()));
			t.setTime(formatter.format(date));
			custBo.insertLogProc(t);
		} else {
			log.info("You have more than one card, please enter the card number that you want to deposit to: ");
			try {
				cardNumberForWithdraw = v.validCardNumber(input.nextLine());
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			}

			Card card = new Card();
			double currentBalance = 0;
			for (Card c : cList) {
				if (c.getCardNumber() == cardNumberForWithdraw) {
					currentBalance = c.getBalance();
					card = c;
				}
			}

			if (card.getCardNumber() != cardNumberForWithdraw) {
				log.error("Invalid input. Please try again!");
				deposit(a);
			} else {
				log.info("Enter amount: ");
				try {
					withdrawAmount = v.validAmount(input.nextLine());
				} catch (BusinessException e) {
					throw new BusinessException(e.getMessage());
				}
				newBalance = currentBalance - withdrawAmount;
				if (newBalance < 0) {
					log.info("Pleaser enter a valid number to continue." + " Your new balance is: $"
							+ card.getBalance());
					withdraw(a);
				}
				card.setBalance(newBalance);
				custBo.updateBalance(card);
				log.info("Succeeded." + " Your new balance is: $" + card.getBalance() + ".");

				t.setPerson_1(a.getUsername());
				t.setAction("Withdraw $" + withdrawAmount);
				t.setPerson_2(Integer.toString(card.getCardNumber()));
				t.setTime(formatter.format(date));
				custBo.insertLogProc(t);
			}
		}
	}

	public void transfer(Account a) throws BusinessException {
		List<Card> myCardList = custBo.cardInfoList(a);
		List<Account> systemCardList = empBo.accountInfoList();
		Transaction t = new Transaction();
		double transferAmount, myCurrentBalance, myNewBalance;
		int mySelectedCardNumber, cardNumberTransferTo;
		Card myCard = new Card();
		Card receiverCard = new Card();

		if (myCardList.size() == 0) {
			log.info("You currently don't own any card for transferring!");
		} else if (myCardList.size() == 1) {
			myCard = myCardList.get(0);
			myCurrentBalance = myCardList.get(0).getBalance();
			log.info("Enter the card number that you want to transfer to: ");
			try {
				cardNumberTransferTo = v.validCardNumber(input.nextLine());
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			}

			for (Account account : systemCardList) {
				if (account.getCard().getCardNumber() == cardNumberTransferTo) {
					receiverCard = account.getCard();
					log.info("Enter amount: ");
					try {
						transferAmount = v.validAmount(input.nextLine());
					} catch (BusinessException e) {
						throw new BusinessException(e.getMessage());
					}
					myNewBalance = myCurrentBalance - transferAmount;
					if (myNewBalance < 0) {
						log.info("Pleaser enter a valid number to continue." + " Your new balance is: $"
								+ account.getCard().getBalance());
						transfer(a);
					}

					t.setSender(a.getUsername());
					t.setAmount(transferAmount);
					t.setReceiver(receiverCard.getUsername());
					custBo.transferProc(t);

					myCard.setBalance(myNewBalance);
					custBo.updateBalance(myCard);
					log.info("Succeeded." + " Your new balance is: $" + myCard.getBalance() + ".");

					t.setPerson_1(a.getUsername());
					t.setAction("Transfer $" + transferAmount);
					t.setPerson_2(receiverCard.getUsername());
					t.setTime(formatter.format(date));
					custBo.insertLogProc(t);
				}
			}
			if (receiverCard.getCardNumber() != cardNumberTransferTo) {
				log.error("Invalid input. Please try again!");
				transfer(a);
			}
		} else {
			log.info("You have more than one card, please enter the card number that you want to deposit to: ");
			try {
				mySelectedCardNumber = v.validCardNumber(input.nextLine());
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			}

			Card card = new Card();
			double currentBalance = 0;
			for (Card c : myCardList) {
				if (c.getCardNumber() == mySelectedCardNumber) {
					currentBalance = c.getBalance();
					card = c;
				}
			}

			if (card.getCardNumber() != mySelectedCardNumber) {
				log.error("Invalid input. Please try again!");
				transfer(a);
			} else {
				log.info("Enter the card number that you want to transfer to: ");
				try {
					cardNumberTransferTo = v.validCardNumber(input.nextLine());
				} catch (BusinessException e) {
					throw new BusinessException(e.getMessage());
				}

				for (Account account : systemCardList) {
					if (account.getCard().getCardNumber() == cardNumberTransferTo) {
						receiverCard = account.getCard();
						log.info("Enter amount: ");
						try {
							transferAmount = v.validAmount(input.nextLine());
						} catch (BusinessException e) {
							throw new BusinessException(e.getMessage());
						}
						myNewBalance = currentBalance - transferAmount;
						if (myNewBalance < 0) {
							log.info("Pleaser enter a valid number to continue." + " Your new balance is: $"
									+ account.getCard().getBalance());
							transfer(a);
						}

						t.setSender(a.getUsername());
						t.setAmount(transferAmount);
						t.setReceiver(receiverCard.getUsername());
						custBo.transferProc(t);

						myCard.setBalance(myNewBalance);
						custBo.updateBalance(myCard);
						log.info("Succeeded." + " Your new balance is: $" + myCard.getBalance() + ".");

						t.setPerson_1(a.getUsername());
						t.setAction("Transfer $" + transferAmount);
						t.setPerson_2(receiverCard.getUsername());
						t.setTime(formatter.format(date));
						custBo.insertLogProc(t);
					}
				}
				if (receiverCard.getCardNumber() != cardNumberTransferTo) {
					log.error("Invalid input. Please try again!");
					transfer(a);
				}
			}
		}
	}

	public void applyNewCard(Account a) throws BusinessException {
		Card c = new Card();
		int customizedCardNumber = 0;
		log.info("Enter 1: Debit Card");
		log.info("Enter 2: Credit Card");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			c.setPd_cardType("Debit Card");
			break;
		case "2":
			c.setPd_cardType("Credit Card");
			break;
		default:
			logout();
		}

		log.info("Enter your customized card number: ");
		try {
			customizedCardNumber = v.validCardNumber(input.nextLine());
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
		c.setPd_cardNumber(customizedCardNumber);
		c.setApplicator(a.getUsername());

		double startingBalance;
		log.info("Enter amount as starting balance: ");
		try {
			startingBalance = v.validAmount(input.nextLine());
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
		c.setPd_balance(startingBalance);

		custBo.applyNewCard(c);
		log.info("Congratulation, your new bank card has been created. Currently pending for approval!");
	}

	public void moneyPendingInfo(Account a) throws BusinessException {
		List<Transaction> pendingMoneyList = custBo.pendingMoneyList(a);
		List<Card> myCardList = custBo.cardInfoList(a);
		int cardNumberForReview = 0;
		double currentBalance = 0, reviewBalance = 0;
		if (pendingMoneyList.size() == 0 || myCardList.size() == 0) {
			log.info("You either don't own any card or your pending list is empty!");
		} else {
			log.info("Enter your card number: ");
			try {
				cardNumberForReview = v.validCardNumber(input.nextLine());
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			}

			Card card = new Card();
			for (Card c : myCardList) {
				if (cardNumberForReview == c.getCardNumber()) {
					card = c;
					currentBalance = c.getBalance();
				}
			}
			for (Transaction list : pendingMoneyList) {
				if (a.getUsername().equals(list.getReceiver())) {
					reviewBalance = list.getAmount();
					log.info("Sender: " + list.getSender() + "\tAmount: " + list.getAmount());
				}
			}
			if (pendingMoneyList.size() > 1) {
				log.info("Enter the amount regarding to the card number you want to make decision on: ");
				try {
					reviewBalance = v.validAmount(input.nextLine());
				} catch (BusinessException e) {
					throw new BusinessException(e.getMessage());
				}
			}

			log.info("Enter 1: Approve");
			log.info("Enter 2: Deny");
			log.info("Enter any key to exit!");
			String option = input.nextLine();
			switch (option) {
			case "1":
				for (Transaction t : pendingMoneyList) {
					if (t.getAmount() == reviewBalance) {
						card.setBalance(currentBalance + reviewBalance);
						custBo.updateBalance(card);
						custBo.removePendingAmount(t);
						log.info("Amount Approved.");

						t.setPerson_1(a.getUsername());
						t.setAction("Approve $" + reviewBalance);
						t.setPerson_2(card.getUsername());
						t.setTime(formatter.format(date));
						custBo.insertLogProc(t);
					}
				}
				break;
			case "2":
				for (Transaction t : pendingMoneyList) {
					if (t.getAmount() == reviewBalance) {
						card.setBalance(currentBalance + reviewBalance);
						custBo.removePendingAmount(t);
						log.info("Amount Denied.");

						t.setPerson_1(a.getUsername());
						t.setAction("Deny $" + reviewBalance);
						t.setPerson_2(card.getUsername());
						t.setTime(formatter.format(date));
						custBo.insertLogProc(t);
					}
				}
				break;
			default:
				logout();
			}
		}
	}

	public void logout() {
		log.info("Thank you for banking with us. See you again!");
		System.exit(0);
	}

}
