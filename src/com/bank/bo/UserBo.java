package com.bank.bo;

import com.bank.exception.BusinessException;
import com.bank.to.Account;

public interface UserBo {

	public Account validLogin(String username, String password) throws BusinessException;
	
	public boolean isEmployee(Account account) throws BusinessException;
	
	public boolean isUsernameExist(String username) throws BusinessException;
	
	public void signup(Account a) throws BusinessException;
}
