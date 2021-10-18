package com.pokergame;

import java.util.HashMap;
import java.util.Map;

public enum PokerHand {

    HC("High Card",1, false),
    OP("One Pair",2,false),
    TP("Two Pairs",3,false),
    TC("Three of a Kind",4, false),
    ST("Straight",5,false),
    FL("Flush",6,true),
    FH("Full House",7, false),
    FK("Four of a Kind",8,false),
    SF("Straight Flush",9,true),
    RF("Royal Flush",10, true);


    private static final Map<String, PokerHand> NAME = new HashMap<>();
    private static final Map<Integer, PokerHand> RANK = new HashMap<>();

    static {
        for (PokerHand e : values()) {
            NAME.put(e.handName, e);
            RANK.put(e.rankName, e);
        }
    }

    public final String handName;
    public final int rankName;
    public final boolean dsiplaySuit;

    private PokerHand(String handName, int rankName, boolean dsiplaySuit) {
        this.handName = handName;
        this.rankName = rankName;
        this.dsiplaySuit = dsiplaySuit;
    }

    public static PokerHand pokerHandByName(String handName) {
        return NAME.get(handName);
    }

    public static PokerHand pokerHandByRank(int rankName) {
        return RANK.get(rankName);
    }

    public String getHandName() {
        return handName;
    }

    public int getRankName() {
        return rankName;
    }

    public boolean isDsiplaySuit() {
        return dsiplaySuit;
    }

}
