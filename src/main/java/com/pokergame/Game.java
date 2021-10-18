package com.pokergame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Game {

    /**
     *
     * @param args Array of strings which constitutes cards
     * @throws PokerException if no entry strings were entered ot more than 10 were entered
     */
    public static void main(String[] args) throws PokerException {

        List<Hand> players;

        if(args.length == 0 ||args.length > 10){
            throw new PokerException(args.length + " want to participate in the game. Illegal number of players attempted.");
        }

        players = dealToPlayers(args);
        showWinner(players);
    }

    /**
     * deal hands to  players
     * @param args array of entry Strings
     * @return hands of players
     */
    public static List<Hand> dealToPlayers(String[] args){
        return Arrays.stream(args).map( arg -> {
            List<Card> cards = null;
            try {
               cards = initCards(arg);
            } catch (PokerException e) {
                e.printStackTrace();
            }
            return new Hand(cards);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * creates a hand
     * @param arg String of entry
     * @return hand containing five cards
     * @throws PokerException if hand doesn't contain five cards
     */
    public static List<Card> initCards(String arg) throws PokerException {
        List<Card> cards =Arrays.stream(arg.split(" ")).map( c -> {
            try {
                return initCard(c);
            } catch (PokerException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        if (cards.size() < 5){
            throw new PokerException("Number of cards is " + cards.size() + ". The acceptable number of cards is 5." );
        }
        return cards;
    }

    /**
     *  creates an instance of card
     * @param c a String with the format 'VS' with V value and S suit
     * @return card
     * @throws PokerException if the String doesn't contain 2 characters
     */
    public static Card initCard(String c) throws PokerException {
        if(c.split("").length == 2){
            return new Card(c.split("")[0], c.split("")[1]);
        }
        throw new PokerException("The Card format " + c + " is incorrect." );
    }

    /**
     * rank players and display results
     * @param players list of players
     */
    public static void showWinner(List<Hand> players){
        List<Hand> sortedPlayers = players.stream().sorted(Comparator.comparing(Hand::getRank).thenComparing(Hand::getWinningCard)
                .thenComparing(Hand::thenComparingCards).reversed()).collect(Collectors.toList());
        int winnerNumber = players.indexOf(sortedPlayers.get(0)) + 1;
        AtomicInteger counter = new AtomicInteger(0);
        printResults(sortedPlayers , players, winnerNumber, counter);
    }

    /**
     * print results in console
     * @param sortedPlayers the list ofmplayers ranked
     * @param players original list of players
     * @param winnerNumber the number of the winner player
     * @param counter a counter
     */
    public static void printResults(List<Hand> sortedPlayers , List<Hand> players, int winnerNumber, AtomicInteger counter){
        System.out.println("Ranking:");
        sortedPlayers.forEach( hand -> {
            PokerHand pokerHand = PokerHand.pokerHandByRank(hand.getRank());
            counter.getAndIncrement();
            System.out.println("\t" + counter + "\t Player " + (players.indexOf(hand) + 1) + "\t" + hand.toString() +
                    "\t\t" + pokerHand.getHandName() + ", " +
                    ((pokerHand.isDsiplaySuit())?hand.getWinningCard().getSuit().getSuitName():hand.getWinningCard().getValue().getValueName()) );
        });
        System.out.println("Player " + winnerNumber + " wins.");
    }
}
