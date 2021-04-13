package com.bank.ridha.model;

import lombok.*;

/**
 * Customer.java a class represent Customer entity
 *
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 *
 */
@Getter
@Builder
@AllArgsConstructor
public class Customer {

    private Integer id;

    private String firstName;

    private String lastName;

    private Account account;
}
