package com.bank.bo;

import com.bank.dao.UserDao;
import com.bank.dao.UserDaoImpl;
import com.bank.exception.BusinessException;

public class Validator {

	private UserDao userDao;

	public UserDao getUserDao() {
		if (userDao == null) {
			userDao = new UserDaoImpl();
		}
		return userDao;
	}

	public int validCardNumber(String input) throws BusinessException {
		int cardNumber;
		if (!input.matches("[0-9]{9}")) {
			throw new BusinessException("Please enter 9 digits valid number for your card number");
		} else {
			cardNumber = Integer.parseInt(input);
		}
		return cardNumber;
	}

	public double validAmount(String input) throws BusinessException {
		double amount;
		if (input.matches("^[a-zA-Z]*$")) {
			throw new BusinessException("Pleaser enter a valid number to continue");
		} else {
			amount = Double.parseDouble(input);
			if (amount < 0) {
				throw new BusinessException("Pleaser enter a positive number to continue");
			}
		}
		return amount;
	}

}
