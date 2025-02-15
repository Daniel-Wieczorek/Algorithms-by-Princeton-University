/******************************************************************************
 *  Compilation:  javac WeightedQuickUnionPathCompressionUF.java
 *  Execution:    java WeightedQuickUnionPathCompressionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                https://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                https://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Weighted quick-union with path compression.
 *  Source: https://algs4.cs.princeton.edu/15uf/WeightedQuickUnionPathCompressionUF.java.html
 ******************************************************************************/

/**
 *  @class WeightedQuickUnionPathCompressionUF
 *  @brief Represents a union–find (disjoint-set) data structure.
 *  
 *  This class supports efficient union and find operations, along with 
 *  methods to determine if two elements are connected and retrieve the total 
 *  number of connected components.
 *  
 *  Key Features:
 *  - **Union**: Merges two components into a single component.
 *  - **Find**: Identifies the component containing a specific element.
 *  - **Path Compression**: Optimizes the structure by flattening paths during find operations.
 *  - **Weighted Union**: Ensures smaller trees are merged into larger ones, minimizing tree height.
 *  
 *  Efficiency:
 *  - Constructor: O(n), where n is the number of elements.
 *  - Union and Find: O(log n) in the worst case.
 *  - Amortized Complexity: O(m α(n)), where m is the number of operations and α(n) is the inverse Ackermann function.
 *
 *  For detailed explanations, see:
 *  - Algorithms, 4th Edition by Robert Sedgewick and Kevin Wayne
 *  - Section 1.5 on Union-Find: https://algs4.cs.princeton.edu/15uf
 *
 *  @authors
 *  - Robert Sedgewick
 *  - Kevin Wayne
 */

 public class WeightedQuickUnionPathCompressionUF {

    private int[] parent;  /**< Array where parent[i] is the parent of node i. */
    private int[] size;    /**< Array where size[i] is the size of the tree rooted at i. */
    private int count;     /**< Tracks the number of connected components. */

    /**
     * @brief Initializes a union-find data structure with n elements.
     * 
     * Each element starts in its own component, with itself as its only member.
     * 
     * @param n Number of elements (must be non-negative).
     * @throws IllegalArgumentException if n < 0.
     */
    public WeightedQuickUnionPathCompressionUF(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of elements cannot be negative.");
        }
        count = n;
        parent = new int[n];
        size = new int[n];
        // Initialize each element to be its own root, and set tree sizes to 1.
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * @brief Gets the number of connected components.
     * 
     * Initially, the number of components equals the number of elements, as each
     * element is its own component.
     * 
     * @return Number of components (between 1 and n).
     */
    public int count() {
        return count;
    }

    /**
     * @brief Finds the root of the set containing the element p.
     * 
     * The root represents the canonical element of the component. During the 
     * process, path compression is applied, flattening the structure for faster 
     * future access.
     * 
     * @param p Element whose root is to be found.
     * @return Root (canonical element) of the set containing p.
     * @throws IllegalArgumentException if p is not a valid index.
     */
    public int find(int p) {
        validate(p);
        int root = p;
        // Traverse up the parent array until the root is reached.
        while (root != parent[root]) {
            root = parent[root];
        }
        // Apply path compression: make every node on the path point directly to the root.
        while (p != root) {
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return root;
    }

    /**
     * @brief Checks if two elements are in the same set.
     * 
     * Two elements are connected if they share the same root.
     * 
     * @param p First element.
     * @param q Second element.
     * @return True if p and q are connected, false otherwise.
     * @throws IllegalArgumentException if p or q is not a valid index.
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * @brief Merges the sets containing elements p and q.
     * 
     * Uses size-weighted union to ensure smaller trees are added under larger trees,
     * keeping the structure balanced.
     * 
     * @param p First element.
     * @param q Second element.
     * @throws IllegalArgumentException if p or q is not a valid index.
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        // If p and q already share the same root, they are in the same set.
        if (rootP == rootQ) return;

        // Merge the smaller tree into the larger tree.
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;  // Reduce the component count since two sets are merged.
    }

    /**
     * @brief Validates that a given index is within range.
     * 
     * Ensures the input index is between 0 and n-1.
     * 
     * @param p Index to validate.
     * @throws IllegalArgumentException if p is out of bounds.
     */
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("Index " + p + " is not between 0 and " + (n - 1));
        }
    }

    /**
     * @brief Reads input and processes union-find operations.
     * 
     * Reads an integer n (number of elements) and a sequence of pairs of integers
     * from standard input. For each pair, performs a union operation if the 
     * elements are not already connected and prints the pair. Finally, outputs 
     * the number of remaining components.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnionPathCompressionUF uf = new WeightedQuickUnionPathCompressionUF(n);
        // Process input pairs until EOF.
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
