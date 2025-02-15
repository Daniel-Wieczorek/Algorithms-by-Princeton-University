## Selection in two sorted arrays

Given two sorted arrays a[] and b[], of lengths N1 and N2 and an integer 
`0 ≤ k < N1 + N2` design an algorithm to find a key of rank k. The order 
of growth of the worst case running time of your algorithm should be logn, 
where `n = N1 + N2`.

Version 1: N1 = N2 and k = n/2
Version 2: k = n/2
Version 3: no restrictions.

### Algorithm

Boundary conditions:
No information about size of n and k but assume we can store it in 32 bits (int32).

Ad.1:
Arrays are equal so total length is N1 + N2 = 2N. It means our k = 2N/2 = n but arrays are individually sorted
so our number does not have to be k = n i.e:
a[] = {1,2,3}
b[] = {1,1,1}
k = 3 != median.
Merging arrays would give us average O(n*logn) complexity what is not our goal (logn).
Instead we have to use binary search here:

1. Choose first array a[n], initialize three vars: left = 0, right = N. k = n / 2.
2. i = (left + right) / 2 (to be in the middle of first array a[n]).
3. Take remaining elements from array b[n] so j = k - i to form in total n
   elements ( i.e. initial i = n/2 and j = k - i = 2n/2 - n/2 = n/2 so j + i = n what is half
   size needed for binary search)
4. Check if given partition is valid:
    1. if(a[i-1] <= b[j] && b[j-1] <= a[i]) found solution.
    2. if(a[i-1] > b[j]) then right = i - 1 (move i left).
    3. if(b[j-1] > a[i]) then left = i + 1 (move i right).
5. Result = max(a[i-1], b[j-1]) what is median.

Ad.2 and Ad.3: ToDo

### Complexity

Ad.1 O(logn) as we have binary search here.
Ad.2 and Ad.3: ToDo
