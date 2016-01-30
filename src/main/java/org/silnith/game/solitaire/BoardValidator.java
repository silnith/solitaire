package org.silnith.game.solitaire;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.silnith.deck.Card;
import org.silnith.deck.Suit;
import org.silnith.game.Validator;


public class BoardValidator implements Validator<Board> {
    
    private final int numberOfPiles;
    
    private final int deckSize;
    
    private final PileValidator pileValidator;
    
    private final GoalValidator goalValidator;
    
    @Inject
    public BoardValidator(final int numberOfPiles, final int deckSize, final PileValidator pileValidator,
            final GoalValidator goalValidator) {
        super();
        this.numberOfPiles = numberOfPiles;
        this.deckSize = deckSize;
        this.pileValidator = pileValidator;
        this.goalValidator = goalValidator;
    }
    
    @Override
    public void validate(final Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        final Map<Suit, List<Card>> goal = board.getGoal();
        goalValidator.validate(goal);
        final List<Pile> piles = board.getPiles();
        if (piles == null) {
            throw new IllegalArgumentException("Board has no list of piles.");
        }
        if (piles.size() != numberOfPiles) {
            throw new IllegalArgumentException(
                    "Board must have " + numberOfPiles + " piles, instead has " + piles.size());
        }
        for (final Pile pile : piles) {
            pileValidator.validate(pile);
        }
        final List<Card> drawPile = board.getDrawPile();
        if (drawPile == null) {
            throw new IllegalArgumentException("Board draw pile cannot be null.");
        }
        final int drawIndex = board.getDrawIndex();
        if (drawIndex < 0) {
            throw new IllegalArgumentException("Draw index must be non-negative, is " + drawIndex);
        }
        if (drawIndex > drawPile.size()) {
            throw new IllegalArgumentException("Draw index must not be larger than the draw pile size.  Draw pile has "
                    + drawPile.size() + " cards, draw index is " + drawIndex);
        }
        // count the cards
        int numCards = drawPile.size();
        for (final Suit suit : Suit.values()) {
            numCards += goal.get(suit).size();
        }
        for (final Pile pile : piles) {
            numCards += pile.getNumberOfFaceDownCards();
            numCards += pile.getNumberOfFaceUpCards();
        }
        if (numCards != deckSize) {
            throw new IllegalArgumentException(
                    "Must have " + deckSize + " cards on the board, instead has " + numCards + ".");
        }
    }
    
}
