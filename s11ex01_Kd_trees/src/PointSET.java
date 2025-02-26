import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    public PointSET() // construct an empty set of points
    {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() // number of points in the set
    {
        return points.size();
    }

    public void insert(Point2D p) // add the point to the set (if it is not already in the set)
    {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        points.add(p);
    }

    public boolean contains(Point2D p) // does the set contain point p?
    {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return points.contains(p);
    }

    public void draw() // draw all points to standard draw
    {
        for (Point2D point : points) {
            point.draw();
        }

    }

    public Iterable<Point2D> range(RectHV rect) // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Point2D> pointsArray = new ArrayList<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                pointsArray.add(point);
            }
        }

        return pointsArray;
    }

    public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (this.isEmpty()) {
            return null;
        }

        Point2D nearest = null;
        double closestDistance = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double distanceToPoint = point.distanceSquaredTo(p);
            if (distanceToPoint < closestDistance) {
                nearest = point;
                closestDistance = distanceToPoint;
            }
        }

        return nearest;
    }

    // Quick unit tests using asserts to quickly validate implementation for TDD
    public static void main(String[] args) {
        System.out.println("Running PointSET tests...");

        PointSET pointSet = new PointSET();

        // Test isEmpty() and size()
        assert pointSet.isEmpty() : "Error: Newly created set should be empty";
        assert pointSet.size() == 0 : "Error: Newly created set should have size 0";

        // Test insert() and contains()
        Point2D p1 = new Point2D(0.1, 0.2);
        Point2D p2 = new Point2D(0.3, 0.7);
        Point2D p3 = new Point2D(0.5, 0.5);
        pointSet.insert(p1);
        pointSet.insert(p2);
        pointSet.insert(p3);

        assert !pointSet.isEmpty() : "Error: Set should not be empty after inserting elements";
        assert pointSet.size() == 3 : "Error: Set should contain 3 points";
        assert pointSet.contains(p1) : "Error: Set should contain point (0.1, 0.2)";
        assert pointSet.contains(p2) : "Error: Set should contain point (0.3, 0.7)";
        assert !pointSet.contains(new Point2D(0.9, 0.9)) : "Error: Set should not contain an uninserted point";

        // Test range()
        RectHV rect = new RectHV(0.0, 0.0, 0.4, 0.6);
        int count = 0;
        for (Point2D point : pointSet.range(rect)) {
            count++;
        }
        assert count == 2 : "Error: Range query should return 2 points";

        // Test nearest()
        Point2D query = new Point2D(0.6, 0.6);
        Point2D nearest = pointSet.nearest(query);
        assert nearest.equals(p3) : "Error: Nearest point to (0.6, 0.6) should be (0.5, 0.5)";

        // Test nearest on an empty set
        PointSET emptySet = new PointSET();
        assert emptySet.nearest(query) == null : "Error: Nearest should return null for an empty set";

        // Test range on an empty set
        assert !emptySet.range(rect).iterator().hasNext() : "Error: Range on empty set should return empty iterable";

        System.out.println("All tests passed!");
    }

}
