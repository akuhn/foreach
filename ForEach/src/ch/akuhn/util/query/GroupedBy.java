package ch.akuhn.util.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GroupedBy<A, E> extends For.Each<E> {

	public E value;
	public A yield;

	public static class Query<A, E> extends For<E, GroupedBy<A, E>> {

		protected GroupedBy<A, E> each;
		private Map<A, Collection<E>> result;

		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			Collection<E> group = result.get(each.yield);
			if (group == null) {
				group = new LinkedList<E>();
				result.put(each.yield, group);
			}
			group.add(each.value);
		}

		@Override
		protected void initialize() {
			each = new GroupedBy<A, E>();
			result = new HashMap<A, Collection<E>>();
		}

		@Override
		protected GroupedBy<A, E> nextEach(E next) {
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
