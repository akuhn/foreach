package examples;

import static ch.akuhn.util.query.Query.$result;
import static ch.akuhn.util.query.Query.count;

import java.util.LinkedList;

import ch.akuhn.util.query.Count;

@SuppressWarnings( { "unchecked", "serial" })
public class Benchmark {

    public static class Struct {

        public Object a, b, c;

    }

    public static int $count;

    public static final Iterable<Struct> $data = new LinkedList() {
        {
            for (int n = 0; n < 1000 * 1000; n++) {
                Struct s = new Struct();
                if (n % 3 == 0) s.a = new Object();
                if (n % 5 == 0) s.b = new Object();
                if (n % 7 == 0) s.c = new Object();
                add(s);
            }
        }
    };

    private static void benchmarkForEach() {
        long start = System.currentTimeMillis();
        for (int n = 0; n < 10; n++) {
            for (Count<Struct> each : count($data)) {
                each.yield = (each.element.a != null && each.element.b != null && each.element.c != null);
            }
            $count = $result();
        }
        System.out.print(System.currentTimeMillis() - start);
        System.out.print('\t');
    }

    private static void benchmarkPlainJava() {
        long start = System.currentTimeMillis();
        for (int n = 0; n < 10; n++) {
            int count = 0;
            for (Struct each : $data) {
                if (each.a != null && each.b != null && each.c != null) count++;
            }
            $count = count;
        }
        System.out.print(System.currentTimeMillis() - start);
        System.out.print('\t');
    }

    public static void main(String[] args) {

        for (int n = 0; n < 10; n++) {
            benchmarkForEach();
            benchmarkPlainJava();
            System.out.println();
        }

    }
}
