import java.util.*;

/**
 * This class implements solutions for the Two Sum and Three Sum problems. 
 * The Two Sum problem requires finding two numbers in an array that sum up 
 * to a given target. The Three Sum problem requires finding all unique 
 * triplets in an array that sum up to a given target.
 * 
 * Key functionalities:
 * - `twoSum(int[] arr, int target)` finds two numbers in the array that 
 *   sum up to the given target.
 * - `threeSum(int[] arr, int target)` finds all unique triplets in the 
 *   array that sum up to the given target.
 * 
 * The solutions are optimized for time complexity:
 * - The Two Sum solution uses a HashMap to find the complement of each 
 *   element in O(n) time.
 * - The Three Sum solution uses sorting and a two-pointer technique to 
 *   find triplets in O(n^2) time, making it more efficient for large inputs.
 * 
 * Time Complexity:
 * - `twoSum`: O(n), where n is the length of the array.
 * - `threeSum`: O(n^2), where n is the length of the array.
 * 
 * Space Complexity:
 * - `twoSum`: O(N), where N is the number of unique elements in the map.
 * - `threeSum`: O(N), where N is the number of unique triplets found.
 */
public class Sum {

    /**
     * Solves the Two Sum problem by finding two indices in the array such 
     * that their values sum up to the given target.
     * 
     * @param arr The input array of integers.
     * @param target The target sum to be found.
     * @return A list of indices whose corresponding values sum up to the target,
     *         or an empty list if no such pair exists.
     */
    // Time Complexity: O(n) 
    // Space Complexity: O(N) 
    static List<Integer> twoSum(int[] arr, int target)
    {
        Map<Integer, Integer> map = new HashMap<>();  // Map to store the complement and index.

        for(int i = 0; i < arr.length; ++i)
        {
            int complement = target - arr[i];  // Calculate the complement for the current element.
            if(map.containsKey(complement))  // If the complement is already in the map, return the result.
            {
                return Arrays.asList(map.get(complement), i);  // Return the pair of indices.
            }
            map.put(arr[i], i);  // Store the current element and its index in the map.
        }

        return Collections.emptyList();  // Return an empty list if no solution is found.
    }

    /**
     * Solves the Three Sum problem by finding all unique triplets in the array 
     * that sum up to the given target.
     * 
     * @param arr The input array of integers.
     * @param target The target sum for the triplets.
     * @return A list of unique triplets that sum up to the target.
     */
    static List<List<Integer>> threeSum(int[] arr, int target)
    {
        if(arr.length < 3)  // If there are fewer than 3 elements, no triplet is possible.
        {
            return Collections.emptyList();
        }

        Arrays.sort(arr);  // Sort the array to use the two-pointer technique.
        Set<List<Integer>> result = new HashSet<>();  // Set to store unique triplets.

        for(int i = 0; i < arr.length - 2; ++i)  // Iterate through each element to find triplets.
        {
            int left = i + 1;  // Left pointer starts just after the current element.
            int right = arr.length - 1;  // Right pointer starts from the end of the array.

            while(left < right)  // While the left pointer is less than the right pointer.
            {
                int sum = arr[i] + arr[left] + arr[right];  // Calculate the sum of the current triplet.
                if(sum == target)  // If the sum matches the target, add the triplet to the result set.
                {
                    result.add(Arrays.asList(arr[i], arr[left], arr[right]));
                    
                    // Move both pointers to avoid duplicates.
                    ++left;
                    --right;
                } 
                else if(sum < target)  // If the sum is less than the target, move the left pointer to the right.
                {
                    left += 1;
                }
                else  // If the sum is greater than the target, move the right pointer to the left.
                {
                    right -=1;
                }
            }
        }

        return new ArrayList<>(result);  // Convert the result set to a list and return it.
    }

    /**
     * Main method to test the twoSum and threeSum methods.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Test case for Two Sum
        int[] arr = {2, 7, 11, 15};
        int target = 9;
        System.out.println(twoSum(arr, target));  // Expected output: [0, 1] (arr[0] + arr[1] = 9)

        // Test case for Three Sum
        int[] arr2 = {-4, -3, -2, 0, 4, 5, 7, 11, 15};
        System.out.println(threeSum(arr2, target));  // Expected output: unique triplets summing to 9

        // Another test case for Three Sum with different target
        int[] arr3 = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(arr3, 0));  // Expected output: [[-1, -1, 2], [-1, 0, 1]]
    }
}
