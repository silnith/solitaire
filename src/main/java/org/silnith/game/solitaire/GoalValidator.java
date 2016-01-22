package org.silnith.game.solitaire;

import static org.silnith.deck.Value.ACE;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.silnith.deck.Card;
import org.silnith.deck.Suit;
import org.silnith.game.Validator;

@Singleton
public class GoalValidator implements Validator<Map<Suit, List<Card>>> {
    
    @Inject
    public GoalValidator() {
        super();
    }
    
    @Override
    public void validate(final Map<Suit, List<Card>> goal) {
        for (final Suit suit : Suit.values()) {
            final List<Card> cardsForSuit = goal.get(suit);
            if (cardsForSuit == null) {
                throw new IllegalArgumentException("No list of cards for suit "
                        + suit);
            }
            if (!cardsForSuit.isEmpty()) {
                final Iterator<Card> iterator = cardsForSuit.iterator();
                Card previousCard = iterator.next();
                validateSuit(suit, previousCard);
                if (previousCard.getValue() != ACE) {
                    throw new IllegalArgumentException(
                            "First card in goal for suit " + suit
                                    + " must be an " + ACE + ", not a "
                                    + previousCard + ".");
                }
                while (iterator.hasNext()) {
                    final Card currentCard = iterator.next();
                    
                    if (previousCard.getValue().getValue() + 1 != currentCard
                            .getValue().getValue()) {
                        throw new IllegalArgumentException("Cannot put "
                                + currentCard + " on top of " + previousCard
                                + " in goal for suit " + suit + ".");
                    }
                    
                    previousCard = currentCard;
                }
            }
        }
    }
    
    private void validateSuit(final Suit suit, final Card card) {
        if (card.getSuit() != suit) {
            throw new IllegalArgumentException("Cannot have " + card
                    + " in goal for suit " + suit + ".");
        }
    }
    
}
