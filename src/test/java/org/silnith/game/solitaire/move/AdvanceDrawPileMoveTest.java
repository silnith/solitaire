package org.silnith.game.solitaire.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.silnith.game.solitaire.Board;


@ExtendWith(MockitoExtension.class)
public class AdvanceDrawPileMoveTest {
    
    @Mock
    private Board board;
    
    @Test
    public void testGetBeginningIndex() {
        final AdvanceDrawPileMove move = new AdvanceDrawPileMove(17, 34);
        
        assertEquals(17, move.getBeginningIndex());
    }
    
    @Test
    public void testGetIncrement() {
        final AdvanceDrawPileMove move = new AdvanceDrawPileMove(17, 34);
        
        assertEquals(34, move.getIncrement());
    }
    
    @Test
    public void testCoalesceBeginningIndex() {
        final AdvanceDrawPileMove firstMove = new AdvanceDrawPileMove(20, 4);
        final AdvanceDrawPileMove secondMove = new AdvanceDrawPileMove(24, 5);
        
        final AdvanceDrawPileMove coalescedMove = firstMove.coalesce(secondMove);
        
        assertEquals(20, coalescedMove.getBeginningIndex());
    }
    
    @Test
    public void testCoalesceIncrement() {
        final AdvanceDrawPileMove firstMove = new AdvanceDrawPileMove(20, 4);
        final AdvanceDrawPileMove secondMove = new AdvanceDrawPileMove(24, 5);
        
        final AdvanceDrawPileMove coalescedMove = firstMove.coalesce(secondMove);
        
        assertEquals(9, coalescedMove.getIncrement());
    }
    
    @Test
    public void testCoalesceNull() {
        final AdvanceDrawPileMove move = new AdvanceDrawPileMove(17, 34);
        
        assertThrows(NullPointerException.class, () -> move.coalesce(null));
    }
    
    @Test
    public void testHasCards() {
        final AdvanceDrawPileMove move = new AdvanceDrawPileMove(17, 34);
        
        assertFalse(move.hasCards());
    }
    
    @Test
    public void testGetCards() {
        final AdvanceDrawPileMove move = new AdvanceDrawPileMove(17, 34);
        
        assertThrows(RuntimeException.class, () -> move.getCards());
    }
    
    @Test
    public void testApply() {
        final AdvanceDrawPileMove move = new AdvanceDrawPileMove(17, 34);
        
        move.apply(board);
        
        verify(board).advanceDrawIndex(eq(34));
    }
    
    @Test
    public void testEquals() {
        final AdvanceDrawPileMove move1 = new AdvanceDrawPileMove(17, 34);
        final AdvanceDrawPileMove move2 = new AdvanceDrawPileMove(17, 34);
        
        assertTrue(move1.equals(move2));
    }
    
    @Test
    public void testHashCode() {
        final AdvanceDrawPileMove move1 = new AdvanceDrawPileMove(17, 34);
        final AdvanceDrawPileMove move2 = new AdvanceDrawPileMove(17, 34);
        
        assertEquals(move1.hashCode(), move2.hashCode());
    }
    
    @Test
    public void testEqualsNull() {
        final AdvanceDrawPileMove move = new AdvanceDrawPileMove(17, 34);
        
        assertFalse(move.equals(null));
    }
    
    @Test
    public void testEqualsDifferentBeginningIndex() {
        final AdvanceDrawPileMove move1 = new AdvanceDrawPileMove(17, 34);
        final AdvanceDrawPileMove move2 = new AdvanceDrawPileMove(20, 34);
        
        assertFalse(move1.equals(move2));
    }
    
    @Test
    public void testEqualsDifferentIncrement() {
        final AdvanceDrawPileMove move1 = new AdvanceDrawPileMove(17, 34);
        final AdvanceDrawPileMove move2 = new AdvanceDrawPileMove(17, 3);
        
        assertFalse(move1.equals(move2));
    }
    
    @Test
    public void testEqualsDifferent() {
        final AdvanceDrawPileMove move1 = new AdvanceDrawPileMove(17, 34);
        final AdvanceDrawPileMove move2 = new AdvanceDrawPileMove(20, 3);
        
        assertFalse(move1.equals(move2));
    }
    
}
