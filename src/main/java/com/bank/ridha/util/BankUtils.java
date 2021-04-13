package com.bank.ridha.util;

import java.util.Random;

/**
 * BankUtils.java an utility class used to manage the bank operations entities
 * processes
 * 
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 *
 */
public class BankUtils {
	/**
	 * a {@link Random} instance (initialized from current time) to be used to
	 * generate unique IDs
	 */
	private static final Random random = new Random(System.currentTimeMillis());

	/**
	 * this method generates a random integer
	 * 
	 * @return a long data type.
	 */
	public static Integer generayteId() {
		return Math.abs(random.nextInt());
	}

}
