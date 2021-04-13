package com.bank.ridha.service;

import com.bank.ridha.exception.BankException;
import com.bank.ridha.model.Account;
import com.bank.ridha.util.Money;

import java.util.Optional;

/**
 * AccountOperation.java an interface to be used when we want to initialize some operations on a given account
 *
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 */
public interface AccountOperation {

    /**
     * deposit fund in a given account
     * (should throw a {@link BankException} when we have null or 0 as amount)
     *
     * @param {@link Account} instance
     * @param {@link Double} amount
     */
    void deposit(Account account, Optional<Money> amount);

	/**
	 * used to make withdrawal from a given account
	 * (should throw a {@link BankException} when we don't have enough funds)
	 *
	 * @param {@link Account} instance
	 * @param {@link Double} amount
	 */
	void withdraw(Account account, Optional<Money> amount);


	/**
	 * used to retrieve the operations history of a given account
	 *
	 * @param {@link Account} instance
	 * @return operations list
	 */
	void history(Account account);

}
