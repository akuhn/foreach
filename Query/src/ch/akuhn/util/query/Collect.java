package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import sandbox.Examples;

public class Collect<A,T> extends For.Each<T> {

	public T value;
	public A yield;
	
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
	
	private static class Query<A,E> extends For<E,Collect<A,E>> {
	
		protected Collect<A,E> each;
		private ArrayList<A> result;
	
		public Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			result.add(each.yield);
		}

		@Override
		protected void initialize() {
			each = new Collect<A,E>();
			result = new ArrayList<A>();
		}

		@Override
		protected Collect<A,E> nextEach(E next) {
			each.value = next;
			each.yield = null;
			return each;
		}

		@Override
		protected void offerResult() {
			Collect.offer(result);
		}
			
	}
	
	public static void main(String[] args) {
		
		Collection<String> in = Examples.sample();
		
		for (Collect<Integer,String> each : Collect.from(Integer.class,in)) {
			each.yield = each.value.length();
		}
		Collection<String> out = Collect.result();

		System.out.println(out);
	}

	private static <A,E> Query<A,E> from(Class<A> type, Collection<E> sample) {
		return new Query<A,E>(sample);
	}
	
}
