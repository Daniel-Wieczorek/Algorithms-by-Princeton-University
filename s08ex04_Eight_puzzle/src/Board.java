import java.util.ArrayList;

public class Board {
    private Integer dimension;
    private int[][] tilesArray;
    private int blankRow;
    private int blankColumn;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        tilesArray = new int[tiles.length][];

        for (int i = 0; i < tiles.length; i++) {
            int[] aTiles = tiles[i];
            int aTilesLength = aTiles.length;
            tilesArray[i] = new int[aTilesLength];
            System.arraycopy(aTiles, 0, tilesArray[i], 0, aTilesLength);
        }
    }

    // string representation of this board
    public String toString() {
        System.out.println(dimension);
        for (int[] row : tilesArray) {
            for (int i : row) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    // board dimension n
    public int dimension()
    {
        return dimension;
    }

    // number of tiles out of place
    public int hamming()

    // sum of Manhattan distances between tiles and goal
    public int manhattan()

    // is this board the goal board?
    public boolean isGoal()

    // does this board equal y?
    public boolean equals(Object y)

    // all neighboring boards
    public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()

    // unit testing (not graded)
    public static void main(String[] args)

}