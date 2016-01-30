package org.silnith.game.solitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.silnith.deck.Card;
import org.silnith.deck.Suit;
import org.silnith.deck.Value;
import org.silnith.game.solitaire.move.AdvanceDrawPileMove;
import org.silnith.game.solitaire.move.DealMove;
import org.silnith.game.solitaire.move.DrawToGoalMove;
import org.silnith.game.solitaire.move.ResetDrawPileMove;
import org.silnith.game.solitaire.move.SolitaireMove;


@RunWith(MockitoJUnitRunner.class)
public class SolitaireTest {
    
    private final List<Card> emptyListOfCards;
    
    private final EnumMap<Suit, List<Card>> emptyGoal;
    
    private final List<Pile> emptyPiles;
    
    private final Solitaire solitaire;
    
    @Mock
    private BoardValidator boardValidator;
    
    public SolitaireTest() {
        super();
        
        this.solitaire = new Solitaire(7, boardValidator);
        this.solitaire.setReturnRedundantMoves(false);
        
        this.emptyListOfCards = Collections.emptyList();
        this.emptyGoal = new EnumMap<>(Suit.class);
        for (final Suit suit : Suit.values()) {
            this.emptyGoal.put(suit, emptyListOfCards);
        }
        this.emptyPiles = new ArrayList<>(this.solitaire.getNumberOfStacks());
        for (int i = 0; i < this.solitaire.getNumberOfStacks(); i++ ) {
            this.emptyPiles.add(new Pile(emptyListOfCards, emptyListOfCards));
        }
    }
    
    private List<Card> orderedDeck() {
        final List<Card> deck = new ArrayList<>(52);
        for (final Suit suit : Suit.values()) {
            for (final Value value : Value.values()) {
                deck.add(new Card(value, suit));
            }
        }
        return deck;
    }
    
    @Test
    public void testCanAddSevenOfHeartsToEightOfSpades() {
        final Card topCard = new Card(EIGHT, SPADE);
        final List<Card> existing = Collections.singletonList(topCard);
        final List<Card> emptyList = Collections.emptyList();
        final Pile pile = new Pile(emptyList, existing);
        
        final Card cardToAdd = new Card(SEVEN, HEART);
        final List<Card> cardsToAdd = Collections.singletonList(cardToAdd);
        
        assertTrue(solitaire.canAddToPile(cardsToAdd, pile));
    }
    
    @Test
    public void testCanNotAddSevenOfClubsToEightOfSpades() {
        final Card topCard = new Card(EIGHT, SPADE);
        final List<Card> existing = Collections.singletonList(topCard);
        final List<Card> emptyList = Collections.emptyList();
        final Pile pile = new Pile(emptyList, existing);
        
        final Card cardToAdd = new Card(SEVEN, CLUB);
        final List<Card> cardsToAdd = Collections.singletonList(cardToAdd);
        
        assertFalse(solitaire.canAddToPile(cardsToAdd, pile));
    }
    
    @Test
    public void testCanNotAddNineOfHeartsToEightOfSpades() {
        final Card topCard = new Card(EIGHT, SPADE);
        final List<Card> existing = Collections.singletonList(topCard);
        final List<Card> emptyList = Collections.emptyList();
        final Pile pile = new Pile(emptyList, existing);
        
        final Card cardToAdd = new Card(NINE, HEART);
        final List<Card> cardsToAdd = Collections.singletonList(cardToAdd);
        
        assertFalse(solitaire.canAddToPile(cardsToAdd, pile));
    }
    
    @Test
    public void testDealMove() {
        final List<Card> deck = orderedDeck();
        final DealMove expectedMove = new DealMove(deck, 7);
        
        final DealMove actualMove = solitaire.dealMove(deck);
        
        assertEquals(expectedMove, actualMove);
    }
    
