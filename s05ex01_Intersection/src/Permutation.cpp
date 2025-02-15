#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>


// Permutation. Given two integer arrays of size n design a subquadratic
// algorithm to determine whether one is a permutation of the other. That is, do
// they contain exactly the same entries but, possibly, in a different order.

// Complexity:
// - Time Complexity: O(n)
// - Space Complexity: O(k), where k is the number of unique elements in the array.

class Permutation
{
public:
    // Function to check if two arrays are permutations of each other
    bool IsPermutation(const std::vector<int32_t> &a, const std::vector<int32_t> &b)
    {
        // Handle the special case of empty arrays
        if (a.empty() && b.empty())
        {
            return true; // Two empty arrays are considered permutations
        }

        // If arrays have different sizes, they cannot be permutations
        if ((a.empty() || b.empty()) || (a.size() != b.size()))
        {
            return false;
        }

        // Hash map to count occurrences of each element in array `a`
        std::unordered_map<int32_t, int32_t> num_map{};
        for (size_t i = 0; i < a.size(); i++)
        {
            num_map[a.at(i)] += 1; // Increment the count for each element in `a`
        }

        // Iterate through array `b` and decrement counts in the hash map
        for (const auto &entry : b)
        {
            if (num_map.find(entry) == num_map.end() || --num_map[entry] < 0)
            {
                // If an element in `b` is not in `a` or is overrepresented, return false
                return false;
            }
        }

        // Verify all counts in the hash map are zero
        for (const auto &[key, value] : num_map)
        {
            if (value != 0)
            {
                return false; // Non-zero count indicates arrays are not permutations
            }
        }

        return true; // Arrays are permutations
    }
};

int main()
{
    Permutation perm;

    // Test 1: Normal case
    std::vector<int32_t> a1 = {1, 2, 3, 4};
    std::vector<int32_t> b1 = {4, 3, 2, 1};
    assert(perm.IsPermutation(a1, b1) == true);

    // Test 2: Not a permutation
    std::vector<int32_t> a2 = {1, 2, 3};
    std::vector<int32_t> b2 = {1, 2, 2};
    assert(perm.IsPermutation(a2, b2) == false);

    // Test 3: Different sizes
    std::vector<int32_t> a3 = {1, 2, 3};
    std::vector<int32_t> b3 = {1, 2};
    assert(perm.IsPermutation(a3, b3) == false);

    // Test 4: Empty arrays
    std::vector<int32_t> a4 = {};
    std::vector<int32_t> b4 = {};
    assert(perm.IsPermutation(a4, b4) == true);

    // Test 5: Arrays with duplicates
    std::vector<int32_t> a5 = {1, 1, 2, 2};
    std::vector<int32_t> b5 = {2, 2, 1, 1};
    assert(perm.IsPermutation(a5, b5) == true);

    // Test 6: Arrays with one element
    std::vector<int32_t> a6 = {42};
    std::vector<int32_t> b6 = {42};
    assert(perm.IsPermutation(a6, b6) == true);

    std::vector<int32_t> a7 = {-1, -2, -3};
    std::vector<int32_t> b7 = {-3, -2, -1};
    assert(perm.IsPermutation(a7, b7) == true);

    std::cout << "All tests passed!" << std::endl;
    return 0;
}
