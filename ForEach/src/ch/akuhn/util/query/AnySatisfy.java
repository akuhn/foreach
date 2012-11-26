package ch.akuhn.util.query;

import java.util.Collection;

public class AnySatisfy<E> extends For.Each<E> {

	public E value;
	public boolean yield;

	public static <E> Query<E> query(Collection<E> collection) {
		return new Query<E>(collection);
	}

	public static class Query<E> extends For<E, AnySatisfy<E>> {

		protected AnySatisfy<E> each;
		private Boolean result;

		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			if (each.yield) {
				result = Boolean.TRUE;
				this.abort();
			}
		}

		@Override
		protected void initialize() {
			each = new AnySatisfy<E>();
			result = Boolean.FALSE;
		}

		@Override
		protected AnySatisfy<E> nextEach(E next) {
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
