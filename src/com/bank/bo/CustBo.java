package com.bank.bo;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public interface CustBo {
	
	public List<Card> cardInfoList(Account a) throws BusinessException;

	public void updateBalance(Card c) throws BusinessException;
	
	public void insertLogProc(Transaction t) throws BusinessException;

	public void transferProc(Transaction t) throws BusinessException;

	public void applyNewCard(Card c) throws BusinessException;

	public List<Transaction> pendingMoneyList(Account a) throws BusinessException;
	
	public void removePendingAmount(Transaction t) throws BusinessException;
	
}
