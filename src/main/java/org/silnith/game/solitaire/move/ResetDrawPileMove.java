package org.silnith.game.solitaire.move;

import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;


public class ResetDrawPileMove implements SolitaireMove {
    
    private final int sourceIndex;
    
    public ResetDrawPileMove(final int sourceIndex) {
        super();
        this.sourceIndex = sourceIndex;
    }
    
    public int getSourceIndex() {
        return sourceIndex;
    }
    
    @Override
    public boolean hasCards() {
        return false;
    }
    
    @Override
    public List<Card> getCards() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Board apply(final Board board) {
        return board.resetDrawIndex();
    }
    
    @Override
    public int hashCode() {
        return 0x5f23bc91 ^ sourceIndex;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof ResetDrawPileMove) {
            final ResetDrawPileMove move = (ResetDrawPileMove) obj;
            return sourceIndex == move.sourceIndex;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Reset draw pile from index " + sourceIndex + " to the beginning.";
    }
    
}
