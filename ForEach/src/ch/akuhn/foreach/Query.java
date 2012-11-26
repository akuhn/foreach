package ch.akuhn.foreach;

import java.util.Arrays;
import java.util.Iterator;

public class Query<Each, ForEach extends For<Each>> implements Iterable<ForEach> {

	private ForEach each;
	private Iterable<Each> all;

	public static <E, F extends For<E>> Query<E, F> with(F each, Iterable<E> all) {
		return new Query<E, F>(each, all);
	}

	public static <E, F extends For<E>> Query<E, F> with(F each, E... all) {
		return new Query<E, F>(each, Arrays.asList(all));
	}

	private Query(ForEach each, Iterable<Each> all) {
		this.each = each;
		this.all = all;
	}

	@Override
	public Iterator<ForEach> iterator() {
		return new Iter(each, all.iterator());
	}

	private static ThreadLocal $result = new ThreadLocal();

	public static <Result> Result result() {
		return (Result) $result.get();
	}

	protected static void offer(Object result) {
		$result.set(result);
	}

}