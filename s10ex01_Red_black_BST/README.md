## Red–black BST with no extra memory

Describe how to save the memory for storing the color information when
implementing a red–black BST. 

Hint: modify the structure of the BST to encode the color information.

### Algorithm/Solution

In general most of the implementations shall use boolean to indicate if given node
is red or black. If we want to eliminate usage of that additional memory we can use
following trick:
- black nodes: do nothing, we leave it as it is.
- red nodes: we swap left and right children nodes.

So for black nodes we have business as usual:
- left child (LCH) has smaller key
- right child (RCH) has bigger key

and for red nodes we swap these these two values and violating BST property but 
that can be undone when necessary. In this case we have to adjust key operations
for Red-black BST tree for red nodes.
- determination if node is black or red will be done by comparing one child to another
- In most of operations we have to reverse comparing for both values (red/black)

Based on:
Solution: https://stackoverflow.com/a/31633167/9904713
Here other one done in more CPP way: https://bannalia.blogspot.com/2008/11/optimizing-red-black-tree-color-bits.html

But yet I am not convinced here. ToDo: Recheck it!

### Complexity

N/A