package com.pokergame;

/**
 * Exception class
 */
public class PokerException extends Exception {

    /**
     * exception
     * @param errorMessage the text that will be displayed to the user
     */
    public PokerException(String errorMessage) {
        super(errorMessage);
    }
}
