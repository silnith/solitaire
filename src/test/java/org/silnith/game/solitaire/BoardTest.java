package org.silnith.game.solitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.silnith.deck.Suit.CLUB;
import static org.silnith.deck.Suit.DIAMOND;
import static org.silnith.deck.Suit.HEART;
import static org.silnith.deck.Suit.SPADE;
import static org.silnith.deck.Value.ACE;
import static org.silnith.deck.Value.EIGHT;
import static org.silnith.deck.Value.FIVE;
import static org.silnith.deck.Value.FOUR;
import static org.silnith.deck.Value.JACK;
import static org.silnith.deck.Value.KING;
import static org.silnith.deck.Value.NINE;
import static org.silnith.deck.Value.QUEEN;
import static org.silnith.deck.Value.SEVEN;
import static org.silnith.deck.Value.SIX;
import static org.silnith.deck.Value.TEN;
import static org.silnith.deck.Value.THREE;
import static org.silnith.deck.Value.TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.silnith.deck.Card;
import org.silnith.deck.Suit;


public class BoardTest {
    
    private final List<Card> emptyListOfCards = Collections.emptyList();
    
    private final EnumMap<Suit, List<Card>> emptyGoal;
    
    private final List<Pile> emptyPiles;
    
    public BoardTest() {
        this.emptyPiles = new ArrayList<>(7);
        for (int i = 0; i < 7; i++ ) {
            this.emptyPiles.add(new Pile(emptyListOfCards, emptyListOfCards));
        }
        this.emptyGoal = new EnumMap<>(Suit.class);
        for (final Suit suit : Suit.values()) {
            this.emptyGoal.put(suit, emptyListOfCards);
        }
    }
    
    @Test
    public void testEqualsEmpty() {
        final Board board1 = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        final Board board2 = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        assertTrue(board1.equals(board2));
    }
    
    @Test
    public void testHashCodeEmpty() {
        final Board board1 = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        final Board board2 = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        assertEquals(board1.hashCode(), board2.hashCode());
    }
    
    @Test
    public void testEqualsOneStack() {
        final List<Card> stack = Arrays.asList(new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, SPADE),
                new Card(TEN, HEART));
                
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(6, new Pile(null, stack));
        
        final Board board1 = new Board(piles, emptyListOfCards, 0, emptyGoal);
        final Board board2 = new Board(piles, emptyListOfCards, 0, emptyGoal);
        
