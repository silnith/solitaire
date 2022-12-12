package org.silnith.game.solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.silnith.deck.Suit.CLUB;
import static org.silnith.deck.Suit.DIAMOND;
import static org.silnith.deck.Suit.HEART;
import static org.silnith.deck.Suit.SPADE;
import static org.silnith.deck.Value.ACE;
import static org.silnith.deck.Value.EIGHT;
import static org.silnith.deck.Value.FOUR;
import static org.silnith.deck.Value.JACK;
import static org.silnith.deck.Value.KING;
import static org.silnith.deck.Value.NINE;
import static org.silnith.deck.Value.QUEEN;
import static org.silnith.deck.Value.SEVEN;
import static org.silnith.deck.Value.SIX;
import static org.silnith.deck.Value.TEN;
import static org.silnith.deck.Value.TWO;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.silnith.deck.Card;


public class PileTest {
    
    @Test
    public void testPileEmpty() {
        new Pile(Arrays.asList(new Card[] {}), Arrays.asList(new Card[] {}));
    }
    
    @Test
    public void testPileDownNull() {
        new Pile(null, Arrays.asList(new Card[] {}));
    }
    
    @Test
    public void testPileUpNull() {
        new Pile(Arrays.asList(new Card[] {}), null);
    }
    
    @Test
    public void testPileNullNull() {
        new Pile(null, null);
    }
    
