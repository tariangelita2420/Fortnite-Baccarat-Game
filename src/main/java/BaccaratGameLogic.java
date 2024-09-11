import java.util.ArrayList;

public class BaccaratGameLogic {

    // Evaluate two hands at the end of the game and return a string
    // depending on the winner: “Player”, “Banker”, “Draw”.
    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        int bankerTotal = handTotal(hand1);
        int playerTotal = handTotal(hand2);

        if (playerTotal == 9 || playerTotal == 9) {
            if (playerTotal != bankerTotal) {
                // natural win for player
                return "Player";
            }
        } else if (bankerTotal == 9 || bankerTotal == 8) {
            if (playerTotal != bankerTotal) {
                // natural win for banker
                return "Banker";
            }
        }

        if (playerTotal == bankerTotal) {
            // draw
            return "Draw";
        } else if (bankerTotal > playerTotal) {
            // banker wins
            return "Banker";
        } else {
            // player wins
            return "Player";
        }
    }

    // Take a hand and return how many points that hand is worth.
    public int handTotal(ArrayList<Card> hand){
        int total = 0;

        for (Card c : hand) {
            if (c.value >= 1 && c.value <= 9) {
                total += c.value;
            }
            // values 10,11,12,13 are considered as 0 pts.
        }

        return total % 10;
    }

    // Return true if the Banker hand should be dealt a third card, otherwise return false
    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard){
        int cardTotal = handTotal(hand);

        if (cardTotal <= 2) { // draw another card
            return true;
        } else if (cardTotal >= 3 && cardTotal <= 6) { // depends if player drew 3rd card
            if (playerCard == null) {
                return cardTotal <= 5;
            }

            if (playerCard.value <= 1 || playerCard.value >= 8) {
                return cardTotal <= 3;
            } else if (playerCard.value <= 3) {
                return cardTotal <= 4;
            } else if (playerCard.value <= 5) {
                return cardTotal <= 5;
            } else {
                return cardTotal <= 6;
            }
        }

        return false;
    }

    // Return true if the Player hand should be dealt a third card, otherwise return false
    public boolean evaluatePlayerDraw(ArrayList<Card> hand){
        return handTotal(hand) <= 5;
    }

}
