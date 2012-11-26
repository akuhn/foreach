package ch.akuhn.util.query;

import java.util.Collection;

public class AllSatisfy<E> extends For.Each<E> {

	public E value;
	public boolean yield;

	public static <E> Query<E> query(Collection<E> collection) {
		return new Query<E>(collection);
	}

	public static class Query<E> extends For<E, AllSatisfy<E>> {

		protected AllSatisfy<E> each;
		private Boolean result;

		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			if (!each.yield) {
				result = Boolean.FALSE;
				this.abort();
			}
		}

		@Override
		protected void initialize() {
			each = new AllSatisfy<E>();
			result = Boolean.TRUE;
		}

		@Override
		protected AllSatisfy<E> nextEach(E next) {
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