    @Test
    public void testPileWithDown() {
        new Pile(Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] {}));
    }
    
    @Test
    public void testPileWithUp() {
        new Pile(Arrays.asList(new Card[] {}), Arrays.asList(new Card[] { new Card(KING, CLUB),
                new Card(QUEEN, DIAMOND), new Card(JACK, CLUB), new Card(TEN, DIAMOND) }));
    }
    
    @Test
    public void testPileWithBoth() {
        new Pile(Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
    }
    
    @Test
    public void testHasFaceDownCards() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertTrue(pile.hasFaceDownCards());
    }
    
    @Test
    public void testHasFaceDownCardsNull() {
        final Pile pile = new Pile(null, Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND),
                new Card(JACK, CLUB), new Card(TEN, DIAMOND) }));
                
        assertFalse(pile.hasFaceDownCards());
    }
    
    @Test
    public void testHasFaceDownCardsEmpty() {
        final Pile pile = new Pile(Arrays.asList(new Card[] {}), Arrays.asList(new Card[] { new Card(KING, CLUB),
                new Card(QUEEN, DIAMOND), new Card(JACK, CLUB), new Card(TEN, DIAMOND) }));
                
        assertFalse(pile.hasFaceDownCards());
    }
    
    @Test
    public void testHasFaceUpCards() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertTrue(pile.hasFaceUpCards());
    }
    
    @Test
    public void testHasFaceUpCardsNull() {
        final Pile pile = new Pile(Arrays.asList(new Card[] {}), null);
        
        assertFalse(pile.hasFaceUpCards());
    }
    
    @Test
    public void testHasFaceUpCardsEmpty() {
        final Pile pile = new Pile(Arrays.asList(new Card[] {}), Arrays.asList(new Card[] {}));
        
        assertFalse(pile.hasFaceUpCards());
    }
    
    @Test
    public void testHasFaceUpCardsAfterFlipFromDown() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] {}));
                
        assertTrue(pile.hasFaceUpCards());
    }
    
    @Test
    public void testGetNumberOfFaceDownCards() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(3, pile.getNumberOfFaceDownCards());
    }
    
    @Test
    public void testGetNumberOfFaceDownCardsNull() {
        final Pile pile = new Pile(null, Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND),
                new Card(JACK, CLUB), new Card(TEN, DIAMOND) }));
                
        assertEquals(0, pile.getNumberOfFaceDownCards());
    }
    
    @Test
    public void testGetNumberOfFaceDownCardsEmpty() {
        final Pile pile = new Pile(Arrays.asList(new Card[] {}), Arrays.asList(new Card[] { new Card(KING, CLUB),
                new Card(QUEEN, DIAMOND), new Card(JACK, CLUB), new Card(TEN, DIAMOND) }));
                
        assertEquals(0, pile.getNumberOfFaceDownCards());
    }
    
    @Test
    public void testGetNumberOfFaceUpCards() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(4, pile.getNumberOfFaceUpCards());
    }
    
    @Test
    public void testGetNumberOfFaceUpCardsNull() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }), null);
                
        assertEquals(1, pile.getNumberOfFaceUpCards());
    }
    
    @Test
    public void testFlippedCardRemovedFromDownNull() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }), null);
                
        assertEquals(2, pile.getNumberOfFaceDownCards());
    }
    
    @Test
    public void testFlippedCardRemovedFromDownEmpty() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] {}));
                
        assertEquals(2, pile.getNumberOfFaceDownCards());
    }
    
    @Test
    public void testGetTopCard() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(new Card(TEN, DIAMOND), pile.getTopCard());
    }
    
    @Test
    public void testGetTopCardNull() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }), null);
                
        assertEquals(new Card(ACE, HEART), pile.getTopCard());
    }
    
    @Test
    public void testGetTopCardEmpty() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] {}));
                
        assertEquals(new Card(ACE, HEART), pile.getTopCard());
    }
    
    @Test
    public void testGetFaceUpCards() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                new Card(TEN, DIAMOND) }), pile.getFaceUpCards());
    }
    
    @Test
    public void testGetFaceUpCardsNull() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }), null);
                
        assertEquals(Arrays.asList(new Card[] { new Card(ACE, HEART) }), pile.getFaceUpCards());
    }
    
    @Test
    public void testGetFaceUpCardsEmpty() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] {}));
                
        assertEquals(Arrays.asList(new Card[] { new Card(ACE, HEART) }), pile.getFaceUpCards());
    }
    
    @Test
    public void testGetTopCardsOverflow() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertThrows(IllegalArgumentException.class, () -> pile.getTopCards(5));
    }
    
    @Test
    public void testGetTopCards4() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                new Card(TEN, DIAMOND) }), pile.getTopCards(4));
    }
    
    @Test
    public void testGetTopCards3() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(
                Arrays.asList(new Card[] { new Card(QUEEN, DIAMOND), new Card(JACK, CLUB), new Card(TEN, DIAMOND) }),
                pile.getTopCards(3));
    }
    
    @Test
    public void testGetTopCards2() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(Arrays.asList(new Card[] { new Card(JACK, CLUB), new Card(TEN, DIAMOND) }), pile.getTopCards(2));
    }
    
    @Test
    public void testGetTopCards1() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertEquals(Arrays.asList(new Card[] { new Card(TEN, DIAMOND) }), pile.getTopCards(1));
    }
    
    @Test
    public void testGetTopCardsUnderflow() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertThrows(IllegalArgumentException.class, () -> pile.getTopCards(0));
    }
    
    @Test
    public void testPileMissingTopCardsOverflow() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
              
        assertThrows(IllegalArgumentException.class, () -> pile.getPileMissingTopCards(5));
    }
    
    @Test
    public void testPileMissingTopCards4() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
        final Pile expected = new Pile(Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART) }),
                Arrays.asList(new Card[] { new Card(ACE, HEART) }));
                
        assertEquals(expected, pile.getPileMissingTopCards(4));
    }
    
    @Test
    public void testPileMissingTopCards3() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
        final Pile expected = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB) }));
                
        assertEquals(expected, pile.getPileMissingTopCards(3));
    }
    
    @Test
    public void testPileMissingTopCards2() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
        final Pile expected = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND) }));
                
        assertEquals(expected, pile.getPileMissingTopCards(2));
    }
    
    @Test
    public void testPileMissingTopCards1() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
        final Pile expected = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB) }));
                
        assertEquals(expected, pile.getPileMissingTopCards(1));
    }
    
    @Test
    public void testPileMissingTopCardsUnderflow() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertThrows(IllegalArgumentException.class, () -> pile.getPileMissingTopCards(0));
    }
    
    @Test
    public void testAddNewCard() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
        final Pile expected = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND), new Card(NINE, CLUB) }));
                        
        assertEquals(expected, pile.addNewCard(new Card(NINE, CLUB)));
    }
    
    @Test
    public void testAddNewCardNull() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
              
        assertThrows(RuntimeException.class, () -> pile.addNewCard(null));
    }
    
    @Test
    public void testAddNewCards1() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
        final Pile expected = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND), new Card(NINE, CLUB) }));
                        
        assertEquals(expected, pile.addNewCards(Arrays.asList(new Card[] { new Card(NINE, CLUB) })));
    }
    
    @Test
    public void testAddNewCards3() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
        final Pile expected = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND), new Card(NINE, CLUB), new Card(EIGHT, DIAMOND),
                        new Card(SEVEN, CLUB) }));
                        
        assertEquals(expected, pile.addNewCards(
                Arrays.asList(new Card[] { new Card(NINE, CLUB), new Card(EIGHT, DIAMOND), new Card(SEVEN, CLUB) })));
    }
    
    @Test
    public void testAddNewCardsEmpty() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                        
        assertThrows(IllegalArgumentException.class, () -> pile.addNewCards(Arrays.asList(new Card[] {})));
    }
    
    @Test
    public void testAddNewCardsNull() {
        final Pile pile = new Pile(
                Arrays.asList(new Card[] { new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(ACE, HEART) }),
                Arrays.asList(new Card[] { new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB),
                        new Card(TEN, DIAMOND) }));
                       
        assertThrows(NullPointerException.class, () -> pile.addNewCards(null));
    }
    
    @Test
    public void testEquals() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
                
        assertTrue(pile1.equals(pile2));
    }
    
    @Test
    public void testHashCode() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
                
        assertEquals(pile1.hashCode(), pile2.hashCode());
    }
    
    @Test
    public void testEqualsFaceDownDiffer() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] { new Card(TWO, SPADE), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
                
        assertFalse(pile1.equals(pile2));
    }
    
    @Test
    public void testEqualsFaceDownShorter() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] {}),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
                
        assertFalse(pile1.equals(pile2));
    }
    
    @Test
    public void testEqualsFaceDownLonger() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] {}),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
                
        assertFalse(pile1.equals(pile2));
    }
    
    @Test
    public void testEqualsFaceUpDiffer() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, HEART), new Card(SIX, CLUB) }));
                
        assertFalse(pile1.equals(pile2));
    }
    
    @Test
    public void testEqualsFaceUpShorter() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] {}));
                
        assertFalse(pile1.equals(pile2));
    }
    
    @Test
    public void testEqualsFaceUpLonger() {
        final Pile pile1 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] {}));
        final Pile pile2 = new Pile(Arrays.asList(new Card[] { new Card(TWO, HEART), new Card(KING, DIAMOND) }),
                Arrays.asList(new Card[] { new Card(EIGHT, SPADE), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB) }));
                
        assertFalse(pile1.equals(pile2));
    }
    
}
