```java
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class implements a solution to the problem of determining the earliest 
 * time at which all members in a social network are connected based on a series 
 * of friendship logs. The problem is solved using the Union-Find (also known as 
 * Disjoint Set) data structure, with enhancements to track the largest element 
 * in each connected component. The Union-Find structure is optimized with path 
 * compression and union by size to ensure logarithmic time complexity for 
 * operations.
 * 
 * The method `findEarliestConnection()` finds the earliest timestamp at which 
 * all members are connected. It processes friendship logs in chronological order 
 * and uses the Union-Find data structure to track the connected components.
 * 
 * Key functionalities:
 * - `UnionFind` class implements the Union-Find data structure with `find()`, 
 *   `union()`, `connected()`, and `findLargest()` methods.
 * - `find()` returns the root of the set containing a given element.
 * - `findLargest()` returns the largest element in the connected component 
 *   containing the given element.
 * - The `union()` method merges two sets if they are not already connected.
 * - The `count()` method returns the number of disjoint sets.
 * - `findEarliestConnection()` processes the log entries and returns the 
 *   earliest timestamp when all members are connected.
 * 
 * This solution ensures a time complexity of O(m log n) where m is the number 
 * of logs and n is the number of members.
 */

public class SocialNetworkConnection {
    
    // Inner class implementing Union-Find data structure with size and largest element tracking.
    private class UnionFind {
        private int[] parent;  // Stores the parent of each element
        private int[] size;    // Stores the size of each set
        private int[] largest; // Stores the largest element in each connected component
        private int count;     // Number of disjoint sets
        
        /**
         * Constructor to initialize the Union-Find data structure.
         * @param n The number of elements in the set.
         */
        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            size = new int[n];
            largest = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
                largest[i] = i;
            }
        }
        
        /**
         * Finds the root of the set containing element p with path compression.
         * @param p The element to find the root for.
         * @return The root of the set containing element p.
         */
        public int find(int p) {
            validate(p);
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];  // Path compression
                p = parent[p];
            }
            return p;
        }
        
        /**
         * Finds the largest element in the connected component containing element p.
         * @param p The element to find the largest in its component.
         * @return The largest element in the connected component of element p.
         */
        public int findLargest(int p) {
            validate(p);
            return largest[find(p)];
        }
        
        /**
         * Merges the sets containing elements p and q. 
         * The set with the larger size becomes the root, and the largest element 
         * is updated accordingly.
         * @param p The first element.
         * @param q The second element.
         */
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            
            if (rootP == rootQ) return;  // They are already in the same set
            
            // Union by size: attach smaller tree to larger one
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
                largest[rootQ] = Math.max(largest[rootP], largest[rootQ]);
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
                largest[rootP] = Math.max(largest[rootP], largest[rootQ]);
            }
            count--;
        }
        
        /**
         * Checks if elements p and q are in the same set (connected).
         * @param p The first element.
         * @param q The second element.
         * @return True if p and q are connected, false otherwise.
         */
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }
        
        /**
         * Returns the number of disjoint sets.
         * @return The number of disjoint sets.
         */
        public int count() {
            return count;
        }
        
        /**
         * Validates if element p is within the valid range.
         * @param p The element to validate.
         * @throws IllegalArgumentException if p is out of range.
         */
        private void validate(int p) {
            int n = parent.length;
            if (p < 0 || p >= n) {
                throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
            }
        }
    }

    /**
     * Finds the earliest timestamp at which all members in the social network are 
     * connected, based on friendship logs.
     * @param n The number of members in the network.
     * @param logs A 2D array representing the friendship logs. Each log contains 
     *             a timestamp and two member indices forming a friendship.
     * @return The earliest timestamp at which all members are connected, or -1 
     *         if it's not possible.
     */
    public static long findEarliestConnection(int n, int[][] logs) {
        UnionFind uf = new SocialNetworkConnection().new UnionFind(n);
        
        for (int[] log : logs) {
            long timestamp = log[0];
            int member1 = log[1];
            int member2 = log[2];
            
            uf.union(member1, member2);
            
            // If all members are connected, return the current timestamp
            if (uf.count() == 1) {
                return timestamp;
            }
        }
        
        return -1; // No full connection found
    }

    // Test methods to validate the functionality of the implementation.
    
    @Test
    public void testBasicConnection() {
        int n = 4;
        int[][] logs = {
            {1, 0, 1},
            {3, 1, 2},
            {5, 2, 3}
        };
        assertEquals(5, findEarliestConnection(n, logs));
    }

    @Test
    public void testAlreadyConnected() {
        int n = 2;
        int[][] logs = {{1, 0, 1}};
        assertEquals(1, findEarliestConnection(n, logs));
    }

    @Test
    public void testNoConnection() {
        int n = 4;
        int[][] logs = {
            {1, 0, 1},
            {2, 2, 3}
        };
        assertEquals(-1, findEarliestConnection(n, logs));
    }

    @Test
    public void testLargestElement() {
        UnionFind uf = new SocialNetworkConnection().new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 4);
        uf.union(2, 3);
        
        assertEquals(4, uf.findLargest(0));
        assertEquals(4, uf.findLargest(1));
        assertEquals(3, uf.findLargest(2));
        assertEquals(3, uf.findLargest(3));
        assertEquals(4, uf.findLargest(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIndex() {
        UnionFind uf = new SocialNetworkConnection().new UnionFind(5);
        uf.find(5);
    }

    @Test
    public void testComplexNetwork() {
        int n = 6;
        int[][] logs = {
            {1, 0, 1},
            {2, 1, 2},
            {3, 3, 4},
            {4, 4, 5},
            {5, 2, 3}
        };
        assertEquals(5, findEarliestConnection(n, logs));
    }

    public static void main(String[] args) {
        // Run all tests
        org.junit.runner.JUnitCore.main("SocialNetworkConnection");
    }
}
```