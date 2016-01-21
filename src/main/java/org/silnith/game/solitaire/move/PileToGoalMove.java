package org.silnith.game.solitaire.move;

import java.util.Collections;
import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;

public class PileToGoalMove implements SolitaireMove {

	private final int sourcePile;

	private final Card card;

	public PileToGoalMove(final int sourcePile, final Card card) {
		super();
		this.sourcePile = sourcePile;
		this.card = card;
	}

	public int getSourcePile() {
		return sourcePile;
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
	public Board apply(Board board) {
		return board.moveCardToGoal(sourcePile);
	}

	@Override
	public int hashCode() {
		return Integer.rotateLeft(sourcePile, 16) ^ card.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof PileToGoalMove) {
			final PileToGoalMove move = (PileToGoalMove) obj;
			return sourcePile == move.sourcePile && card.equals(move.card);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Move " + card + " from pile " + sourcePile + " to goal.";
	}

}
