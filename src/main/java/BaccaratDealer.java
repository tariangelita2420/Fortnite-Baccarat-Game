import java.util.Collections;
import java.util.ArrayList;

public class BaccaratDealer {
    ArrayList<Card> deck;
    // 52 Cards
    // 4 Suites ('H'earts, 'D'iamonds, 'S'pades, 'C'lubs)
    // 13 Cards for each suit (A=1, 2-10, J=11, Q=12, K=13)

    BaccaratDealer(){
        deck = new ArrayList<Card>();
    }


    // Generate a new standard 52 card deck where each card is an
    // instance of the Card class in the ArrayList<Card> deck.
    public void generateDeck() {
        // Array of suits
        String[] suits = {"H", "D", "S", "C"};

        // Iterate over each suit
        for (String suit : suits) {
            // Create 13 cards of that suit
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(suit, i);
                deck.add(card);
            }
        }
    }

    // Will deal two cards and return them in an ArrayList<Card>
    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();

        // Update the ArrayList while also removing the element from the deck
        hand.add(deck.remove(0));
        hand.add(deck.remove(0));
        return hand;
    }

    // Will deal a single card and return it.
    public Card drawOne(){
        return deck.remove(0);
    }

    // Will create a new deck of 52 cards and “shuffle”; randomize the cards in that ArrayList<Card>
    public void shuffleDeck() {
        Collections.shuffle(deck);  // Call shuffle method given by Collections to shuffle ArrayList
    }

    // Return how many cards are in this deck at any given time.
    public int deckSize(){
        return this.deck.size();
    }

    // Returns current deck (mainly for testing)
    public ArrayList<Card> getCards() {
        return this.deck;
    }

}
