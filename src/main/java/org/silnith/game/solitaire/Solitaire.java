package org.silnith.game.solitaire;

import static org.silnith.deck.Suit.*;
import static org.silnith.deck.Value.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.silnith.deck.Card;
import org.silnith.deck.Suit;
import org.silnith.deck.Value;
import org.silnith.game.Game;
import org.silnith.game.GameState;
import org.silnith.game.solitaire.move.AdvanceDrawPileMove;
import org.silnith.game.solitaire.move.DealMove;
import org.silnith.game.solitaire.move.DrawToGoalMove;
import org.silnith.game.solitaire.move.DrawToPileMove;
import org.silnith.game.solitaire.move.GoalToPileMove;
import org.silnith.game.solitaire.move.SolitaireMove;
import org.silnith.game.solitaire.move.PileToGoalMove;
import org.silnith.game.solitaire.move.ResetDrawPileMove;
import org.silnith.game.solitaire.move.StackMove;
import org.silnith.util.LinkedNode;

public class Solitaire implements Game<SolitaireMove, Board> {

	private final int numberOfStacks;

	private int drawAdvance;

	private boolean returnRedundantMoves;

	private final BoardValidator boardValidator;

	private final AtomicLong cyclesDetected;

	private final AtomicLong drawAdvancesCoalesced;

	private final AtomicLong pileMoveAfterDrawAdvancePrune;

	private final AtomicLong samePileMovedTwicePrune;

	/**
	 * Constructs a standard solitaire game with the specified number of stacks
	 * and a draw advance of three.  This game will not return redundant moves
	 * by default.
	 * 
	 * @param numberOfStacks the number of stacks in the game
	 * @param boardValidator the board validator
	 */
	@Inject
	public Solitaire(final int numberOfStacks, final BoardValidator boardValidator) {
		super();
		this.numberOfStacks = numberOfStacks;
		this.drawAdvance = 3;
		this.returnRedundantMoves = false;
		this.boardValidator = boardValidator;

		this.cyclesDetected = new AtomicLong();
		this.drawAdvancesCoalesced = new AtomicLong();
		this.pileMoveAfterDrawAdvancePrune = new AtomicLong();
		this.samePileMovedTwicePrune = new AtomicLong();
	}

	/**
	 * Returns the number of stacks allowed on the game board.  For a standard
	 * game, this will be seven.
	 * 
	 * @return the number of stacks on the board
	 */
	public int getNumberOfStacks() {
		return numberOfStacks;
	}

	/**
	 * Returns the number of cards that will be flipped at once when the draw
	 * pile is advanced.
	 * 
	 * @return the number of cards to be flipped for each advance of the draw pile
	 */
	public int getDrawAdvance() {
		return drawAdvance;
	}

	/**
	 * Sets the number of cards to flip at once when the draw pile is advanced.
	 * 
	 * @param drawAdvance the number of cards to flip for each advance of the draw pile
	 */
	public void setDrawAdvance(final int drawAdvance) {
		if (drawAdvance <= 0) {
			throw new IllegalArgumentException();
		}
		this.drawAdvance = drawAdvance;
	}

	/**
	 * Returns whether {@link #findAllMoves(Board)} will return more than one
	 * move that results in an equivalent game state.  For example, if the game
	 * board allows a king to be moved to an empty pile and there is more than
	 * one empty pile, {@link #findAllMoves(Board)} will return a separate move
	 * for each empty pile if this is {@code true}.  If this is {@code false},
	 * {@link #findAllMoves(Board)} will only return a single move representing
	 * the move of the king to an empty pile.
	 * 
	 * <p>In less precise terms, this represents whether a human would consider
	 * moves returned by {@link #findAllMoves(Board)} to be redundant.  For
	 * listing all possible legal moves that may be made, this should be set to
	 * {@code true}.  For searching the game tree efficiently, this should be
	 * set to {@code false}.
	 * 
	 * @return whether {@link #findAllMoves(Board)} will return more than one
	 *         move that can result in an equivalent game state
	 */
	public boolean isReturnRedundantMoves() {
		return returnRedundantMoves;
	}

