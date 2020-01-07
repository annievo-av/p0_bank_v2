package com.bank.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.exception.BusinessException;

public class Main {

	final static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		UserHelper helper = new UserHelper();
		
		String option;
		do {
			welcomeBanner();
			log.info("Enter 1: Login");
			log.info("Enter 2: Sign Up");
			log.info("Enter any key to exit!");
			option = input.nextLine();
			switch (option) {
			case "1":
				try {
					helper.loginEntry();
				} catch (BusinessException e) {
					log.error(e.getMessage());
				}
				break;
			case "2":
				try {
					helper.signupEntry();
				} catch (BusinessException e) {
					log.error(e.getMessage());
				}
				break;
			default:
				System.exit(0);
			}
		} while (option != "1" || option != "2");
		
	}

	public static void welcomeBanner() {
		log.info("#####################################################################################");
		log.info("#####################################################################################");
		log.info("###########-------------" + ConsoleColors.BLUE_BOLD
				+ "Welcome to Noncreative-Naming Java Bank" + ConsoleColors.RESET_BLACK + "-----------###########");
		log.info("#####################################################################################");
		log.info("#####################################################################################");
	}

	public class ConsoleColors {
		public static final String RESET_BLACK = "\033[0;30m";
		public static final String BLUE_BOLD = "\033[1;34m";
	}

}
