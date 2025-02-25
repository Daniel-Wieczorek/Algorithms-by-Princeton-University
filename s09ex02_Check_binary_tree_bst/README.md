## Check if a binary tree is a BST

Given a binary tree where each `Node` contains a key, determine whether it is a
binary search tree. Use extra space proportional to the height of the tree.

Hint: 
design a recursive function `isBST(Nodex,Keymin,Keymax)` that determines whether
`x` is the root of a binary search tree with all keys between `min` and `max`.

### Algorithm

Binary search tree (BST) is a node-based binary tree with following properties:
- All keys in the left subtree are smaller that the root
- All keys in the right subtree are greater that the root
- Both left and right subtrees must also be binary search trees

We can do that using `isBst()` proposed above and recursion to check every subtree of the tree:
```
function isBst(node, minKey, maxKey):
    // Empty tree is BST. Also stop condition
    if node is null:
        return true 
    
    // Boundary check
    if node.key <= minKey OR node.key >= maxKey:
        return false

    // Recursive call with updated max boundary
    left_result = isBST(node.left, minKey, node.key)

    // Recursive call with updated min boundary
    right_result = isBST(node.right, node.key, maxKey)

    return left_result && right_result;
    // 

```

### Complexity

Time complexity: O(N) as we visit each node once.
Space complexity: O(h) where h is height of the tree. Each call will be stored
on stack and max size will be tree height. 
