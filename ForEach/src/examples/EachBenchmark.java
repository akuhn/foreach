package examples;

import static ch.akuhn.util.Interval.range;

import java.util.Iterator;
import java.util.Random;


public class EachBenchmark
        implements Iterable<EachBenchmark>, Iterator<EachBenchmark> {

    public static final class Immutable {
        public final int index;
        public final EachBenchmark value;

        public Immutable(EachBenchmark eachBenchmark, int index) {
            this.index = index;
            this.value = eachBenchmark;
        }
    }

    static class ImmutableIter implements Iterable<Immutable>, Iterator<Immutable> {

        private EachBenchmark array;
        private int index;

        public ImmutableIter(EachBenchmark eachBenchmark) {
            this.array = eachBenchmark;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return array.hasNext();
        }

        @Override
        public Iterator<Immutable> iterator() {
            return this;
        }

        @Override
        public Immutable next() {
            return new Immutable(array.next(), index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static final class Mutable {
        public int index;

        public EachBenchmark value;

        public Mutable() {
            // TODO Auto-generated constructor stub
        }
        public Mutable(EachBenchmark value, int i) {
            this.index = i;
            this.value = value;
        }
    }
    static class MutableIter implements Iterable<Mutable>, Iterator<Mutable> {

        private EachBenchmark array;
        private Mutable each;
        private int index;

        public MutableIter(EachBenchmark eachBenchmark) {
            this.each = new Mutable();
            this.array = eachBenchmark;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return array.hasNext();
        }

        @Override
        public Iterator<Mutable> iterator() {
            return this;
        }

        @Override
        public Mutable next() {
            each.value = array.next();
            each.index = index++;
            return each;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    static class MutableIter2 implements Iterable<Mutable>, Iterator<Mutable> {

        private EachBenchmark array;
        private int index;

        public MutableIter2(EachBenchmark eachBenchmark) {
            this.array = eachBenchmark;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return array.hasNext();
        }

        @Override
        public Iterator<Mutable> iterator() {
            return this;
        }

        @Override
        public Mutable next() {
            return new Mutable(array.next(), index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static Iterable<Immutable> immutable(EachBenchmark eachBenchmark) {
        return new ImmutableIter(eachBenchmark);
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("start!");
        Thread.sleep(500);
        
        for (int kase: range(0,4)) {
            double total = 0;
            double sum = 0;
        for (int _ : range(10)) {

            long tiem;

            tiem = System.nanoTime();
            sum = 0;

            switch (kase) {
            case 0:
                for (Mutable each : mutable(new EachBenchmark()))
                    sum += each.value.n;
                break;
            case 1:
                for (Mutable each : mutable(new EachBenchmark()))
                    sum += each.value.n;
                break;
            case 2:
                for (Immutable each : immutable(new EachBenchmark()))
                    sum += each.index + each.value.n;
                break;
            case 3:
                for (Mutable each : mutable2(new EachBenchmark()))
                    sum += each.index + each.value.n;
                break;
            }
            total += (System.nanoTime() - tiem);
            
        }            System.out.println(total);

        System.out.println(sum*1e-20);
        }
    }

    public static Iterable<Mutable> mutable(EachBenchmark eachBenchmark) {
        return new MutableIter(eachBenchmark);
    }

    public static Iterable<Mutable> mutable2(EachBenchmark eachBenchmark) {
        return new MutableIter2(eachBenchmark);
    }

    private long n = 10*1000*1000;

    @Override
    public Iterator<EachBenchmark> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return n  > 0;
    }

    @Override
    public EachBenchmark next() {
        n--; return this;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
