package com.pokergame;


import java.util.*;
import java.util.stream.Collectors;

/**
 *  Hand class which represents a hand of a player
 */
public class Hand {

    /**
     * Player cards
     */
    private List<Card> cards ;

    /**
     * Hand rank
     * Example 10 if the cards are royal flush
     */
    private Integer rank ;

    /**
     * The card that makes the difference in the hand. In case of two hands have the same rank we compare these cards.
     * example : in high card hand the winning card would be the high card
     */
    private Card winningCard ;

    /**
     * Constructor
     * @param cards cards which compose the hand
     */
    public Hand(List<Card> cards){
        this.winningCard = rankCards(cards);
        this.cards = cards;
    }

    /**
     * Evaluate hand following :
     * 1 weakest and 10 strongest hand
     */
    private Card rankCards(List<Card> cards){
        if(hasRoyalFlush(cards) != null){
            this.rank = 10;
            return hasRoyalFlush(cards);
        }
        if(hasStraightFlush(cards) != null){
            this.rank = 9;
            return hasStraightFlush(cards);
        }
        if(hasFourOfAKind(cards) != null){
            this.rank = 8;
            return hasFourOfAKind(cards);
        }
        if(hasFullHouse(cards) != null){
            this.rank = 7;
            return hasFullHouse(cards);
        }
        if(hasFlush(cards) != null){
            this.rank = 6;
            return hasFlush(cards);
        }
        if(hasStraight(cards) != null){
            this.rank = 5;
            return hasStraight(cards);
        }
        if(hasThreeOfAKind(cards) != null){
            this.rank = 4;
            return hasThreeOfAKind(cards);
        }
        if(hasTwoPair(cards) != null){
            this.rank = 3;
            return hasTwoPair(cards);
        }
        if(hasPair(cards) != null){
            this.rank = 2;
            return hasPair(cards);
        }
        this.rank = 1;
        return hasHighCard(cards);
    }


    /**
     * check if hand has high card and return the high card
     * @param cards cards of the hand
     * @return the winning card (high card)
     */
    private static Card hasHighCard(List<Card> cards) {
        Card wCard = cards.get(0);
        for (int idx = 1; idx < cards.size() ; idx++) {
            if (cards.get(idx).compareTo(wCard) > 0) {
                wCard = cards.get(idx);
            }
        }

        return wCard;
    }

