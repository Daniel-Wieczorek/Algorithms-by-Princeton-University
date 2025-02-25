## Java autoboxing and equals()

Consider  two `double` values `a` and `b` their corresponding  `Double` values
`x` and `y`:

- Find values such that (a==b) is true but x.equals(y) is  false.
- Find values such that (a==b) is false but x.equals(y) is true.

Hint:
IEEE floating point arithmetic has some peculiar rules for `0.0`, `−0.0`, and
`NaN`. Java requires that `equals()` implements an equivalence relation.

### Algorithm

In general `==` will compare primitive double values numerically (let's say in primitive way).
`equals()` will compare `Double` objects as Java specification says:
https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object- and implementing
Java equivalence relation

What is equivalence relation in `Java`: relation that satisfies three key
properties:  
1. **Reflexivity**: `x.equals(x)` must always be `true`.  
2. **Symmetry**: If `x.equals(y)` is `true`, then `y.equals(x)` must also be
   `true`.  
3. **Transitivity**: `x.equals(y)` is `true` and `y.equals(z)` is `true`, then
   `x.equals(z)` must also be `true`.  

These properties ensure that `equals()` behaves consistently when comparing
objects. For `Double` objects, Java overrides the `equals()` method to maintain
this equivalence relation, even though IEEE 754 floating-point arithmetic has
some inconsistencies (like `NaN` not being equal to itself under `==`).

Hint made life easier as we are searching only around 0.0, -0.0 and NaN.

Ad.1: values such that (a==b) is true but x.equals(y) is  false

```Java
double a = 0.0;
double b = -0.0;

Double x = a;
Double y = b;
```

`a == b` returns true because IEEE 754 defines them as numerically equal.
`Double.valueOf(0.0).equals(Double.valueOf(-0.0))` will return false as it can 
differentiate between these two values.

Ad.2: values such that (a==b) is false but x.equals(y) is true.

```Java
double a = Double.NaN;
double b = Double.NaN;

Double x = a;
Double y = b;
```

`a == b` returns false because IEEE 754 defines NaN as never equal to any other
value (including itself). `Double.valueOf(NaN).equals(Double.valueOf(NaN))`
returns true, because Java requires equals() to be an equivalence relation, so
NaN.equals(NaN) must be true.

### Complexity

N/A