	/**
	 * Sets whether {@link #findAllMoves(Board)} will return more than one
	 * move that results in an equivalent game state.  For example, if the game
	 * board allows a king to be moved to an empty pile and there is more than
	 * one empty pile, {@link #findAllMoves(Board)} will return a separate move
	 * for each empty pile if this is {@code true}.  If this is {@code false},
	 * {@link #findAllMoves(Board)} will only return a single move representing
	 * the move of the king to an empty pile.
	 * 
	 * <p>In less precise terms, this represents whether a human would consider
	 * moves returned by {@link #findAllMoves(Board)} to be redundant.  For
	 * listing all possible legal moves that may be made, this should be set to
	 * {@code true}.  For searching the game tree efficiently, this should be
	 * set to {@code false}.
	 * 
	 * @param returnRedundantMoves whether {@link #findAllMoves(Board)} will
	 *         return more than one move that can result in an equivalent game state
	 */
	public void setReturnRedundantMoves(final boolean returnRedundantMoves) {
		this.returnRedundantMoves = returnRedundantMoves;
	}

	public long getCyclesDetected() {
		return cyclesDetected.get();
	}

	public long getDrawAdvancesCoalesced() {
		return drawAdvancesCoalesced.get();
	}

	public long getPileMoveAfterDrawAdvancesPruned() {
		return pileMoveAfterDrawAdvancePrune.get();
	}

	public long getSamePileMovedTwicePrunes() {
		return samePileMovedTwicePrune.get();
	}

	/**
	 * Returns a {@link SolitaireMove} representing a deal of the provided deck of cards
	 * into a new game of solitaire.
	 * 
	 * @param deck the deck of cards to deal
	 * @return a {@link SolitaireMove} representing the dealing of the cards
	 */
	public DealMove dealMove(final List<Card> deck) {
		return new DealMove(deck, numberOfStacks);
	}

	/**
	 * Returns the board that results from the provided deal.
	 * 
	 * @param move a move representing dealing a deck into a game of solitaire
	 * @return the initial board after the deal
	 */
	public Board deal(final DealMove move) {
		return move.apply(null);
	}

	/**
	 * Returns the board that results from dealing the provided deck of cards
	 * into a new game of solitaire.
	 * 
	 * @param deck the deck of cards to deal
	 * @return the initial board after the deal
	 */
	public Board deal(final List<Card> deck) {
		return deal(dealMove(deck));
	}

	/**
	 * Validate that the given board does not violate the rules of the game.
	 * Note that this only validates the provided board, it does not check the
	 * move history to see if the board was created from a legal sequence of moves.
	 * 
	 * @param board the board to validate
	 */
	public void validate(final Board board) {
		boardValidator.validate(board);
	}

	/**
	 * Applies the move to the given board and returns a new board.
	 * 
	 * @param move the move to apply
	 * @param board the board to apply the move to
	 * @return the new board state
	 */
	public Board applyMove(final SolitaireMove move, final Board board) {
		return move.apply(board);
	}

