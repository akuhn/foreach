package ch.akuhn.util.query;

import java.util.Collection;

public class InjectInto<V, E> extends For.Each<E> {

	public E value;
	public V yield;

	public static class Query<V, E> extends For<E, InjectInto<V, E>> {

		protected InjectInto<V, E> each;
		private V result;

		private Query(V value, Collection<E> source) {
			super(source);
			this.result = value;
		}

		@Override
		public void apply() {
			this.result = each.yield;
		}

		@Override
		protected void initialize() {
			each = new InjectInto<V, E>();
		}

		@Override
		protected InjectInto<V, E> nextEach(E next) {
			each.value = next;
			each.yield = result;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}

	}

	public static <V, E> Query<V, E> query(V value, Collection<E> sample) {
		return new Query<V, E>(value, sample);
	}

}
