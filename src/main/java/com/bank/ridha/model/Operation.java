package com.bank.ridha.model;

import com.bank.ridha.util.Money;
import lombok.*;

import java.util.Date;

/**
 * Operation.java a class represent Operation entity
 *
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 *
 */
@Getter
@Builder
@AllArgsConstructor
public class Operation {

    private Integer id;

    private String label;

    private Date date;

    private Money amount;

    private Money balance;

}
