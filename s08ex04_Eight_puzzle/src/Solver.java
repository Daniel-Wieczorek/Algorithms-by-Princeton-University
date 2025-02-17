import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Stack<Board> solution;
    private int minMoves;
    private boolean isBoardSolvable;

    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private int moves; // our g(n), number of moves made to reach the board
        private SearchNode previous;
        private int priority; // total priority f(n) = g(n) + h(n);

        SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Invalid argument");
        }

        isBoardSolvable = false;
        minMoves = -1;
        solution = null;

        MinPQ<SearchNode> initialPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        initialPQ.insert(new SearchNode(initial, null, 0));
        twinPQ.insert(new SearchNode(initial.twin(), null, 0));

        while (true) {
            SearchNode nodeSolution = nextQueueIteration(initialPQ);
            if (nodeSolution != null) {
                createSolution(nodeSolution);
                return; // Solution found
            }

            SearchNode nodeTwin = nextQueueIteration(twinPQ);
            if (nodeTwin != null) {
                return; // Twin reached goal, so initial is unsolvable
            }
        }

    }

    private SearchNode nextQueueIteration(MinPQ<SearchNode> nodeQueue) {
        if (nodeQueue.isEmpty()) {
            return null;
        }

        SearchNode node = nodeQueue.delMin();

        if (node.board.isGoal()) {
            return node;
        }

        for (Board neighbor : node.board.neighbors()) {
            if (node.previous != null && neighbor.equals(node.previous.board)) {
                continue;
            }
            nodeQueue.insert(new SearchNode(neighbor, node, node.moves + 1));
        }

        return null;
    }

    // create solution as stack of boards
    private void createSolution(SearchNode goalNode) {
        // set private var on solution found, called from one place only!
        isBoardSolvable = true;
        minMoves = goalNode.moves;

        // create solution
        solution = new Stack<>();
        SearchNode goal = goalNode;
        while (goal != null) {
            solution.push(goal.board);
            goal = goal.previous;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isBoardSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? minMoves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable() ? solution : null;
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles = { { 1, 5, 3 }, { 8, 4, 2 }, { 7, 0, 6 } };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}