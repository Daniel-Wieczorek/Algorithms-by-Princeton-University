#include <stack>
#include <stdexcept>
#include <cassert>
#include <iostream>

/*
 * QueueTwoStacks: A queue implementation using two stacks.
 * This class provides the standard queue operations:
 * - enqueue: Adds an element to the back of the queue.
 * - dequeue: Removes and returns the front element of the queue.
 * - isEmpty: Checks whether the queue is empty.
 *
 * Internally, it uses two stacks:
 * - in_stack_: For enqueue operations.
 * - out_stack_: For dequeue operations.
 *
 * The amortized time complexity for both enqueue and dequeue is O(1).
 */

template <typename T>
class QueueTwoStacks
{
public:
    // Adds an element to the back of the queue
    void enqueue(const T &value)
    {
        in_stack_.push(value);
    }

    // Removes and returns the front element of the queue
    T dequeue()
    {
        if (isEmpty())
        {
            throw std::runtime_error("Queue is empty!");
        }

        // Transfer elements from in_stack_ to out_stack_ if out_stack_ is empty
        if (out_stack_.empty())
        {
            while (!in_stack_.empty())
            {
                out_stack_.push(in_stack_.top());
                in_stack_.pop();
            }
        }

        // Pop the top element from out_stack_ (front of the queue)
        T front = out_stack_.top();
        out_stack_.pop();
        return front;
    }

    // Checks if the queue is empty
    bool isEmpty() const
    {
        return in_stack_.empty() && out_stack_.empty();
    }

private:
    std::stack<T> in_stack_;  // Stack for enqueue operations
    std::stack<T> out_stack_; // Stack for dequeue operations
};

// Test the QueueTwoStacks class
int main()
{
    QueueTwoStacks<int> queue;

    // Test enqueue and isEmpty
    assert(queue.isEmpty());
    queue.enqueue(10);
    queue.enqueue(20);
    queue.enqueue(30);
    assert(!queue.isEmpty());

    // Test dequeue
    assert(queue.dequeue() == 10);
    assert(queue.dequeue() == 20);

    // Test enqueue after some dequeue operations
    queue.enqueue(40);
    assert(queue.dequeue() == 30);
    assert(queue.dequeue() == 40);

    // Test isEmpty after all elements are dequeued
    assert(queue.isEmpty());

    // Test exception on dequeue from empty queue
    try
    {
        queue.dequeue();
        assert(false); // This line should not be reached
    }
    catch (const std::runtime_error &e)
    {
        std::cout << "Caught exception: " << e.what() << std::endl;
    }

    std::cout << "All tests passed!" << std::endl;
    return 0;
}
