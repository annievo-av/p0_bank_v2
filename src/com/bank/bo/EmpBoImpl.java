package com.bank.bo;

import java.util.List;

import com.bank.dao.EmpDao;
import com.bank.dao.EmpDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public class EmpBoImpl implements EmpBo {

	private EmpDao empDao;

	public EmpDao getEmpDao() {
		if (empDao == null) {
			empDao = new EmpDaoImpl();
		}
		return empDao;
	}

	@Override
	public List<Account> accountInfoList() throws BusinessException {
		List<Account> accountInfoList = getEmpDao().accountInfoList();
		return accountInfoList;
	}

	@Override
	public List<Account> pendingAccountList() throws BusinessException {
		List<Account> pendingAccountList = getEmpDao().pendingAccountList();
		return pendingAccountList;
	}

	@Override
	public boolean approveAccount(String pendingUsername) throws BusinessException {
		boolean valid = false;
		Account account = new Account();
		List<Account> pendingAccountList = pendingAccountList();
		for (Account a : pendingAccountList) {
			if (pendingUsername.equals(a.getPd_username())) {
				account = new Account(a.getPd_username(), a.getPd_password(), a.getPd_usertype(), a.getPd_fullname(),
						a.getPd_email());
				getEmpDao().approveAccountProc(account);
				getEmpDao().removePendingAccount(account);
				valid = true;
			}
		}
		return valid;
	}

	@Override
	public boolean removeAccount(String pendingUsername) throws BusinessException {
		boolean valid = false;
		Account account = new Account();
		List<Account> pendingAccountList = pendingAccountList();
		for (Account a : pendingAccountList) {
			if (pendingUsername.equals(a.getPd_username())) {
				account = new Account(a.getPd_username(), a.getPd_password(), a.getPd_usertype(), a.getPd_fullname(),
						a.getPd_email());
				getEmpDao().removePendingAccount(account);
				valid = true;
			}
		}
		return valid;
	}

	@Override
	public List<Card> pendingCardList() throws BusinessException {
		List<Card> pendingCardList = getEmpDao().pendingCardList();
		return pendingCardList;
	}
	
	@Override
	public boolean approveCard(String pendingApplicator) throws BusinessException {
		boolean valid = false;
		Card card = new Card();
		List<Card> pendingCardList = pendingCardList();
		for (Card c : pendingCardList) {
			if (pendingApplicator.equals(c.getApplicator())) {
				card = new Card(c.getPd_cardNumber(), c.getPd_cardType(), c.getPd_balance(), c.getApplicator());
				getEmpDao().approveCardProc(card);
				getEmpDao().removePendingCard(card);
				valid = true;
			}
		}
		return valid;
	}

	@Override
	public boolean removeCard(String pendingApplicator) throws BusinessException {
		boolean valid = false;
		Card card = new Card();
		List<Card> pendingCardList = pendingCardList();
		for (Card c : pendingCardList) {
			if (pendingApplicator.equals(c.getApplicator())) {
				card = new Card(c.getPd_cardNumber(), c.getPd_cardType(), c.getPd_balance(), c.getApplicator());
				getEmpDao().removePendingCard(card);
				valid = true;
			}
		}
		return valid;
	}

	@Override
	public List<Transaction> logList() throws BusinessException {
		List<Transaction> logList = getEmpDao().logList();
		return logList;
	}

}
