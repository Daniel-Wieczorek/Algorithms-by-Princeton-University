import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int dimension;
    private int[][] tilesArray;
    private int zeroRow;
    private int zeroCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        if(tiles == null || tiles.length == 0 || !FindEmptySpace(tiles))
        {
            throw new IllegalArgumentException("Invalid Input - invalid array received!");
        }
        this.dimension = tiles.length;
        this.tilesArray = this.copy2dArray(tiles);
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder outString = new StringBuilder();
        outString.append(dimension).append("\n");
        for (int[] row : tilesArray) {
            for (int i : row) {
                outString.append(String.format("%2d", i)).append(" ");
            }
            outString.append("\n");
        }

        return outString.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingCnt = 0;
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                final int expected = row * dimension + col + 1;
                if (tilesArray[row][col] != 0 && tilesArray[row][col] != expected) {
                    hammingCnt++;
                }
            }
        }

        return hammingCnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanCnt = 0;
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                int currentVal = tilesArray[row][col];
                if (currentVal != 0) {
                    int targetRow = (currentVal - 1) / dimension;
                    int targetCol = (currentVal - 1) % dimension;
                    manhattanCnt += Math.abs(row - targetRow) + Math.abs(col - targetCol);
                }
            }
        }

        return manhattanCnt;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null || (y.getClass() != this.getClass())) {
            return false;
        }

        Board cmp = (Board) y;
        return Arrays.deepEquals(this.tilesArray, cmp.tilesArray);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] possible_moves = {
                // row, col
                { 1, 0 },
                { -1, 0 },
                { 0, 1 },
                { 0, -1 }
        };

        for (int[] move : possible_moves) {
            // Calculate new positions
            int newRow = zeroRow + move[0];
            int newCol = zeroCol + move[1];

            if (isPositionValid(newRow, newCol)) {
                int[][] newTiles = copy2dArray(tilesArray);
                newTiles[zeroRow][zeroCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = copy2dArray(tilesArray);
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension - 1; col++) {
                if (newTiles[row][col] != 0 && newTiles[row][col + 1] != 0) {
                    int tmp = newTiles[row][col];
                    newTiles[row][col] = newTiles[row][col + 1];
                    newTiles[row][col + 1] = tmp;
                    return new Board(newTiles);
                }
            }
        }
        return null;
    }

    private boolean FindEmptySpace(int[][] tiles) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] == 0) {
                    zeroRow = row;
                    zeroCol = col;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPositionValid(int row, int col) {
        return (row >= 0 && row < dimension && col >= 0 && col < dimension);
    }

    private int[][] copy2dArray(final int[][] input2dArray) {
        int[][] copyOfArray = new int[input2dArray.length][];
        for (int i = 0; i < input2dArray.length; i++) {
            copyOfArray[i] = Arrays.copyOf(input2dArray[i], input2dArray[i].length);
        }
        return copyOfArray;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        System.out.println("Board:");
        System.out.println(board);
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Goal: " + board.isGoal());
        System.out.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }
        System.out.println("Twin Board:");
        System.out.println(board.twin());
    }
}