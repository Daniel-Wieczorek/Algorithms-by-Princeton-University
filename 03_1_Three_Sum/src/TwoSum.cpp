#include <vector>
#include <iostream>
#include <unordered_map>
#include <string>
#include <unordered_set>
#include <algorithm>
#include <tuple>

/*
Two sum problem:

Hashing Approach: The hashing approach works by storing previously seen numbers
and their indices in a hash table (unordered_map in C++). This allows us to
check in constant time whether the complement (the number required to meet the
target) exists in the array.

Steps: 
1. Iterate through the array: For each number, calculate the complement required
   to reach the target (i.e., complement = target - nums[i]). 
2. Check the hash table: If the complement is already in the hash table, we have
   found the pair of indices. 
3. Update the hash table: If not, add the current number and its index to the
   hash table for future reference.
4. Return target indexes or empty vector if not found
*/

// Time complexity: O(n), where each lookup in hash table is const. time O(1)
// Space complexity: O(n) due to hash table usage
template<typename T>
std::vector<size_t> twoSum(const std::vector<T> &arr, const T target)
{
    std::unordered_map<T,T> hash_table{};
    for(size_t i = 0; i < arr.size(); ++i)
    {
        const T complement = target - arr[i]; // find complement number
        // Check if complement is in hash table
        if(hash_table.find(complement) != hash_table.end())
        {
            return {hash_table[complement], i};
        }
        // Add the current number and index to the hash table to use in final return
        hash_table[arr[i]] = i;
    }
    return {};
}

// Custom hash function for std::vector<T> to avoid using std::set with O(NlogN)
// and use unordered_map instead with const lookup.
template<typename T>
struct vector_hash {
    size_t operator()(const std::vector<T>& v) const {
        size_t seed = 0;
        for (const T& elem : v) {
            seed ^= std::hash<T>{}(elem) + 0x9e3779b9 + (seed << 6) + (seed >> 2);
        }
        return seed;
    }
};

template<typename T>
std::vector<std::vector<T>> threeSum(std::vector<T> &nums, const T target)
{
    std::vector<std::vector<T>> result;
    std::unordered_set<std::vector<T>, vector_hash<T>> seen;
    if(nums.empty() || nums.size() < 3)
    {
        return {};
    }
    // sort the array to use two pointer technique:
    std::sort(nums.begin(), nums.end()); // O(N * logN)
    for(size_t i = 0; i < nums.size(); ++i)
    {
        // we have number i picked now. Take left and right from sub-array:
        size_t left = i + 1;
        size_t right = nums.size() - 1;

        while(left < right)
        {
            T sum = nums.at(i) + nums.at(left) + nums.at(right);
            if(sum == target)
            {
                std::vector<T> triplet = {nums.at(i), nums.at(left), nums.at(right)};
                // Add triplet to the set (automatically ensures uniqueness)
                if(seen.find(triplet) == seen.end())
                {
                    seen.insert(triplet);
                    result.push_back(triplet);
                }
                ++left;
                --right;
            }
            else if(sum < target) // take higher number from sorted input
            {
                ++left;
            }
            else    // take lower number from sorted input
            {
                --right;
            }
        }
    }
    return result;
}

int main()
{
    std::vector<int32_t> nums = {2, 7, 11, 15};
    int32_t target = 26;
    const auto result = twoSum(nums, target);

    if (!result.empty()) 
    {
        std::cout << "Indices: " << result[0] << ", " << result[1] << std::endl;
    } else 
    {
        std::cout << "No solution found!" << std::endl;
    }

    std::vector<int32_t> nums_triplet = {1, 2, -1, -1, -4, 0, 2, -1, 1};
    int32_t target_val = 0;

    std::vector<std::vector<int32_t>> triplets = threeSum(nums_triplet, target_val);

    if(!triplets.empty())
    {
    std::cout << "Triplets that sum to " << target_val << ":\n";
    for (const auto& triplet : triplets) {
        std::cout << "[" << triplet[0] << ", " << triplet[1] << ", " << triplet[2] << "]\n";
    }
    }
}