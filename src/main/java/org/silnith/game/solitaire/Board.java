package org.silnith.game.solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.silnith.deck.Card;
import org.silnith.deck.Suit;


public class Board {
    
    private final List<Pile> piles;
    
    private final List<Card> drawPile;
    
    private final int drawIndex;
    
    private final Map<Suit, List<Card>> goal;
    
    public Board(final List<Card> deck, final int numPiles) {
        super();
        
        int remaining = deck.size();
        final Iterator<Card> iter = deck.iterator();
        final List<List<Card>> stacks = new ArrayList<>(numPiles);
        for (int i = 0; i < numPiles; i++ ) {
            stacks.add(new ArrayList<Card>(i));
        }
        for (int i = 0; i < numPiles; i++ ) {
            for (int j = i; j < numPiles; j++ ) {
                final Card card = iter.next();
                remaining-- ;
                stacks.get(j).add(card);
            }
        }
        
        final List<Pile> tempPiles = new ArrayList<>(numPiles);
        for (final List<Card> stack : stacks) {
            tempPiles.add(new Pile(stack, null));
        }
        
        // this.piles = Collections.unmodifiableList(tempPiles);
        this.piles = tempPiles;
        
        final List<Card> tempDrawPile = new ArrayList<>(remaining);
        while (iter.hasNext()) {
            final Card card = iter.next();
            tempDrawPile.add(card);
        }
        // this.drawPile = Collections.unmodifiableList(tempDrawPile);
        this.drawPile = tempDrawPile;
        this.drawIndex = 0;
        
        final Map<Suit, List<Card>> tempGoal = new EnumMap<>(Suit.class);
        for (final Suit suit : Suit.values()) {
            final List<Card> cards = Collections.emptyList();
            tempGoal.put(suit, cards);
        }
        
        // this.goal = Collections.unmodifiableMap(tempGoal);
        this.goal = tempGoal;
    }
    
    /**
     * Constructs a new board. All parameters must be immutable.
     */
    protected Board(final List<Pile> piles, final List<Card> drawPile, final int drawIndex,
            final Map<Suit, List<Card>> goal) {
        super();
        this.piles = piles;
        if (drawIndex > drawPile.size()) {
            throw new IllegalArgumentException("Draw index outside of draw pile.");
        }
        this.drawPile = drawPile;
        this.drawIndex = drawIndex;
        this.goal = goal;
    }
    
    public List<Pile> getPiles() {
        return piles;
    }
    
    public List<Card> getDrawPile() {
        return drawPile;
    }
    
    public int getDrawIndex() {
        return drawIndex;
    }
    
    public Map<Suit, List<Card>> getGoal() {
        return goal;
    }
    
    public boolean canFlipMoreDrawPileCards() {
        return drawIndex < drawPile.size();
    }
    
    public boolean isAtEndOfDrawPile() {
        // 0 represents no cards flipped, so offset by one
        return drawIndex >= drawPile.size();
    }
    
    public boolean canResetDrawPile() {
        return drawIndex > 0;
    }
    
    public Card getDrawCard() {
        return drawPile.get(drawIndex - 1);
    }
    
    private Card getTopOfGoal(final Suit suit) {
        final List<Card> goalForSuit = goal.get(suit);
        return goalForSuit.get(goalForSuit.size() - 1);
    }
    
    public Board resetDrawIndex() {
        return new Board(piles, drawPile, 0, goal);
    }
    
    public Board advanceDrawIndex(final int advance) {
        if (advance < 1) {
            throw new IllegalArgumentException();
        }
        
        final int newIndex = Math.min(drawIndex + advance, drawPile.size());
        return new Board(piles, drawPile, newIndex, goal);
    }
    
    public Board moveStack(final int fromIndex, final int toIndex, final int numCards) {
        if (fromIndex == toIndex) {
            throw new IllegalArgumentException();
        }
        
        final Pile fromPile = piles.get(fromIndex);
        final Pile toPile = piles.get(toIndex);
        final List<Card> stackToMove = fromPile.getTopCards(numCards);
        final Pile newFromPile = fromPile.getPileMissingTopCards(numCards);
        final Pile newToPile = toPile.addNewCards(stackToMove);
        
        final List<Pile> newPiles = new ArrayList<>(piles);
        newPiles.set(fromIndex, newFromPile);
        newPiles.set(toIndex, newToPile);
        // return new Board(Collections.unmodifiableList(newPiles), drawPile,
        // drawIndex, goal);
        return new Board(newPiles, drawPile, drawIndex, goal);
    }
    
