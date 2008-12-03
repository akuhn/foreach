package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import sandbox.Examples;

public class Select<T> extends For.Each<T> {

	public T value;
	public boolean yield;
	
	private static Collection<?> $result;
	
	static void offer(Collection<?> result) {
		if ($result != null) throw new AssertionError();
		$result = result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> result() {
		Object result = $result;
		$result = null;
		return (Collection<T>) result;
	}
	
	private static class Query<E> extends For<E,Select<E>> {
	
		protected Select<E> each;
		private ArrayList<E> result;
	
		public Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			if (each.yield) result.add(each.value);
		}

		@Override
		protected void initialize() {
			each = new Select<E>();
			result = new ArrayList<E>();
		}

		@Override
		protected Select<E> nextEach(E next) {
			each.value = next;
			each.yield = false;
			return each;
		}

		@Override
		protected void offerResult() {
			Select.offer(result);
		}
	
		
	}
	
	public static void main(String[] args) {
		
		Collection<String> in = Examples.sample();
		
		for (Select<String> each : Select.from(in)) {
			each.yield = each.value.length() > 3;
		}
		Collection<String> out = Select.result();

		System.out.println(out);
	}

	private static <E> Query<E> from(Collection<E> sample) {
		return new Query<E>(sample);
	}
	
}