    /**
     * check if hand has a Pair and return one card of the pair
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasPair(List<Card> cards) {
        List <Value> cardsValues =  cards.stream().map(Card::getValue).collect(Collectors.toList());
        Value wValue = cardsValues.stream().filter(c ->  Collections.frequency(cardsValues, c) >1).findFirst().orElse(null);
        return cards.stream().filter(c -> c.getValue() == wValue).findFirst().orElse(null);
    }

    /**
     * check if hand has two Pairs and return one card of the high pair
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasTwoPair(List<Card> cards) {
        List <Value> cardsValues =  cards.stream().map(Card::getValue).collect(Collectors.toList());
        List<Value> wValues = cardsValues.stream().filter(c ->  Collections.frequency(cardsValues, c) >1).collect(Collectors.toList());
        if(wValues.size() == 4){
            return cards.stream().filter(c -> c.getValue() == Collections.max(wValues)).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * check if hand has three cards of a kind and return one card of the three
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasThreeOfAKind(List<Card> cards) {
        List <Value> cardsValues =  cards.stream().map(Card::getValue).collect(Collectors.toList());
        Value wValue = cardsValues.stream().filter(c ->  Collections.frequency(cardsValues, c) >2).findFirst().orElse(null);
        return cards.stream().filter(c -> c.getValue() == wValue).findFirst().orElse(null);
    }

    /**
     * check if hand has a Pair and return one of the pairs
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasStraight(List<Card> cards) {
        Card wCard = null;
        boolean[] values = new boolean[14];
        for (int index = 0; index < cards.size(); index++) {
            values[cards.get(index).getValue().evaluationValue] = true;
        }

        for (int index = 1; index < 9; index++) {
            if (values[index] && values[index + 1] && values[index + 2]  && values[index + 3] && values[index + 4]) {
                int finalIndex = index + 4;
                return cards.stream().filter(c -> c.getValue().evaluationValue == finalIndex ).findFirst().orElse(null);
            }
        }

        if (values[1] && values[10] && values[11] && values[12] && values[13]) {
            wCard = cards.stream().filter( c -> c.getValue().evaluationValue == 1).findFirst().orElse(null);
        }

        return wCard;
    }

    /**
     * check if hand has a Pair and return one of the pairs
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasFlush(List<Card> cards) {
        List <Suit> cardsSuits =  cards.stream().map(Card::getSuit).collect(Collectors.toList());
        if(Collections.frequency(cardsSuits, cardsSuits.get(0)) == 5){
            return cards.stream().sorted(Comparator.reverseOrder()).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * check if hand has a Pair and return one of the pairs
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasFullHouse(List<Card> cards) {
        if (hasThreeOfAKind(cards) != null && hasTwoPair(cards) != null) {
            return hasThreeOfAKind(cards);
        }

        return null;
    }

    /**
     * check if hand has a Pair and return one of the pairs
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasFourOfAKind(List<Card> cards) {
        List <Value> cardsValues =  cards.stream().map(Card::getValue).collect(Collectors.toList());
        Value wValue = cardsValues.stream().filter(c ->  Collections.frequency(cardsValues, c) >3).findFirst().orElse(null);
        return cards.stream().filter(c -> c.getValue() == wValue).findFirst().orElse(null);
    }

    /**
     * check if hand has a Pair and return one of the pairs
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasStraightFlush(List<Card> cards) {
        Card wCard = null;
        if (hasStraight(cards) != null && hasFlush(cards) != null) {
            wCard = hasStraight(cards);
        }
        return wCard;
    }

    /**
     * check if hand has a Pair and return one of the pairs
     * @param cards cards of the hand
     * @return the winning card
     */
    private static Card hasRoyalFlush(List<Card> cards) {
        Card wCard = hasStraightFlush(cards);
        if (wCard == null){
            return null ;
        } else if(wCard.getValue().evaluationValue != 1){
            wCard = null;
        }
        return wCard;
    }

    /**
     * getter
     * @return cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * setter
     * @param cards
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * getter
     * @return rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * setter
     * @param rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * getter
     * @return winning card
     */
    public Card getWinningCard() {
        return winningCard;
    }

    /**
     * setter
     * @param winningCard
     */
    public void setWinningCard(Card winningCard) {
        this.winningCard = winningCard;
    }

    /**
     * "toString" of hand
     * @return list of cards of the hand as a String with format "VS VS VS VS VS"
     * with V for value and S for suit
     */
    public String toString(){
        StringBuilder hand = new StringBuilder();
        for (Card card:cards){
            hand.append( " " + card.toString());
        }
        return hand.toString();
    }

    /**
     *
     * @param other
     * @return
     */
    public int thenComparingCards(Hand other) {
        List< Card> cards1 = cards.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        List< Card> cards2 = other.getCards().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        if (this.rank == 3){
            Value secondPair1 =  findSecondPair(cards1, this.winningCard);
            Value secondPair2 =  findSecondPair(cards2, other.winningCard);
            int compare = secondPair1.compareTo(secondPair2);
            if (compare != 0) {
                return compare;
            }else{
                return findLastCardInTwoPairsHand(cards1, this.winningCard, secondPair1).compareTo(findLastCardInTwoPairsHand(cards2, other.winningCard, secondPair2));
            }
        }else{
            for (int idx = 0 ; idx < cards1.size() ; idx++){
                int compare = cards1.get(idx).compareTo(cards2.get(idx));
                if (compare != 0){
                    return compare;
                }
            }
        }
        return 0;
    }

    public Value findSecondPair(List<Card> cards , Card winningCard){
        List <Value> cardsValues =  cards.stream().map(Card::getValue).collect(Collectors.toList());
        return  cardsValues.stream().filter(c ->  Collections.frequency(cardsValues, c) >1 && c != winningCard.getValue()).findFirst().orElse(null);
    }

    public Card findLastCardInTwoPairsHand(List<Card> cards , Card winningCard , Value secondPair){
        return  cards.stream().filter(c ->  c.getValue() != winningCard.getValue() && c.getValue() != secondPair).findFirst().orElse(null);
    }
}