	/**
	 * Returns a test board that simply has one king in the draw pile and one
	 * king on the board, all other cards are already in the goal.
	 */
	protected Board testBoard() {
		final List<Pile> piles = new ArrayList<>(7);
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, Collections.singletonList(new Card(KING, SPADE))));
		final List<Card> draw = Collections.singletonList(new Card(KING, HEART));
		final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
		final List<Value> values = Arrays.asList(Value.values());
		final List<Card> clubs = new ArrayList<>(13);
		for (final Value value : values) {
			clubs.add(new Card(value, CLUB));
		}
		final List<Card> diamonds = new ArrayList<>(13);
		for (final Value value : values) {
			diamonds.add(new Card(value, DIAMOND));
		}
		final List<Card> hearts = new ArrayList<>(13);
		for (final Value value : values.subList(0, 12)) {
			hearts.add(new Card(value, HEART));
		}
		final List<Card> spades = new ArrayList<>(13);
		for (final Value value : values.subList(0, 12)) {
			spades.add(new Card(value, SPADE));
		}
		goal.put(CLUB, clubs);
		goal.put(DIAMOND, diamonds);
		goal.put(HEART, hearts);
		goal.put(SPADE, spades);
		return new Board(piles, draw, 0, goal);
	}

	/**
	 * Returns a board designed to be won.  All cards are on the board, stacked
	 * in four piles from king to ace.
	 */
	protected Board winningBoard() {
		final List<Pile> piles = new ArrayList<>(7);
		piles.add(new Pile(null, Arrays.asList(new Card(KING, CLUB), new Card(QUEEN, DIAMOND), new Card(JACK, CLUB), new Card(TEN, DIAMOND), new Card(NINE, CLUB), new Card(EIGHT, DIAMOND), new Card(SEVEN, CLUB), new Card(SIX, DIAMOND), new Card(FIVE, CLUB), new Card(FOUR, DIAMOND), new Card(THREE, CLUB), new Card(TWO, DIAMOND), new Card(ACE, CLUB))));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, Arrays.asList(new Card(KING, DIAMOND), new Card(QUEEN, CLUB), new Card(JACK, DIAMOND), new Card(TEN, CLUB), new Card(NINE, DIAMOND), new Card(EIGHT, CLUB), new Card(SEVEN, DIAMOND), new Card(SIX, CLUB), new Card(FIVE, DIAMOND), new Card(FOUR, CLUB), new Card(THREE, DIAMOND), new Card(TWO, CLUB), new Card(ACE, DIAMOND))));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, Arrays.asList(new Card(KING, SPADE), new Card(QUEEN, HEART), new Card(JACK, SPADE), new Card(TEN, HEART), new Card(NINE, SPADE), new Card(EIGHT, HEART), new Card(SEVEN, SPADE), new Card(SIX, HEART), new Card(FIVE, SPADE), new Card(FOUR, HEART), new Card(THREE, SPADE), new Card(TWO, HEART), new Card(ACE, SPADE))));
		piles.add(new Pile(null, null));
		piles.add(new Pile(null, Arrays.asList(new Card(KING, HEART), new Card(QUEEN, SPADE), new Card(JACK, HEART), new Card(TEN, SPADE), new Card(NINE, HEART), new Card(EIGHT, SPADE), new Card(SEVEN, HEART), new Card(SIX, SPADE), new Card(FIVE, HEART), new Card(FOUR, SPADE), new Card(THREE, HEART), new Card(TWO, SPADE), new Card(ACE, HEART))));
		final List<Card> drawPile = Collections.emptyList();
		final Map<Suit, List<Card>> goal = new EnumMap<>(Suit.class);
		goal.put(CLUB, Collections.<Card>emptyList());
		goal.put(DIAMOND, Collections.<Card>emptyList());
		goal.put(HEART, Collections.<Card>emptyList());
		goal.put(SPADE, Collections.<Card>emptyList());
		
		return new Board(piles, drawPile, 0, goal);
	}

	/**
	 * Returns a deck of cards that, when dealt into a standard game, is
	 * guaranteed to be winnable.
	 */
	protected List<Card> winningDeck() {
		final List<Card> deck = Arrays.asList(
				new Card(ACE, CLUB), new Card(THREE, CLUB), new Card(SIX, CLUB),  new Card(TEN, CLUB),   new Card(KING, CLUB),   new Card(SEVEN, DIAMOND), new Card(KING, DIAMOND),
				                     new Card(TWO, CLUB),   new Card(FIVE, CLUB), new Card(NINE, CLUB),  new Card(QUEEN, CLUB),  new Card(SIX, DIAMOND),   new Card(QUEEN, DIAMOND),
				                                            new Card(FOUR, CLUB), new Card(EIGHT, CLUB), new Card(JACK, CLUB),   new Card(FIVE, DIAMOND),  new Card(JACK, DIAMOND),
				                                                                  new Card(SEVEN, CLUB), new Card(TWO, DIAMOND), new Card(FOUR, DIAMOND),  new Card(TEN, DIAMOND),
				                                                                                         new Card(ACE, DIAMOND), new Card(THREE, DIAMOND), new Card(NINE, DIAMOND),
				                                                                                                                 new Card(ACE, HEART),     new Card(EIGHT, DIAMOND),
				                                                                                                                                           new Card(ACE, SPADE),
				new Card(KING, HEART), new Card(QUEEN, HEART), new Card(JACK, HEART), new Card(TEN, HEART), new Card(NINE, HEART), new Card(EIGHT, HEART), new Card(SEVEN, HEART), new Card(SIX, HEART), new Card(FIVE, HEART), new Card(FOUR, HEART), new Card(THREE, HEART), new Card(TWO, HEART),
				new Card(KING, SPADE), new Card(QUEEN, SPADE), new Card(JACK, SPADE), new Card(TEN, SPADE), new Card(NINE, SPADE), new Card(EIGHT, SPADE), new Card(SEVEN, SPADE), new Card(SIX, SPADE), new Card(FIVE, SPADE), new Card(FOUR, SPADE), new Card(THREE, SPADE), new Card(TWO, SPADE));
		
		assert deck.size() == 52;
		
		return deck;
	}

	protected List<Card> unknownDeck1() {
		final List<Card> deck = Arrays.asList(
				new Card(SIX, DIAMOND), new Card(NINE, SPADE), new Card(QUEEN, HEART), new Card(FOUR, SPADE), new Card(JACK, HEART), new Card(TEN, DIAMOND), new Card(FIVE, CLUB),
				new Card(FOUR, DIAMOND), new Card(TWO, DIAMOND), new Card(FIVE, SPADE), new Card(SIX, SPADE), new Card(KING, SPADE), new Card(KING, DIAMOND),
				new Card(NINE, DIAMOND), new Card(THREE, HEART), new Card(ACE, HEART), new Card(TEN, SPADE), new Card(TEN, CLUB),
				new Card(FOUR, CLUB), new Card(FIVE, HEART), new Card(JACK, SPADE), new Card(TEN, HEART),
				new Card(TWO, SPADE), new Card(KING, HEART), new Card(SIX, HEART),
				new Card(NINE, HEART), new Card(FOUR, HEART),
				new Card(SEVEN, HEART),
				new Card(THREE, CLUB), new Card(QUEEN, DIAMOND), new Card(SEVEN, SPADE),
				new Card(EIGHT, CLUB), new Card(NINE, CLUB), new Card(EIGHT, DIAMOND),
				new Card(SEVEN, CLUB), new Card(ACE, DIAMOND), new Card(EIGHT, SPADE),
				new Card(JACK, CLUB), new Card(THREE, SPADE), new Card(ACE, CLUB),
				new Card(KING, CLUB), new Card(ACE, SPADE), new Card(THREE, DIAMOND),
				new Card(EIGHT, HEART), new Card(TWO, HEART), new Card(TWO, CLUB),
				new Card(SIX, CLUB), new Card(QUEEN, SPADE), new Card(FIVE, DIAMOND),
				new Card(QUEEN, CLUB), new Card(SEVEN, DIAMOND), new Card(JACK, DIAMOND));
		
		assert deck.size() == 52;
		
		return deck;
	}

	/**
	 * Returns whether the given card can be added to the given pile.
	 * This checks whether the top face-up card on the pile is one higher and of
	 * the opposite color to the provided card.
	 * 
	 * @param card the card to potentially put on the pile
	 * @param pile the pile of cards that is to receive the card
	 * @return whether this is a legal move
	 */
	protected boolean canAddToPile(final Card card, final Pile pile) {
		return canAddToPile(Collections.singletonList(card), pile);
	}

	/**
	 * Returns whether the given stack of cards can be added to the given pile.
	 * This checks whether the top face-up card on the pile is one higher and of
	 * the opposite color to the first card in the provided stack of cards.
	 * 
	 * @param cards the stack of cards to potentially put on the pile
	 * @param pile the pile of cards that is to receive the stack
	 * @return whether this is a legal move
	 */
	protected boolean canAddToPile(final List<Card> cards, final Pile pile) {
		final Card cardToAdd = cards.get(0);
		if ( !pile.hasFaceUpCards()) {
			return cardToAdd.getValue() == Value.KING;
		}
		
		final Card topCard = pile.getTopCard();
		
		if (topCard.getSuit().getColor() != cardToAdd.getSuit().getColor()
				&& topCard.getValue().getValue() == 1 + cardToAdd.getValue().getValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the top card in the goal for the given suit.
	 * 
	 * @param suit the suit to check
	 * @param goal the goal
	 * @return the top card from the goal for {@code suit}
	 */
	protected Card getTopOfGoal(final Suit suit, final Map<Suit, List<Card>> goal) {
		final List<Card> goalForSuit = goal.get(suit);
		
		final Card topOfGoal = goalForSuit.get(goalForSuit.size() - 1);
		
		return topOfGoal;
	}

	/**
	 * Returns whether the given card can be added to the goal.
	 * 
	 * @param card the card to add to the goal
	 * @param goal the goal
	 * @return whether this is a legal move
	 */
	protected boolean canAddToGoal(final Card card, final Map<Suit, List<Card>> goal) {
		final Suit suit = card.getSuit();
		final List<Card> goalForSuit = goal.get(suit);
		
		if (goalForSuit.isEmpty()) {
			return card.getValue() == Value.ACE;
		}
		
		final Card topOfGoal = goalForSuit.get(goalForSuit.size() - 1);
		
		if (card.getValue().getValue() == 1 + topOfGoal.getValue().getValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns whether the top card of the given suit in the goal can be stacked
	 * on top of the given card.
	 * 
	 * @param suit the suit in the goal from which to draw
	 * @param goal the goal
	 * @param card the card to stack on
	 * @return whether this is a legal move
	 */
	protected boolean canTakeFromGoal(final Suit suit, final Map<Suit, List<Card>> goal, final Card card) {
		if (card.getSuit().getColor() == suit.getColor()) {
			return false;
		}
		
		final List<Card> goalForSuit = goal.get(suit);
		
		if (goalForSuit.isEmpty()) {
			return false;
		}
		
		final Card topOfGoal = goalForSuit.get(goalForSuit.size() - 1);
		
		if (card.getValue().getValue() - 1 == topOfGoal.getValue().getValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns whether the given board is a game of solitaire that has been won.
	 * 
	 * @param currentBoard the board to check
	 * @return {@code true} if all the cards in the given board have been moved
	 *         to the goal
	 */
	@Override
	public boolean isWin(final Board currentBoard) {
		final Map<Suit, List<Card>> goal = currentBoard.getGoal();
		for (final Suit suit : Suit.values()) {
			final List<Card> goalForSuit = goal.get(suit);
			if (goalForSuit.size() != Value.values().length) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns all the legal moves for the provided solitaire game.  The
	 * returned collection of moves will contain redundant possibilities if
	 * {@link #isReturnRedundantMoves()} is set to {@code true}.  Note that
	 * redundant moves will not be identical, but some set of moves may result
	 * in virtually equivalent board states.
	 * 
	 * @param state the game state to search for legal moves
	 * @return a collection of legal moves for the given game
	 */
	@Override
	public Collection<SolitaireMove> findAllMoves(
			final GameState<SolitaireMove, Board> state) {
		return findAllMoves(state.getBoards().get(0));
	}

	/**
	 * Returns all the legal moves for the provided solitaire board.  The
	 * returned collection of moves will contain redundant possibilities if
	 * {@link #isReturnRedundantMoves()} is set to {@code true}.  Note that
	 * redundant moves will not be identical, but some set of moves may result
	 * in virtually equivalent board states.
	 * 
	 * @param currentBoard the board to search for legal moves
	 * @return a collection of legal moves for the given board
	 */
	public Collection<SolitaireMove> findAllMoves(final Board currentBoard) {
		final Collection<SolitaireMove> moves = new ArrayList<>();
		
		final Map<Suit, List<Card>> goal = currentBoard.getGoal();
		int minGoalStack = 0;
		for (final List<Card> goalForSuit : goal.values()) {
			minGoalStack = Math.max(minGoalStack, goalForSuit.size());
		}
		final List<Pile> piles = currentBoard.getPiles();
		/*
		 * Check whether we can draw a card.
		 */
		final int drawIndex = currentBoard.getDrawIndex();
		if (drawIndex > 0) {
			final Card card = currentBoard.getDrawCard();
			// Can we draw it directly to the goal?
			if (canAddToGoal(card, goal)) {
				moves.add(new DrawToGoalMove(drawIndex, card));
			}
			/*
			 * If all the goal stacks are up to 4, then there is no reason to
			 * keep any card 6 or lower on the board.
			 */
			if (returnRedundantMoves || card.getValue().getValue() > minGoalStack + 2) {
				// Can we draw it to one of the piles?
				final boolean isKing = card.getValue() == Value.KING;
				for (int i = 0; i < piles.size(); i++) {
					if (canAddToPile(card, piles.get(i))) {
						moves.add(new DrawToPileMove(drawIndex, i, card));
						if (isKing && !returnRedundantMoves) {
							// If it is a King, we should only create one move.
							break;
						}
					}
				}
			}
		}
		for (int i = 0; i < piles.size(); i++) {
			final Pile sourcePile = piles.get(i);
			if ( !sourcePile.hasFaceUpCards()) {
				assert !sourcePile.hasFaceDownCards();
				continue;
			}
			final List<Card> sourceFaceUpCards = sourcePile.getFaceUpCards();
			final int size = sourceFaceUpCards.size();
			final Card sourceTopCard = sourceFaceUpCards.get(size - 1);
			
			/*
			 * Check whether we can move a card from a pile to the goal.
			 */
			if (canAddToGoal(sourceTopCard, goal)) {
				moves.add(new PileToGoalMove(i, sourceTopCard));
			}
			
			/*
			 * Check whether we can move a card from the goal to a pile.
			 */
			for (final Suit suit : Suit.values()) {
				if (canTakeFromGoal(suit, goal, sourceTopCard)) {
					moves.add(new GoalToPileMove(i, getTopOfGoal(suit, goal)));
				}
			}
			
			final Card sourceBottomCard = sourceFaceUpCards.get(0);
			final int min = sourceTopCard.getValue().getValue();
			final int max = sourceBottomCard.getValue().getValue();
			
			assert min + size - 1 == max;
			
			/*
			 * If all the goal stacks are up to 4, then there is no reason to
			 * keep any card 6 or lower on the board.
			 */
			if (min <= minGoalStack + 2 && !returnRedundantMoves) {
				continue;
			}
			
			/*
			 * Check whether we can move cards from one pile to another pile.
			 */
			for (int j = 0; j < piles.size(); j++) {
				if (i == j) {
					continue;
				}
				
				// check if we can move cards from stack i to stack j
				final Pile destinationPile = piles.get(j);
				if ( !destinationPile.hasFaceUpCards()) {
					// can only move a King here
					assert !destinationPile.hasFaceDownCards();
					
					if ( !sourcePile.hasFaceDownCards() && !returnRedundantMoves) {
						// No reason to move a King when it is not on top of another card.
						continue;
					}
					
					if (sourceBottomCard.getValue() == Value.KING) {
						moves.add(new StackMove(i, j, size, sourceFaceUpCards));
					}
				} else {
					// destination has cards already, can only move source card(s) that are lower value and of the right color
					final Card destinationTopCard = destinationPile.getTopCard();
					final Value destinationValue = destinationTopCard.getValue();
					final int targetValue = destinationValue.getValue() - 1;
					
					if (min <= targetValue && max >= targetValue) {
						final int numCards = targetValue - min + 1;
						
						final List<Card> stackToMove = sourcePile.getTopCards(numCards);
						
						final Suit destinationSuit = destinationTopCard.getSuit();
						final Suit suitToMove = stackToMove.get(0).getSuit();
						if (destinationSuit.getColor() != suitToMove.getColor()) {
							moves.add(new StackMove(i, j, numCards, stackToMove));
						}
					}
				}
			}
		}
		/*
		 * Try to flip through the draw pile.
		 */
		if (currentBoard.canFlipMoreDrawPileCards()) {
			moves.add(new AdvanceDrawPileMove(drawIndex, drawAdvance));
		} else {
			if (currentBoard.canResetDrawPile()) {
				moves.add(new ResetDrawPileMove(drawIndex));
			}
		}
		return moves;
	}

	@Override
	public GameState<SolitaireMove, Board> pruneGameState(final GameState<SolitaireMove, Board> state) {
		final SolitaireMove possibleMove = state.getMoves().getFirst();
		final LinkedNode<SolitaireMove> pastMoves = state.getMoves().getNext();
		final Board possibleBoard = state.getBoards().getFirst();
		final LinkedNode<Board> pastBoards = state.getBoards().getNext();
		
		if (searchTreeHasCycle(pastBoards, possibleBoard)) {
			// cycle in the tree
			cyclesDetected.incrementAndGet();
			return null;
		}
		final SolitaireMove currentMove = pastMoves.getFirst();
		/*
		 * If two consecutive moves are moving the same stack of
		 * cards, they are redundant and the second may be pruned.
		 */
		if (currentMove.hasCards() && possibleMove.hasCards()
				&& currentMove.getCards().equals(possibleMove.getCards())) {
			samePileMovedTwicePrune.incrementAndGet();
			return null;
		}
		/*
		 * Attempt to prune the search space by only allowing moves
		 * that interact with the draw pile if the previous move
		 * changed the draw index.
		 */
		if (shouldPruneDueToStackMoveAfterDrawAdvance(currentMove, possibleMove)) {
			pileMoveAfterDrawAdvancePrune.incrementAndGet();
			return null;
		}
		/*
		 * An optimization that prunes repeated "advance 3" moves
		 * and replaces them with a single coalesced "advance N" move.
		 * This does not prune the search space, but it does make
		 * checking for cycles faster.
		 */
		if (possibleMove instanceof AdvanceDrawPileMove && currentMove instanceof AdvanceDrawPileMove) {
			final AdvanceDrawPileMove currentAdvance = (AdvanceDrawPileMove) currentMove;
			final AdvanceDrawPileMove possibleAdvance = (AdvanceDrawPileMove) possibleMove;
			final SolitaireMove coalescedMove = currentAdvance.coalesce(possibleAdvance);
			final LinkedNode<SolitaireMove> newMovesList = new LinkedNode<SolitaireMove>(coalescedMove, pastMoves.getNext());
			final LinkedNode<Board> newBoardsList = new LinkedNode<Board>(possibleBoard, pastBoards.getNext());
			drawAdvancesCoalesced.incrementAndGet();
			return new GameState<>(newMovesList, newBoardsList);
		}
		
		return state;
	}

	private boolean searchTreeHasCycle(final LinkedNode<Board> pastBoards, final Board possibleBoard) {
		return pastBoards.contains(possibleBoard);
	}

	private boolean shouldPruneDueToStackMoveAfterDrawAdvance(final SolitaireMove currentMove, final SolitaireMove possibleMove) {
		if (currentMove instanceof AdvanceDrawPileMove || currentMove instanceof ResetDrawPileMove) {
			if (possibleMove instanceof StackMove || possibleMove instanceof PileToGoalMove) {
				return true;
			}
		}
		return false;
	}

}
