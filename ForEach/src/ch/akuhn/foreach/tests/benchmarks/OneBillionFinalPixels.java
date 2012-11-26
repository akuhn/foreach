package ch.akuhn.foreach.tests.benchmarks;

import static ch.akuhn.foreach.tests.benchmarks.Benchmark.ARRAY;
import static ch.akuhn.foreach.tests.benchmarks.Benchmark.BUSY;
import static ch.akuhn.foreach.tests.benchmarks.Benchmark.LEN;
import static ch.akuhn.foreach.tests.benchmarks.Benchmark.RANDOM;

import java.util.Iterator;

import ch.akuhn.foreach.tests.benchmarks.OneBillionFinalPixels.Pixel;


public class OneBillionFinalPixels implements Runnable, Iterable<Pixel> {

    public static class Pixel {
        public final int x;
        public final int y;
        public final double value;
        public Pixel(int x, int y, double value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
    
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
        return new Iter();
    }

    public static class Iter implements Iterator<Pixel> {

        private int x, y;
        
        public boolean hasNext() {
            return y < LEN;
        }

        public Pixel next() {
            Pixel p = new Pixel(x, y, ARRAY ? RANDOM[x][y] : Benchmark.next());
            if (++x == LEN) { x = 0; ++y; }
            return p;
        }

        public void remove() {
            throw null;
        }
        
    }
    
}
