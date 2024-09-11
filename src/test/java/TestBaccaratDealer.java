import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBaccaratDealer {
	private BaccaratDealer testDeck;

	// Setup and generate a deck before each test
	@BeforeEach
	public void setUp() {
		testDeck = new BaccaratDealer();
		testDeck.generateDeck();
	}

	@Test	// Card -> Constructor()
	public void testCardConstructor() {
		String expectedSuite = "S";
		int expectedValue = 5;

		Card card = new Card(expectedSuite, expectedValue);

		assertEquals(expectedSuite, card.getSuite(), "Constructor should set the suite correctly.");
		assertEquals(expectedValue, card.getValue(), "Constructor should set the value correctly.");
	}

	@Test // BaccaratDealer -> Constructor()
	public void testDealerConstructor() {
		BaccaratDealer dealer = new BaccaratDealer();

		ArrayList<Card> cards = dealer.getCards();

		assertEquals(0, cards.size(), "New deck should be empty after construction.");
	}

	@Test	// BaccaratGame -> Constructor()
	public void gameConstructor() {
		BaccaratGame game = new BaccaratGame();

		assertNotNull(game.getPlayerHand(), "Player hand should not be null.");
		assertNotNull(game.getBankerHand(), "Banker hand should not be null.");
		assertNotNull(game.getTheDealer(), "The dealer should not be null.");
		assertNotNull(game.getGameLogic(), "Game logic should not be null.");
	}

	@Test	// BaccaratDealer -> generateDeck()
	public void testGenerateDeck() {
		// Check that the deck has 52 cards
		assertEquals(52, testDeck.deckSize());

		// Check that there are 13 cards of each suit
		int[] suitCounts = new int[4];
		for (Card card : testDeck.getCards()) {
			switch (card.getSuite()) {
				case "H":
					suitCounts[0]++;
					break;
				case "D":
					suitCounts[1]++;
					break;
				case "S":
					suitCounts[2]++;
					break;
				case "C":
					suitCounts[3]++;
					break;
			}
		}

		for (int count : suitCounts) {
			assertEquals(13, count);
		}

		// Check that the values of the cards are between 1 and 13
		for (Card card : testDeck.getCards()) {
			assertTrue(card.getValue() >= 1 && card.getValue() <= 13);
		}
	}

	@Test 	// BaccaratDealer -> dealHand()
	public void testDealHand() {
		ArrayList<Card> hand = testDeck.dealHand();

		assertEquals(2, hand.size());
		assertEquals(50, testDeck.deckSize());

		for (Card card : hand) {
			assertNotNull(card.getSuite());
			assertTrue(card.getValue() >= 1 && card.getValue() <= 13);
		}
	}

	@Test 	// BaccaratDealer -> drawOne()
	public void testDrawOne() {
		int initialDeckSize = testDeck.deckSize();
		Card card = testDeck.drawOne();

		// Check that a card was drawn
		assertNotNull(card);

		// Check that the deck size has decreased by one
		assertEquals(initialDeckSize - 1, testDeck.deckSize());
	}

	@Test	// BaccaratDealer -> shuffleDeck()
	public void testShuffleDeck() {
		// Create a copy of the original deck
		ArrayList<Card> originalDeck = new ArrayList<>(testDeck.getCards());

		testDeck.shuffleDeck();

		// Check that the deck size remains the same after shuffling
		assertEquals(originalDeck.size(), testDeck.deckSize());

		// Check that the deck still contains the same cards after shuffling
		for (Card card : originalDeck) {
			assertTrue(testDeck.getCards().contains(card));
		}

		// Check that the order of cards has changed
		assertFalse(originalDeck.equals(testDeck.getCards()));
	}

	@Test	// BaccaratDealer -> deckSize()
	public void testDeckSize() {
		// Check that the deck has 52 cards after generating
		assertEquals(52, testDeck.deckSize());

		// Draw one card and check that the deck now has 51 cards
		testDeck.drawOne();
		assertEquals(51, testDeck.deckSize());
	}

}




