package org.silnith.game.solitaire.move;

import java.util.Collections;
import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;

public class DrawToGoalMove implements SolitaireMove {

	private final int sourceIndex;

	private final Card card;

	public DrawToGoalMove(final int sourceIndex, final Card card) {
		super();
		this.sourceIndex = sourceIndex;
		this.card = card;
	}

	public int getSourceIndex() {
		return sourceIndex;
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
		return board.drawCardToGoal();
	}

	@Override
	public int hashCode() {
		return sourceIndex ^ card.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof DrawToGoalMove) {
			final DrawToGoalMove move = (DrawToGoalMove) obj;
			return sourceIndex == move.sourceIndex && card.equals(move.card);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Move " + card + " from draw pile " + sourceIndex + " to goal.";
	}

}
