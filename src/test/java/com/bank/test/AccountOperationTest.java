package com.parking.test;

import com.bank.ridha.exception.BankException;
import com.bank.ridha.model.Account;
import com.bank.ridha.model.AccountType;
import com.bank.ridha.model.Customer;
import com.bank.ridha.service.OperationProxy;
import com.bank.ridha.util.BankUtils;
import com.bank.ridha.util.Money;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountOperationTest {

    @Test
    public void deposit_account_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy.instance.deposit(customer.getAccount(), Optional.of(Money.euros("500")));

        assertThat(customer.getAccount().getId()).isNotNull();
        assertThat(customer.getAccount().getNumber()).isEqualTo("123456");
        assertThat(customer.getAccount().getType()).isEqualTo(AccountType.PERSONAL);
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getFirstName()).isEqualTo("John");
        assertThat(customer.getLastName()).isEqualTo("Doe");
        assertThat(customer.getAccount().getOperations()).isNotNull();
        assertThat(customer.getAccount().getBalance()).isEqualByComparingTo(Money.euros("1800"));
        assertThat(customer.getAccount().getOperations()).hasSize(1);
        assertThat(customer.getAccount().getOperations().get(0).getLabel()).isEqualTo("Deposit");
    }

    @Test(expected = BankException.class)
    public void deposit_null_account_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy.instance.deposit(customer.getAccount(), Optional.empty());
    }


    @Test
    public void withdrawal_account_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy.instance.withdraw(customer.getAccount(), Optional.of(Money.euros("500")));

        assertThat(customer.getAccount().getOperations()).isNotNull();
        assertThat(customer.getAccount().getBalance()).isEqualByComparingTo(Money.euros("800"));
        assertThat(customer.getAccount().getOperations()).hasSize(1);
        assertThat(customer.getAccount().getOperations().get(0).getLabel()).isEqualTo("Withdrawal");
    }

    @Test(expected = BankException.class)
    public void withdrawal_null_account_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy.instance.withdraw(customer.getAccount(), Optional.of(Money.euros("1500")));
    }

    @Test
    public void history_account_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();
        OperationProxy.instance.withdraw(customer.getAccount(), Optional.of(Money.euros("500")));
        OperationProxy.instance.withdraw(customer.getAccount(), Optional.of(Money.euros("300")));
        OperationProxy.instance.deposit(customer.getAccount(), Optional.of(Money.euros("900")));
        OperationProxy.instance.deposit(customer.getAccount(), Optional.of(Money.euros("3000")));
        OperationProxy.instance.withdraw(customer.getAccount(), Optional.of(Money.euros("150")));
        OperationProxy.instance.withdraw(customer.getAccount(), Optional.of(Money.euros("50")));

        assertThat(customer.getAccount().getOperations()).isNotNull();
        assertThat(customer.getAccount().getBalance()).isEqualByComparingTo(Money.euros("4200"));
        assertThat(customer.getAccount().getOperations()).hasSize(6);
        assertThat(customer.getAccount().getOperations().get(0).getLabel()).isEqualTo("Withdrawal");
        assertThat(customer.getAccount().getOperations().get(0).getLabel()).isEqualTo("Withdrawal");
        assertThat(customer.getAccount().getOperations().get(2).getLabel()).isEqualTo("Deposit");

        OperationProxy.instance.history(customer.getAccount());
    }

    @Test
    public void empty_history_account_test() {
        Account account = Account.builder().balance(Money.euros("1300")).number("123456").type(AccountType.PERSONAL).id(BankUtils.generayteId()).build();
        Customer customer = Customer.builder().firstName("John").lastName("Doe").account(account).id(BankUtils.generayteId()).build();

        assertThat(customer.getAccount().getOperations()).isNull();
        assertThat(customer.getAccount().getBalance()).isEqualByComparingTo(Money.euros("1300"));

        OperationProxy.instance.history(customer.getAccount());
    }
}
