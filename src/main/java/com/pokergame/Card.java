package com.pokergame;


import java.util.Objects;

public class Card implements Comparable<Card>{

    /**
     * suit of card from enum class Suit
     */
    private final Suit suit;

    /**
     * value of card from enum class Value
     */
    private final Value value;


    /**
     * constructor
     * @param value 2 to 9, T ,J Q K and A
     * @param suit C,D,H and S
     */
    public Card(String value, String suit) throws PokerException {
        if (Value.valueByEntryCode(value) == null) {
            throw new PokerException("Illegal card value attempted. The acceptable values are " +
                    "2 to 9 and then T,J,Q,K and A. You tried " + value);
        }

        if (Suit.suitByEntryCode(suit) == null) {
            throw new PokerException("Illegal card suit attempted. The acceptable values are " +
                    "C,D,H and S. You tried " + suit);
        }

        this.suit = Suit.suitByEntryCode(suit);
        this.value = Value.valueByEntryCode(value);
    }

    /**
     * "Getter" for value of Card.
     * @return value of card
     */
    public Value getValue() {
        return value;
    }

    /**
     * "Getter" for suit of Card.
     * @return suit of card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * "toString" for card.
     * @return Card as It was entred in the console
     */
    @Override
    public String toString() {
        return value.getEntryCode() + suit.getEntryCode();
    }

    /**
     * "compareTo"
     * @param o Card
     * @return Comparison value
     */
    @Override
    public int compareTo(Card o) {
        return this.getValue().compareTo(o.getValue());
    }

    /**
     * "equals" was added since compareTo was overriden
     * @param o Object
     * @return if two cards are equal true,if not false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && value == card.value;
    }

    /**
     * "hashCode" was added since equals was overriden
     * @return a hshcode of card
     */
    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }
}
