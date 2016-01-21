package org.silnith.game.solitaire.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.silnith.deck.Card;

public class CardPanel extends JPanel {

	public CardPanel(final Card card) {
		// if switch layout manager, specify in call to super()
		super(true);
		final JLabel valuePanel = new JLabel(card.getValue().toSymbol());
		add(valuePanel);
		final JLabel suitPanel = new JLabel(card.getSuit().toSymbol());
		switch (card.getSuit().getColor()) {
		case BLACK: suitPanel.setForeground(Color.BLACK); break;
		case RED: suitPanel.setForeground(Color.RED); break;
		}
		add(suitPanel);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		setBackground(Color.WHITE);
	}

	public static void main(final String[] args) {
		;
	}

}
