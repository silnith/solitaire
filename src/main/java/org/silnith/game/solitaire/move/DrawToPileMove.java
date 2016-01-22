package org.silnith.game.solitaire.move;

import java.util.Collections;
import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;

public class DrawToPileMove implements SolitaireMove {
    
    private final int sourceIndex;
    
    private final int destinationPile;
    
    private final Card card;
    
    public DrawToPileMove(final int sourceIndex, final int destinationPile,
            final Card card) {
        super();
        this.sourceIndex = sourceIndex;
        this.destinationPile = destinationPile;
        this.card = card;
    }
    
    public int getSourceIndex() {
        return sourceIndex;
    }
    
    public int getDestinationPile() {
        return destinationPile;
    }
    
    public Card getCard() {
        return card;
    }
    
    @Override
    public boolean hasCards() {
        return true;
    }
    
    @Override
    public List<Card> getCards() {
        return Collections.singletonList(card);
    }
    
    @Override
    public Board apply(final Board board) {
        return board.drawCardToPile(destinationPile);
    }
    
    @Override
    public int hashCode() {
        return sourceIndex ^ destinationPile ^ card.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof DrawToPileMove) {
            final DrawToPileMove move = (DrawToPileMove) obj;
            return sourceIndex == move.sourceIndex
                    && destinationPile == move.destinationPile
                    && card.equals(move.card);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Move " + card + " from draw pile index " + sourceIndex
                + " to pile " + destinationPile + ".";
    }
    
}
