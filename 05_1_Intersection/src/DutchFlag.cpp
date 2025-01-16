#include <vector>
#include <iostream>
#include <optional>
#include <cassert>

enum class Colors
{
    RED,
    WHITE,
    BLUE
};

class DutchFlag
{
public:
    // Sorts the Dutch flag problem in-place using the three-way partitioning
    // algorithm.
    // - Time Complexity: O(n)
    // - Space Complexity: O(1)
    std::optional<std::vector<Colors>> SortDutchFlag(std::vector<Colors> &colors)
    {
        if (colors.empty())
        {
            return colors;
        }

        size_t low{0};                  // Boundary for RED
        size_t mid{0};                  // Currrent element
        size_t high{colors.size() - 1}; // Boundary for BLUE

        while (mid <= high && high != 0)
        {
            if (colors.at(mid) == Colors::RED)
            {
                std::swap(colors.at(mid), colors.at(low));
                ++low; // Expand RED
                ++mid; // Move to next unprocessed element.
            }
            else if (colors.at(mid) == Colors::WHITE)
            {
                ++mid; // WHITE is already in the middle; move on.
            }
            else if (colors.at(mid) == Colors::BLUE)
            {
                std::swap(colors.at(mid), colors.at(high));
                /* Expand BLUE region, but recheck the swapped element at mid as
                we did not check it before. It is different use case that for
                red where it is already checked.*/
                --high;
            }
            else
            {
                return std::nullopt;
            }
        }

        return colors; // Return the sorted array.
    }
};

int main()
{
    DutchFlag sorter;

    // Test 1: Mixed
    std::vector<Colors> input1{Colors::WHITE, Colors::RED, Colors::BLUE, Colors::RED, Colors::WHITE, Colors::BLUE};
    auto result1 = sorter.SortDutchFlag(input1);
    assert(result1.has_value());
    assert((result1.value() == std::vector<Colors>{Colors::RED, Colors::RED, Colors::WHITE, Colors::WHITE, Colors::BLUE, Colors::BLUE}));

    // Test 2: Already sorted
    std::vector<Colors> input2{Colors::RED, Colors::RED, Colors::WHITE, Colors::WHITE, Colors::BLUE, Colors::BLUE};
    auto result2 = sorter.SortDutchFlag(input2);
    assert(result2.has_value());
    assert((result2.value() == std::vector<Colors>{Colors::RED, Colors::RED, Colors::WHITE, Colors::WHITE, Colors::BLUE, Colors::BLUE}));

    // Test 3: All RED
    std::vector<Colors> input3{Colors::RED, Colors::RED, Colors::RED};
    auto result3 = sorter.SortDutchFlag(input3);
    assert(result3.has_value());
    assert((result3.value() == std::vector<Colors>{Colors::RED, Colors::RED, Colors::RED}));

    // Test 4: All BLUE
    std::vector<Colors> input4{Colors::BLUE, Colors::BLUE, Colors::BLUE};
    auto result4 = sorter.SortDutchFlag(input4);
    assert(result4.has_value());
    assert((result4.value() == std::vector<Colors>{Colors::BLUE, Colors::BLUE, Colors::BLUE}));

    // Test 5: Empty
    std::vector<Colors> input5{};
    auto result5 = sorter.SortDutchFlag(input5);
    assert(result5.has_value());
    assert(result5.value().empty());

    // Test 6: All WHITE
    std::vector<Colors> input6{Colors::WHITE, Colors::WHITE, Colors::WHITE};
    auto result6 = sorter.SortDutchFlag(input6);
    assert(result6.has_value());
    assert((result6.value() == std::vector<Colors>{Colors::WHITE, Colors::WHITE, Colors::WHITE}));
    std::cout << "All tests passed successfully.\n";
    return 0;
}