    @Test
    public void testDeal() {
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(Arrays.asList(new Card(ACE, CLUB)), null));
        piles.add(new Pile(Arrays.asList(new Card(TWO, CLUB), new Card(EIGHT, CLUB)), null));
        piles.add(new Pile(Arrays.asList(new Card(THREE, CLUB), new Card(NINE, CLUB), new Card(ACE, DIAMOND)), null));
        piles.add(new Pile(Arrays.asList(new Card(FOUR, CLUB), new Card(TEN, CLUB), new Card(TWO, DIAMOND),
                new Card(SIX, DIAMOND)), null));
        piles.add(new Pile(Arrays.asList(new Card(FIVE, CLUB), new Card(JACK, CLUB), new Card(THREE, DIAMOND),
                new Card(SEVEN, DIAMOND), new Card(TEN, DIAMOND)), null));
        piles.add(new Pile(Arrays.asList(new Card(SIX, CLUB), new Card(QUEEN, CLUB), new Card(FOUR, DIAMOND),
                new Card(EIGHT, DIAMOND), new Card(JACK, DIAMOND), new Card(KING, DIAMOND)), null));
        piles.add(new Pile(
                Arrays.asList(new Card(SEVEN, CLUB), new Card(KING, CLUB), new Card(FIVE, DIAMOND),
                        new Card(NINE, DIAMOND), new Card(QUEEN, DIAMOND), new Card(ACE, HEART), new Card(TWO, HEART)),
                null));
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB, Collections.<Card> emptyList());
        goal.put(DIAMOND, Collections.<Card> emptyList());
        goal.put(HEART, Collections.<Card> emptyList());
        goal.put(SPADE, Collections.<Card> emptyList());
        final List<Card> drawPile = Arrays.asList(new Card(THREE, HEART), new Card(FOUR, HEART), new Card(FIVE, HEART),
                new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART), new Card(NINE, HEART),
                new Card(TEN, HEART), new Card(JACK, HEART), new Card(QUEEN, HEART), new Card(KING, HEART),
                new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(FOUR, SPADE),
                new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE),
                new Card(NINE, SPADE), new Card(TEN, SPADE), new Card(JACK, SPADE), new Card(QUEEN, SPADE),
                new Card(KING, SPADE));
        final Board expectedBoard = new Board(piles, drawPile, 0, goal);
        
        final List<Card> deck = orderedDeck();
        final DealMove move = new DealMove(deck, 7);
        
        final Board actualBoard = solitaire.deal(move);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testDealDeck() {
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(Arrays.asList(new Card(ACE, CLUB)), null));
        piles.add(new Pile(Arrays.asList(new Card(TWO, CLUB), new Card(EIGHT, CLUB)), null));
        piles.add(new Pile(Arrays.asList(new Card(THREE, CLUB), new Card(NINE, CLUB), new Card(ACE, DIAMOND)), null));
        piles.add(new Pile(Arrays.asList(new Card(FOUR, CLUB), new Card(TEN, CLUB), new Card(TWO, DIAMOND),
                new Card(SIX, DIAMOND)), null));
        piles.add(new Pile(Arrays.asList(new Card(FIVE, CLUB), new Card(JACK, CLUB), new Card(THREE, DIAMOND),
                new Card(SEVEN, DIAMOND), new Card(TEN, DIAMOND)), null));
        piles.add(new Pile(Arrays.asList(new Card(SIX, CLUB), new Card(QUEEN, CLUB), new Card(FOUR, DIAMOND),
                new Card(EIGHT, DIAMOND), new Card(JACK, DIAMOND), new Card(KING, DIAMOND)), null));
        piles.add(new Pile(
                Arrays.asList(new Card(SEVEN, CLUB), new Card(KING, CLUB), new Card(FIVE, DIAMOND),
                        new Card(NINE, DIAMOND), new Card(QUEEN, DIAMOND), new Card(ACE, HEART), new Card(TWO, HEART)),
                null));
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB, Collections.<Card> emptyList());
        goal.put(DIAMOND, Collections.<Card> emptyList());
        goal.put(HEART, Collections.<Card> emptyList());
        goal.put(SPADE, Collections.<Card> emptyList());
        final List<Card> drawPile = Arrays.asList(new Card(THREE, HEART), new Card(FOUR, HEART), new Card(FIVE, HEART),
                new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART), new Card(NINE, HEART),
                new Card(TEN, HEART), new Card(JACK, HEART), new Card(QUEEN, HEART), new Card(KING, HEART),
                new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(FOUR, SPADE),
                new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE),
                new Card(NINE, SPADE), new Card(TEN, SPADE), new Card(JACK, SPADE), new Card(QUEEN, SPADE),
                new Card(KING, SPADE));
        final Board expectedBoard = new Board(piles, drawPile, 0, goal);
        
        final List<Card> deck = orderedDeck();
        
        final Board actualBoard = solitaire.deal(deck);
        
        assertEquals(expectedBoard, actualBoard);
    }
    
    @Test
    public void testIsWin() {
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        final List<Card> drawPile = Collections.emptyList();
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB),
                        new Card(KING, CLUB)));
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND), new Card(THREE, DIAMOND),
                new Card(FOUR, DIAMOND), new Card(FIVE, DIAMOND), new Card(SIX, DIAMOND), new Card(SEVEN, DIAMOND),
                new Card(EIGHT, DIAMOND), new Card(NINE, DIAMOND), new Card(TEN, DIAMOND), new Card(JACK, DIAMOND),
                new Card(QUEEN, DIAMOND), new Card(KING, DIAMOND)));
        goal.put(HEART,
                Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART), new Card(FOUR, HEART),
                        new Card(FIVE, HEART), new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART),
                        new Card(NINE, HEART), new Card(TEN, HEART), new Card(JACK, HEART), new Card(QUEEN, HEART),
                        new Card(KING, HEART)));
        goal.put(SPADE,
                Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(FOUR, SPADE),
                        new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE),
                        new Card(NINE, SPADE), new Card(TEN, SPADE), new Card(JACK, SPADE), new Card(QUEEN, SPADE),
                        new Card(KING, SPADE)));
        final Board board = new Board(piles, drawPile, 0, goal);
        
        assertTrue(solitaire.isWin(board));
    }
    
    @Test
    public void testIsWinMissingSuit() {
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        final List<Card> drawPile = Collections.emptyList();
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB, Collections.<Card> emptyList());
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND), new Card(THREE, DIAMOND),
                new Card(FOUR, DIAMOND), new Card(FIVE, DIAMOND), new Card(SIX, DIAMOND), new Card(SEVEN, DIAMOND),
                new Card(EIGHT, DIAMOND), new Card(NINE, DIAMOND), new Card(TEN, DIAMOND), new Card(JACK, DIAMOND),
                new Card(QUEEN, DIAMOND), new Card(KING, DIAMOND)));
        goal.put(HEART,
                Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART), new Card(FOUR, HEART),
                        new Card(FIVE, HEART), new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART),
                        new Card(NINE, HEART), new Card(TEN, HEART), new Card(JACK, HEART), new Card(QUEEN, HEART),
                        new Card(KING, HEART)));
        goal.put(SPADE,
                Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(FOUR, SPADE),
                        new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE),
                        new Card(NINE, SPADE), new Card(TEN, SPADE), new Card(JACK, SPADE), new Card(QUEEN, SPADE),
                        new Card(KING, SPADE)));
        final Board board = new Board(piles, drawPile, 0, goal);
        
        assertFalse(solitaire.isWin(board));
    }
    
    @Test
    public void testIsWinMissingOneCard() {
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        final List<Card> drawPile = Collections.emptyList();
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB),
                        new Card(KING, CLUB)));
        goal.put(DIAMOND,
                Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND), new Card(THREE, DIAMOND),
                        new Card(FOUR, DIAMOND), new Card(FIVE, DIAMOND), new Card(SEVEN, DIAMOND),
                        new Card(EIGHT, DIAMOND), new Card(NINE, DIAMOND), new Card(TEN, DIAMOND),
                        new Card(JACK, DIAMOND), new Card(QUEEN, DIAMOND), new Card(KING, DIAMOND)));
        goal.put(HEART,
                Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART), new Card(FOUR, HEART),
                        new Card(FIVE, HEART), new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART),
                        new Card(NINE, HEART), new Card(TEN, HEART), new Card(JACK, HEART), new Card(QUEEN, HEART),
                        new Card(KING, HEART)));
        goal.put(SPADE,
                Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(FOUR, SPADE),
                        new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE),
                        new Card(NINE, SPADE), new Card(TEN, SPADE), new Card(JACK, SPADE), new Card(QUEEN, SPADE),
                        new Card(KING, SPADE)));
        final Board board = new Board(piles, drawPile, 0, goal);
        
        assertFalse(solitaire.isWin(board));
    }
    
    @Test
    public void testIsWinOneCardOnPiles() {
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, Arrays.asList(new Card(KING, SPADE))));
        final List<Card> drawPile = Collections.emptyList();
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB),
                        new Card(KING, CLUB)));
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND), new Card(THREE, DIAMOND),
                new Card(FOUR, DIAMOND), new Card(FIVE, DIAMOND), new Card(SIX, DIAMOND), new Card(SEVEN, DIAMOND),
                new Card(EIGHT, DIAMOND), new Card(NINE, DIAMOND), new Card(TEN, DIAMOND), new Card(JACK, DIAMOND),
                new Card(QUEEN, DIAMOND), new Card(KING, DIAMOND)));
        goal.put(HEART,
                Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART), new Card(FOUR, HEART),
                        new Card(FIVE, HEART), new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART),
                        new Card(NINE, HEART), new Card(TEN, HEART), new Card(JACK, HEART), new Card(QUEEN, HEART),
                        new Card(KING, HEART)));
        goal.put(SPADE,
                Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(FOUR, SPADE),
                        new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE),
                        new Card(NINE, SPADE), new Card(TEN, SPADE), new Card(JACK, SPADE), new Card(QUEEN, SPADE)));
        final Board board = new Board(piles, drawPile, 0, goal);
        
        assertFalse(solitaire.isWin(board));
    }
    
    @Test
    public void testIsWinOneCardInDrawPile() {
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        piles.add(new Pile(null, null));
        final List<Card> drawPile = Arrays.asList(new Card(KING, SPADE));
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB,
                Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB), new Card(FOUR, CLUB),
                        new Card(FIVE, CLUB), new Card(SIX, CLUB), new Card(SEVEN, CLUB), new Card(EIGHT, CLUB),
                        new Card(NINE, CLUB), new Card(TEN, CLUB), new Card(JACK, CLUB), new Card(QUEEN, CLUB),
                        new Card(KING, CLUB)));
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND), new Card(TWO, DIAMOND), new Card(THREE, DIAMOND),
                new Card(FOUR, DIAMOND), new Card(FIVE, DIAMOND), new Card(SIX, DIAMOND), new Card(SEVEN, DIAMOND),
                new Card(EIGHT, DIAMOND), new Card(NINE, DIAMOND), new Card(TEN, DIAMOND), new Card(JACK, DIAMOND),
                new Card(QUEEN, DIAMOND), new Card(KING, DIAMOND)));
        goal.put(HEART,
                Arrays.asList(new Card(ACE, HEART), new Card(TWO, HEART), new Card(THREE, HEART), new Card(FOUR, HEART),
                        new Card(FIVE, HEART), new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART),
                        new Card(NINE, HEART), new Card(TEN, HEART), new Card(JACK, HEART), new Card(QUEEN, HEART),
                        new Card(KING, HEART)));
        goal.put(SPADE,
                Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE), new Card(THREE, SPADE), new Card(FOUR, SPADE),
                        new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE),
                        new Card(NINE, SPADE), new Card(TEN, SPADE), new Card(JACK, SPADE), new Card(QUEEN, SPADE)));
        final Board board = new Board(piles, drawPile, 1, goal);
        
        assertFalse(solitaire.isWin(board));
    }
    
    @Test
    public void testIsWinNewGame() {
        final List<Card> deck = orderedDeck();
        final Board board = solitaire.deal(deck);
        
        assertFalse(solitaire.isWin(board));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnAdvanceDrawForEmptyDrawPile() {
        final SolitaireMove move = new AdvanceDrawPileMove(0, solitaire.getDrawAdvance());
        
        final Board board = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnResetDrawForEmptyDrawPile() {
        final SolitaireMove move = new ResetDrawPileMove(0);
        
        final Board board = new Board(emptyPiles, emptyListOfCards, 0, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesReturnsAdvanceDraw() {
        final SolitaireMove move = new AdvanceDrawPileMove(0, solitaire.getDrawAdvance());
        
        final List<Card> drawPile = Arrays.asList(new Card(EIGHT, CLUB));
        final Board board = new Board(emptyPiles, drawPile, 0, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertTrue(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnAdvanceDrawAtEndOfDrawPile() {
        final SolitaireMove move = new AdvanceDrawPileMove(1, solitaire.getDrawAdvance());
        
        final List<Card> drawPile = Arrays.asList(new Card(EIGHT, CLUB));
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnResetDrawAtBeginningOfDrawPile() {
        final SolitaireMove move = new ResetDrawPileMove(0);
        
        final List<Card> drawPile = Arrays.asList(new Card(EIGHT, CLUB));
        final Board board = new Board(emptyPiles, drawPile, 0, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesReturnsResetDrawAtEndOfDrawPile() {
        final SolitaireMove move = new ResetDrawPileMove(1);
        
        final List<Card> drawPile = Arrays.asList(new Card(EIGHT, CLUB));
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertTrue(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesReturnsAdvanceDrawInMiddleOfDrawPile() {
        final SolitaireMove move = new AdvanceDrawPileMove(1, solitaire.getDrawAdvance());
        
        final List<Card> drawPile = Arrays.asList(new Card(EIGHT, CLUB), new Card(SEVEN, SPADE));
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertTrue(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnResetDrawInMiddleOfDrawPile() {
        final SolitaireMove move = new ResetDrawPileMove(1);
        
        final List<Card> drawPile = Arrays.asList(new Card(EIGHT, CLUB), new Card(SEVEN, SPADE));
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesReturnsDrawToGoal() {
        final Card card = new Card(ACE, CLUB);
        final SolitaireMove move = new DrawToGoalMove(1, card);
        
        final List<Card> drawPile = Arrays.asList(card);
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertTrue(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnDrawToGoal() {
        final Card card = new Card(TWO, CLUB);
        final SolitaireMove move = new DrawToGoalMove(1, card);
        
        final List<Card> drawPile = Arrays.asList(card);
        final Board board = new Board(emptyPiles, drawPile, 1, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnDrawToGoalUnderflow() {
        // Degenerate test case.
        final Card card = new Card(TWO, CLUB);
        final SolitaireMove move = new DrawToGoalMove(1, card);
        
        final List<Card> drawPile = Arrays.asList(card);
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB), new Card(THREE, CLUB)));
        final Board board = new Board(emptyPiles, drawPile, 1, goal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    @Test
    public void testFindAllMovesDoesNotReturnDrawToGoalDuplicate() {
        // Degenerate test case.
        final Card card = new Card(TWO, CLUB);
        final SolitaireMove move = new DrawToGoalMove(1, card);
        
        final List<Card> drawPile = Arrays.asList(card);
        final Map<Suit, List<Card>> goal = new EnumMap<>(emptyGoal);
        goal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB)));
        final Board board = new Board(emptyPiles, drawPile, 1, goal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        assertFalse(moves.contains(move));
    }
    
    // @Test
    public void testFindAllMoves1() {
        final List<Card> drawPile = Arrays.asList(new Card(THREE, CLUB), new Card(QUEEN, DIAMOND),
                new Card(SEVEN, SPADE), new Card(EIGHT, CLUB), new Card(NINE, CLUB), new Card(EIGHT, DIAMOND),
                new Card(SEVEN, CLUB), new Card(JACK, CLUB), new Card(KING, CLUB), new Card(EIGHT, HEART),
                new Card(SIX, CLUB), new Card(QUEEN, SPADE), new Card(FIVE, DIAMOND), new Card(QUEEN, CLUB),
                new Card(SEVEN, DIAMOND), new Card(JACK, DIAMOND));
        final Card nil = null;
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(emptyListOfCards, Arrays.asList(new Card(SIX, DIAMOND))));
        piles.add(new Pile(Arrays.asList(nil),
                Arrays.asList(new Card(FOUR, DIAMOND), new Card(THREE, SPADE), new Card(TWO, HEART))));
        piles.add(new Pile(Arrays.asList(nil, nil),
                Arrays.asList(new Card(NINE, DIAMOND), new Card(EIGHT, SPADE), new Card(SEVEN, HEART))));
        piles.add(new Pile(Arrays.asList(nil, nil), Arrays.asList(new Card(THREE, HEART))));
        piles.add(new Pile(Arrays.asList(nil, nil, nil),
                Arrays.asList(new Card(FIVE, HEART), new Card(FOUR, CLUB), new Card(THREE, DIAMOND))));
        piles.add(new Pile(Arrays.asList(nil, nil, nil, nil, nil), Arrays.asList(new Card(NINE, HEART))));
        piles.add(new Pile(Arrays.asList(nil, nil, nil, nil, nil), Arrays.asList(new Card(FOUR, HEART))));
        final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
        goal.put(CLUB, Arrays.asList(new Card(ACE, CLUB), new Card(TWO, CLUB)));
        goal.put(DIAMOND, Arrays.asList(new Card(ACE, DIAMOND)));
        goal.put(HEART, emptyListOfCards);
        goal.put(SPADE, Arrays.asList(new Card(ACE, SPADE), new Card(TWO, SPADE)));
        final Board board = new Board(piles, drawPile, 0, goal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        fail(moves.toString());
    }
    
    // @Test
    public void testFindAllMoves2() {
        final List<Card> drawPile = Arrays.asList(new Card(THREE, CLUB), new Card(QUEEN, DIAMOND),
                new Card(SEVEN, SPADE), new Card(EIGHT, CLUB), new Card(NINE, CLUB), new Card(EIGHT, DIAMOND),
                new Card(SEVEN, CLUB), new Card(ACE, DIAMOND), new Card(EIGHT, SPADE), new Card(JACK, CLUB),
                new Card(THREE, SPADE), new Card(ACE, CLUB), new Card(KING, CLUB), new Card(ACE, SPADE),
                new Card(THREE, DIAMOND), new Card(EIGHT, HEART), new Card(TWO, HEART), new Card(TWO, CLUB),
                new Card(SIX, CLUB), new Card(QUEEN, SPADE), new Card(FIVE, DIAMOND), new Card(QUEEN, CLUB),
                new Card(SEVEN, DIAMOND), new Card(JACK, DIAMOND));
        final List<Pile> piles = new ArrayList<>(7);
        piles.add(new Pile(emptyListOfCards, Arrays.asList(new Card(SIX, DIAMOND))));
        piles.add(new Pile(Arrays.asList(new Card(NINE, SPADE)), Arrays.asList(new Card(FOUR, DIAMOND))));
        piles.add(new Pile(Arrays.asList(new Card(QUEEN, HEART), new Card(TWO, DIAMOND)),
                Arrays.asList(new Card(NINE, DIAMOND))));
        piles.add(new Pile(Arrays.asList(new Card(FOUR, SPADE), new Card(FIVE, SPADE), new Card(THREE, HEART)),
                Arrays.asList(new Card(FOUR, CLUB))));
        piles.add(new Pile(
                Arrays.asList(new Card(JACK, HEART), new Card(SIX, SPADE), new Card(ACE, HEART), new Card(FIVE, HEART)),
                Arrays.asList(new Card(TWO, SPADE))));
        piles.add(new Pile(Arrays.asList(new Card(TEN, DIAMOND), new Card(KING, SPADE), new Card(TEN, SPADE),
                new Card(JACK, SPADE), new Card(KING, HEART)), Arrays.asList(new Card(NINE, HEART))));
        piles.add(
                new Pile(
                        Arrays.asList(new Card(FIVE, CLUB), new Card(KING, DIAMOND), new Card(TEN, CLUB),
                                new Card(TEN, HEART), new Card(SIX, HEART), new Card(FOUR, HEART)),
                        Arrays.asList(new Card(SEVEN, HEART))));
        final Board board = new Board(piles, drawPile, 0, emptyGoal);
        
        final Collection<SolitaireMove> moves = solitaire.findAllMoves(board);
        
        fail(moves.toString());
    }
    
}
