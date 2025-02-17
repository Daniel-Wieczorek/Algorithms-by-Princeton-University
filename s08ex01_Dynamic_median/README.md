## Dynamic median

Design a data type that supports insert in logarithmic time, find-the-median in
constant time, and remove-the-median in logarithmic time. 
If the number of keys in the data type is even, find/remove the lower median.

### Algorithm

We will need to use max-heap and min-heap of the data set (double heap approach).

Max heap stores the lower half of elements.
Min heap stores the upper of elements.
Balancing if number of elements is even then median is largest element in max help and always low.size() >= high.size().

TODO: Finish it!

### Complexity