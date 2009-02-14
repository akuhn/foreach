package examples.benchmarks;

import static examples.benchmarks.Benchmark.ARRAY;
import static examples.benchmarks.Benchmark.BUSY;
import static examples.benchmarks.Benchmark.LEN;
import static examples.benchmarks.Benchmark.RANDOM;
import static examples.benchmarks.Benchmark.next;

public class PlainForLoop implements Runnable {

    public void run() {
        Benchmark ben = new Benchmark(this);
        ben.begin();
        long tally = 0L;
        for (int x = 0; x < LEN; x++) {
            for (int y = 0; y < LEN; y++) {
                double value = ARRAY ? RANDOM[x][y] : next();
                if (BUSY) value = Math.sqrt(Math.pow(x-value,2) + Math.pow(y-value,2));
                if (value > 0.8) tally++;
            }
        }
        ben.end();
        System.out.println(tally);
    }
    
}
