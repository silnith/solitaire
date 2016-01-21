package org.silnith.game.solitaire;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.silnith.deck.Card;
import org.silnith.game.Validator;

@Singleton
public class PileValidator implements Validator<Pile> {

	@Inject
	public PileValidator() {
		super();
	}

	@Override
	public void validate(final Pile pile) {
		if (pile == null) {
			throw new IllegalArgumentException("Pile cannot be null.");
		}
		if (pile.hasFaceDownCards() && !pile.hasFaceUpCards()) {
			throw new IllegalArgumentException("Pile has face down cards but no face up cards.");
		}
		if (pile.hasFaceUpCards()) {
			validateStack(pile.getFaceUpCards());
		}
	}

	private void validateStack(final List<Card> stack) {
		assert stack != null;
		assert !stack.isEmpty();
		
		final Iterator<Card> iterator = stack.iterator();
		Card previousCard = iterator.next();
		while (iterator.hasNext()) {
			final Card currentCard = iterator.next();
			
			if (currentCard.getValue().getValue() + 1 != previousCard.getValue().getValue()) {
				throw new IllegalArgumentException("Cannot stack " + currentCard.getValue() + " on top of " + previousCard.getValue() + ".");
			}
			if (currentCard.getSuit().getColor() == previousCard.getSuit().getColor()) {
				throw new IllegalArgumentException("Cannot stack " + currentCard.getSuit() + " on top of " + previousCard.getSuit() + ".");
			}
			
			previousCard = currentCard;
		}
	}

}
