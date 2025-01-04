import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Search in a bitonic array. An array is bitonic if it is comprised of an
increasing sequence of integers followed immediately by a decreasing sequence of
integers. Write a program that, given a bitonic array of n n distinct integer
values, determines whether a given integer is in the array.

Bitonic Search Algorithm:
This algorithm searches for a target value in a bitonic array (an array that
first increases and then decreases). Steps:
1. Find the peak element (maximum value) in the bitonic array.
2. Perform binary search on the increasing part of the array (from start to peak).
3. Perform binary search on the decreasing part of the array (from peak to end).
4. Return true if the target is found in either part, otherwise return false.

Time Complexity: O(logn)
Space Complexity: O(1)
*/

public class BitonicSearch {
    public static boolean search(ArrayList<Integer> arr, int target)
    {
        var peak = findPeak(arr);
        if(!peak.isPresent())
        {
            return false;
        }

        return searchBinary(arr, 0, peak.get(), target, true) || searchBinary(arr, peak.get() + 1, arr.size() - 1, target, false);
    }

    static Optional<Integer> findPeak(ArrayList<Integer> arr) {

        if (arr.isEmpty()) {
            return Optional.empty();
        }

        if (arr.size() == 1) {
            return Optional.of(0);
        }

        if (arr.size() == 2) {
            return arr.get(0) > arr.get(1) ? Optional.of(0) : Optional.of(1);
        }

        int low = 0;
        int high = arr.size() - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            boolean is_in_bounds = ((mid > 0) && (mid < arr.size() - 1));

            if (is_in_bounds && arr.get(mid) > arr.get(mid + 1) && arr.get(mid) > arr.get(mid - 1)) {
                return Optional.of(mid); // Peak found.
            }

            if (is_in_bounds && arr.get(mid) < arr.get(mid + 1)) {
                // We are in the ascending part.
                low = mid + 1;
            } else {
                // We are in the descending part.
                high = mid;
            }
        }

        return Optional.of(low);
    }

    static boolean searchBinary(ArrayList<Integer> arr, int low, int high, int target, boolean ascending) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr.get(mid) == target) {
                return true;
            }

            if (ascending) {
                // Binary search for ascending order.
                if (arr.get(mid) < target) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            } else {
                // Binary search for descending order.
                if (arr.get(mid) > target) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // Test case 1: Basic bitonic array
        ArrayList<Integer> arr1 = new ArrayList<>(List.of(1, 3, 8, 12, 4, 2));
        System.out.println("Search 8 in arr1: " + (search(arr1, 8) ? "Found" : "Not Found"));
        System.out.println("Search 5 in arr1: " + (search(arr1, 5) ? "Found" : "Not Found"));

        // Test case 2: Entirely ascending array
        ArrayList<Integer> arr2 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        System.out.println("Search 4 in arr2: " + (search(arr2, 4) ? "Found" : "Not Found"));
        System.out.println("Search 7 in arr2: " + (search(arr2, 7) ? "Found" : "Not Found"));

        // Test case 3: Entirely descending array
        ArrayList<Integer> arr3 = new ArrayList<>(List.of(9, 7, 5, 3, 2, 1));
        System.out.println("Search 7 in arr3: " + (search(arr3, 7) ? "Found" : "Not Found"));
        System.out.println("Search 10 in arr3: " + (search(arr3, 10) ? "Found" : "Not Found"));

        // Test case 4: Single element array
        ArrayList<Integer> arr4 = new ArrayList<>(List.of(10));
        System.out.println("Search 10 in arr4: " + (search(arr4, 10) ? "Found" : "Not Found"));
        System.out.println("Search 5 in arr4: " + (search(arr4, 5) ? "Found" : "Not Found"));

        // Test case 5: Two element array
        ArrayList<Integer> arr5 = new ArrayList<>(List.of(10, 5));
        System.out.println("Search 10 in arr5: " + (search(arr5, 10) ? "Found" : "Not Found"));
        System.out.println("Search 7 in arr5: " + (search(arr5, 7) ? "Found" : "Not Found"));

        // Test case 6: Empty array
        ArrayList<Integer> arr6 = new ArrayList<>();
        System.out.println("Search 1 in arr6: " + (search(arr6, 1) ? "Found" : "Not Found"));
    }
}
