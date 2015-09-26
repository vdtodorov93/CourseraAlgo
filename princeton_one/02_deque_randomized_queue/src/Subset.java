import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by vasil on 9/26/15.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String str;
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while((str = StdIn.readString()) != null) {
            rq.enqueue(str);
        }

        for(int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
