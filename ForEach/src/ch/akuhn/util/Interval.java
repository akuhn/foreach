package ch.akuhn.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Interval implements Iterable<Integer> {

    private class Iter implements Iterator<Integer> {

        private int index = start;

        @Override
        public boolean hasNext() {
            return step > 0 ? index < end : index > end;
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            int current = index;
            index += step;
            return current;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    public static Interval range(int end) {
        return new Interval(0, end, +1);
    }
    public static Interval range(int start, int end) {
        return new Interval(start, end, +1);
    }

    public static Interval range(int start, int end, int step) {
        return new Interval(start, end, step);
    }

    public final int end;

    public final int start;

    public final int step;

    public Interval(int start, int end, int step) {
        if (step == 0 && start != end) throw new IllegalArgumentException();
        this.end = end;
        this.step = step;
        this.start = start;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iter();
    }

}
