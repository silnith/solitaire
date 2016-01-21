package org.silnith.game.solitaire;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.silnith.deck.Card;
import org.silnith.game.GameState;
import org.silnith.game.ai.Searcher;
import org.silnith.game.solitaire.config.SearcherConfiguration;
import org.silnith.game.solitaire.move.DealMove;
import org.silnith.game.solitaire.move.SolitaireMove;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Solver {

	public static void main(final String[] args) throws InterruptedException {
		try (final AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(SearcherConfiguration.class)) {
			final List<Card> deck = context.getBean("deck", List.class);
			final Solitaire solitaire = context.getBean(Solitaire.class);
			final DealMove dealMove = solitaire.dealMove(deck);
			final Board initialBoard = solitaire.deal(dealMove);
			final GameState<SolitaireMove, Board> initialState = new GameState<SolitaireMove, Board>(dealMove, initialBoard);
			
			final Searcher<SolitaireMove, Board> searcher = new Searcher<>(solitaire, initialState);
			
			final int numThreads = 8;
			final Collection<Thread> threads = new ArrayList<>(numThreads);
			for (int i = 0; i < numThreads; i++) {
				final Runnable worker = searcher.getNewWorker();
				final Thread thread = new Thread(worker);
				threads.add(thread);
				thread.start();
			}
			
			final NumberFormat formatter = NumberFormat.getIntegerInstance();
			do {
				Thread.sleep(3000);
				System.out.println();
				System.out.println();
				System.out.println("Number of solutions found: " + formatter.format(searcher.getSolutions().size()));
				System.out.println("Maximum tree depth searched: " + formatter.format(searcher.getMaxDepthSearched()));
				System.out.println("Pending nodes to search: " + formatter.format(searcher.getPendingNodesCount()));
				System.out.println("Nodes searched: " + formatter.format(searcher.getNodesSearched()));
				System.out.println();
				
				System.out.println("Cycles detected: " + formatter.format(solitaire.getCyclesDetected()));
				System.out.println("Draw advances coalesced: " + formatter.format(solitaire.getDrawAdvancesCoalesced()));
				System.out.println("Pile moves after draw advance pruned: " + formatter.format(solitaire.getPileMoveAfterDrawAdvancesPruned()));
				System.out.println("Repeat moves of the same pile pruned: " + formatter.format(solitaire.getSamePileMovedTwicePrunes()));
			} while ( !searcher.isDone());
			
			for (final Thread thread : threads) {
				thread.interrupt();
			}
			
			System.out.println(searcher.getSolutions());
		}
	}

}
