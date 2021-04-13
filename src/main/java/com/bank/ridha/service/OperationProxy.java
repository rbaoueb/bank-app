package com.bank.ridha.service;

import com.bank.ridha.model.Customer;
import com.bank.ridha.model.OperationType;
import com.bank.ridha.util.Money;

import java.util.Optional;

/**
 * OperationProxy.java an interface to be used as a proxy to execute the right operation using its type
 *
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 */
public interface OperationProxy {

    /**
     * and instance of {@link AccountOperation} to be used when we want to
     * initialize an operation
     */
    AccountOperation instance = new AccountOperationImpl();

    /**
     * depending upton the {@link OperationType}, this method wil execute the corresponding process
     * @param type
     * @param customer
     * @param amount
     */
    void processOperation(OperationType type, Customer customer, Optional<Money> amount);
}
