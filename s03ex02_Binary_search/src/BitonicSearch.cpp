#include <vector>
#include <iostream>
#include <optional>

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

class BitonicSearch
{
public:
    // Public method to search for a target in a bitonic array.
    bool search(const std::vector<int32_t> &arr, const int32_t target)
    {
        // Find the peak element in the bitonic array.
        const auto peak = findPeak(arr);
        if (!peak.has_value())
        {
            return false; // If no peak is found (e.g., empty array), return false.
        }

        // Perform binary search on both increasing and decreasing parts of the array.
        return searchBinary(arr, 0, peak.value(), target, true) || 
               searchBinary(arr, peak.value() + 1, arr.size() - 1, target, false);
    }

private:
    // Helper method to perform binary search on a subarray.
    bool searchBinary(const std::vector<int32_t> &arr, int32_t low, int32_t high, int32_t target, bool ascending)
    {
        while (low <= high)
        {
            int32_t mid = low + (high - low) / 2; // Calculate mid-point to avoid overflow.
            if (arr[mid] == target)
            {
                return true; // Target found.
            }

            if (ascending)
            {
                // Binary search for ascending order.
                if (arr[mid] < target)
                {
                    low = mid + 1;
                }
                else
                {
                    high = mid - 1;
                }
            }
            else
            {
                // Binary search for descending order.
                if (arr[mid] > target)
                {
                    low = mid + 1;
                }
                else
                {
                    high = mid - 1;
                }
            }
        }

        return false; // Target not found.
    }

    // Helper method to find the peak element in a bitonic array.
    std::optional<int32_t> findPeak(const std::vector<int32_t> &arr)
    {
        if (arr.empty())
        {
            return std::nullopt; // Return no value for empty array.
        }

        // Handle edge case where array has one or two elements.
        if (arr.size() == 1)
        {
            return 0; // Single element is the peak.
        }

        if (arr.size() == 2)
        {
            return arr[0] > arr[1] ? 0 : 1; // Return the index of the larger element.
        }

        int32_t low{0};
        int32_t high = arr.size() - 1;

        while (low < high)
        {
            const int32_t mid = low + (high - low) / 2;
            bool is_in_bounds = ((mid > 0) && (mid < arr.size() - 1));
            if (is_in_bounds && arr[mid] > arr[mid + 1] && arr[mid] > arr[mid - 1])
            {
                return mid; // Peak found.
            }

            if (is_in_bounds && arr[mid] < arr[mid + 1])
            {
                // We are in the ascending part.
                low = mid + 1;
            }
            else
            {
                // We are in the descending part.
                high = mid;
            }
        }

        return low; // Peak index.
    }
};


// Some simple test cases:
int main() {
    BitonicSearch bs;

    // Test case 1: Basic bitonic array
    std::vector<int32_t> arr1 = {1, 3, 8, 12, 4, 2};
    std::cout << "Search 8 in arr1: " << (bs.search(arr1, 8) ? "Found" : "Not Found") << std::endl;
    std::cout << "Search 5 in arr1: " << (bs.search(arr1, 5) ? "Found" : "Not Found") << std::endl;

    // Test case 2: Entirely ascending array
    std::vector<int32_t> arr2 = {1, 2, 3, 4, 5, 6};
    std::cout << "Search 4 in arr2: " << (bs.search(arr2, 4) ? "Found" : "Not Found") << std::endl;
    std::cout << "Search 7 in arr2: " << (bs.search(arr2, 7) ? "Found" : "Not Found") << std::endl;

    // Test case 3: Entirely descending array
    std::vector<int32_t> arr3 = {9, 7, 5, 3, 2, 1};
    std::cout << "Search 7 in arr3: " << (bs.search(arr3, 7) ? "Found" : "Not Found") << std::endl;
    std::cout << "Search 10 in arr3: " << (bs.search(arr3, 10) ? "Found" : "Not Found") << std::endl;

    // Test case 4: Single element array
    std::vector<int32_t> arr4 = {10};
    std::cout << "Search 10 in arr4: " << (bs.search(arr4, 10) ? "Found" : "Not Found") << std::endl;
    std::cout << "Search 5 in arr4: " << (bs.search(arr4, 5) ? "Found" : "Not Found") << std::endl;

    // Test case 5: Two element array
    std::vector<int32_t> arr5 = {10, 5};
    std::cout << "Search 10 in arr5: " << (bs.search(arr5, 10) ? "Found" : "Not Found") << std::endl;
    std::cout << "Search 7 in arr5: " << (bs.search(arr5, 7) ? "Found" : "Not Found") << std::endl;

    // Test case 6: Empty array
    std::vector<int32_t> arr6 = {};
    std::cout << "Search 1 in arr6: " << (bs.search(arr6, 1) ? "Found" : "Not Found") << std::endl;

    return 0;
}
