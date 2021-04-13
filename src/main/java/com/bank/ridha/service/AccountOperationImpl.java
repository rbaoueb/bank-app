package com.bank.ridha.service;

import com.bank.ridha.exception.BankException;
import com.bank.ridha.model.Account;
import com.bank.ridha.model.Operation;
import com.bank.ridha.util.BankUtils;
import com.bank.ridha.util.Money;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * (non-Javadoc)
 *
 * @author mrbaoueb
 * @version 1.0
 * @see AccountOperation
 * @since 2021-04-13
 */
public class AccountOperationImpl implements AccountOperation {

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bank.ridha.service.AccountOperation#deposit(com.bank.ridha.
     * model.Account, Optional<com.bank.ridha.model.Money>)
     */
    @Override
    public void deposit(Account account, Optional<Money> amount) {
        Money amountValue = amount.orElseThrow(() -> new BankException(Account.class, "amount", "should not be null"));
        account.setBalance(account.getBalance().plus(amountValue));
        Operation operation = Operation.builder().id(BankUtils.generayteId()).amount(amountValue).date(new Date()).balance(account.getBalance()).label("Deposit").build();
        if (account.getOperations() == null) {
            account.setOperations(new ArrayList<>());
        }
        account.getOperations().add(operation);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bank.ridha.service.AccountOperation#withdraw(com.bank.ridha.
     * model.Account, Optional<com.bank.ridha.model.Money>)
     */
    @Override
    public void withdraw(Account account, Optional<Money> amount) {
        Money amountValue = amount.orElseThrow(() -> new BankException(Account.class, "amount", "should not be null"));
        Money balanceAfterWithdraw = account.getBalance().minus(amountValue);
        if (!balanceAfterWithdraw.isPositive()) {
            throw new BankException(Account.class, "balance", "should not be negative");
        }
        account.setBalance(balanceAfterWithdraw);
        Operation operation = Operation.builder().id(BankUtils.generayteId()).amount(amountValue).date(new Date()).balance(account.getBalance()).label("Withdrawal").build();
        if (account.getOperations() == null) {
            account.setOperations(new ArrayList<>());
        }
        account.getOperations().add(operation);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bank.ridha.service.AccountOperation#history(com.bank.ridha.
     * model.Account)
     */
    @Override
    public void history(Account account) {
        if (CollectionUtils.isEmpty(account.getOperations())) {
            System.out.format("+------------+------------+--------------------+------------+------------+%n");
            System.out.format("| Number     | Operation  | Date               | Amount     | Balance    |%n");
            System.out.format("+------------+------------+--------------------+------------+------------+%n");
            System.out.format("+                           NO OPERATION FOUND                           +%n");
            System.out.format("+------------+------------+--------------------+------------+------------+%n");
        } else {
            String leftAlignFormat = "| %-10s | %-10s | %s | %-10s | %-10s |%n";
            System.out.format("+------------+------------+--------------------+------------+------------+%n");
            System.out.format("| Number     | Operation  | Date               | Amount     | Balance    |%n");
            System.out.format("+------------+------------+--------------------+------------+------------+%n");

            account.getOperations().forEach(op -> {
                System.out.format(leftAlignFormat, op.getId(), op.getLabel(), String.format("%1$td/%1$tm/%1$tY à %1$tH:%1$tM", op.getDate()), op.getAmount().formatFrench(), op.getBalance().formatFrench());
            });
            System.out.format("+------------+------------+--------------------+------------+------------+%n");
        }

    }

}


