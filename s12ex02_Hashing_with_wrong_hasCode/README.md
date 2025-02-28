## Hashing with wrong hashCode() or equals()

Suppose that you implement a data type `Car` for use in a
`java.util.HashMap`

- Describe what happens if you override `hashCode()` but not `equals()`.
- Describe what happens if you override `equals()` but not `hashCode()`.
- Describe what happens if you override `hashCode()` but implement  `public
  boolean equals(Car that)` instead of  `public boolean equals(Object
  that)`.

### Algorithm/Solution

The easiest way will be just to implement it in Java, run and see what is going on.
Example test code:

```Java
import java.util.HashMap;

class Car {
    String brand;

    public Car(String brand) {
        this.brand = brand;
    }

    // Use case 1: only implementation below:
    @Override
    public int hashCode() {
        return brand.hashCode();
    }
    // -------------------------------------

    // Use case 2: only implementation below:
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Car))
            return false;
        Car other = (Car) obj;
        return this.brand.equals(other.brand);
    }
    // -------------------------------------

    // Use case 3: only implementation below:
    @Override
    public int hashCode() {
        return brand.hashCode();
    }

    // INCORRECT: This does not override Object.equals()
    public boolean equals(Car that) {
        return this.brand.equals(that.brand);
    }
    // -------------------------------------

    public static void main(String[] args) {

        /*
         * Use case 1: override hashCode() but not equals():
         * In this case default implementation from Object will be taken so objects will
         * be same if point to the same memory location.
         * It means even if two objects will have same hashCode() they will be not equal
         * if not they are same instance.
         */
        HashMap<Car, String> mapCase1 = new HashMap<>();
        Car a1 = new Car("VW");
        Car a2 = new Car("VW");
        mapCase1.put(a1, "Tiguan");
        mapCase1.put(a2, "Galaxy");
        System.out.println(mapCase1.size()); // Output: 2 (should be 1 if equals() was overridden)

        /*
         * Use case 2: override equals() but not hashCode():
         * In this case default implementation from Object will be taken so objects will
         * have hash generated based on memory location.
         * It means even if two objects are equal() they may have different hash!
         */
        HashMap<Car, String> mapCase2 = new HashMap<>();
        Car b1 = new Car("VW");
        Car b2 = new Car("VW");

        mapCase2.put(b1, "Gold");

        System.out.println(mapCase2.containsKey(b2)); // Output: false (should be true if hashCode() was overridden)

        /*
         * Use case 3: We are not overriding but we are overloading. So our equals()
         * implementation will not be ever called I think. Instead not override version
         * will be called.
         */
    }

}

```

### Complexity

N/A