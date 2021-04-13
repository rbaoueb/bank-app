package com.bank.ridha.model;

import com.bank.ridha.util.Money;
import lombok.*;

import java.util.List;

/**
 * Account.java a class represent Account entity
 *
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 *
 */
@Data
@Builder
@AllArgsConstructor
public class Account {

    private Integer id;

    private String number;

    private AccountType type;

    private Money balance = Money.cents(0);

    private List<Operation> operations;
}
