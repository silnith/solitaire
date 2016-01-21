package org.silnith.game.solitaire.move;

import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.solitaire.Board;

public class DealMove implements SolitaireMove {

	private final int numberOfStacks;

	private final List<Card> deck;

	public DealMove(final List<Card> deck, final int numberOfStacks) {
		super();
		this.deck = deck;
		this.numberOfStacks = numberOfStacks;
	}

	public int getNumberOfStacks() {
		return numberOfStacks;
	}

	public List<Card> getDeck() {
		return deck;
	}

	@Override
	public boolean hasCards() {
		return true;
	}

	@Override
	public List<Card> getCards() {
		return deck;
	}

	@Override
	public Board apply(final Board board) {
		return new Board(deck, numberOfStacks);
	}

	@Override
	public int hashCode() {
		return Integer.rotateLeft(numberOfStacks, 16) ^ deck.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof DealMove) {
			final DealMove move = (DealMove) obj;
			return numberOfStacks == move.numberOfStacks && deck.size() == move.deck.size()
					&& deck.equals(move.deck);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Deal " + numberOfStacks + " stacks using deck " + deck + ".";
	}

}
