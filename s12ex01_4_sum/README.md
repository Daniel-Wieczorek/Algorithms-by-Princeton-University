## 4-SUM

Given an array `a[]` of `n` integers, the 4-SUM problem is to determine if there
exist distinct indices `i`, `j`, `k`, `l` such that `a[i]+a[j]=a[k]+a[l]`.
Design an algorithm for the 4-SUM problem that takes time proportional to `n^2`
(under suitable technical assumptions).

Hint: create a hash table with `(n | 2)` key-value pairs.

### Algorithm/Solution

Border conditions:

- We assume there could be at least one such option
- Method will return boolean: `True` iff at least one set of such indices were found, `False` otherwise.

As hint suggest we will have to use hash map here. In CPP that can be unordered_map<key, value> `hashMap`
where:

- key: sum of two elements `a[i] + a[j]`
- value: pair `(i,j)`

Algorithm:

1. Iterate over all elements in `a[]` using `i` and `j`
2. Iterate over all pairs `(i,j)` (where `i<j`):
   1. Calculate `Sum = a[i] + a[j]`:
        - if `Sum` exists in the `hashMap`:
          - check indices (value) for already existing `Sum` (x,y) and compare
            these two the current value (`i != x && i != y && j !=x && j!=y`) as
            all indices must be unique in this case
            - if above check success return `True`, otherwise continue.
3. Insert `(i,j)` pair into `hashMap` and continue to the next iteration.
4. Return `False` as nno indices found

So we iterate to stored sum (i,j) and if we find again, during iteration same value then we check if all indices are unique. If they are it's a hit.

### Complexity

Hash table has lookup and insertion in O(1) so main time is taken to iterate over double for loop (i,j)

Time Complexity: O(n^2)
Space Complexity: O(n(n-1)) => O(n^2)