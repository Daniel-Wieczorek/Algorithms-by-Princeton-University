## Diameter and center of a tree

Given a connected graph with no cycles
- Diameter: design a linear-time algorithm to find the longest simple path in
  the graph.
- Center: design a linear-time algorithm to find a vertex such that its maximum
  distance from any other vertex is minimized.

Hint (diameter): to compute the diameter, pick a vertex `s` then run BFS again
from the vertex that is furthest from `s`. 

Hint (center): consider vertices on the longest path.

### Algorithm/Solution

##### Diameter of the tree:

We need longest simple path in the graph what is longest simple path between two
nodes.

Algorithm:

1. Pick root node
2. Run DFS algorithm to find farthest node `X`
3. Stared from root `X` run DFS again to find the farthest node `Y` and record
   path.
4. The path from `X` to `Y` will be diameter of the tree

The farthest node in tree from any starting node is always one of the endpoints
of the diameter so we do not have to start from root actually and that can be
any node.

We run BFS twice so that will give us 2*O(N) complexity what is fine for this problem.

##### Center of the tree:

We need a vertex such that its maximum distance from any other vertex is minimized. 

So simples exaple to illustrate it would be something like:

```log
      1
       \
        2
         \
          3
           \
            4
             \
              5
               \
                6
```

TODO: What is middle node for even branch?

Algorithm:

1. Execute alghoritm from the previous chapter `Diameter of the tree`.
2. Find the middle node what should be between `X` and `Y`. 
   1. If the path length is odd, return the middle node.
   2. If the path length is even do ... TODO: Think about it. Should it be one before, one after or something else?

### Complexity

Time complexity:
First one: 2 * O(n)
Second one: O(n) after reduction (we have here O(n) for diameter, O(1) to find center so worst is something around O(n))