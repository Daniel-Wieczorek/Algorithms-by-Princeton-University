import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        ValidateInput(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        CheckDuplicates(pointsCopy);

        for (Point origin : points) {
            Point[] sortedBySlope = points.clone();
            Arrays.sort(sortedBySlope, origin.slopeOrder());
            int count = 1;
            for (int j = 1; j < sortedBySlope.length; j++) {
                if (origin.slopeTo(sortedBySlope[j]) == origin.slopeTo(sortedBySlope[j - 1])) {
                    count++;
                    if (count >= 3 && j == sortedBySlope.length - 1) {
                        addSegment(origin, sortedBySlope, j - count + 1, j);
                    }
                } else {
                    if (count >= 3) {
                        addSegment(origin, sortedBySlope, j - count, j - 1);
                    }
                    count = 1;
                }
            }
        }
    }

    private void addSegment(Point origin, Point[] sortedBySlope, int start, int end) {
        Point[] collinearPoints = new Point[end - start + 2];
        collinearPoints[0] = origin;
        for (int i = start; i <= end; i++) {
            collinearPoints[i - start + 1] = sortedBySlope[i];
        }
        Arrays.sort(collinearPoints);
        if (origin.compareTo(collinearPoints[0]) == 0) {
            segments.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]));
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    private void ValidateInput(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Passed invalid array of points");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Null point in array");
            }
        }
    }

    private void CheckDuplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points found");
            }
        }
    }

}