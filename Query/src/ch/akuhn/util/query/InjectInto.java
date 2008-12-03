package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import sandbox.Examples;

public class InjectInto<V,T> extends For.Each<T> {

	public T value;
	public V yield;
	
	private static Object $result;
	
	static void offer(Object result) {
		if ($result != null) throw new AssertionError();
		$result = result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T result() {
		Object result = $result;
		$result = null;
		return (T) result;
	}
	
	private static class Query<V,E> extends For<E,InjectInto<V,E>> {
	
		protected InjectInto<V,E> each;
		private V result;
	
		public Query(V value, Collection<E> source) {
			super(source);
			this.result = value;
		}

		@Override
		public void apply() {
			this.result = each.yield; 
		}

		@Override
		protected void initialize() {
			each = new InjectInto<V,E>();
		}

		@Override
		protected InjectInto<V,E> nextEach(E next) {
			each.value = next;
			each.yield = result;
			return each;
		}

		@Override
		protected void offerResult() {
			InjectInto.offer(result);
		}
	
		
	}
	
	public static void main(String[] args) {
		
		Collection<String> in = Examples.sample();
		
		for (InjectInto<Integer,String> each : InjectInto.from(0, in)) {
			each.yield = each.yield + each.value.length();
		}
		int out = InjectInto.result();

		System.out.println(out);
	}

	private static <V,E> Query<V,E> from(V value, Collection<E> sample) {
		return new Query<V,E>(value, sample);
	}
	
}
