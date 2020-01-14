package com.bank.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bank.bo.Validator;
import com.bank.exception.BusinessException;

class ValidatorTest {

	@Test
	void validCardNumberTest() throws BusinessException {
		Validator v = new Validator();
		int expected = 123456789;
		int actual = v.validCardNumber("123456789");
		assertEquals(expected, actual);
	}

	@Test
	void validAmountTest() throws BusinessException {
		Validator v = new Validator();
		double expected = 89.89;
		double actual = v.validAmount("89.89");
		assertEquals(expected, actual);
	}

}
