package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;

public class Collect<A, E> extends For.Each<E> {

	public E value;
	public A yield;

	public static class Query<A, E> extends For<E, Collect<A, E>> {

		protected Collect<A, E> each;
		private ArrayList<A> result;

		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			result.add(each.yield);
		}

		@Override
		protected void initialize() {
			each = new Collect<A, E>();
			result = new ArrayList<A>();
		}

		@Override
		protected Collect<A, E> nextEach(E next) {
			each.value = next;
			each.yield = null;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}

	}

	public static <A, E> Query<A, E> query(Class<A> type, Collection<E> sample) {
		return new Query<A, E>(sample);
	}

}
