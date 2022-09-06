import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final MinPQ<SearchNode> minPQ;
    private final MinPQ<SearchNode> twinPQ;

    private final Board initBoard;

    private class SearchNode implements Comparable<SearchNode> {

        private final int numMoves;
        private final int currPriority;
        private final Board currBoard;
        private final SearchNode prevNode;

        public SearchNode(Board board, int moves, SearchNode previousNode) {
            currBoard = board;
            numMoves = moves;
            currPriority = moves + board.manhattan();
            prevNode = previousNode;
        }

        public int compareTo(SearchNode that) {
            return this.currPriority - that.currPriority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException("Null Argument");
        }

        initBoard = initial;
        int num = initial.dimension();
        minPQ = new MinPQ<SearchNode>();
        twinPQ = new MinPQ<SearchNode>();

        SearchNode currNode;
        SearchNode currNodeTwin;

        minPQ.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        while (!minPQ.min().currBoard.isGoal() && !twinPQ.min().currBoard.isGoal()) {

            currNode = minPQ.min();
            currNodeTwin = twinPQ.min();

            minPQ.delMin();
            twinPQ.delMin();

            for (Board neighbor: currNode.currBoard.neighbors()) {
                if (currNode.numMoves == 0) {
                    minPQ.insert(new SearchNode(neighbor, currNode.numMoves + 1, currNode));

                }
                else if (!neighbor.equals(currNode.prevNode.currBoard)) {
                    minPQ.insert(new SearchNode(neighbor, currNode.numMoves + 1, currNode));
                }
            }

            for (Board neighbor: currNodeTwin.currBoard.neighbors()) {
                if (currNodeTwin.numMoves == 0) {
                    twinPQ.insert(new SearchNode(neighbor, currNodeTwin.numMoves + 1, currNodeTwin));

                }
                else if (!neighbor.equals(currNodeTwin.prevNode.currBoard)) {
                    twinPQ.insert(new SearchNode(neighbor, currNodeTwin.numMoves + 1, currNodeTwin));
                }
            }
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (minPQ.min().currBoard.isGoal()) {
            return true;
        }
        if (twinPQ.min().currBoard.isGoal()) {
            return false;
        }
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        else {
            return minPQ.min().numMoves;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> solution = new Stack<Board>();
        SearchNode currentNode = minPQ.min();

        while (currentNode.prevNode != null) {
            solution.push(currentNode.currBoard);
            currentNode = currentNode.prevNode;
        }

        solution.push(initBoard);
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
