package com.bank.ridha.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

/**
 * Money.java a class to compute the money values
 *
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 */
public class Money implements Comparable<Money>, Serializable {

    private final int amountInCents;
    private Currency currency = Currency.getInstance("EUR");

    /**
     * constructor
     * @param amountInCents
     */
    private Money(int amountInCents) {
        this.amountInCents = amountInCents;
    }

    /**
     * constructor
     * @param amountInCents
     * @param currency
     */
    Money(int amountInCents, String currency) {
        this.amountInCents = amountInCents;
        this.currency = Currency.getInstance(currency);
    }

    /**
     * initilize money with interger value representation of cents
     * @param amountInCents
     * @return Money
     */
    public static Money cents(int amountInCents) {
        return new Money(amountInCents);
    }

    /**
     * initilize money with long value representation of cents
     * @param amountInCents
     * @return Money
     */
    public static Money cents(long amountInCents) {
        return cents(Math.toIntExact(amountInCents));
    }

    /**
     * initilize money with integer value representation of cents and currency
     * @param amountInCents
     * @param currency
     * @return Money
     */
    public static Money cents(int amountInCents, String currency) {
        return new Money(amountInCents, currency);
    }

    /**
     * initilize money with long value representation of cents and currency
     * @param amountInCents
     * @param currency
     * @return Money
     */
    public static Money cents(long amountInCents, String currency) {
        return new Money(Math.toIntExact(amountInCents), currency);
    }

    /**
     * initilize money with string value representation of euros and currency
     * Mostly for readability in tests, you should probably manipulate money as cents otherwise
     * @param amountInEuros
     * @return Money
     */
    public static Money euros(String amountInEuros) {
        return euros(new BigDecimal(amountInEuros));
    }

    /**
     * initilize money with BigDecimal value representation of euros and currency
     * @param amountInEuros
     * @return Money
     */
    public static Money euros(BigDecimal amountInEuros) {
        return new Money(amountInEuros.multiply(BigDecimal.valueOf(100)).intValue());
    }

    /**
     * retrieve the amount in cents
     * @return integer
     */
    public final int cents() {
        return amountInCents;
    }

    /**
     * retrieve the currency
     * @return
     */
    public String currency() {
        return currency.getCurrencyCode();
    }

    /**
     * to add money to existing one
     * @param toAdd
     * @return
     */
    public Money plus(Money toAdd) {
        return new Money(amountInCents + toAdd.amountInCents, toAdd.currency());
    }

    /**
     * to substract money from existing one
     * @param toSubstract
     * @return
     */
    public Money minus(Money toSubstract) {
        return new Money(amountInCents - toSubstract.amountInCents);
    }

    /**
     * to compare two moneys
     * @param o
     * @return
     */
    @Override
    public int compareTo(Money o) {
        return amountInCents - o.amountInCents;
    }

    /**
     * to verify if money is positive
     * @return
     */
    public boolean isPositive() {
        return amountInCents >= 0;
    }

    /**
     * to format money as french string
     * @return
     */
    public String formatFrench() {
        return formatFrenchWithoutCurrency() + " " + currency.getSymbol(Locale.FRANCE);
    }

    /**
     * to format money as english string
     * @return
     */
    public String formatEnglish() {
        return formatEnglishWithoutCurrency() + " " + currency.getSymbol(Locale.US);
    }

    /**
     * to convert money to french without currency
     * French will display with a comma (ex 12,30)
     * @return
     */
    public String formatFrenchWithoutCurrency() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.FRANCE);
        DecimalFormat df = new DecimalFormat("0.00", dfs);
        double value = BigDecimal.valueOf(amountInCents / 100.0).setScale(2, RoundingMode.DOWN).doubleValue();
        return df.format(value);
    }

    /**
     * to convert money to english without currency
     * French will display with a dot (ex 12.30)
     * @return
     */
    public String formatEnglishWithoutCurrency() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
        DecimalFormat df = new DecimalFormat("0.00", dfs);
        double value = BigDecimal.valueOf(amountInCents / 100.0).setScale(2, RoundingMode.DOWN).doubleValue();
        return df.format(value);
    }

    /**
     * equals
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (amountInCents != money.amountInCents) return false;
        return currency.equals(money.currency);
    }

    /**
     * hashcode
     * @return int
     */
    @Override
    public int hashCode() {
        int result = amountInCents;
        result = 31 * result + currency.hashCode();
        return result;
    }

}
