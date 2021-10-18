package com.pokergame;

import java.util.HashMap;
import java.util.Map;

public enum Suit {
    C("Clubs", 1, "C"),
    D("Diamonds", 2, "D"),
    H("Hearts", 3, "H"),
    S("Spades", 4, "S");

    private static final Map<String, Suit> NAME = new HashMap<>();
    private static final Map<Integer, Suit> EVALUATION_VALUE = new HashMap<>();
    private static final Map<String, Suit> ENTRY_CODE = new HashMap<>();

    static {
        for (Suit e : values()) {
            NAME.put(e.suitName, e);
            EVALUATION_VALUE.put(e.evaluationValue, e);
            ENTRY_CODE.put(e.entryCode, e);
        }
    }

    public final String suitName;
    public final int evaluationValue;
    public final String entryCode;

    private Suit(String suitName , int evaluationValue, String entryCode) {
        this.suitName = suitName;
        this.evaluationValue = evaluationValue;
        this.entryCode = entryCode;
    }

    public static Suit suitBySuitName(String suitName) {
        return NAME.get(suitName);
    }

    public static Suit suitByEvaluationValue(int evaluationValue) {
        return EVALUATION_VALUE.get(evaluationValue);
    }

    public static Suit suitByEntryCode(String entryCode) {
        return ENTRY_CODE.get(entryCode);
    }

    public String getSuitName() {
        return suitName;
    }

    public String getEntryCode() {
        return entryCode;
    }
}
