import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBaccaratGameLogic {
    private BaccaratGameLogic dealer;
    private ArrayList<Card> hand1;
    private ArrayList<Card> hand2;

    @BeforeEach
    public void setUp() {
        dealer = new BaccaratGameLogic();
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
    }

    @Test	// BaccaratGameLogic -> whoWon()
    public void testWhoWon_PlayerNaturalWin() {
        hand1.add(new Card("H", 7)); // Banker's hand total: 7
        hand2.add(new Card("D", 9)); // Player's hand total: 9
        assertEquals("Player", dealer.whoWon(hand1, hand2));
    }

    @Test	// BaccaratGameLogic -> whoWon()
    public void testWhoWon_BankerNaturalWin() {
        hand1.add(new Card("H", 9)); // Banker's hand total: 9
        hand2.add(new Card("D", 7)); // Player's hand total: 7
        assertEquals("Banker", dealer.whoWon(hand1, hand2));
    }

    @Test	// BaccaratGameLogic -> whoWon()
    public void testWhoWon_Draw() {
        hand1.add(new Card("H", 8)); // Banker's hand total: 8
        hand2.add(new Card("D", 8)); // Player's hand total: 8
        assertEquals("Draw", dealer.whoWon(hand1, hand2));
    }

    @Test	// BaccaratGameLogic -> whoWon()
    public void testWhoWon_BankerWins() {
        hand1.add(new Card("H", 7)); // Banker's hand total: 7
        hand2.add(new Card("D", 6)); // Player's hand total: 6
        assertEquals("Banker", dealer.whoWon(hand1, hand2));
    }

    @Test	// BaccaratGameLogic -> whoWon()
    public void testWhoWon_PlayerWins() {
        hand1.add(new Card("H", 5)); // Banker's hand total: 5
        hand2.add(new Card("D", 6)); // Player's hand total: 6
        assertEquals("Player", dealer.whoWon(hand1, hand2));
    }

    @Test   // BaccaratGameLogic -> handTotal()
    public void testHandTotal_EmptyHand() {
        assertEquals(0, dealer.handTotal(hand1));
    }

    @Test   // BaccaratGameLogic -> handTotal()
    public void testHandTotal_SingleCard() {
        hand1.add(new Card("H", 5)); // Hand total: 5
        assertEquals(5, dealer.handTotal(hand1));
    }

    @Test   // BaccaratGameLogic -> handTotal()
    public void testHandTotal_MultipleCards() {
        hand1.add(new Card("H", 5)); // Hand total: 5
        hand1.add(new Card("D", 3)); // Hand total: 8
        assertEquals(8, dealer.handTotal(hand1));
    }

    @Test   // BaccaratGameLogic -> handTotal()
    public void testHandTotal_ValueExceedsNine() {
        hand1.add(new Card("H", 5)); // Hand total: 5
        hand1.add(new Card("D", 6)); // Hand total: 11 (should return 1)
        assertEquals(1, dealer.handTotal(hand1));
    }

    @Test   // BaccaratGameLogic -> handTotal()
    public void testHandTotal_FaceCards() {
        hand1.add(new Card("H", 11)); // Jack, worth 0 points
        hand1.add(new Card("D", 12)); // Queen, worth 0 points
        hand1.add(new Card("S", 13)); // King, worth 0 points
        assertEquals(0, dealer.handTotal(hand1));
    }

    @Test   // BaccaratGameLogic -> evaluateBankerDraw()
    public void testEvaluateBankerDraw_BankerTotalLessThanThree() {
        hand1.add(new Card("H", 2)); // Banker's hand total: 2
        Card playerCard = new Card("D", 5); // Player's card
        assertTrue(dealer.evaluateBankerDraw(hand1, playerCard));
    }

    @Test   // BaccaratGameLogic -> evaluateBankerDraw()
    public void testEvaluateBankerDraw_BankerTotalThreeToSix_PlayerCardNull() {
        hand1.add(new Card("H", 4)); // Banker's hand total: 4
        Card playerCard = null; // Player did not draw a third card
        assertTrue(dealer.evaluateBankerDraw(hand1, playerCard));
    }

    @Test   // BaccaratGameLogic -> evaluateBankerDraw()
    public void testEvaluateBankerDraw_BankerTotalThreeToSix_PlayerCardNotNull() {
        hand1.add(new Card("H", 4)); // Banker's hand total: 4
        Card playerCard = new Card("D", 5); // Player's card
        assertTrue(dealer.evaluateBankerDraw(hand1, playerCard));
    }

    @Test   // BaccaratGameLogic -> evaluateBankerDraw()
    public void testEvaluateBankerDraw_BankerTotalGreaterThanSix() {
        hand1.add(new Card("H", 7)); // Banker's hand total: 7
        Card playerCard = new Card("D", 5); // Player's card
        assertFalse(dealer.evaluateBankerDraw(hand1, playerCard));
    }

    @Test   // BaccaratGameLogic -> evaluatePlayerDraw()
    public void testEvaluatePlayerDraw_PlayerTotalLessThanFive() {
        hand1.add(new Card("H", 4)); // Player's hand total: 4
        assertTrue(dealer.evaluatePlayerDraw(hand1));
    }

    @Test   // BaccaratGameLogic -> evaluatePlayerDraw()
    public void testEvaluatePlayerDraw_PlayerTotalEqualToFive() {
        hand1.add(new Card("H", 5)); // Player's hand total: 5
        assertTrue(dealer.evaluatePlayerDraw(hand1));
    }

    @Test   // BaccaratGameLogic -> evaluatePlayerDraw()
    public void testEvaluatePlayerDraw_PlayerTotalGreaterThanFive() {
        hand1.add(new Card("H", 6)); // Player's hand total: 6
        assertFalse(dealer.evaluatePlayerDraw(hand1));
    }
}
