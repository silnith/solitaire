package org.silnith.game.solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.silnith.deck.Card;


public class Pile {
    
    private final List<Card> faceDown;
    
    private final List<Card> faceUp;
    
    public Pile(List<? extends Card> faceDownCards, List<? extends Card> faceUpCards) {
        super();
        if (faceDownCards == null) {
            faceDownCards = Collections.emptyList();
        }
        if (faceUpCards == null) {
            faceUpCards = Collections.emptyList();
        }
        if (faceUpCards.isEmpty() && !faceDownCards.isEmpty()) {
            final int index = faceDownCards.size() - 1;
            final Card newTopCard = faceDownCards.get(index);
            faceDownCards = faceDownCards.subList(0, index);
            faceUpCards = Collections.singletonList(newTopCard);
        }
        this.faceDown = new ArrayList<>(faceDownCards);
        this.faceUp = new ArrayList<>(faceUpCards);
        /*
         * It actually performs significantly better to copy the lists than to
         * use the provided lists, since the provided lists are often sublists.
         */
    }
    
    public boolean hasFaceDownCards() {
        return !faceDown.isEmpty();
    }
    
    public boolean hasFaceUpCards() {
        return !faceUp.isEmpty();
    }
    
    public int getNumberOfFaceDownCards() {
        return faceDown.size();
    }
    
    public int getNumberOfFaceUpCards() {
        return faceUp.size();
    }
    
    public List<Card> getFaceUpCards() {
        return faceUp;
    }
    
    public Card getTopCard() {
        if (faceUp.isEmpty()) {
            // TODO: throw or not?
            return null;
        } else {
            return faceUp.get(faceUp.size() - 1);
        }
    }
    
    public List<Card> getTopCards(final int numberOfCards) {
        if (numberOfCards < 1) {
            throw new IllegalArgumentException();
        }
        if (numberOfCards > faceUp.size()) {
            throw new IllegalArgumentException();
        }
        
        final int end = faceUp.size();
        final int start = end - numberOfCards;
        return faceUp.subList(start, end);
    }
    
    public Pile getPileMissingTopCards(final int numberOfCards) {
        if (numberOfCards < 1) {
            throw new IllegalArgumentException();
        }
        if (numberOfCards > faceUp.size()) {
            throw new IllegalArgumentException();
        }
        
        final int end = faceUp.size();
        final int start = end - numberOfCards;
        return new Pile(faceDown, faceUp.subList(0, start));
    }
    
    public Pile addNewCards(final List<Card> newCards) {
        if (newCards.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Card> newFaceUp = new ArrayList<>(faceUp.size() + newCards.size());
        newFaceUp.addAll(faceUp);
        newFaceUp.addAll(newCards);
        return new Pile(faceDown, newFaceUp);
    }
    
    public Pile addNewCard(final Card newCard) {
        if (newCard == null) {
            throw new IllegalArgumentException();
        }
        final List<Card> newFaceUp = new ArrayList<>(faceUp.size() + 1);
        newFaceUp.addAll(faceUp);
        newFaceUp.add(newCard);
        return new Pile(faceDown, newFaceUp);
    }
    
    @Override
    public int hashCode() {
        return 0x1280ce7a ^ faceDown.hashCode() ^ Integer.rotateLeft(faceUp.hashCode(), 16);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Pile) {
            final Pile pile = (Pile) obj;
            if (faceDown.size() != pile.faceDown.size()) {
                return false;
            }
            if (faceUp.size() != pile.faceUp.size()) {
                return false;
            }
            return faceUp.equals(pile.faceUp) && faceDown.equals(pile.faceDown);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Pile (down: " + faceDown + ", up: " + faceUp + ")";
    }
    
}
