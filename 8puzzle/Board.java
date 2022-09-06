import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {

    private final int num;
    private final char[] board;
    private Board twin;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        num = tiles.length;
        board = new char[num*num];
        twin = null;

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                board[i*num+j] = (char) tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(num + "\n");
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                s.append(String.format("%2d ", (int) board[i*num + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return num;
    }

    private int actualBoardRow(int currIdx) {
        return currIdx / num;
    }

    private int actualBoardCol(int currIdx) {
        return currIdx % num;
    }

    private int solBoardRow(int currNum) {
        if (currNum == 0) {
            return num-1;
        }
        return (currNum-1)/num;
    }

    private int solBoardCol(int currNum) {
        if (currNum == 0) {
            return num-1;
        }
        return (currNum-1) % num;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDist = 0;

        for (int i = 0; i < num*num; i++) {
            if (board[i] != i+1 && board[i] != 0) {
                hammingDist++;
            }
        }

        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDist = 0;
        int vertDist;
        int horzDist;

        for (int i = 0; i < num*num; i++) {

            if (board[i] != i+1 && board[i] != 0) {
                vertDist = Math.abs(solBoardRow(board[i]) - actualBoardRow(i));
                horzDist = Math.abs(solBoardCol(board[i]) - actualBoardCol(i));

                manhattanDist += vertDist;
                manhattanDist += horzDist;
            }
        }

        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        boolean result = false;
        for (int i = 0; i < num*num-1; i++) {
            if (board[i] == i+1) {
                result = true;
            }
            else {
                return false;
            }
        }

        return result;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (!Arrays.equals(this.board, that.board))  {
            return false;
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();
        int zeroIdx = 0;

        for (int i = 0; i < num*num; i++) {
            if (board[i] == 0) {
                zeroIdx = i;
                break;
            }
        }

        int zeroRow = actualBoardRow(zeroIdx);
        int zeroCol = actualBoardCol(zeroIdx);

        if (zeroRow != 0) {
            stack.push(getTopNeighbor(board, zeroRow, zeroCol));
        }
        if (zeroRow != num-1) {
            stack.push(getBottomNeighbor(board, zeroRow, zeroCol));
        }
        if (zeroCol != 0) {
            stack.push(getLeftNeighbor(board, zeroRow, zeroCol));
        }
        if (zeroCol != num-1) {
            stack.push(getRightNeighbor(board, zeroRow, zeroCol));
        }

        return stack;
    }

    private Board createBoard(char[] thisBoard) {
        int[][] newTiles = new int[num][num];

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                newTiles[i][j] = thisBoard[i*num+j];
            }
        }

        Board newBoard = new Board(newTiles);
        return newBoard;
    }

    private Board getTopNeighbor(char[] thisBoard, int zeroRow, int zeroCol) {
            char[] copyBoard = thisBoard.clone();

            // Swap 0 with top neighbor
            int currIdx = getIndex(zeroRow, zeroCol);
            int topIdx = getIndex(zeroRow-1, zeroCol);

            int temp = copyBoard[currIdx];
            copyBoard[currIdx] = copyBoard[topIdx];
            copyBoard[topIdx] = (char) temp;

            // Create new Board with char[]
            Board topBoard = createBoard(copyBoard);

            // Return new Board
            return topBoard;

    }

    private Board getBottomNeighbor(char[] thisBoard, int zeroRow, int zeroCol) {
            char[] copyBoard = thisBoard.clone();

            // Swap 0 with bottom neighbor
            int currIdx = getIndex(zeroRow, zeroCol);
            int bottomIdx = getIndex(zeroRow+1, zeroCol);

            int temp = copyBoard[currIdx];
            copyBoard[currIdx] = copyBoard[bottomIdx];
            copyBoard[bottomIdx] = (char) temp;

            // Create new Board with char[]
            Board bottomBoard = createBoard(copyBoard);

            // Return new Board
            return bottomBoard;
    }

    private Board getLeftNeighbor(char[] thisBoard, int zeroRow, int zeroCol) {
            char[] copyBoard = thisBoard.clone();

            // Swap 0 with left neighbor
            int currIdx = getIndex(zeroRow, zeroCol);
            int leftIdx = getIndex(zeroRow, zeroCol-1);

            int temp = copyBoard[currIdx];
            copyBoard[currIdx] = copyBoard[leftIdx];
            copyBoard[leftIdx] = (char) temp;

            // Create new Board with char[]
            Board leftBoard = createBoard(copyBoard);

            // Return new Board
            return leftBoard;
    }

    private Board getRightNeighbor(char[] thisBoard, int zeroRow, int zeroCol) {
            char[] copyBoard = thisBoard.clone();

            // Swap 0 with right neighbor
            int currIdx = getIndex(zeroRow, zeroCol);
            int rightIdx = getIndex(zeroRow, zeroCol+1);

            int temp = copyBoard[currIdx];
            copyBoard[currIdx] = copyBoard[rightIdx];
            copyBoard[rightIdx] = (char) temp;

            // Create new Board with char[]
            Board rightBoard = createBoard(copyBoard);

            // Return new Board
            return rightBoard;
    }

    private int getIndex(int row, int col) {
        return row*num + col;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin == null) {
            char[] copyBoard = board.clone();

            int randomIdx1 = StdRandom.uniform(num * num);
            int randomIdx2 = StdRandom.uniform(num * num);

            while (true) {
                if (copyBoard[randomIdx1] != 0
                        && copyBoard[randomIdx2] != 0
                        && randomIdx1 != randomIdx2) {
                    int temp = copyBoard[randomIdx1];
                    copyBoard[randomIdx1] = copyBoard[randomIdx2];
                    copyBoard[randomIdx2] = (char) temp;
                    break;
                }
                else {
                    randomIdx1 = StdRandom.uniform(num * num);
                    randomIdx2 = StdRandom.uniform(num * num);
                }
            }

            twin = createBoard(copyBoard);
            return twin;
        }
        else {
            return twin;
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);
        System.out.println(initial);

        System.out.println("Hamming: " + initial.hamming());
        System.out.println("Manhattan: " + initial.manhattan());
    }
}
