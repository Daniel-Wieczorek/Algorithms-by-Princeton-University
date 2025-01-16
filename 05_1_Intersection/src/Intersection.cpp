#include <vector>
#include <cassert>
#include <algorithm>
#include <unordered_set>
#include <iostream>

using Coordinates = std::pair<int32_t, int32_t>;
using ArrayPair = std::vector<Coordinates>;

class ArrayPairHasher
{
public:
    size_t operator()(const Coordinates &point) const
    {
        auto hash_1 = std::hash<int32_t>{}(point.first);
        auto hash_2 = std::hash<int32_t>{}(point.second);
        return hash_1 ^ (hash_2 << 1); // Combine hashes
    }
};

class Intersection
{
public:
    /**
     * Finds the number of points present in both arrays a and b.
     * 
     * @param a: First array of 2D points.
     * @param b: Second array of 2D points.
     * @return The count of common points in both arrays.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    int32_t IntersectionTwoDimensional(const ArrayPair &a, const ArrayPair &b)
    {
        if (a.empty() || b.empty())
        {
            return 0;
        }

        std::unordered_set<Coordinates, ArrayPairHasher> points_set;
        for (const auto &point : a)
        {
            points_set.emplace(point);
        }

        int32_t count{0};
        for (const auto &point : b)
        {
            if (points_set.find(point) != points_set.end())
            {
                ++count;
            }
        }

        return count;
    }
};

int main() {
    Intersection intersection;

    // Test 1: Normal case
    ArrayPair a1 = {{1, 2}, {3, 4}, {5, 6}};
    ArrayPair b1 = {{3, 4}, {7, 8}, {5, 6}};
    assert(intersection.IntersectionTwoDimensional(a1, b1) == 2);

    // Test 2: No common points
    ArrayPair a2 = {{1, 2}, {3, 4}};
    ArrayPair b2 = {{5, 6}, {7, 8}};
    assert(intersection.IntersectionTwoDimensional(a2, b2) == 0);

    // Test 3: Identical arrays
    ArrayPair a3 = {{1, 1}, {2, 2}, {3, 3}};
    ArrayPair b3 = {{1, 1}, {2, 2}, {3, 3}};
    assert(intersection.IntersectionTwoDimensional(a3, b3) == 3);

    // Test 4: Empty array a
    ArrayPair a4 = {};
    ArrayPair b4 = {{1, 1}, {2, 2}};
    assert(intersection.IntersectionTwoDimensional(a4, b4) == 0);

    // Test 5: Empty array b
    ArrayPair a5 = {{1, 1}, {2, 2}};
    ArrayPair b5 = {};
    assert(intersection.IntersectionTwoDimensional(a5, b5) == 0);

    // Test 6: Both arrays empty
    ArrayPair a6 = {};
    ArrayPair b6 = {};
    assert(intersection.IntersectionTwoDimensional(a6, b6) == 0);

    // Test 7: Single common point
    ArrayPair a7 = {{1, 1}, {2, 2}};
    ArrayPair b7 = {{2, 2}, {3, 3}};
    assert(intersection.IntersectionTwoDimensional(a7, b7) == 1);

    std::cout << "All tests passed!" << std::endl;
    return 0;
}
