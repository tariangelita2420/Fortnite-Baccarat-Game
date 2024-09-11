public class Card {
    String suite;
    int value;

    // Constructor
    Card(String theSuite, int theValue) {
        this.suite = theSuite;
        this.value = theValue;
    }

    public String getSuite() {
        return suite;
    }

    public int getValue() {
        return value;
    }
}
