package examples.benchmarks;

import static examples.benchmarks.Benchmark.LEN;

import java.util.Iterator;

import examples.benchmarks.OneBillionPixels.Pixel;

public class OneBillionPixels implements Runnable, Iterable<Pixel> {

    public static class Pixel {
        public int x, y;
        public double value;
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
            if (each.value > 0.8) tally++;
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
            Pixel p = new Pixel(x, y, Benchmark.next());
            if (++x == LEN) { x = 0; ++y; }
            return p;
        }

        public void remove() {
            throw null;
        }
        
    }
    
}
