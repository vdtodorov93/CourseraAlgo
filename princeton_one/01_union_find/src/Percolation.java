import java.util.Random;

public class Percolation {

    //FIELD VALUES
    private static final short BLOCKED = 0;
    private static final short OPEN = 1;
    private static final short FULL = 2;

    // top and bottom sources
    private int topId;
    private int bottomId;

    private short[] gameField;
    private int[] id;
    private int N;
    private int opened;

    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        this.N = n;
        int N = n + 1;
        int maxSize = N * (2 + N);
        gameField = new short[maxSize];
        id = new int[maxSize];
        for (int i = 0; i < N * N; i++) {
            gameField[i] = BLOCKED;
            id[i] = i;
        }
        topId = N * N + 1;
        bottomId = N * N + 2;
        id[topId] = topId;
        id[bottomId] = bottomId;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validate(i, j);
        if (!isOpen(i, j)) {
            gameField[getId(i, j)] = OPEN;
            opened++;
            if(i == 1) {
                union(getId(i, j), topId);
            } else if (i == N - 1) {
                union(getId(i, j), bottomId);
            }
            unionIfOpen(i, j, i + 1, j);
            unionIfOpen(i, j, i - 1, j);
            unionIfOpen(i, j, i, j + 1);
            unionIfOpen(i, j, i, j - 1);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return gameField[getId(i, j)] == OPEN;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i, j);
        return gameField[getId(i, j)] == FULL;
    }

    // does the system percolate?
    public boolean percolates() {
        return root(topId) == root(bottomId);
    }

    private int getOpened() {
        return opened;
    }

    // ---------------------------------------
    //            UNION-FIND
    // ---------------------------------------
    private int root(int element) {
        while (element != id[element]) {
            id[element] = id[id[element]];
            element = id[element];
        }
        return element;
    }

    private void unionIfOpen(int openedRow, int openedCol, int row, int col) {
        if(row < 1 || row >= N || col < 1 || col >= N) return;
        if (isOpen(row, col)) {
            unionEl(openedRow, openedCol, row, col);
        }
    }

    private void unionEl(int row1, int col1, int row2, int col2) {
        union(getId(row1, col1), getId(row2, col2));
    }

    private void union(int first, int second) {
        id[root(first)] = root(second);
    }

    private boolean connected(int row1, int col1, int row2, int col2) {
        return root(getId(row1, col1)) == root(getId(row2, col2));
    }

    private int getId(int row, int col) {
        return row * N + col;
    }

    private int getRowFromX(int x) {
        return x / N;
    }

    private int getColFromX(int x) {
        return x % N;
    }

    private void print() {
        for (int i = 0; i < gameField.length; i++) {
            if (i % N == 0 && i != 0) {
                System.out.println();
            }
            System.out.format("%4d ", gameField[i]);

        }
    }

    private void printIds() {
        for (int i = 0; i < id.length; i++) {
            if (i % N == 0 && i != 0) {
                System.out.println();
            }
            System.out.format("%4d ", id[i]);

        }
    }


    // ---------------------------------------
    //              TESTS
    // ---------------------------------------

    public static void main(String[] args) {
  //      test();
//        Percolation per = new Percolation(5);
//        per.open(1, 3);
//        per.open(2, 3);
//        per.open(2, 2);
//        per.open(3, 2);
//        per.open(4, 2);
//        per.open(4, 3);
//        per.open(5, 3);
//        per.print();
//        System.out.println();
//        System.out.println(per.percolates());
//        System.out.println("----------------------------------");
//        per.printIds();
    }

    // ---------------------------------------
    //              VALIDATORS
    // ---------------------------------------


    private void validate(int i, int j) {
        validateSingleDimension(i);
        validateSingleDimension(j);
    }

    private void validateSingleDimension(int i) {
        if (i <= 0 || i >= this.N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static void test() {
        int n = 80;
        Percolation p = new Percolation(n);
        Random rn = new Random();
        while(!p.percolates()) {
            int i = rn.nextInt(n) + 1;
            int j = rn.nextInt(n) + 1;
            p.open(i, j);
        }
        System.out.println("TEST FINISHED:");
        System.out.println("RATIO:" + p.getOpened() / ((double) n * n));
    }
}
