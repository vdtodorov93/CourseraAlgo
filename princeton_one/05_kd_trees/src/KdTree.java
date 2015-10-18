import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static final Point2D FARAWAY_POINT = new Point2D(Double.MAX_VALUE / 4, Double.MAX_VALUE / 4);

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(p, true);
            size++;
            return;
        }

        Node x = root;
        while(x != null) {
            int cmp;
            if (x.isHorizontal()) {
                cmp = Point2D.X_ORDER.compare(p, x.getPoint());
            } else {
                cmp = Point2D.Y_ORDER.compare(p, x.getPoint());
            }
            if (cmp < 0) { //set left
                if (x.getLeft() != null) {
                    x = x.getLeft();
                } else {
                    Node node = new Node(p, !x.isHorizontal());
                    x.setLeft(node);
                    return;
                }
            } else { //set right
                if (x.getRight() != null) {
                    x = x.getRight();
                } else {
                    Node node = new Node(p, !x.isHorizontal());
                    x.setRight(node);
                    return;
                }
            }
        }

        size++;
    }

    public boolean contains(Point2D p) {
        Node x = root;
        while(x != null) {
            if (x.getPoint().equals(p)) {
                return true;
            }
            int cmp;
            if (x.isHorizontal()) {
                //compare by X
                cmp = Point2D.X_ORDER.compare(p, x.getPoint());
            } else {
                //compare by Y
                cmp = Point2D.Y_ORDER.compare(p, x.getPoint());
            }

            if (cmp < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) {
            return;
        }

        x.getPoint().draw();
        draw(x.getLeft());
        draw(x.getRight());
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if(isEmpty()) {
            return null;
        }
        List<Point2D> inRange = new ArrayList<>();
        put(root, rect, inRange);
        return inRange;
    }

    private void put(Node x, RectHV rect, List<Point2D> inRange) {
        if (x == null) {
            return;
        }

        Point2D point = x.getPoint();
        if (rect.contains(point)) {
            inRange.add(point);
        }

        if (x.isHorizontal()) {
            // x
            if(rect.xmin() <= point.x()) {
                put(x.left, rect, inRange);
            }
            if(rect.xmax() >= point.x()) {
                put(x.right, rect, inRange);
            }
        } else {
            // y
            if(rect.ymin() <= point.y()) {
                put(x.left, rect, inRange);
            }
            if(rect.ymax() >= point.y()) {
                put(x.right, rect, inRange);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }

        return nearest(root, p);

    }

    private Point2D nearest(Node x, Point2D point) {
        if (x == null) {
            return null;
        }
        Point2D result;

        int cmp;
        if (x.isHorizontal()) {
            // by X
            cmp = Point2D.X_ORDER.compare(point, x.getPoint());
        } else {
            // by Y
            cmp = Point2D.Y_ORDER.compare(point, x.getPoint());
        }

        if(cmp < 0) {
            result = nearest(x.left, point);
        } else {
            result = nearest(x.right, point);
        }

        if(result == null || result.distanceTo(point) > x.getPoint().distanceTo(point)) {
            return x.getPoint();
        } else {
            return result;
        }
    }

    private class Node {
        private Point2D point;
        private boolean isHorizontal;
        private Node left;
        private Node right;

        public Node(Point2D point, boolean isHorizontal) {
            setPoint(point);
            setIsHorizontal(isHorizontal);
        }

        public Point2D getPoint() {
            return point;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }

        public boolean isHorizontal() {
            return isHorizontal;
        }

        public void setIsHorizontal(boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node node) {
            left = node;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node node) {
            right = node;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();

        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.4, 0.4));
        tree.insert(new Point2D(0.4, 0.5));


        tree.insert(new Point2D(0.6, 0.5));
        tree.insert(new Point2D(0.5, 0.8));
        tree.insert(new Point2D(0.1, 0.1));

        Iterable<Point2D> res = tree.range(new RectHV(0.39, 0.39, 0.5, 0.5));
        for(Point2D p: res) {
            System.out.println(p);
        }
        tree.draw();


    }
}