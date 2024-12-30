import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int virtualTopRow;
    private final int virtualBottomRow;
    private int openSitesCount;
    private final WeightedQuickUnionUF unionFind;
    private final int gridSize;
    private final boolean[][] siteState;

    /**
     * Creates an n-by-n grid, with all sites initially blocked.
     *
     * @param n the size of the grid
     * @throws IllegalArgumentException if n <= 0
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0.");
        }

        siteState = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n + 2); // Plus two virtual nodes
        gridSize = n;

        // Note: grid rows are numbered to n-1
        virtualTopRow = n * n;
        virtualBottomRow = n * n + 1;

        for (int col = 1; col <= n; ++col) {
            unionFind.union(to1DIndex(1, col), virtualTopRow); // Connect first row to virtual top
            unionFind.union(to1DIndex(n, col), virtualBottomRow); // Connect last row to virtual bottom
        }
    }

    /**
     * Opens the site (row, col) if it is not already open.
     *
     * @param row the row index (1-indexed)
     * @param col the column index (1-indexed)
     */
    public void open(int row, int col) {
        validateIndices(row, col);

        if (!isOpen(row, col)) {
            siteState[row - 1][col - 1] = true;
            connectNeighbors(row, col);
            ++openSitesCount;
        }
    }

    /**
     * Checks if the site (row, col) is open.
     *
     * @param row the row index (1-indexed)
     * @param col the column index (1-indexed)
     * @return true if the site is open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return siteState[row - 1][col - 1];
    }

    /**
     * Checks if the site (row, col) is full.
     *
     * @param row the row index (1-indexed)
     * @param col the column index (1-indexed)
     * @return true if the site is full, false otherwise
     */
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return isOpen(row, col) && (unionFind.find(virtualTopRow) == unionFind.find(to1DIndex(row, col)));
    }

    /**
     * Returns the number of open sites in the grid.
     *
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    /**
     * Checks if the system percolates.
     *
     * @return true if the system percolates, false otherwise
     */
    public boolean percolates() {
        return unionFind.find(virtualTopRow) == unionFind.find(virtualBottomRow);
    }

    private void connectNeighbors(int row, int col) {
        final int current = to1DIndex(row, col);

        final int topNeighbor = row - 1;
        if (isValidNeighbor(topNeighbor, col) && isOpen(topNeighbor, col)) {
            unionFind.union(current, to1DIndex(topNeighbor, col));
        }

        final int bottomNeighbor = row + 1;
        if (isValidNeighbor(bottomNeighbor, col) && isOpen(bottomNeighbor, col)) {
            unionFind.union(current, to1DIndex(bottomNeighbor, col));
        }

        final int leftNeighbor = col - 1;
        if (isValidNeighbor(row, leftNeighbor) && isOpen(row, leftNeighbor)) {
            unionFind.union(current, to1DIndex(row, leftNeighbor));
        }

        final int rightNeighbor = col + 1;
        if (isValidNeighbor(row, rightNeighbor) && isOpen(row, rightNeighbor)) {
            unionFind.union(current, to1DIndex(row, rightNeighbor));
        }
    }

    private int to1DIndex(int row, int col) {
        validateIndices(row, col);
        return (gridSize * (row - 1)) + (col - 1);
    }

    private void validateIndices(int row, int col) {
        if (!isValidNeighbor(row, col)) {
            throw new IllegalArgumentException("Row or column index out of valid range.");
        }
    }

    private boolean isValidNeighbor(int row, int col) {
        return row >= 1 && col >= 1 && row <= gridSize && col <= gridSize;
    }
}
