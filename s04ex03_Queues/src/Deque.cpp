/**
 * @file deque.hpp
 * @brief Implementation of a generic double-ended queue (Deque) in C++.
 *
 * This Deque supports constant time operations for:
 * - Adding elements to the front or back: O(1)
 * - Removing elements from the front or back: O(1)
 * - Accessing size and checking if empty: O(1)
 *
 * Memory usage is efficient, adhering to the requirement of 48n + 192 bytes per n elements.
 */

#include <iostream>
#include <memory>
#include <stdexcept>
#include <cassert>

/**
 * @class Deque
 * @brief A double-ended queue supporting operations from both ends in constant time.
 * @tparam T The type of elements stored in the Deque.
 */
template <typename T>
class Deque
{
private:
    /**
     * @struct Node
     * @brief Represents a node in the Deque.
     */
    struct Node
    {
        T data;        ///< The value stored in the node.
        Node *prev;    ///< Pointer to the previous node.
        Node *next;    ///< Pointer to the next node.

        /**
         * @brief Constructs a Node with the given value.
         * @param value The value to store in the node.
         */
        Node(const T &value)
            : data{value}, prev{nullptr}, next{nullptr} {}
    };

    Node *head_;       ///< Pointer to the front of the Deque.
    Node *tail_;       ///< Pointer to the back of the Deque.
    std::uintmax_t size_; ///< The number of elements in the Deque.

public:
    /**
     * @brief Constructs an empty Deque.
     */
    Deque()
        : head_{nullptr}, tail_{nullptr}, size_{0U} {}

    /**
     * @brief Destructor that frees all allocated nodes.
     */
    ~Deque()
    {
        while (head_ != nullptr)
        {
            Node* tmp = head_;
            head_ = head_->next;
            delete tmp;
        }
    }

    /**
     * @brief Checks if the Deque is empty.
     * @return True if the Deque is empty, otherwise false.
     */
    bool isEmpty() const noexcept
    {
        return size_ == 0;
    }

    /**
     * @brief Returns the number of items in the Deque.
     * @return The size of the Deque.
     */
    std::uintmax_t size() const noexcept
    {
        return size_;
    }

    /**
     * @brief Adds an item to the front of the Deque.
     * @param item The item to add.
     */
    void addFirst(const T &item) noexcept
    {
        auto new_node = std::make_unique<Node>(item);

        if (head_ == nullptr) // Empty Deque
        {
            tail_ = new_node.get();
        }
        else
        {
            new_node->next = head_;
            head_->prev = new_node.get();
        }
        head_ = new_node.release();
        ++size_;
    }

    /**
     * @brief Adds an item to the back of the Deque.
     * @param item The item to add.
     */
    void addLast(const T &item) noexcept
    {
        auto new_node = std::make_unique<Node>(item);

        if (tail_ == nullptr) // Empty Deque
        {
            head_ = new_node.get();
        }
        else
        {
            new_node->prev = tail_;
            tail_->next = new_node.get();
        }

        tail_ = new_node.release();
        ++size_;
    }

    /**
     * @brief Removes and returns the item from the front of the Deque.
     * @return The item from the front.
     * @throws std::out_of_range If the Deque is empty.
     */
    T removeFirst()
    {
        if (isEmpty())
        {
            throw std::out_of_range("Dequeue is empty!");
        }

        Node *tmp = head_;
        head_ = head_->next;

        if (head_ == nullptr) // Removed last element
        {
            tail_ = nullptr;
        }
        else
        {
            head_->prev = nullptr;
        }

        const T result = tmp->data;
        delete tmp;
        --size_;
        return result;
    }

    /**
     * @brief Removes and returns the item from the back of the Deque.
     * @return The item from the back.
     * @throws std::out_of_range If the Deque is empty.
     */
    T removeLast()
    {
        if (isEmpty())
        {
            throw std::out_of_range("Dequeue is empty!");
        }

        Node *tmp = tail_;
        tail_ = tail_->prev;

        if (tail_ == nullptr) // Removed last element
        {
            head_ = nullptr;
        }
        else
        {
            tail_->next = nullptr;
        }

        const T result = tmp->data;
        delete tmp;
        --size_;
        return result;
    }

    /**
     * @class Iterator
     * @brief A bidirectional iterator for Deque.
     */
    class Iterator
    {
    public:
        using iterator_category = std::bidirectional_iterator_tag;
        using difference_type = std::ptrdiff_t;

        /**
         * @brief Constructs an iterator for the given node.
         * @param node The starting node for the iterator.
         */
        Iterator(Node *node) : current_(node) {}

        /**
         * @brief Dereferences the iterator to access the current element.
         * @return A reference to the current element.
         */
        T &operator*() const { return current_->data; }

        /**
         * @brief Moves the iterator to the next element.
         * @return A reference to the updated iterator.
         */
        Iterator &operator++()
        {
            if (current_ != nullptr)
            {
                current_ = current_->next;
            }
            return *this;
        }

        /**
         * @brief Moves the iterator to the previous element.
         * @return A reference to the updated iterator.
         */
        Iterator &operator--()
        {
            if (current_ != nullptr)
            {
                current_ = current_->prev;
            }
            return *this;
        }

        /**
         * @brief Checks if two iterators are equal.
         * @param other The iterator to compare against.
         * @return True if the iterators are equal, otherwise false.
         */
        bool operator==(const Iterator &other) const { return current_ == other.current_; };

        /**
         * @brief Checks if two iterators are not equal.
         * @param other The iterator to compare against.
         * @return True if the iterators are not equal, otherwise false.
         */
        bool operator!=(const Iterator &other) const { return current_ != other.current_; };

    private:
        Node *current_; ///< Pointer to the current node in the Deque.
    };

    /**
     * @brief Returns an iterator to the beginning of the Deque.
     * @return An iterator pointing to the first element.
     */
    Iterator begin()
    {
        return Iterator(head_);
    }

    /**
     * @brief Returns an iterator to the end of the Deque.
     * @return An iterator pointing past the last element.
     */
    Iterator end()
    {
        return Iterator(nullptr);
    }

    /**
     * @brief Returns a const iterator to the beginning of the Deque.
     * @return A const iterator pointing to the first element.
     */
    Iterator begin() const
    {
        return Iterator(head_);
    }

    /**
     * @brief Returns a const iterator to the end of the Deque.
     * @return A const iterator pointing past the last element.
     */
    Iterator end() const
    {
        return Iterator(nullptr);
    }
};

/**
 * @brief Tests for the Deque class.
 */
int main()
{
    Deque<int> dq;

    assert(dq.isEmpty() && "Deque should be empty initially.");
    dq.addFirst(10);
    assert(dq.size() == 1 && "Deque size should be 1 after addFirst.");
    dq.addLast(20);
    assert(dq.size() == 2 && "Deque size should be 2 after addLast.");

    assert(dq.removeFirst() == 10 && "First element should be 10.");
    assert(dq.size() == 1 && "Deque size should be 1 after removeFirst.");

    assert(dq.removeLast() == 20 && "Last element should be 20.");
    assert(dq.isEmpty() && "Deque should be empty after removing all elements.");

    std::cout << "All tests passed!\n";
    return 0;
}
