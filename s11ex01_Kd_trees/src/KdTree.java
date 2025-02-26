// TODO: Fix me, 83/100, try to reach at least 95/100!
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node left; // the left/bottom subtree
        private Node right; // the right/top subtree
        private int numOfNodes; // number of nodes in subtree
        private boolean orientation;

        Node(Point2D p, RectHV rect, int numOfNode, boolean orientation) {
            this.p = p;
            this.rect = rect;
            this.numOfNodes = numOfNode;
            this.orientation = orientation;
        }
    }

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private Node rootNode;

    public KdTree() // construct an empty set of points
    {
        rootNode = null;
    }

    // Drawings
    private void draw(Node node) {
        // Stop condition
        if (node == null) {
            return;
        }

        drawPoint(node);
        if (node.orientation == VERTICAL) {
            drawVertical(node);
        } else {
            drawHorizontal(node);
        }

        draw(node.left);
        draw(node.right);
    }

    private void prepareDrawing() {
        StdDraw.clear();
        StdDraw.setTitle("2d tree");
    }

    private void drawPoint(Node node) {
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.p.x(), node.p.y());
    }

    private void drawVertical(Node node) {
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
    }

    private void drawHorizontal(Node node) {
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.numOfNodes;
        }
    }

    private Node insert(Node currentNode, RectHV rect, Point2D point, boolean orientation) {
        // Node creation:
        if (currentNode == null) {
            return new Node(point, rect, 1, orientation);
        }

        if (currentNode.p.equals(point)) {
            return currentNode;
        }

        if (currentNode.orientation == VERTICAL) {
            if (point.x() < currentNode.p.x()) {
                RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), currentNode.p.x(), rect.ymax());
                currentNode.left = insert(currentNode.left, leftRect, point, HORIZONTAL);
            } else {
                RectHV rightRect = new RectHV(currentNode.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                currentNode.right = insert(currentNode.right, rightRect, point, HORIZONTAL);
            }
        } else {
            if (point.y() < currentNode.p.y()) {
                RectHV bottomRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), currentNode.p.y());
                currentNode.left = insert(currentNode.left, bottomRect, point, VERTICAL);
            } else {
                RectHV topRect = new RectHV(rect.xmin(), currentNode.p.y(), rect.xmax(), rect.ymax());
                currentNode.right = insert(currentNode.right, topRect, point, VERTICAL);
            }
        }
        currentNode.numOfNodes = size(currentNode.left) + size(currentNode.right) + 1;
        return currentNode;
    }

    private boolean contains(Node currentNode, Point2D point) {
        if (currentNode == null) {
            return false;
        }

        // stop condition
        if (currentNode.p.equals(point)) {
            return true;
        }

        // Same as 4 separate conditions for two use cases:
        boolean compare = currentNode.orientation == VERTICAL ? (point.x() < currentNode.p.x())
                : (point.y() < currentNode.p.y());
        return contains(compare ? currentNode.left : currentNode.right, point);
    }

    private void range(Node node, RectHV rect, List<Point2D> result) {
        if (node == null || !node.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(node.p)) {
            result.add(node.p);
        }

        range(node.left, rect, result);
        range(node.right, rect, result);
    }

    private Point2D nearest(Node node, Point2D p, double closestDistance, Point2D best) {
        if (node == null) {
            return best;
        }

        double dist = node.p.distanceSquaredTo(p);
        if (dist < closestDistance) {
            closestDistance = dist;
            best = node.p;
        }

        Node frontRunner, secondRunner;
        boolean checkLeftFirst = (node.orientation == VERTICAL && p.x() < node.p.x()) ||
                (node.orientation == HORIZONTAL && p.y() < node.p.y());

        if (checkLeftFirst) {
            frontRunner = node.left;
            secondRunner = node.right;
        } else {
            frontRunner = node.right;
            secondRunner = node.left;
        }

        // Search the first subtree
        best = nearest(frontRunner, p, closestDistance, best);
        closestDistance = best.distanceSquaredTo(p); // Update closest distance after first search

        // Prune only if necessary (if the second rectangle could contain a closer
        // point)
        if (secondRunner != null && secondRunner.rect.distanceSquaredTo(p) < closestDistance) {
            best = nearest(secondRunner, p, closestDistance, best);
        }

        return best;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() // number of points in the set
    {
        return size(rootNode);
    }

    public void insert(Point2D p) // add the point to the set (if it is not already in the set)
    {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        rootNode = insert(rootNode, new RectHV(0, 0, 1, 1), p, VERTICAL);
    }

    public boolean contains(Point2D p) // does the set contain point p?
    {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return (contains(rootNode, p));
    }

    public Iterable<Point2D> range(RectHV rect) // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<Point2D> result = new ArrayList<>();
        range(rootNode, rect, result);
        return result;

    }

    public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (rootNode == null) {
            return null;
        }

        return nearest(rootNode, p, rootNode.rect.distanceSquaredTo(p), rootNode.p);
    }

    public void draw() // draw all points to standard draw
    {
        prepareDrawing();
        if (rootNode == null) {
            return;
        }

        draw(rootNode);
        StdDraw.show();
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        // Insert a few points
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));

        // Draw the kd-tree
        kdTree.draw();
        System.out.println("Size: " + kdTree.size());
    }
}
