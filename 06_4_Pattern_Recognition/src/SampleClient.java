import java.io.File;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class SampleClient {
    private static String getTestPath() {
        String testDir = System.getProperty("user.dir");
        testDir = testDir + "/06_4_Pattern_Recognition/test/test_data/";
        System.out.println("Test Directory: " + testDir);
        return testDir;
    }

    private static void findTxtFiles(File directory, ArrayList<String> txtFilePaths) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Given path is not directory!");
        }

        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("Error accessing directory!");
        }

        for (final var file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                txtFilePaths.add(file.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        // ArrayList<String> txtFilePaths = new ArrayList<>();
        // findTxtFiles(new File(getTestPath()), txtFilePaths);

            String fileName = "/home/daniel/workareas/Algorithms-by-Princeton-University/06_4_Pattern_Recognition/test/test_data/rs1423.txt";
            // Read the n points from a file
            In in = new In(fileName);
            int n = in.readInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }

            // Draw the points
            StdDraw.clear(); // Clear the canvas before drawing new points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show();

            // Print and draw the line segments
            // var collinear = new BruteCollinearPoints(points);
            var collinear = new FastCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
    }
}
