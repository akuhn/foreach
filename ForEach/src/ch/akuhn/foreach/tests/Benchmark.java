package ch.akuhn.foreach.tests;

import java.util.LinkedList;
import java.util.List;

import ch.akuhn.foreach.Count;
import ch.akuhn.foreach.ForEach;

public class Benchmark {

	public static int $count;

	@SuppressWarnings("serial")
	public static final List<Struct> $data = new LinkedList<Struct>() {
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
			for (Count<Struct> each: ForEach.count($data)) {
				each.yield = (each.value.a != null && each.value.b != null && each.value.c != null);
			}
			$count = ForEach.result();
		}
		System.out.print(System.currentTimeMillis() - start);
		System.out.print('\t');
	}

	private static void benchmarkPlainJava() {
		long start = System.currentTimeMillis();
		for (int n = 0; n < 10; n++) {
			int count = 0;
			for (Struct each: $data) {
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

class Struct {

	public Object a, b, c;

}
