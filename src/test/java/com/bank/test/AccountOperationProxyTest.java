package com.parking.test;

import com.bank.ridha.exception.BankException;
import com.bank.ridha.model.Account;
import com.bank.ridha.model.AccountType;
import com.bank.ridha.model.Customer;
import com.bank.ridha.model.OperationType;
import com.bank.ridha.service.OperationProxy;
import com.bank.ridha.service.OperationProxyImpl;
import com.bank.ridha.util.BankUtils;
import com.bank.ridha.util.Money;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountOperationProxyTest {

    @Test
    public void deposit_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy proxy = new OperationProxyImpl();
        proxy.processOperation(OperationType.DEPOSIT, customer, Optional.of(Money.cents(50000L)));

        assertThat(customer.getAccount().getOperations()).isNotNull();
        assertThat(customer.getAccount().getBalance()).isEqualByComparingTo(Money.euros("1800"));
        assertThat(customer.getAccount().getOperations()).hasSize(1);
        assertThat(customer.getAccount().getOperations().get(0).getLabel()).isEqualTo("Deposit");
    }

    @Test
    public void withdrawal_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy proxy = new OperationProxyImpl();
        proxy.processOperation(OperationType.WITHDRAWAL, customer, Optional.of(Money.cents(30000, "EUR")));

        assertThat(customer.getAccount().getOperations()).isNotNull();
        assertThat(customer.getAccount().getBalance()).isEqualByComparingTo(Money.euros("1000"));
        assertThat(customer.getAccount().getOperations()).hasSize(1);
        assertThat(customer.getAccount().getOperations().get(0).getLabel()).isEqualTo("Withdrawal");
    }


    @Test
    public void history_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy proxy = new OperationProxyImpl();
        proxy.processOperation(OperationType.WITHDRAWAL, customer, Optional.of(Money.cents(30000L, "EUR")));
        proxy.processOperation(OperationType.DEPOSIT, customer, Optional.of(Money.cents(20000L, "EUR")));
        proxy.processOperation(OperationType.HISTORY, customer, null);
        assertThat(customer.getAccount().getOperations()).isNotNull();
        assertThat(customer.getAccount().getBalance()).isEqualByComparingTo(Money.euros("1200"));
        assertThat(customer.getAccount().getOperations()).hasSize(2);
        assertThat(customer.getAccount().getOperations().get(0).getLabel()).isEqualTo("Withdrawal");
    }

    @Test(expected = BankException.class)
    public void invalid_operation_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy proxy = new OperationProxyImpl();
        proxy.processOperation(OperationType.OTHER, customer, Optional.of(Money.cents(30000L, "EUR")));
    }
}
