package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;

public class Reject<E> extends For.Each<E> {

	public E value;
	public boolean yield;

	public static <E> Query<E> query(Collection<E> collection) {
		return new Query<E>(collection);
	}

	public static class Query<E> extends For<E, Reject<E>> {

		protected Reject<E> each;
		private ArrayList<E> result;

		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			if (!each.yield) result.add(each.value);
		}

		@Override
		protected void initialize() {
			each = new Reject<E>();
			result = new ArrayList<E>();
		}

		@Override
		protected Reject<E> nextEach(E next) {
			each.value = next;
			each.yield = false;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}

	}

}
