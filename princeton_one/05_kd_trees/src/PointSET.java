import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : points) {
            point2D.draw();
        }

    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> inRange = new ArrayList<>();
        for (Point2D point2D : points) {
            if (rect.contains(point2D)) {
                inRange.add(point2D);
            }
        }

        return inRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        double minDist = Double.MAX_VALUE;
        Point2D nearest = null;

        for(Point2D point2D: points) {
            double dist = point2D.distanceTo(p);
            if (dist < minDist) {
                minDist = dist;
                nearest = point2D;
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET set = new PointSET();
        for(double i = 0; i < 0.66; i += 0.1) {
            Point2D point = new Point2D(i * 1.5, i * 1.25);
            Point2D point2 = new Point2D(i * 0.5 + 0.1, i * 0.25 + 0.2);
            set.insert(point);
            set.insert(point2);
        }

        RectHV rect = new RectHV(0.1, 0.1, 0.3, 0.4);
//        for(Point2D point: set.range(rect)) {
//            System.out.println(point);
//        }
        set.draw();
        rect.draw();
        Point2D mark = new Point2D(0.33, 0);
        Point2D n = set.nearest(mark);
        System.out.println("Nearest: " + n);
        System.out.println(n.distanceTo(mark));
    }
}