package com.bank.bo;

import java.util.List;

import com.bank.dao.CustDao;
import com.bank.dao.CustDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public class CustBoImpl implements CustBo {

	private CustDao custDao;

	public CustDao getCustDao() {
		if (custDao == null) {
			custDao = new CustDaoImpl();
		}
		return custDao;
	}

	@Override
	public List<Card> cardInfoList(Account a) throws BusinessException {
		List<Card> cardInfoList = getCustDao().cardInfoList(a);
		return cardInfoList;
	}

	@Override
	public void updateBalance(Card c) throws BusinessException {
		getCustDao().updateBalance(c);
	}

	@Override
	public void insertLogProc(Transaction t) throws BusinessException {
		getCustDao().insertLogProc(t);
	}

	@Override
	public void transferProc(Transaction t) throws BusinessException {
		getCustDao().transferProc(t);
	}

	@Override
	public void applyNewCard(Card c) throws BusinessException {
		getCustDao().applyNewCard(c);
	}

	@Override
	public List<Transaction> pendingMoneyList(Account a) throws BusinessException {
		List<Transaction> pendingMoneyList = getCustDao().pendingMoneyList(a);
		return pendingMoneyList;
	}

	@Override
	public void removePendingAmount(Transaction t) throws BusinessException {
		getCustDao().removePendingAmount(t);
	}

}
