package ch.akuhn.util.query;

import java.util.Collection;

public class Detect<E> extends For.Each<E> {

	public E value;
	public boolean yield;

	public static <E> Query<E> query(Collection<E> collection) {
		return new Query<E>(collection);
	}

	public static class Query<E> extends For<E, Detect<E>> {

		protected Detect<E> each;
		private E result;

		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			if (each.yield) {
				result = each.value;
				this.abort();
			}
		}

		@Override
		protected void initialize() {
			each = new Detect<E>();
			result = null;
		}

		@Override
		protected Detect<E> nextEach(E next) {
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
