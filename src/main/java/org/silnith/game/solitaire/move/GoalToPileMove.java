package org.silnith.game.solitaire.move;

import java.util.Collections;
import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;

public class GoalToPileMove implements SolitaireMove {

	private final int destinationPile;

	private final Card card;

	public GoalToPileMove(final int destinationPile, final Card card) {
		super();
		this.destinationPile = destinationPile;
		this.card = card;
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
		return board.moveCardFromGoal(card.getSuit(), destinationPile);
	}

	@Override
	public int hashCode() {
		return Integer.rotateLeft(destinationPile, 8) ^ card.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof GoalToPileMove) {
			final GoalToPileMove move = (GoalToPileMove) obj;
			return destinationPile == move.destinationPile && card.equals(move.card);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Move " + card + " from goal to pile " + destinationPile + ".";
	}

}
