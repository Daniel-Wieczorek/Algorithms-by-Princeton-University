/*******************************************************************************
 * Problem: Egg Drop
 * 
 * Description:
 * Given an n-story building and plenty of eggs, find the floor T where eggs start 
 * breaking when dropped. An egg breaks if dropped from floor T or higher, and 
 * survives drops from floors below T.
 * 
 * Implementations:
 * 
 * Version 0:
 * - Constraints: 1 egg, ≤T tosses
 * - Approach: Linear search from bottom up
 * - Time Complexity: O(T) where T is the breaking point floor
 * - Space Complexity: O(1)
 * - Strategy: Check each floor sequentially until egg breaks
 * 
 * Version 1:
 * - Constraints: ~1lg n eggs and ~1lg n tosses
 * - Approach: k-way partitioning where k ≈ n/lg n
 * - Time Complexity: O(lg n)
 * - Space Complexity: O(1)
 * - Strategy: Divide building into segments of size n/lg n, find correct segment,
 *   then binary search within that segment
 * 
 * Version 2:
 * - Constraints: ~lg T eggs and ~2lg T tosses
 * - Approach: Exponential search followed by binary search
 * - Time Complexity: O(lg T) for exponential search + O(lg T) for binary search
 * - Space Complexity: O(1)
 * - Strategy: First find upper bound using powers of 2, then binary search in range
 * 
 * Version 3:
 * - Constraints: 2 eggs and ~2√n tosses
 * - Approach: Jump search followed by linear search
 * - Time Complexity: O(√n) for first egg + O(√n) for second egg = O(2√n)
 * - Space Complexity: O(1)
 * - Strategy: Use first egg to jump √n floors at a time, use second egg for
 *   linear search in the identified range
 * 
 * Note: All versions maintain optimal complexity within their given constraints
 * of eggs and tosses allowed.
 ******************************************************************************/

#include <iostream>
#include <optional>
#include <cmath>
#include <algorithm>
#include <cassert>

static constexpr int32_t kGroundLevel{1};
class EggDrop
{
public:
    // Version 0: 1 egg, <= T tosses
    // Linear search from bottom up.
    std::optional<int32_t> version0(int32_t total_numbers_of_floors)
    {
        for (int i = kGroundLevel; i <= total_numbers_of_floors; ++i)
        {
            if (eggBrakesAt(i))
            {
                return i;
            }
        }
        return std::nullopt;
    }

    // Version 1: ~1lg n eggs and ~1lg n tosses
    // We need to use k-way partitioning where k ≈ n/lg n
    std::optional<int32_t> version1(int32_t total_numbers_of_floors)
    {
        int32_t segment_size = std::ceil(total_numbers_of_floors / std::log2(total_numbers_of_floors));
        int32_t current_floor = segment_size;
        
        while (current_floor <= total_numbers_of_floors && !eggBrakesAt(current_floor)) 
        {
            current_floor += segment_size;
        }
        
        // Search within the segment
        int32_t low = current_floor - segment_size;
        int32_t high = std::min(current_floor, total_numbers_of_floors);
        
        return binarySearch(low, high);
    }

    int32_t version2(int32_t total_numbers_of_floors)
    {
        int32_t exp{0};
        // Drop the at floor 1, 2, 4, 8, 16,… until it breaks. It will use 1 egg and lgT tosses:
        while(exp < 31 && std::pow(2, exp) <= total_numbers_of_floors && !eggBrakesAt(std::pow(2,exp)))
        {
            ++exp;
        }

        // Perform binary search in given range where egg broken what shall take another lgT
        int32_t low_border = (exp == 0) ? 1 : std::pow(2, exp - 1);
        int32_t high_border = std::min(total_numbers_of_floors, static_cast<int32_t>(std::pow(2, exp)));
        return  binarySearch(low_border, high_border);
    }

    // Version 3: 2 eggs and ~2sqrt(n) tosses
    int32_t version3(int32_t total_numbers_of_floors)
    {
        const int32_t step = std::ceil(std::sqrt(total_numbers_of_floors));
        int32_t test_level{0};
        while(test_level < total_numbers_of_floors)
        {
            if(eggBrakesAt(test_level))
            {
                break;
            }
            test_level += step;
        }

        int32_t low_border = (test_level == 0) ? 1 : test_level - step;
        int32_t high_border = std::min(total_numbers_of_floors, test_level);

        return linearSearch(low_border, high_border);
    }


private:
    bool eggBrakesAt(const int32_t floor)
    {
        const int32_t breaking_point{10}; // only for testing
        return floor >= breaking_point;
    }

    int32_t binarySearch(int32_t low, int32_t high)
    {
        while (low <= high)
        {
            const int32_t mid = low + (high - low) / 2;
            if (eggBrakesAt(mid))
            {
                high = mid - 1;
            }
            else
            {
                low = mid + 1;
            }
        }
        return low;
    }

    int32_t linearSearch(int32_t low, int32_t high)
    {
        for (int i = low; i <= high; ++i)
        {
            if (eggBrakesAt(i))
            {
                return i;
            }
        }
        return -1; // floor not found!
    }

};

int main() {
    EggDrop egg_drop;
    const int32_t test_floors = 100;
    
    // Test Version 0
    auto result0 = egg_drop.version0(test_floors);
    assert(result0.has_value() && "Version 0 should find a breaking point");
    assert(result0.value() == 10 && "Version 0 should find correct breaking point");
    std::cout << "Version 0 tests passed!\n";

    // Test Version 1
    auto result1 = egg_drop.version1(test_floors);
    assert(result1.has_value() && "Version 1 should find a breaking point");
    assert(result1.value() == 10 && "Version 1 should find correct breaking point");
    std::cout << "Version 1 tests passed!\n";

    // Test Version 2
    int32_t result2 = egg_drop.version2(test_floors);
    assert(result2 == 10 && "Version 2 should find correct breaking point");
    std::cout << "Version 2 tests passed!\n";

    // Test Version 3
    int32_t result3 = egg_drop.version3(test_floors);
    assert(result3 == 10 && "Version 3 should find correct breaking point");
    std::cout << "Version 3 tests passed!\n";
    std::cout << "All tests passed successfully!\n";
    
    return 0;
}