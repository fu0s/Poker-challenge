package com.pokergame;

import java.util.HashMap;
import java.util.Map;

public enum Value {

    TWO("Two", 2, "2"),
    THREE("Three", 3, "3"),
    FOUR( "Four", 4, "4"),
    FIVE("Five", 5, "5"),
    SIX("Six", 6, "6"),
    SEVEN("Seven", 7, "7"),
    EIGHT("Eight",8, "8"),
    NINE("Nine", 9, "9"),
    TEN( "Ten", 10, "T"),
    JACK("Jack",11, "J"),
    QUEEN("Queen",12, "Q"),
    KING( "King", 13, "K"),
    ACE("Ace",1, "A");


    private static final Map<String, Value> NAME = new HashMap<>();
    private static final Map<Integer, Value> EVALUATION_VALUE = new HashMap<>();
    private static final Map<String, Value> ENTRY_CODE = new HashMap<>();

    static {
        for (Value e : values()) {
            NAME.put(e.valueName, e);
            EVALUATION_VALUE.put(e.evaluationValue, e);
            ENTRY_CODE.put(e.entryCode, e);
        }
    }

    public final String valueName;
    public final int evaluationValue;
    public final String entryCode;

    private Value(String valueName, int evaluationValue, String entryCode) {
        this.valueName = valueName;
        this.evaluationValue = evaluationValue;
        this.entryCode = entryCode;
    }

    public static Value valueByValueName(String valueName) {
        return NAME.get(valueName);
    }

    public static Value valueByEvaluationValue(int evaluationValue) {
        return EVALUATION_VALUE.get(evaluationValue);
    }

    public static Value valueByEntryCode(String entryCode) {
        return ENTRY_CODE.get(entryCode);
    }

    public String getValueName() {
        return valueName;
    }

    public int getEvaluationValue() {
        return evaluationValue;
    }

    public String getEntryCode() {
        return entryCode;
    }

}