    public Board moveCardToGoal(final int index) {
        final Pile pile = piles.get(index);
        final Card card = pile.getTopCard();
        final Pile newPile = pile.getPileMissingTopCards(1);
        
        final Map<Suit, List<Card>> newGoal = addToGoal(card);
        
        final List<Pile> newPiles = new ArrayList<>(piles);
        newPiles.set(index, newPile);
        
        // return new Board(Collections.unmodifiableList(newPiles), drawPile,
        // drawIndex, newGoal);
        return new Board(newPiles, drawPile, drawIndex, newGoal);
    }
    
    public Board drawCardToPile(final int index) {
        final Card card = getDrawCard();
        final List<Card> newDrawPile = extractDrawnCard();
        
        final int newDrawIndex = drawIndex - 1;
        
        final Pile pile = piles.get(index);
        final Pile newPile = pile.addNewCard(card);
        
        final List<Pile> newPiles = new ArrayList<>(piles);
        newPiles.set(index, newPile);
        
        // return new Board(Collections.unmodifiableList(newPiles), newDrawPile,
        // newDrawIndex, goal);
        return new Board(newPiles, newDrawPile, newDrawIndex, goal);
    }
    
    public Board drawCardToGoal() {
        final Card card = getDrawCard();
        final List<Card> newDrawPile = extractDrawnCard();
        
        final int newDrawIndex = drawIndex - 1;
        
        final Map<Suit, List<Card>> newGoal = addToGoal(card);
        
        return new Board(piles, newDrawPile, newDrawIndex, newGoal);
    }
    
    public Board moveCardFromGoal(final Suit suit, final int index) {
        final Card card = getTopOfGoal(suit);
        
        final Map<Suit, List<Card>> newGoal = removeFromGoal(suit);
        
        final Pile pile = piles.get(index);
        final Pile newPile = pile.addNewCard(card);
        
        final List<Pile> newPiles = new ArrayList<>(piles);
        newPiles.set(index, newPile);
        
        return new Board(newPiles, drawPile, drawIndex, newGoal);
    }
    
    private Map<Suit, List<Card>> addToGoal(final Card card) {
        final Map<Suit, List<Card>> newGoal = new EnumMap<>(goal);
        final Suit suit = card.getSuit();
        final List<Card> stackForSuit = newGoal.get(suit);
        final List<Card> newStackForSuit = new ArrayList<>(13);
        newStackForSuit.addAll(stackForSuit);
        newStackForSuit.add(card);
        // newGoal.put(suit, Collections.unmodifiableList(newStackForSuit));
        newGoal.put(suit, newStackForSuit);
        
        // return Collections.unmodifiableMap(newGoal);
        return newGoal;
    }
    
    private Map<Suit, List<Card>> removeFromGoal(final Suit suit) {
        final Map<Suit, List<Card>> newGoal = new EnumMap<>(goal);
        final List<Card> stackForSuit = newGoal.get(suit);
        // final List<Card> newStackForSuit = stackForSuit.subList(0,
        // stackForSuit.size() - 1);
        final List<Card> newStackForSuit = new ArrayList<>(stackForSuit.subList(0, stackForSuit.size() - 1));
        newGoal.put(suit, newStackForSuit);
        
        // return Collections.unmodifiableMap(newGoal);
        return newGoal;
    }
    
    private List<Card> extractDrawnCard() {
        final int size = drawPile.size();
        final List<Card> newDrawPile = new ArrayList<>(size - 1);
        newDrawPile.addAll(drawPile.subList(0, drawIndex - 1));
        newDrawPile.addAll(drawPile.subList(drawIndex, size));
        // return Collections.unmodifiableList(newDrawPile);
        return newDrawPile;
    }
    
    @Override
    public int hashCode() {
        return 0xc284f7a1 ^ piles.hashCode() ^ Integer.rotateLeft(drawPile.hashCode(), 8)
                ^ Integer.rotateLeft(drawIndex, 16) ^ Integer.rotateLeft(goal.hashCode(), 24);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Board) {
            final Board board = (Board) obj;
            if (drawPile.size() != board.drawPile.size()) {
                return false;
            }
            for (final Suit suit : Suit.values()) {
                if (goal.get(suit).size() != board.goal.get(suit).size()) {
                    return false;
                }
            }
            // Put drawIndex first since it changes a lot.
            return drawIndex == board.drawIndex && piles.equals(board.piles) && goal.equals(board.goal)
                    && drawPile.equals(board.drawPile);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Board {piles: " + piles + ", drawPile: " + drawPile + ", drawIndex: " + drawIndex + ", goal: " + goal
                + "}";
    }
    
}
