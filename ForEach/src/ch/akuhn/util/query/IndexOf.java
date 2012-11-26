package ch.akuhn.util.query;

import java.util.Collection;

public class IndexOf<E> extends For.Each<E> {

	public E value;
	public boolean yield;

	public static <E> Query<E> query(Collection<E> collection) {
		return new Query<E>(collection);
	}

	public static class Query<E> extends For<E, IndexOf<E>> {

		protected IndexOf<E> each;
		private int index;
		private int result;

		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			index++;
			if (each.yield) {
				result = index;
				this.abort();
			}
		}

		@Override
		protected void initialize() {
			each = new IndexOf<E>();
			index = 0;
			result = -1;
		}

		@Override
		protected IndexOf<E> nextEach(E next) {
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
