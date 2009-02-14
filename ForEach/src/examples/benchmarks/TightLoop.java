package examples.benchmarks;

import static examples.benchmarks.Benchmark.LEN;

public class TightLoop implements Runnable {

    public void run() {
        Benchmark ben = new Benchmark(this);
        ben.begin();
        long tally = 0L;
        for (int x = 0; x < LEN; x++) {
            for (int y = 0; y < LEN; y++) {
                double value = Math.random();
                if (value > 0.8) tally++;
            }
        }
        ben.end();
        System.out.println(tally);
    }
    
}
