## Generalized queue

Design a generalized queue data type that supports all of the following
operations in logarithmic time (or better) in the worst case.

- Create an empty data structure.
- Append an item to the end of the queue.
- Remove an item from the front of the queue.
- Return the `i^th` item in the queue.
- Remove the `i^th` item in the queue.

Hint: create a red–black BST where the keys are integers and the values are the
items such that the  `i^th` largest integer key in the red–black BST corresponds
to the `i^th` item in the queue.

### Algorithm

Hmmm... we have to use as suggested a Red-Black Tree to supports logarithmic
time operations for insertion, deletion, and searching as data structure behind that queue.

The keys will be integers representing the position (index) of elements in the
queue. The values are the actual stored items.

Each node in the tree maintains a subtree size, so we will operate with O(logN) time.
The smallest key corresponds to the front of the queue, while the largest key corresponds to the back.

Todo: That's nice exercise to implement, do that in CppLibraries repo!

### Complexity

Expected O(logN) as Red black tree will be used here. 