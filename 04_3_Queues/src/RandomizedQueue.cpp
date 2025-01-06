/**
 * @file RandomizedQueue.cpp
 * @brief Implementation of a Randomized Queue using a linked list.
 *
 * A RandomizedQueue is similar to a stack or queue, but the item removed is
 * chosen uniformly at random. This implementation uses a singly linked list for
 * storage.
 *
 * Complexity:
 * - Enqueue: O(1)
 * - Dequeue: O(n) due to traversal for random element removal
 * - Sample: O(n) for traversal to a random element
 * - Iterator creation: O(n)
 * - Iterator next/hasNext: O(1)
 */

#include <iostream>
#include <cassert>
#include <cstdint>
#include <memory>
#include <chrono>
#include <random>
#include <vector>
#include <algorithm>

/**
 * @class RandomizedQueue
 * @brief A queue where elements are dequeued in random order.
 *
 * @tparam T Type of elements stored in the queue.
 */
template <typename T>
class RandomizedQueue
{
private:
    struct Node
    {
        T data;                     // Data stored in the node
        std::shared_ptr<Node> next; // Pointer to the next node

        Node(const T &data) : data{data}, next{nullptr} {}
    };

    std::shared_ptr<Node> head_; // Pointer to the head of the list
    std::shared_ptr<Node> tail_; // Pointer to the tail of the list
    std::uintmax_t size_;        // Number of elements in the queue
    std::random_device rd;       // Random device for seeding
    std::mt19937 rng_;           // Random number generator

    /**
     * @brief Generates a random index in the range [0, size_ - 1].
     * @return Random index.
     */
    std::uintmax_t getRandomNumber()
    {
        std::uniform_int_distribution<> distribution(0, size_ - 1);
        return distribution(rng_);
    }

public:
    /**
     * @brief Constructs an empty randomized queue.
     */
    RandomizedQueue() : head_{nullptr}, tail_{nullptr}, size_{0U}, rng_(rd()) {}

    /**
     * @brief Checks if the queue is empty.
     * @return True if the queue is empty, false otherwise.
     */
    bool isEmpty() const
    {
        return size_ == 0;
    }

    /**
     * @brief Returns the number of elements in the queue.
     * @return Size of the queue.
     */
    std::uintmax_t size() const
    {
        return size_;
    }

    /**
     * @brief Adds an item to the queue.
     * @param item The item to add.
     */
    void enqueue(const T &item)
    {
        auto new_node = std::make_shared<Node>(item);

        if (isEmpty())
        {
            head_ = tail_ = new_node;
        }
        else
        {
            tail_->next = new_node;
            tail_ = new_node;
        }
        ++size_;
    }

    /**
     * @brief Removes and returns a random item from the queue.
     * @return The removed item.
     * @throws std::out_of_range if the queue is empty.
     */
    T dequeue()
    {
        if (isEmpty())
        {
            throw std::out_of_range("Dequeue is empty!");
        }

        const auto element_num = getRandomNumber();
        auto current = head_;

        if (element_num == 0) // Remove the first element
        {
            head_ = head_->next;
            if (head_ == nullptr)
            {
                tail_ = nullptr;
            }
        }
        else // Remove an element in the middle or end
        {
            auto prev = head_;
            for (std::uintmax_t i = 0; i < element_num - 1; ++i)
            {
                prev = prev->next;
            }
            current = prev->next;
            prev->next = current->next;
            if (current == tail_)
            {
                tail_ = prev;
            }
        }
        --size_;
        return current->data;
    }

    /**
     * @brief Returns a random item without removing it.
     * @return The random item.
     * @throws std::out_of_range if the queue is empty.
     */
    T sample()
    {
        if (isEmpty())
        {
            throw std::out_of_range("Dequeue is empty!");
        }
        const auto element_num = getRandomNumber();
        auto current = head_;
        for (std::uintmax_t i = 0; i < element_num; ++i)
        {
            current = current->next;
        }
        return current->data;
    }

    /**
     * @class Iterator
     * @brief Iterator to traverse the randomized queue in random order.
     */
    class Iterator
    {
    private:
        std::vector<T> container_;             // To store iteems
        typename std::vector<T>::iterator it_; // Iterator over the container

    public:
        /**
         * @brief Constructs an iterator from the queue.
         * @param head Head node of the queue.
         */
        Iterator(std::shared_ptr<Node> head)
        {
            while (head != nullptr)
            {
                container_.push_back(head->data);
                head = head->next;
            }
            std::random_device rd;
            std::mt19937 rng_(rd());
            std::shuffle(container_.begin(), container_.end(), rng_);
            it_ = container_.begin();
        }

        /**
         * @brief Checks if there are more items in the iterator.
         * @return True if there are more items, false otherwise.
         */
        bool hasNext() const
        {
            return it_ != container_.end();
        }

        /**
         * @brief Returns the next item in the iterator.
         * @return The next item.
         * @throws std::runtime_error if no more items are available.
         */
        T next()
        {
            if (!hasNext())
            {
                throw std::runtime_error("End of container!");
            }
            return *(it_++);
        }
    };

    /**
     * @brief Creates and returns an iterator for the queue.
     * @return An iterator to traverse the queue in random order.
     */
    Iterator iterator()
    {
        return Iterator(head_);
    }
};

int main()
{
    RandomizedQueue<int> rq;

    // Test enqueue and size
    rq.enqueue(10);
    rq.enqueue(20);
    rq.enqueue(30);
    assert(rq.size() == 3);

    // Test isEmpty
    assert(!rq.isEmpty());

    // Test sample (random, but must be one of the elements)
    int sample = rq.sample();
    assert(sample == 10 || sample == 20 || sample == 30);

    // Test dequeue
    int dequeued = rq.dequeue();
    assert(dequeued == 10 || dequeued == 20 || dequeued == 30);
    assert(rq.size() == 2);

    // Test iterator
    auto it = rq.iterator();
    std::vector<int> iterated;
    while (it.hasNext())
    {
        iterated.push_back(it.next());
    }
    assert(iterated.size() == 2);

    std::cout << "All tests passed!\n";
    return 0;
}
