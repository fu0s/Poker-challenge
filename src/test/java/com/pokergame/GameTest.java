package com.pokergame;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testInitCard() throws PokerException {
        String entry = "TC";
        Card card = Game.initCard(entry);

        Assert.assertNotNull(card);

        Value value = card.getValue();
        Suit suit = card.getSuit();

        Assert.assertNotNull(value);
        Assert.assertNotNull(suit);
        Assert.assertEquals("Ten", value.getValueName());
        Assert.assertEquals("Clubs", suit.getSuitName());
        Assert.assertEquals("T", value.getEntryCode());
        Assert.assertEquals("C", suit.getEntryCode());
        Assert.assertEquals(10, value.getEvaluationValue());
        Assert.assertEquals(1, suit.getEvaluationValue());
    }

    @Test
    public void testInitCardWithUnvalidEntry() throws PokerException {
        String entry = "TCC";

        exceptionRule.expect(PokerException.class);
        exceptionRule.expectMessage("The Card format " + entry + " is incorrect.");
        Card card = Game.initCard(entry);
    }

    @Test
    public void testInitCards() throws PokerException {
        String entry = "AC AH AS QS TS";
        List<Card> cards = Game.initCards(entry);

        List<Card> expectedCards = new ArrayList<>();
        expectedCards.add(new Card("A","C"));
        expectedCards.add(new Card("A","H"));
        expectedCards.add(new Card("A","S"));
        expectedCards.add(new Card("Q","S"));
        expectedCards.add(new Card("T","S"));

        Assert.assertNotNull(cards);
        Assert.assertEquals(5,cards.size());
        Assert.assertEquals(expectedCards,cards);
        Assert.assertEquals(expectedCards.get(0),cards.get(0));
        Assert.assertEquals(expectedCards.get(1),cards.get(1));
        Assert.assertEquals(expectedCards.get(2),cards.get(2));
        Assert.assertEquals(expectedCards.get(3),cards.get(3));
        Assert.assertEquals(expectedCards.get(4),cards.get(4));

    }

    @Test
    public void testInitCardsWithUnvalidEntry() throws PokerException {
        String entry = "AC AH AS QS TS KS";

        exceptionRule.expect(PokerException.class);
        exceptionRule.expectMessage("Number of cards is 6 for hand [AC, AH, AS, QS, TS, KS]. The acceptable number of cards is 5.");
        List<Card> cards = Game.initCards(entry);
    }

    @Test
    public void testDealToPlayers() throws PokerException {
        String[] args = {"AC AH AS QS TS", "AC AH AS QS TS"};

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("A","C"));
        cards.add(new Card("A","H"));
        cards.add(new Card("A","S"));
        cards.add(new Card("Q","S"));
        cards.add(new Card("T","S"));

        Hand expectedHand = new Hand(cards);
        List<Hand> expectedHands = new ArrayList<>();
        expectedHands.add(expectedHand);
        expectedHands.add(expectedHand);

        List<Hand> hands = Game.dealToPlayers(args);

        Assert.assertNotNull(hands);
        Assert.assertEquals(2,hands.size());
        Assert.assertEquals(expectedHands,hands);
        Assert.assertEquals(expectedHands.get(0),hands.get(0));
        Assert.assertEquals(expectedHands.get(1),hands.get(1));
        Assert.assertEquals((Integer) 4,hands.get(0).getRank());
        Assert.assertEquals(new Card("A","C"),hands.get(1).getWinningCard());
    }



    @Test
    public void testPrintResults() throws PokerException {
        List<Hand> sortedPlayers = new ArrayList<>();
        List<Hand> players = new ArrayList<>();
        int winnerNumber = 1 ;
        AtomicInteger counter = new AtomicInteger(0);

        List<Card> cards1 = new ArrayList<>();
        cards1.add(new Card("A","C"));
        cards1.add(new Card("A","H"));
        cards1.add(new Card("A","S"));
        cards1.add(new Card("Q","S"));
        cards1.add(new Card("T","S"));

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("A","C"));
        cards2.add(new Card("A","H"));
        cards2.add(new Card("A","S"));
        cards2.add(new Card("Q","S"));
        cards2.add(new Card("5","S"));

        Hand player1 = new Hand(cards1);
        Hand player2 = new Hand(cards2);
        players.add(player1);
        players.add(player2);

        sortedPlayers.add(player1);
        sortedPlayers.add(player2);

        Game.printResults(sortedPlayers, players, winnerNumber, counter);
        Assert.assertEquals("Ranking:\n" +
                "\t1\t Player 1\t AC AH AS QS TS\t\tThree of a Kind, Ace\n" +
                "\t2\t Player 2\t AC AH AS QS 5S\t\tThree of a Kind, Ace\n" +
                "Player 1 wins.\n", outContent.toString());
    }

    @Test
    public void testShowWinner() throws PokerException {

        List<Hand> players = new ArrayList<>();
        List<Card> cards1 = new ArrayList<>();
        cards1.add(new Card("A","C"));
        cards1.add(new Card("A","H"));
        cards1.add(new Card("A","S"));
        cards1.add(new Card("Q","S"));
        cards1.add(new Card("2","S"));

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("A","C"));
        cards2.add(new Card("A","H"));
        cards2.add(new Card("A","S"));
        cards2.add(new Card("Q","S"));
        cards2.add(new Card("5","S"));

        Hand player1 = new Hand(cards1);
        Hand player2 = new Hand(cards2);
        players.add(player1);
        players.add(player2);

        Game.showWinner(players);
        Assert.assertEquals("Ranking:\n" +
                "\t1\t Player 2\t AC AH AS QS 5S\t\tThree of a Kind, Ace\n" +
                "\t2\t Player 1\t AC AH AS QS 2S\t\tThree of a Kind, Ace\n" +
                "Player 2 wins.\n", outContent.toString());
    }
}
