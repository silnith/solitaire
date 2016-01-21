package org.silnith.game.solitaire.move;

import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;

public class StackMove implements SolitaireMove {

	private final int sourcePile;

	private final int destinationPile;

	private final int numberOfCards;

	private final List<Card> cards;

	public StackMove(final int sourcePile, final int destinationPile, final int numberOfCards, final List<Card> cards) {
		super();
		this.sourcePile = sourcePile;
		this.destinationPile = destinationPile;
		this.numberOfCards = numberOfCards;
		this.cards = cards;
	}

	public int getSourcePile() {
		return sourcePile;
	}

	public int getDestinationPile() {
		return destinationPile;
	}

	public int getNumberOfCards() {
		return numberOfCards;
	}

	@Override
	public boolean hasCards() {
		return true;
	}

	@Override
	public List<Card> getCards() {
		return cards;
	}

	@Override
	public Board apply(final Board board) {
		return board.moveStack(sourcePile, destinationPile, numberOfCards);
	}

	@Override
	public int hashCode() {
		return Integer.rotateLeft(sourcePile, 8) ^ Integer.rotateLeft(destinationPile, 16)
				^ Integer.rotateLeft(numberOfCards, 24) ^ cards.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof StackMove) {
			final StackMove move = (StackMove) obj;
			return sourcePile == move.sourcePile && destinationPile == move.destinationPile
					&& numberOfCards == move.numberOfCards && cards.equals(move.cards);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		if (numberOfCards == 1) {
			return "Move " + cards.get(0) + " from pile " + sourcePile + " to pile " + destinationPile + ".";
		} else {
			return "Move stack " + cards + " from pile " + sourcePile + " to pile " + destinationPile + ".";
		}
	}

}
