package org.silnith.game.solitaire.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.silnith.deck.Card;
import org.silnith.deck.Suit;
import org.silnith.deck.Value;
import org.silnith.game.solitaire.BoardValidator;
import org.silnith.game.solitaire.GoalValidator;
import org.silnith.game.solitaire.PileValidator;
import org.silnith.game.solitaire.Solitaire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearcherConfiguration {
    
    @Bean
    public GoalValidator goalValidator() {
        return new GoalValidator();
    }
    
    @Bean
    public PileValidator pileValidator() {
        return new PileValidator();
    }
    
    @Bean
    public BoardValidator boardValidator() {
        return new BoardValidator(7, 52, pileValidator(), goalValidator());
    }
    
    @Bean
    public Solitaire game() {
        final Solitaire solitaire = new Solitaire(7, boardValidator());
        solitaire.setReturnRedundantMoves(false);
        return solitaire;
    }
    
    @Bean
    public List<Card> deck() {
        final List<Card> deck = new ArrayList<>(52);
        for (final Value value : Value.values()) {
            for (final Suit suit : Suit.values()) {
                deck.add(new Card(value, suit));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }
    
}
