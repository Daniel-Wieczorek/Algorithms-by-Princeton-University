## Nuts and bolts

A disorganized carpenter has a mixed pile of n nuts and n bolts. The goal is to
find the corresponding pairs of nuts and bolts. Each nut fits exactly one bolt
and each bolt fits exactly one nut. By fitting a nut and a bolt together, the
carpenter can see which one is bigger (but the carpenter cannot compare two nuts
or two bolts directly). Design an algorithm for the problem that uses at most
proportional to n*log(n) compares (probabilistically).

### Algorithm:

Boundary conditions:
We have two unsorted arrays with nuts and bolts
- `nuts[0...n-1]`
- `bolts[0...n-1]`
Where n is natural number. Both arrays are same size and we have 1:1 relationship
between buts and bolts so all the values are unique. 

Approach:
1. Brute force approach where we iterate over nuts and for each we iterate over bolts. 
For each bolt we compare is it fitting to nut and if it is we swap with position of nut.
Then we cont till the end of nuts. It will be O(n^2) what is too much here. We need other
solution

2. We can use QuickSort inspired approach to sort nuts and bolts so nuts[k] will fit 
bolts[k] where k is (0...n-1)

Problem is `carpenter cannot compare two nuts or two bolts directly` so we have to compare
nuts and bolts with each other to do that quick sort:

Solving alg:
1. Call MatchNutsAndBolts(nuts[], bolts[], start, end)
    1. `if(start >= end) return`. That's our base condition for recursion
    2. Choose last element from `nuts` as pivot.
    3. Using that pivot take `pivot_bolt = nuts[pivot]`:
        1. Compare each nut with `pivot_bolt`.
        2. Move smaller nuts to the left, bigger to the right.
        4. Pivot nut is now in correct position in the middle.
    4. Using that pivot nut from above
        1. Compare each bolt with `pivot_nut`.
        2. Move smaller bolts to the left, bigger to the right.
        3. Pivot bolt is now in correct position in the middle.
    5. At this point `pivot_nut` and `pivot_bolt` are matched and on left there
       are smaller elements and bigger on right side.
    6. Recursive call for left side to pivot and right (repeat all the steps above).

So pivot here is taken from other array to satisfy exercise.


### Complexity
- Partitioning: O(n) (each pivot partitions both `Nuts` and `Bolts` in O(n)).  
- Recursive Calls: Similar to QuickSort, O(log n) recursive calls.  
- Overall O(n log n) is expected here.


Notes:
There is a research paper about it: https://web.cs.ucla.edu/~rafail/PUBLIC/17.pdf 