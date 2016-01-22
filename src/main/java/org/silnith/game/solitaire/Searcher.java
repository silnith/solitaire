package org.silnith.game.solitaire;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.silnith.deck.Card;
import org.silnith.game.GameState;
import org.silnith.game.solitaire.config.SearcherConfiguration;
import org.silnith.game.solitaire.move.DealMove;
import org.silnith.game.solitaire.move.SolitaireMove;
import org.silnith.util.LinkedNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * A stateful search engine used to expand and traverse the game state tree for
 * a particular game starting position.
 *
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class Searcher implements Runnable {
    
    private final Solitaire game;
    
    private final GameState<SolitaireMove, Board> startNode;
    
//    private final BlockingDeque<GameState<Board>> pendingNodes;
    private final Deque<GameState<SolitaireMove, Board>> pendingNodes;
    
    private final AtomicInteger maxDepthSearched;
    
    private final AtomicLong nodesSearched;
    
    private final Collection<GameState<SolitaireMove, Board>> solutions;
    
    @Inject
    public Searcher(final Solitaire game,
            final GameState<SolitaireMove, Board> startNode) {
        super();
        this.startNode = startNode;
        this.game = game;
//        this.pendingNodes = new LinkedBlockingDeque<>();
        this.pendingNodes = new ConcurrentLinkedDeque<>();
        this.maxDepthSearched = new AtomicInteger();
        this.nodesSearched = new AtomicLong();
        this.solutions = Collections
                .synchronizedSet(new HashSet<GameState<SolitaireMove, Board>>());
    }
    
    public Solitaire getGame() {
        return game;
    }
    
    public GameState<SolitaireMove, Board> getStartNode() {
        return startNode;
    }
    
    public int getMaxDepthSearched() {
        return maxDepthSearched.get();
    }
    
    public long getNodesSearched() {
        return nodesSearched.get();
    }
    
    public Collection<GameState<SolitaireMove, Board>> getSolutions() {
        return Collections.unmodifiableCollection(solutions);
    }
    
    @Override
    public void run() {
        searchNode(startNode);
    }
    
    private void searchNode(final GameState<SolitaireMove, Board> node) {
        for (final GameState<SolitaireMove, Board> newNode : search(node)) {
            searchNode(newNode);
        }
    }
    
    private void setMaxDepthSearched(final int depth) {
        boolean succeeded;
        do {
            final int current = maxDepthSearched.get();
            if (current >= depth) {
                return;
            }
            succeeded = maxDepthSearched.compareAndSet(current, depth);
        } while (!succeeded);
    }
    
    public Collection<GameState<SolitaireMove, Board>> search(
            final GameState<SolitaireMove, Board> node) {
        nodesSearched.incrementAndGet();
        final LinkedNode<SolitaireMove> pastMoves = node.getMoves();
        final LinkedNode<Board> pastBoards = node.getBoards();
        final Board currentBoard = pastBoards.getFirst();
        
//        if (game.isWin(currentBoard)) {
//            solutions.add(node);
//            printSolution(node);
//            continue;
//        }
        
//        if (pastBoards.size() > MAX_DEPTH) {
//            continue;
//        }
        
        final Collection<SolitaireMove> possibleMoves = game
                .findAllMoves(currentBoard);
        final List<GameState<SolitaireMove, Board>> nextMoves = new ArrayList<>();
        for (final SolitaireMove possibleMove : possibleMoves) {
            final Board possibleBoard = possibleMove.apply(currentBoard);
            game.validate(possibleBoard);
            
            final GameState<SolitaireMove, Board> newNode = game
                    .pruneGameState(new GameState<>(node, possibleMove,
                            possibleBoard));
            
            if (game.isWin(possibleBoard)) {
                solutions.add(newNode);
                setMaxDepthSearched(pastBoards.size());
//                printSolution(newNode);
                continue;
            }
            
            nextMoves.add(newNode);
        }
        // Flip the list to preserve the LIFO behavior.
        Collections.reverse(nextMoves);
        return nextMoves;
    }
    
//    private static final int MAX_DEPTH = 54;
    
    private void putNodeInQueue(final GameState<SolitaireMove, Board> node)
            throws InterruptedException {
//        pendingNodes.putLast(node);
        pendingNodes.add(node);
    }
    
    private GameState<SolitaireMove, Board> getNodeFromQueue()
            throws InterruptedException {
//        return pendingNodes.takeLast();
        GameState<SolitaireMove, Board> node = pendingNodes.pollLast();
        while (node == null) {
            Thread.sleep(3000);
            node = pendingNodes.pollLast();
        }
        return node;
    }
    
    public Runnable getNewWorker() {
        return new Worker();
    }
    
    private class Worker implements Runnable {
        
        @Override
        public void run() {
            do {
                try {
                    final GameState<SolitaireMove, Board> node = getNodeFromQueue();
                    final Collection<GameState<SolitaireMove, Board>> newNodes = search(node);
                    for (final GameState<SolitaireMove, Board> newNode : newNodes) {
                        putNodeInQueue(newNode);
                    }
                } catch (final InterruptedException e) {
//                    e.printStackTrace();
                }
            } while (!pendingNodes.isEmpty());
        }
        
    }
    
    private static final int NUM_THREADS = 8;
    
    public static void main(final String[] args) throws InterruptedException {
        final ApplicationContext context = new AnnotationConfigApplicationContext(
                SearcherConfiguration.class);
        
        final List<Card> deck = context.getBean("deck", List.class);
        
        final Solitaire solitaire = context.getBean(Solitaire.class);
//        final List<Card> deck = solitaire.winningDeck();
//        final List<Card> deck = solitaire.unknownDeck1();
        
        final DealMove firstMove = solitaire.dealMove(deck);
        final LinkedNode<SolitaireMove> moves = new LinkedNode<SolitaireMove>(
                firstMove);
//        final Board firstBoard = solitaire.winningBoard();
        final Board firstBoard = solitaire.deal(firstMove);
        solitaire.validate(firstBoard);
        final LinkedNode boards = new LinkedNode<Board>(firstBoard);
        final GameState<SolitaireMove, Board> startingNode = new GameState<SolitaireMove, Board>(
                moves, boards);
        
        System.out.println(firstBoard);
        
        final Searcher searcher = new Searcher(solitaire, startingNode);
//        searcher.pendingNodes.putLast(startingNode);
        searcher.pendingNodes.add(startingNode);
        
//        searcher.run();
        final Collection<Thread> threads = new ArrayList<>(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            final Runnable worker = searcher.getNewWorker();
            final Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }
        
        final NumberFormat formatter = NumberFormat.getIntegerInstance();
        do {
            Thread.sleep(3000);
            System.out.println();
            System.out.println("Number of solutions found: "
                    + formatter.format(searcher.solutions.size()));
            System.out.println("Maximum tree depth searched: "
                    + formatter.format(searcher.maxDepthSearched.get()));
            System.out.println("Pending nodes to search: "
                    + formatter.format(searcher.pendingNodes.size()));
            System.out.println("Nodes searched: "
                    + formatter.format(searcher.nodesSearched.get()));
//            System.out.println("Cycles detected: " +
//                    formatter.format(solitaire.cyclesDetected.get()));
//            System.out.println("Draw advances coalesced: " +
//                    formatter.format(searcher.drawAdvancesCoalesced.get()));
//            System.out.println("Pile moves after draw advance pruned: " +
//                    formatter.format(searcher.pileMoveAfterDrawAdvancePrune.get()));
//            System.out.println("Repeated moves of the same pile pruned: " +
//                    formatter.format(searcher.samePileMovedTwicePrune.get()));
        } while (!searcher.pendingNodes.isEmpty());
        
        for (final Thread thread : threads) {
            thread.interrupt();
        }
        
        System.out.println("Number of solutions found: "
                + formatter.format(searcher.solutions.size()));
        System.out.println();
        for (final GameState<SolitaireMove, Board> node : searcher.solutions) {
            System.out.println();
            printSolution(node);
        }
    }
    
    private static void printSolution(final GameState<SolitaireMove, Board> node) {
        final LinkedNode<SolitaireMove> winningMoves = node.getMoves();
        final List<SolitaireMove> copy = new ArrayList<>(winningMoves);
        Collections.reverse(copy);
        int count = 0;
        synchronized (System.out) {
            for (final SolitaireMove move : copy) {
                System.out.print(++count);
                System.out.print(": ");
                System.out.println(move);
            }
        }
    }
    
}
