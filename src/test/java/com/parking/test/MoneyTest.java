package com.parking.test;

import com.bank.ridha.util.Money;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

    @Test
    public void french_local_test() {
        Money money = Money.cents(30000L);
        assertThat(money.formatFrench()).isEqualTo("300,00 €");
        assertThat(money.formatEnglish()).isEqualTo("300.00 €");
    }

    @Test
    public void cents_test() {
        Money moneyEur = Money.euros("300");
        Money moneyCents = Money.cents(30000L);
        assertThat(moneyEur.cents()).isEqualTo(30000);
        assertThat(moneyEur.equals(moneyCents)).isTrue();
        assertThat(moneyEur.hashCode()).isEqualTo(moneyCents.hashCode());
    }
}
