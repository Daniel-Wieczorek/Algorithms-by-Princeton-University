import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x coordinate of the point
     * @param y coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard
     * draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if
     * the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 -
     * x0). For completeness, the slope is defined to be +0.0 if the line segment
     * connecting the two points is horizontal; Double.POSITIVE_INFINITY if the line
     * segment is vertical; and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1)
     * are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY; // Degenerate line
        }
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY; // Vertical line
        }
        if (this.y == that.y) {
            return +0.0; // Horizontal line
        }
        return (double) (that.y - this.y) / (that.x - this.x); // Regular slope
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally,
     * the invoking point (x0, y0) is less than the argument point (x1, y1) if and
     * only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0
     *         = x1 and y0 = y1); a negative integer if this point is less than the
     *         argument point; and a positive integer if this point is greater than
     *         the argument point
     */
    public int compareTo(Point that) {
        return (this.y == that.y) ? Integer.compare(this.x, that.x) : Integer.compare(this.y, that.y);
    }

    /**
     * Compares two points by the slope they make with this point. The slope is
     * defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point point1, Point point2) {
            return Double.compare(slopeTo(point1), slopeTo(point2));
        }
    }

    /**
     * Returns a string representation of this point. This method is provide for
     * debugging; your program should not rely on the format of the string
     * representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // Test cases
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 0);
        Point p4 = new Point(0, 2);
        Point p5 = new Point(0, 0); // Same as p1

        // Test slopeTo
        assert p1.slopeTo(p2) == 1.0 : "Test failed: slope p1 -> p2";
        assert p1.slopeTo(p3) == 0.0 : "Test failed: slope p1 -> p3";
        assert p1.slopeTo(p4) == Double.POSITIVE_INFINITY : "Test failed: slope p1 -> p4";
        assert p1.slopeTo(p5) == Double.NEGATIVE_INFINITY : "Test failed: slope p1 -> p5";

        // Test compareTo
        assert p1.compareTo(p2) < 0 : "Test failed: p1 < p2";
        assert p2.compareTo(p3) > 0 : "Test failed: p2 > p3";
        assert p1.compareTo(p5) == 0 : "Test failed: p1 == p5";

        // Test slopeOrder comparator
        Comparator<Point> comparator = p1.slopeOrder();
        assert comparator.compare(p2, p3) > 0 : "Test failed: slope p2 > slope p3 from p1";
        assert comparator.compare(p3, p4) < 0 : "Test failed: slope p3 < slope p4 from p1";

        System.out.println("All tests passed!");
    }
}