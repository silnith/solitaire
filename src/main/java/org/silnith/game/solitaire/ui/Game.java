package org.silnith.game.solitaire.ui;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.silnith.deck.Card;
import org.silnith.deck.Suit;
import org.silnith.deck.Value;

public class Game extends JFrame {

	public Game() {
		super("Solitaire");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final Box contentPane = Box.createVerticalBox();
		setContentPane(contentPane);
		final JComponent upperPanel = Box.createHorizontalBox();
		final JComponent lowerPanel = Box.createHorizontalBox();
		contentPane.add(upperPanel);
		contentPane.add(lowerPanel);
		final JComponent drawPile = new JPanel();
		final JComponent goal = new JPanel();
		upperPanel.add(drawPile);
		upperPanel.add(Box.createHorizontalGlue());
		upperPanel.add(goal);
		for (int i = 0; i < 7; i++) {
			final JComponent pile = new JPanel();
			lowerPanel.add(pile);
			pile.add(new CardPanel(new Card(Value.values()[i + 1], Suit.HEART)));
		}
		drawPile.add(new CardPanel(new Card(Value.KING, Suit.CLUB)));
		goal.add(new CardPanel(new Card(Value.ACE, Suit.CLUB)));
		goal.add(new CardPanel(new Card(Value.ACE, Suit.DIAMOND)));
		goal.add(new CardPanel(new Card(Value.ACE, Suit.HEART)));
		goal.add(new CardPanel(new Card(Value.ACE, Suit.SPADE)));
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final Game game = new Game();
		game.pack();
		game.setLocationByPlatform(true);
		game.setVisible(true);
		
		;
	}

}
