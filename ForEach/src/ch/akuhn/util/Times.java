package ch.akuhn.util;

public class Times {

    public static Iterable<Integer> repeat(int times) {
        return Interval.range(times);
    }
    
}
