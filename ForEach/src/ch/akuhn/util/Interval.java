package ch.akuhn.util;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Interval implements Iterable<Integer> {

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

    public Interval(final int start, final int end, final int step) {
        if (step == 0 && start != end) throw new IllegalArgumentException();
        this.end = end;
        this.step = step;
        this.start = start;
    }

    @Override
    public final Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            private int index = start;

            @Override
            public final boolean hasNext() {
                return step > 0 ? index < end : index > end;
            }

            @Override
            public final Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                int current = index;
                index += step;
                return current;
            }

            @Override
            public final void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

    public int size() {
        return step == 0 ? 0 : max(0, (end - start + step + (step < 0 ? +1 : -1)) / step);
    }
    
    public int[] asArray() {
        int[] array = new int[size()];
        Iterator<Integer> it = iterator();
        for (int n = 0; n < array.length; n++) array[n] = it.next();
        assert !it.hasNext();
        return array;
    }    
    
}
