package com.bank.ridha.service;

import com.bank.ridha.exception.BankException;
import com.bank.ridha.model.Customer;
import com.bank.ridha.model.OperationType;
import com.bank.ridha.util.Money;

import java.util.Optional;

/**
 * (non-Javadoc)
 *
 * @author mrbaoueb
 * @version 1.0
 * @see OperationProxy
 * @since 2021-04-13
 */
public class OperationProxyImpl implements OperationProxy {

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bank.ridha.service.OperationProxy#processOperation(com.bank.ridha.
     * model.OperationType, com.bank.ridha.model.Customer, Optional<com.bank.ridha.model.Money>)
     */
    @Override
    public void processOperation(OperationType type, Customer customer, Optional<Money> amount) {
        switch (type) {
            case DEPOSIT:
                OperationProxy.instance.deposit(customer.getAccount(), amount);
                break;
            case WITHDRAWAL:
                OperationProxy.instance.withdraw(customer.getAccount(), amount);
                break;
            case HISTORY:
                OperationProxy.instance.history(customer.getAccount());
                break;
            default:
                throw new BankException(OperationProxy.class, "type", "not valid value");
        }
    }
}
