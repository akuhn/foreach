package ch.akuhn.foreach.benchmarks;

import static ch.akuhn.foreach.benchmarks.Benchmark.ARRAY;
import static ch.akuhn.foreach.benchmarks.Benchmark.BUSY;
import static ch.akuhn.foreach.benchmarks.Benchmark.LEN;
import static ch.akuhn.foreach.benchmarks.Benchmark.RANDOM;
import static ch.akuhn.foreach.benchmarks.Benchmark.next;

import java.util.Iterator;

import ch.akuhn.foreach.benchmarks.OneRunningPixel.Pixel;


public class OneRunningPixel implements Runnable, Iterable<Pixel> {

    public void run() {
        Benchmark ben = new Benchmark(this);
        ben.begin();
        long tally = 0;
        for (Pixel each: this) {
            double value = each.value;
            if (BUSY) value = Math.sqrt(Math.pow(each.x-value,2) + Math.pow(each.y-value,2));
            if (value > 0.8) tally++;
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
            value = ARRAY ? RANDOM[x][y] : Benchmark.next();
            if (++x0 == LEN) { x0 = 0; ++y0; }
            return this;
        }

        public void remove() {
            throw null;
        }
        
    }
    
}
