import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    @Test
    public void testSolverInitialization() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        assertNotNull(solver);
    }

    @Test
    public void testIsSolvable() {
        int[][] solvableTiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board solvableBoard = new Board(solvableTiles);
        Solver solvableSolver = new Solver(solvableBoard);
        assertTrue(solvableSolver.isSolvable());

        int[][] unsolvableTiles = {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
        };
        Board unsolvableBoard = new Board(unsolvableTiles);
        Solver unsolvableSolver = new Solver(unsolvableBoard);
        assertFalse(unsolvableSolver.isSolvable());
    }

    @Test
    public void testMoves() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        assertEquals(0, solver.moves());

        int[][] tiles2 = {
            {1, 2, 3},
            {4, 5, 6},
            {0, 7, 8}
        };
        Board initial2 = new Board(tiles2);
        Solver solver2 = new Solver(initial2);
        assertEquals(2, solver2.moves());
    }

    @Test
    public void testSolution() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        Iterable<Board> solution = solver.solution();
        assertNotNull(solution);
        int count = 0;
        for (Board board : solution) {
            count++;
        }
        assertEquals(1, count);
    }

    @Test
    public void testUnsolvableSolution() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        assertNull(solver.solution());
    }
}