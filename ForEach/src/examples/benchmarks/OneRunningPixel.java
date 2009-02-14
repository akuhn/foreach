package examples.benchmarks;

import static examples.benchmarks.Benchmark.LEN;

import java.util.Iterator;

import examples.benchmarks.OneRunningPixel.Pixel;

public class OneRunningPixel implements Runnable, Iterable<Pixel> {

    public void run() {
        Benchmark ben = new Benchmark(this);
        ben.begin();
        long tally = 0;
        for (Pixel each: this) {
            if (each.value > 0.8) tally++;
        }
        ben.end();
        System.out.println(tally);
    }

    public Iterator<Pixel> iterator() {
        return new Pixel();
    }

    public static class Pixel implements Iterator<Pixel> {

        private int x0 = 0, y0 = 0;
        public double value;
        public int x, y;
        
        public boolean hasNext() {
            return y0 < LEN;
        }

        public Pixel next() {
            x = x0;
            y = y0;
            value = Benchmark.next();
            if (++x0 == LEN) { x0 = 0; ++y0; }
            return this;
        }

        public void remove() {
            throw null;
        }
        
    }
    
}
