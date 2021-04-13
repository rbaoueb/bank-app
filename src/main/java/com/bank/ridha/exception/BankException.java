package com.bank.ridha.exception;

import org.apache.commons.lang3.StringUtils;


/**
 * BankException.java a class to customize exceptions app
 *
 * @author mrbaoueb
 * @version 1.0
 * @since 2021-04-13
 */
public class BankException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    /**
     * Class constructor specifying error message
     *
     * @param clazz
     * @param paramName
     */
    public BankException(Class clazz, String paramName, String message) {
        super(BankException.generateMessage(clazz.getSimpleName(), paramName, message));
    }

    /**
     * method to generate error message
     *
     * @param entity
     * @param paramName
     * @return built message
     */
    private static String generateMessage(String entity, String paramName, String message) {
        return StringUtils.capitalize(entity) + " " + paramName + " " + message;
    }
}