        assertTrue(board1.equals(board2));
    }
    
    @Test
    public void testHashCodeOneStack() {
        final List<Card> stack = Arrays.asList(new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, SPADE),
                new Card(TEN, HEART));
                
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(6, new Pile(null, stack));
        
        final Board board1 = new Board(piles, emptyListOfCards, 0, emptyGoal);
        final Board board2 = new Board(piles, emptyListOfCards, 0, emptyGoal);
        
        assertEquals(board1.hashCode(), board2.hashCode());
    }
    
    @Test
    public void testEqualsTwoStacks() {
        final List<Card> stack1 = Arrays.asList(new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, SPADE),
                new Card(TEN, HEART));
        final List<Card> stack2 = Arrays.asList(new Card(TWO, SPADE), new Card(FIVE, DIAMOND));
        
        final List<Pile> piles1 = new ArrayList<>(emptyPiles);
        piles1.set(6, new Pile(null, stack1));
        piles1.set(2, new Pile(stack2, null));
        
        final List<Pile> piles2 = new ArrayList<>(emptyPiles);
        piles2.set(6, new Pile(null, stack1));
        piles2.set(2, new Pile(stack2.subList(0, 1), stack2.subList(1, 2)));
        
        final Board board1 = new Board(piles1, emptyListOfCards, 0, emptyGoal);
        final Board board2 = new Board(piles2, emptyListOfCards, 0, emptyGoal);
        
        assertTrue(board1.equals(board2));
    }
    
    @Test
    public void testHashCodeTwoStacks() {
        final List<Card> stack1 = Arrays.asList(new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, SPADE),
                new Card(TEN, HEART));
        final List<Card> stack2 = Arrays.asList(new Card(TWO, SPADE), new Card(FIVE, DIAMOND));
        
        final List<Pile> piles1 = new ArrayList<>(emptyPiles);
        piles1.set(6, new Pile(null, stack1));
        piles1.set(2, new Pile(stack2, null));
        
        final List<Pile> piles2 = new ArrayList<>(emptyPiles);
        piles2.set(6, new Pile(null, stack1));
        piles2.set(2, new Pile(stack2.subList(0, 1), stack2.subList(1, 2)));
        
        final Board board1 = new Board(piles1, emptyListOfCards, 0, emptyGoal);
        final Board board2 = new Board(piles2, emptyListOfCards, 0, emptyGoal);
        
        assertEquals(board1.hashCode(), board2.hashCode());
    }
    
    // TODO: differing piles
    
    @Test
    public void testEqualsDrawPile() {
        final List<Card> drawPile = Arrays.asList(new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(EIGHT, HEART),
                new Card(EIGHT, CLUB), new Card(EIGHT, DIAMOND), new Card(KING, DIAMOND), new Card(QUEEN, CLUB),
                new Card(ACE, HEART));
                
        final Board board1 = new Board(emptyPiles, drawPile, 0, emptyGoal);
        final Board board2 = new Board(emptyPiles, drawPile, 0, emptyGoal);
        
        assertTrue(board1.equals(board2));
    }
    
    @Test
    public void testHashCodeDrawPile() {
        final List<Card> drawPile = Arrays.asList(new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(EIGHT, HEART),
                new Card(EIGHT, CLUB), new Card(EIGHT, DIAMOND), new Card(KING, DIAMOND), new Card(QUEEN, CLUB),
                new Card(ACE, HEART));
                
        final Board board1 = new Board(emptyPiles, drawPile, 0, emptyGoal);
        final Board board2 = new Board(emptyPiles, drawPile, 0, emptyGoal);
        
        assertEquals(board1.hashCode(), board2.hashCode());
    }
    
    @Test
    public void testNotEqualsDrawPile() {
        final List<Card> drawPile1 = Arrays.asList(new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(EIGHT, HEART),
                new Card(EIGHT, CLUB), new Card(EIGHT, DIAMOND), new Card(KING, DIAMOND), new Card(QUEEN, CLUB),
                new Card(ACE, HEART));
        final List<Card> drawPile2 = Arrays.asList(new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(EIGHT, CLUB),
                new Card(EIGHT, DIAMOND), new Card(KING, DIAMOND), new Card(QUEEN, CLUB), new Card(ACE, HEART));
                
        final Board board1 = new Board(emptyPiles, drawPile1, 0, emptyGoal);
        final Board board2 = new Board(emptyPiles, drawPile2, 0, emptyGoal);
        
        assertFalse(board1.equals(board2));
    }
    
    @Test
    public void testEqualsDrawIndex() {
        final List<Card> drawPile = Arrays.asList(new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(EIGHT, HEART),
                new Card(EIGHT, CLUB), new Card(EIGHT, DIAMOND), new Card(KING, DIAMOND), new Card(QUEEN, CLUB),
                new Card(ACE, HEART));
                
        final Board board1 = new Board(emptyPiles, drawPile, 8, emptyGoal);
        final Board board2 = new Board(emptyPiles, drawPile, 8, emptyGoal);
        
        assertTrue(board1.equals(board2));
    }
    
    @Test
    public void testHashCodeDrawIndex() {
        final List<Card> drawPile = Arrays.asList(new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(EIGHT, HEART),
                new Card(EIGHT, CLUB), new Card(EIGHT, DIAMOND), new Card(KING, DIAMOND), new Card(QUEEN, CLUB),
                new Card(ACE, HEART));
                
        final Board board1 = new Board(emptyPiles, drawPile, 8, emptyGoal);
        final Board board2 = new Board(emptyPiles, drawPile, 8, emptyGoal);
        
        assertEquals(board1.hashCode(), board2.hashCode());
    }
    
    @Test
    public void testEqualsGoal() {
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal.put(HEART, Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART),
                new Card(FOUR, HEART)));
        goal.put(SPADE, Arrays.asList(new Card[] {}));
        
        final Board board1 = new Board(emptyPiles, emptyListOfCards, 0, goal);
        final Board board2 = new Board(emptyPiles, emptyListOfCards, 0, goal);
        
        assertTrue(board1.equals(board2));
    }
    
    @Test
    public void testHashCodeGoal() {
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal.put(HEART, Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART),
                new Card(FOUR, HEART)));
        goal.put(SPADE, Arrays.asList(new Card[] {}));
        
        final Board board1 = new Board(emptyPiles, emptyListOfCards, 0, goal);
        final Board board2 = new Board(emptyPiles, emptyListOfCards, 0, goal);
        
        assertEquals(board1.hashCode(), board2.hashCode());
    }
    
    @Test
    public void testEqualsGoalCopy() {
        final Map<Suit, List<Card>> goal1 = new EnumMap<>(Suit.class);
        goal1.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        goal1.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal1.put(HEART, Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART),
                new Card(FOUR, HEART)));
        goal1.put(SPADE, Arrays.asList(new Card[] {}));
        
        final Map<Suit, List<Card>> goal2 = new EnumMap<>(Suit.class);
        goal2.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        goal2.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal2.put(HEART, Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART),
                new Card(FOUR, HEART)));
        goal2.put(SPADE, Arrays.asList(new Card[] {}));
        
        final Board board1 = new Board(emptyPiles, emptyListOfCards, 0, goal1);
        final Board board2 = new Board(emptyPiles, emptyListOfCards, 0, goal2);
        
        assertTrue(board1.equals(board2));
    }
    
    @Test
    public void testHashCodeGoalCopy() {
        final Map<Suit, List<Card>> goal1 = new EnumMap<>(Suit.class);
        goal1.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        goal1.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal1.put(HEART, Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART),
                new Card(FOUR, HEART)));
        goal1.put(SPADE, Arrays.asList(new Card[] {}));
        
        final Map<Suit, List<Card>> goal2 = new EnumMap<>(Suit.class);
        goal2.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        goal2.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal2.put(HEART, Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART),
                new Card(FOUR, HEART)));
        goal2.put(SPADE, Arrays.asList(new Card[] {}));
        
        final Board board1 = new Board(emptyPiles, emptyListOfCards, 0, goal1);
        final Board board2 = new Board(emptyPiles, emptyListOfCards, 0, goal2);
        
        assertEquals(board1.hashCode(), board2.hashCode());
    }
    
    @Test
    public void testNotEqualsGoal() {
        final Map<Suit, List<Card>> goal1 = new EnumMap<>(Suit.class);
        goal1.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        goal1.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal1.put(HEART, Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART),
                new Card(FOUR, HEART)));
        goal1.put(SPADE, Arrays.asList(new Card[] {}));
        
        final Map<Suit, List<Card>> goal2 = new EnumMap<>(goal1);
        goal2.put(DIAMOND, Arrays.asList(new Card[] { new Card(ACE, DIAMOND), new Card(TWO, DIAMOND) }));
        
        final Board board1 = new Board(emptyPiles, emptyListOfCards, 0, goal1);
        final Board board2 = new Board(emptyPiles, emptyListOfCards, 0, goal2);
        
        assertFalse(board1.equals(board2));
    }
    
    @Test
    public void testMoveStack() {
        final List<Card> stack = Arrays.asList(new Card(KING, SPADE));
        
        final List<Pile> stackInSecondPile = new ArrayList<>(emptyPiles);
        stackInSecondPile.set(1, new Pile(null, stack));
        final Board expectedBoard = new Board(stackInSecondPile, emptyListOfCards, 0, emptyGoal);
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, emptyListOfCards, 0, emptyGoal);
        
        final Board actualBoard = board.moveStack(0, 1, 1);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test(expected = RuntimeException.class)
    public void testMoveStackTooManyCards() {
        final List<Card> stack = Arrays.asList(new Card(KING, SPADE));
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, emptyListOfCards, 0, emptyGoal);
        
        board.moveStack(0, 1, 2);
    }
    
    @Test(expected = RuntimeException.class)
    public void testMoveStackTooFewCards() {
        final List<Card> stack = Arrays.asList(new Card(KING, SPADE));
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, emptyListOfCards, 0, emptyGoal);
        
        board.moveStack(0, 1, 0);
    }
    
    @Test(expected = RuntimeException.class)
    public void testMoveStackSameFromTo() {
        final List<Card> stack = Arrays.asList(new Card(KING, SPADE));
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, emptyListOfCards, 0, emptyGoal);
        
        board.moveStack(0, 0, 1);
    }
    
    @Test
    public void testMoveStackBig() {
        final List<Card> stack = Arrays.asList(new Card(KING, HEART), new Card(QUEEN, CLUB), new Card(JACK, HEART),
                new Card(TEN, CLUB), new Card(NINE, HEART));
                
        final List<Pile> stackInSecondPile = new ArrayList<>(emptyPiles);
        stackInSecondPile.set(1, new Pile(null, stack));
        final Board expectedBoard = new Board(stackInSecondPile, emptyListOfCards, 0, emptyGoal);
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, emptyListOfCards, 0, emptyGoal);
        
        final Board actualBoard = board.moveStack(0, 1, 5);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveStackOntoAnother() {
        final List<Card> topStack = Arrays.asList(new Card(SEVEN, HEART), new Card(SIX, CLUB));
        final List<Card> bottomStack =
                Arrays.asList(new Card(FIVE, HEART), new Card(FOUR, CLUB), new Card(THREE, HEART));
        final List<Card> combinedStack = Arrays.asList(new Card(SEVEN, HEART), new Card(SIX, CLUB),
                new Card(FIVE, HEART), new Card(FOUR, CLUB), new Card(THREE, HEART));
                
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(2, new Pile(null, combinedStack));
        final Board expectedBoard = new Board(expectedPiles, emptyListOfCards, 0, emptyGoal);
        
        final List<Pile> splitPile = new ArrayList<>(emptyPiles);
        splitPile.set(2, new Pile(null, topStack));
        splitPile.set(4, new Pile(null, bottomStack));
        final Board board = new Board(splitPile, emptyListOfCards, 0, emptyGoal);
        
        final Board actualBoard = board.moveStack(4, 2, 3);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveStackPartialOntoAnother() {
        final List<Card> topStack = Arrays.asList(new Card(SEVEN, HEART), new Card(SIX, CLUB));
        final List<Card> bottomStack = Arrays.asList(new Card(SIX, SPADE), new Card(FIVE, HEART), new Card(FOUR, CLUB),
                new Card(THREE, HEART));
        final List<Card> combinedStack = Arrays.asList(new Card(SEVEN, HEART), new Card(SIX, CLUB),
                new Card(FIVE, HEART), new Card(FOUR, CLUB), new Card(THREE, HEART));
        final List<Card> remainingStack = Arrays.asList(new Card(SIX, SPADE));
        
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(5, new Pile(null, combinedStack));
        expectedPiles.set(1, new Pile(null, remainingStack));
        final Board expectedBoard = new Board(expectedPiles, emptyListOfCards, 0, emptyGoal);
        
        final List<Pile> splitPile = new ArrayList<>(emptyPiles);
        splitPile.set(5, new Pile(null, topStack));
        splitPile.set(1, new Pile(null, bottomStack));
        final Board board = new Board(splitPile, emptyListOfCards, 0, emptyGoal);
        
        final Board actualBoard = board.moveStack(1, 5, 3);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveStackKeepsDrawPile() {
        final List<Card> stack = Arrays.asList(new Card(KING, SPADE));
        final List<Card> drawPile = Arrays.asList(new Card(TWO, CLUB), new Card(TEN, SPADE), new Card(THREE, SPADE),
                new Card(THREE, HEART));
                
        final List<Pile> stackInSecondPile = new ArrayList<>(emptyPiles);
        stackInSecondPile.set(1, new Pile(null, stack));
        final Board expectedBoard = new Board(stackInSecondPile, drawPile, 0, emptyGoal);
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, drawPile, 0, emptyGoal);
        
        final Board actualBoard = board.moveStack(0, 1, 1);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveStackKeepsDrawIndex() {
        final List<Card> stack = Arrays.asList(new Card(KING, SPADE));
        final List<Card> drawPile = Arrays.asList(new Card(TWO, CLUB), new Card(TEN, SPADE), new Card(THREE, SPADE),
                new Card(THREE, HEART));
                
        final List<Pile> stackInSecondPile = new ArrayList<>(emptyPiles);
        stackInSecondPile.set(1, new Pile(null, stack));
        final Board expectedBoard = new Board(stackInSecondPile, drawPile, 4, emptyGoal);
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, drawPile, 4, emptyGoal);
        
        final Board actualBoard = board.moveStack(0, 1, 1);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveStackKeepsGoal() {
        final List<Card> stack = Arrays.asList(new Card(KING, SPADE));
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND), new Card(THREE, DIAMOND)));
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE)));
        
        final List<Pile> stackInSecondPile = new ArrayList<>(emptyPiles);
        stackInSecondPile.set(1, new Pile(null, stack));
        final Board expectedBoard = new Board(stackInSecondPile, emptyListOfCards, 0, goal);
        
        final List<Pile> stackInFirstPile = new ArrayList<>(emptyPiles);
        stackInFirstPile.set(0, new Pile(null, stack));
        final Board board = new Board(stackInFirstPile, emptyListOfCards, 0, goal);
        
        final Board actualBoard = board.moveStack(0, 1, 1);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveCardToGoal() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(CLUB, Arrays.asList(new Card(ACE, CLUB)));
        final Board expectedBoard = new Board(emptyPiles, emptyListOfCards, 0, expectedGoal);
        
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(2, new Pile(null, Arrays.asList(new Card(ACE, CLUB))));
        final Board board = new Board(piles, emptyListOfCards, 0, emptyGoal);
        
        final Board actualBoard = board.moveCardToGoal(2);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test(expected = RuntimeException.class)
    public void testMoveCardToGoalFromEmptyPile() {
        final Board board = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        board.moveCardToGoal(2);
    }
    
    @Test
    public void testMoveCardToGoalNonEmptyGoal() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB)));
        final Board expectedBoard = new Board(emptyPiles, emptyListOfCards, 0, expectedGoal);
        
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(2, new Pile(null, Arrays.asList(new Card(FOUR, CLUB))));
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB)));
        final Board board = new Board(piles, emptyListOfCards, 0, goal);
        
        final Board actualBoard = board.moveCardToGoal(2);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToPile() {
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(4, new Pile(null, Arrays.asList(new Card(KING, CLUB))));
        final Board expectedBoard = new Board(piles, emptyListOfCards, 0, emptyGoal);
        
        final List<Card> drawPile = Arrays.asList(new Card(KING, CLUB));
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Board actualBoard = board.drawCardToPile(4);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test(expected = RuntimeException.class)
    public void testDrawCardToPileEmpty() {
        final Board board = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        board.drawCardToPile(4);
    }
    
    @Test(expected = RuntimeException.class)
    public void testDrawCardToPileUnderflow() {
        final List<Card> drawPile = Arrays.asList(new Card(KING, CLUB));
        final Board board = new Board(emptyPiles, drawPile, 0, emptyGoal);
        
        board.drawCardToPile(4);
    }
    
    @Test
    public void testDrawCardToPileNonEmpty() {
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART),
                new Card(SIX, CLUB), new Card(FIVE, DIAMOND), new Card(FOUR, SPADE))));
        final Board expectedBoard = new Board(expectedPiles, emptyListOfCards, 0, emptyGoal);
        
        final List<Card> drawPile = Arrays.asList(new Card(FOUR, SPADE));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART), new Card(SIX, CLUB),
                new Card(FIVE, DIAMOND))));
        final Board board = new Board(piles, drawPile, 1, emptyGoal);
        
        final Board actualBoard = board.drawCardToPile(4);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToPileFromBeginningNonEmpty() {
        final List<Card> expectedDrawPile =
                Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB), new Card(QUEEN, CLUB));
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART),
                new Card(SIX, CLUB), new Card(FIVE, DIAMOND), new Card(FOUR, SPADE))));
        final Board expectedBoard = new Board(expectedPiles, expectedDrawPile, 0, emptyGoal);
        
        final List<Card> drawPile =
                Arrays.asList(new Card(FOUR, SPADE), new Card(SIX, SPADE), new Card(JACK, CLUB), new Card(QUEEN, CLUB));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART), new Card(SIX, CLUB),
                new Card(FIVE, DIAMOND))));
        final Board board = new Board(piles, drawPile, 1, emptyGoal);
        
        final Board actualBoard = board.drawCardToPile(4);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToPileFromMiddleNonEmpty() {
        final List<Card> expectedDrawPile =
                Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB), new Card(QUEEN, CLUB));
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART),
                new Card(SIX, CLUB), new Card(FIVE, DIAMOND), new Card(FOUR, SPADE))));
        final Board expectedBoard = new Board(expectedPiles, expectedDrawPile, 1, emptyGoal);
        
        final List<Card> drawPile =
                Arrays.asList(new Card(SIX, SPADE), new Card(FOUR, SPADE), new Card(JACK, CLUB), new Card(QUEEN, CLUB));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART), new Card(SIX, CLUB),
                new Card(FIVE, DIAMOND))));
        final Board board = new Board(piles, drawPile, 2, emptyGoal);
        
        final Board actualBoard = board.drawCardToPile(4);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToPileFromEndNonEmpty() {
        final List<Card> expectedDrawPile =
                Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB), new Card(QUEEN, CLUB));
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART),
                new Card(SIX, CLUB), new Card(FIVE, DIAMOND), new Card(FOUR, SPADE))));
        final Board expectedBoard = new Board(expectedPiles, expectedDrawPile, 3, emptyGoal);
        
        final List<Card> drawPile =
                Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB), new Card(QUEEN, CLUB), new Card(FOUR, SPADE));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(4, new Pile(null, Arrays.asList(new Card(EIGHT, SPADE), new Card(SEVEN, HEART), new Card(SIX, CLUB),
                new Card(FIVE, DIAMOND))));
        final Board board = new Board(piles, drawPile, 4, emptyGoal);
        
        final Board actualBoard = board.drawCardToPile(4);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToPileKeepsGoal() {
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND), new Card(THREE, DIAMOND)));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(4, new Pile(null, Arrays.asList(new Card(KING, CLUB))));
        final Board expectedBoard = new Board(piles, emptyListOfCards, 0, goal);
        
        final List<Card> drawPile = Arrays.asList(new Card(KING, CLUB));
        final Board board = new Board(emptyPiles, drawPile, 1, goal);
        
        final Board actualBoard = board.drawCardToPile(4);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToGoal() {
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE)));
        final Board expectedBoard = new Board(emptyPiles, emptyListOfCards, 0, goal);
        
        final List<Card> drawPile = Arrays.asList(new Card(ACE, SPADE));
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Board actualBoard = board.drawCardToGoal();
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test(expected = RuntimeException.class)
    public void testDrawCardToGoalEmpty() {
        final Board board = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        board.drawCardToGoal();
    }
    
    @Test(expected = RuntimeException.class)
    public void testDrawCardToGoalUnderflow() {
        final List<Card> drawPile = Arrays.asList(new Card(ACE, SPADE));
        final Board board = new Board(emptyPiles, drawPile, 0, emptyGoal);
        
        board.drawCardToGoal();
    }
    
    @Test
    public void testDrawCardToGoalNonEmpty() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        expectedGoal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE), new Card(FIVE, SPADE)));
        final Board expectedBoard = new Board(emptyPiles, emptyListOfCards, 0, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE)));
        final List<Card> drawPile = Arrays.asList(new Card(FIVE, SPADE));
        final Board board = new Board(emptyPiles, drawPile, 1, goal);
        
        final Board actualBoard = board.drawCardToGoal();
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToGoalFromBeginningNonEmpty() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        expectedGoal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE), new Card(FIVE, SPADE)));
        final List<Card> expectedDrawPile = Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB),
                new Card(QUEEN, CLUB), new Card(TWO, HEART), new Card(TEN, DIAMOND));
        final Board expectedBoard = new Board(emptyPiles, expectedDrawPile, 0, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE)));
        final List<Card> drawPile = Arrays.asList(new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(JACK, CLUB),
                new Card(QUEEN, CLUB), new Card(TWO, HEART), new Card(TEN, DIAMOND));
        final Board board = new Board(emptyPiles, drawPile, 1, goal);
        
        final Board actualBoard = board.drawCardToGoal();
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToGoalFromMiddleNonEmpty() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        expectedGoal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE), new Card(FIVE, SPADE)));
        final List<Card> expectedDrawPile = Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB),
                new Card(QUEEN, CLUB), new Card(TWO, HEART), new Card(TEN, DIAMOND));
        final Board expectedBoard = new Board(emptyPiles, expectedDrawPile, 2, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE)));
        final List<Card> drawPile = Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB), new Card(FIVE, SPADE),
                new Card(QUEEN, CLUB), new Card(TWO, HEART), new Card(TEN, DIAMOND));
        final Board board = new Board(emptyPiles, drawPile, 3, goal);
        
        final Board actualBoard = board.drawCardToGoal();
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToGoalFromEndNonEmpty() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        expectedGoal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE), new Card(FIVE, SPADE)));
        final List<Card> expectedDrawPile = Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB),
                new Card(QUEEN, CLUB), new Card(TWO, HEART), new Card(TEN, DIAMOND));
        final Board expectedBoard = new Board(emptyPiles, expectedDrawPile, 5, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND)));
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE),
                new Card(FOUR, SPADE)));
        final List<Card> drawPile = Arrays.asList(new Card(SIX, SPADE), new Card(JACK, CLUB), new Card(QUEEN, CLUB),
                new Card(TWO, HEART), new Card(TEN, DIAMOND), new Card(FIVE, SPADE));
        final Board board = new Board(emptyPiles, drawPile, 6, goal);
        
        final Board actualBoard = board.drawCardToGoal();
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDrawCardToGoalKeepsPiles() {
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE)));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(1, new Pile(null, Arrays.asList(new Card(FOUR, DIAMOND), new Card(THREE, SPADE),
                new Card(TWO, DIAMOND), new Card(ACE, SPADE))));
        piles.set(2,
                new Pile(Arrays.asList(new Card(FOUR, CLUB), new Card(TEN, DIAMOND)), Arrays.asList(new Card(TEN, CLUB),
                        new Card(NINE, HEART), new Card(EIGHT, CLUB), new Card(SEVEN, HEART))));
        final Board expectedBoard = new Board(piles, emptyListOfCards, 0, goal);
        
        final List<Card> drawPile = Arrays.asList(new Card(ACE, SPADE));
        final Board board = new Board(piles, drawPile, 1, emptyGoal);
        
        final Board actualBoard = board.drawCardToGoal();
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveCardFromGoal() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB)));
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(3, new Pile(null, Arrays.asList(new Card(KING, CLUB))));
        final Board expectedBoard = new Board(expectedPiles, emptyListOfCards, 0, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB),
                        new Card(KING, CLUB)));
        final Board board = new Board(emptyPiles, emptyListOfCards, 0, goal);
        
        final Board actualBoard = board.moveCardFromGoal(CLUB, 3);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test(expected = RuntimeException.class)
    public void testMoveCardFromGoalEmpty() {
        final Board board = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        board.moveCardFromGoal(CLUB, 3);
    }
    
    @Test
    public void testMoveCardFromGoalNonEmptyPile() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB)));
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(3, new Pile(Arrays.asList(new Card(FOUR, SPADE)), Arrays.asList(new Card(SIX, HEART),
                new Card(FIVE, SPADE), new Card(FOUR, HEART), new Card(THREE, CLUB))));
        final Board expectedBoard = new Board(expectedPiles, emptyListOfCards, 0, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB)));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(3, new Pile(Arrays.asList(new Card(FOUR, SPADE)),
                Arrays.asList(new Card(SIX, HEART), new Card(FIVE, SPADE), new Card(FOUR, HEART))));
        final Board board = new Board(piles, emptyListOfCards, 0, goal);
        
        final Board actualBoard = board.moveCardFromGoal(CLUB, 3);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveCardFromGoalKeepsDrawPile() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB)));
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(3, new Pile(Arrays.asList(new Card(FOUR, SPADE)), Arrays.asList(new Card(SIX, HEART),
                new Card(FIVE, SPADE), new Card(FOUR, HEART), new Card(THREE, CLUB))));
        final List<Card> drawPile = Arrays.asList(new Card(JACK, CLUB), new Card(TWO, DIAMOND), new Card(SIX, HEART));
        final Board expectedBoard = new Board(expectedPiles, drawPile, 0, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB)));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(3, new Pile(Arrays.asList(new Card(FOUR, SPADE)),
                Arrays.asList(new Card(SIX, HEART), new Card(FIVE, SPADE), new Card(FOUR, HEART))));
        final Board board = new Board(piles, drawPile, 0, goal);
        
        final Board actualBoard = board.moveCardFromGoal(CLUB, 3);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testMoveCardFromGoalKeepsDrawIndex() {
        final Map<Suit, List<Card>> expectedGoal = new EnumMap<>(emptyGoal);
        expectedGoal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB)));
        final List<Pile> expectedPiles = new ArrayList<>(emptyPiles);
        expectedPiles.set(3, new Pile(Arrays.asList(new Card(FOUR, SPADE)), Arrays.asList(new Card(SIX, HEART),
                new Card(FIVE, SPADE), new Card(FOUR, HEART), new Card(THREE, CLUB))));
        final List<Card> drawPile = Arrays.asList(new Card(JACK, CLUB), new Card(TWO, DIAMOND), new Card(SIX, HEART));
        final Board expectedBoard = new Board(expectedPiles, drawPile, 2, expectedGoal);
        
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB)));
        final List<Pile> piles = new ArrayList<>(emptyPiles);
        piles.set(3, new Pile(Arrays.asList(new Card(FOUR, SPADE)),
                Arrays.asList(new Card(SIX, HEART), new Card(FIVE, SPADE), new Card(FOUR, HEART))));
        final Board board = new Board(piles, drawPile, 2, goal);
        
        final Board actualBoard = board.moveCardFromGoal(CLUB, 3);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
}
