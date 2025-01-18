import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        ValidateInput(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        CheckDuplicates(pointsCopy);

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    final double baseSlope = pointsCopy[p].slopeTo(pointsCopy[q]);
                    if (baseSlope == pointsCopy[p].slopeTo(pointsCopy[r])) {
                        for (int s = r + 1; s < pointsCopy.length; s++) {
                            if (baseSlope == pointsCopy[p].slopeTo(pointsCopy[s])) {
                                Point[] collinearPoints = { pointsCopy[p], pointsCopy[q], pointsCopy[r], pointsCopy[s] };
                                Arrays.sort(collinearPoints);
                                segments.add(new LineSegment(collinearPoints[0], collinearPoints[3]));
                            }
                        }
                    }
                }
            }
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
