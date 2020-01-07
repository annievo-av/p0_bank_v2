package com.bank.bo;

import java.util.List;

import com.bank.dao.UserDao;
import com.bank.dao.UserDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.Account;

public class UserBoImpl implements UserBo {

	private UserDao userDao;

	public UserDao getUserDao() {
		if (userDao == null) {
			userDao = new UserDaoImpl();
		}
		return userDao;
	}

	@Override
	public Account validLogin(String username, String password) throws BusinessException {
		Account account = new Account();
		List<Account> usernameList = getUserDao().accountList();
		for (Account a : usernameList) {
			if (a.getUsername().equals(username) && (a.getPassword().equals(password))) {
				account = a;
			}
		}
		return account;
	}

	@Override
	public boolean isEmployee(Account account) throws BusinessException {
		boolean valid;
		if (account.getUsertype().equals("Employee")) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;
	}
	
	@Override
	public boolean isUsernameExist(String username) throws BusinessException {
		boolean exist = false;
		List<Account> accountList = getUserDao().accountList();
		for (Account user : accountList) {
			if (user.getUsername().equals(username)) {
				exist = true;
			}
		}
		return exist;
	}

	@Override
	public void signup(Account a) throws BusinessException {
		getUserDao().signup(a);
	}

}
