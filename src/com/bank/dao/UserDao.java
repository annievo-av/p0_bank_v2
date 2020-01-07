package com.bank.dao;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.Account;

public interface UserDao {

	public void signup(Account a) throws BusinessException;
	
	public List<Account> accountList() throws BusinessException;

}
