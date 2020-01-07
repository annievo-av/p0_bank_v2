package com.bank.bo;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public interface EmpBo {

	public List<Account> accountInfoList() throws BusinessException;

	public List<Account> pendingAccountList() throws BusinessException;

	public boolean approveAccount(String pendingUsername) throws BusinessException;

	public boolean removeAccount(String pendingUsername) throws BusinessException;

	public List<Card> pendingCardList() throws BusinessException;

	public boolean approveCard(String pendingApplicator) throws BusinessException;

	public boolean removeCard(String pendingApplicator) throws BusinessException;

	public List<Transaction> logList() throws BusinessException;
	
}
