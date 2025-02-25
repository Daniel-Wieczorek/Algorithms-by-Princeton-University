## Java autoboxing and equals()

Web tracking. Suppose that you are tracking `n` web sites and `m` users and you
want to support the following API:

- User visits a website.
- How many times has a given user visited a given site?

What data structure or data structures would you use?

Hint: maintain a symbol table of symbol tables.

### Algorithm/Solution

We need data structure with good insert and lookups (best will be O(1), maybe O(logN)). The easiest way to do that will be using
symbol table described in the course. In this case we will need nested symbol table. So:
- Outer symbol table will map `m` users (usernames, UUID)
- Inner symbol table will map websites (URLs) with the cont of visits.

So that will be something like:

```Java
HashMap<String, HashMap<String, Integer>>
```

in Cpp we will need to use `unordered_map_` to store it and in Rust that will be `HashMap` too.


### Complexity

N/A
