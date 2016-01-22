package org.silnith.game.solitaire.move;

import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;

public class AdvanceDrawPileMove implements SolitaireMove {
    
    private final int beginningIndex;
    
    private final int increment;
    
    public AdvanceDrawPileMove(final int beginningIndex, final int increment) {
        super();
        this.beginningIndex = beginningIndex;
        this.increment = increment;
    }
    
    public int getBeginningIndex() {
        return beginningIndex;
    }
    
    public int getIncrement() {
        return increment;
    }
    
    public AdvanceDrawPileMove coalesce(final AdvanceDrawPileMove next) {
        assert beginningIndex + increment == next.beginningIndex;
        return new AdvanceDrawPileMove(beginningIndex, increment
                + next.increment);
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
        return board.advanceDrawIndex(increment);
    }
    
    @Override
    public int hashCode() {
        return Integer.rotateLeft(beginningIndex, 24)
                ^ Integer.rotateLeft(increment, 8);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AdvanceDrawPileMove) {
            final AdvanceDrawPileMove move = (AdvanceDrawPileMove) obj;
            return beginningIndex == move.beginningIndex
                    && increment == move.increment;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Advance draw pile by " + increment + ".";
    }
    
}
