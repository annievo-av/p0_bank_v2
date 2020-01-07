package com.bank.dao;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public interface EmpDao {

	public List<Account> accountInfoList() throws BusinessException;

	public List<Account> pendingAccountList() throws BusinessException;

	public List<Card> pendingCardList() throws BusinessException;

	public List<Transaction> logList() throws BusinessException;
	
	public void approveAccountProc(Account a) throws BusinessException;
	
	public void approveCardProc(Card c) throws BusinessException;
	
	public void removePendingAccount (Account a) throws BusinessException;
	
	public void removePendingCard (Card c) throws BusinessException;

}
