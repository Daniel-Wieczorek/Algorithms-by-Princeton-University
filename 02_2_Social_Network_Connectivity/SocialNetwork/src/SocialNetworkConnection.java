import org.junit.Test;
import static org.junit.Assert.*;

public class SocialNetworkConnection {
    private class UnionFind {
        private int[] parent;
        private int[] size;
        private int[] largest;
        private int count;
        
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
        
        public int find(int p) {
            validate(p);
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        
        public int findLargest(int p) {
            validate(p);
            return largest[find(p)];
        }
        
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            
            if (rootP == rootQ) return;
            
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
        
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }
        
        public int count() {
            return count;
        }
        
        private void validate(int p) {
            int n = parent.length;
            if (p < 0 || p >= n) {
                throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
            }
        }
    }

    public static long findEarliestConnection(int n, int[][] logs) {
        UnionFind uf = new SocialNetworkConnection().new UnionFind(n);
        
        for (int[] log : logs) {
            long timestamp = log[0];
            int member1 = log[1];
            int member2 = log[2];
            
            uf.union(member1, member2);
            
            if (uf.count() == 1) {
                return timestamp;
            }
        }
        
        return -1; // No full connection found
    }


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
