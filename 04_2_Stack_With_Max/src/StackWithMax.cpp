/**
 * @file StackWithMax.cpp
 * @brief A stack data structure that supports efficient push, pop, and max operations.
 * 
 * Stack with max. Create a data structure that efficiently supports
 * the stack operations (push and pop) and also a return-the-maximum operation.
 * Assume the elements are real numbers so that you can compare them.
 * 
 * This implementation uses a deque to store elements along with their current
 * maximum index.
 *
 * - `push` adds an element to the stack and updates the maximum index.
 * - `pop` removes the top element from the stack.
 * - `top` returns the top element.
 * - `max` returns the current maximum element in the stack.
 * - `size` and `empty` provide utility functions for querying stack properties.
 *
 * Complexity:
 * - `push`: O(1)
 * - `pop`: O(1)
 * - `max`: O(1)
 * - `top`: O(1)
 */

#include <cassert>
#include <iostream>
#include <deque>
#include <utility>

template <typename T>
class StackWithMax
{
public:
    /**
     * @brief Returns the number of elements in the stack.
     * @return The size of the stack.
     */
    size_t size() const
    {
        return stack_.size();
    }

    /**
     * @brief Checks if the stack is empty.
     * @return True if the stack is empty, false otherwise.
     */
    bool empty() const
    {
        return stack_.empty();
    }

    /**
     * @brief Returns the top element of the stack.
     * @return Reference to the top element.
     */
    const T &top() const
    {
        assert(!empty() && "Stack is empty"); // Ensure stack is not empty.
        return stack_.back().first;
    }

    /**
     * @brief Returns the maximum element in the stack.
     * @return Reference to the maximum element.
     */
    const T &max() const
    {
        assert(!empty() && "Stack is empty"); // Ensure stack is not empty.
        return stack_[stack_.back().second].first;
    }

    /**
     * @brief Removes the top element from the stack.
     */
    void pop()
    {
        assert(!empty() && "Stack is empty"); // Ensure stack is not empty.
        stack_.pop_back();
    }

    /**
     * @brief Pushes a new element onto the stack.
     * @param element The element to be added.
     */
    void push(const T &element)
    {
        if (empty())
        {
            stack_.emplace_back(std::make_pair(element, 0));
        }
        else
        {
            // take current biggest index
            size_t biggest_index = stack_.back().second;

            // compare values under previous biggest index and inserted one
            const auto biggest_value = max();
            if (biggest_value < element)
            {
                biggest_index = stack_.size();
            }

            stack_.emplace_back(std::make_pair(element, biggest_index));
        }
    }

private:
    std::deque<std::pair<T, size_t>> stack_; // Stores elements and their corresponding max index.
};

int main()
{
    // Create a stack of integers.
    StackWithMax<int> stack;

    // Test: Stack should be empty initially.
    assert(stack.empty());
    assert(stack.size() == 0);

    // Push elements and check size and max.
    stack.push(10);
    assert(!stack.empty());
    assert(stack.size() == 1);
    assert(stack.top() == 10);
    assert(stack.max() == 10);

    stack.push(20);
    assert(stack.size() == 2);
    assert(stack.top() == 20);
    assert(stack.max() == 20); // 20 is the max.

    stack.push(5);
    assert(stack.size() == 3);
    assert(stack.top() == 5);
    assert(stack.max() == 20); // Max remains 20.

    stack.push(25);
    assert(stack.size() == 4);
    assert(stack.top() == 25);
    assert(stack.max() == 25); // Max is now 25.

    // Test: Pop elements and check max.
    stack.pop();
    assert(stack.size() == 3);
    assert(stack.top() == 5);
    assert(stack.max() == 20); // Max reverts to 20.

    stack.pop();
    assert(stack.size() == 2);
    assert(stack.top() == 20);
    assert(stack.max() == 20); // Max remains 20.

    stack.pop();
    assert(stack.size() == 1);
    assert(stack.top() == 10);
    assert(stack.max() == 10); // Max reverts to 10.

    stack.pop();
    assert(stack.empty());
    assert(stack.size() == 0);

    // Edge case: popping from an empty stack (should fail assertion).
    // Uncomment to test: stack.pop();

    std::cout << "All tests passed successfully!\n";
    return 0;
}