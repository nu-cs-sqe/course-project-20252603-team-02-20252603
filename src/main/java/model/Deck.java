package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> deck = new ArrayList<Card>();
    private List<Card> discard = new ArrayList<Card>();
    private int numPlayers = 0;
    private Random random;

    public Deck(List<Player> players, Random random){
        this.numPlayers = players.size();
        this.random = random;

        // set up deck
        for (int i = 0; i < 4; i++){
            deck.add(new Card(CardType.ATTACK));
            deck.add(new Card(CardType.FAVOR));
            deck.add(new Card(CardType.SHUFFLE));
            deck.add(new Card(CardType.NOPE));
            deck.add(new Card(CardType.SKIP));
            deck.add(new Card(CardType.SEE_THE_FUTURE));
            deck.add(new Card(CardType.BEARD_CAT));
            deck.add(new Card(CardType.CATTERMELON));
            deck.add(new Card(CardType.HAIRY_POTATO_CAT));
            deck.add(new Card(CardType.TACOCAT));
            deck.add(new Card(CardType.RAINBOW_RALPHING_CAT));
        }

        deck.add(new Card(CardType.NOPE)); // 5th nope card
        deck.add(new Card(CardType.SEE_THE_FUTURE)); // 5th see the future card

        for (int i = 0; i < 6 - numPlayers; i++){ // extra defuse cards
            deck.add(new Card(CardType.DEFUSE));
        }

        // shuffle deck

        // deal hands of 7 cards to each player
        for (int i = 0; i < 7; i++){
            for (Player p : players){
                p.addCard(deck.get(0));
                deck.remove(0);
            }
        }

        // give each a defuse card
        for (Player p : players){
            p.addCard(new Card(CardType.DEFUSE));
        }

        // put exploding kittens into deck
        for (int i = 0; i < 4; i++){
            deck.add(new Card(CardType.EXPLODING_KITTEN));
        }
    }

    /* Shuffle the deck */
    public void shuffle(){
        Collections.shuffle(deck, random);
    }

    public Card drawCard(){
        if (deck.isEmpty()) {
            throw new IllegalStateException("Draw pile is empty");
        }
        return deck.remove(0);
    }

    public void discardCard(Card card){

    }

    public void addToDrawPile(Card card, int position){

    }

    public void removeAllExplodingKittens(){

    }

    public void addExplodingKittens(int count){

    }

    public void addDefuseCards(int count){

    }

    public List<Card> peekTopCards(){
        return null;
    }

    /* Getters */
    public List<Card> getDeck(){
        return new ArrayList<>(deck);
    }
}
