package blackjack;

class Card {
    private String suit, name;
    private int value1, value2;
    private boolean faceUp, ace;

    public Card(String suit, String name, int value1, boolean isAce) {
        this.suit = suit;
        this.name = name;
        this.value1 = value1;
        this.value2 = 0;
        this.faceUp = true;
        this.ace = isAce;
    }

    public Card(String suit, String name, int value1, boolean isAce, int value2) {
        this.suit = suit;
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
        this.faceUp = true;
        this.ace = isAce;
    }

    public boolean isAce() {
        return ace;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setFaceUp(boolean faceUp) {
        if(faceUp) {
            this.faceUp = faceUp;
        } else {
            this.faceUp = false;
        }
    }

    @Override
    public String toString() {
        if(!faceUp) {
            return "???????";
        }
        return name + " of " + suit;
    }
}
