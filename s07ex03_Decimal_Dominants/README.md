## Decimal dominants

Given an array with n keys, design an algorithm to find all values that occur
more than n/10 times. The expected running time of your algorithm should be
linear.

### Algorithm

Boundary conditions:
No information but such problems are related to big data so we can assume n is very big number
and we shall (can) not store it enterly in memory. 

Classic Misra-Gries alg, more details here: https://en.wikipedia.org/wiki/Misra%E2%80%93Gries_summary.

```python
    input: 
        A positive integer k (number of items to track)
        A finite sequence s taking values in the range 1,2,...,m
    output: An associative array A with frequency estimates for each item in s
    
    A := new (empty) associative array
    while s is not empty:
        take a value i from s
        if i is in keys(A):  # If the element is already in the list, increment its count.
            A[i] := A[i] + 1
        else if keys.size < k - 1:  # If there is space (less than k−1 items), add the new element with a count of 1.
            A[i] := 1
        else:  # If the list is full and the element is not in it
            for each K in keys(A):
                A[K] := A[K] - 1  # decrement all counts by 1.
                if A[K] = 0:  # If any count reaches zero, remove the corresponding item:
                    remove K from keys(A)
    return A  #  remaining items in the list are candidates for frequent items.

```
and then perform 2nd pass over to count actual occurrences  to determine if they truly appear more than n/k times.

----

Algorithm for use case above:
1. k = 9 so we will have up to 9 possible candidates (we need **more** than 10 so we end up with max 9 candidates, not 10!)
2. Create hash map / dictionary to store the items.
3. If the current element exists in the hash map, increment its count.
4. If the current element is not in the hash map:
    1. If there are fewer than 9 elements in the hash map, add it with count 1.
    2. If there are already 9 elements, decrement the count of every element in the hash map.
    3. If any count reaches 0, remove that element from the hash map.

This ensures that any element that appears **more** than 9 times will remain as a candidate.
Result will be hash map with potential candidates.

To confirm their actual frequencies, make a second pass through the array and count the occurrences of each candidate.
Collect all candidates that appear more than n/10 times.

### Complexity
Time complexity: O(n) + O(n) (first and 2nd pass)
Space complexity: O(1) what is much better than standard approach what could be memory intensive.