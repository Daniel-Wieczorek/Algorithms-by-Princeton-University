import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testBoardInitialization() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        assertNotNull(board);
        assertEquals(3, board.dimension());
    }

    @Test
    public void testToString() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        String expectedString = "3\n 1  2  3 \n 4  0  5 \n 7  8  6 \n";
        assertEquals(expectedString, board.toString());
    }

    @Test
    public void testHamming() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        assertEquals(2, board.hamming());
    }

    @Test
    public void testManhattan() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        assertEquals(2, board.manhattan());
    }

    @Test
    public void testIsGoal() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        assertFalse(board.isGoal());
    }

    @Test
    public void testNeighbors() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        Iterable<Board> neighbors = board.neighbors();
        assertNotNull(neighbors);
        int count = 0;
        for (Board neighbor : neighbors) {
            count++;
        }
        assertEquals(4, count);
    }

    @Test
    public void testTwin() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board board = new Board(tiles);
        Board twin = board.twin();
        assertNotNull(twin);
        assertNotEquals(board, twin);
    }
}