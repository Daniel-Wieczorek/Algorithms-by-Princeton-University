import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final int gridSideSize;
    private final int trials;
    private final double[] thresholds;

    /**
     * Performs trials independent experiments on an n-by-n grid.
     *
     * @param n      the size of the grid (n-by-n)
     * @param trials the number of independent experiments
     * @throws IllegalArgumentException if n <= 0 or trials <= 0
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size (n) and number of trials must be greater than zero.");
        }

        this.gridSideSize = n;
        this.trials = trials;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(gridSideSize);
            int openCount = 0;

            while (!percolation.percolates()) {
                openRandomNode(percolation);
                openCount++;
            }
            thresholds[i] = (double) openCount / (gridSideSize * gridSideSize);
        }
    }

    /**
     * Opens a random site in the grid that is not already open.
     *
     * @param percolation the Percolation instance
     */
    private void openRandomNode(Percolation percolation) {
        int row, col;

        do {
            row = StdRandom.uniformInt(1, gridSideSize + 1); // Rows are 1-indexed
            col = StdRandom.uniformInt(1, gridSideSize + 1); // Columns are 1-indexed
        } while (percolation.isOpen(row, col));

        percolation.open(row, col);
    }

    /**
     * Calculates the sample mean of percolation thresholds.
     *
     * @return the mean of percolation thresholds
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * Calculates the sample standard deviation of percolation thresholds.
     *
     * @return the standard deviation of percolation thresholds
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /**
     * Calculates the lower bound of the 95% confidence interval.
     *
     * @return the lower bound of the confidence interval
     */
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    /**
     * Calculates the upper bound of the 95% confidence interval.
     *
     * @return the upper bound of the confidence interval
     */
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    /**
     * Entry point of the program.
     *
     * @param args command-line arguments: grid size and number of trials
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: java PercolationStats <grid size> <number of trials>");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.printf("mean:\t\t\t\t = %.16f%n", percolationStats.mean());
        System.out.printf("stddev:\t\t\t\t = %.16f%n", percolationStats.stddev());
        System.out.printf("95%% confidence interval:\t = [%.16f, %.16f]%n",
                percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}
