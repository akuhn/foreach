package ch.akuhn.util.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import sandbox.Examples;

public class GroupedBy<A,T> extends For.Each<T> {

	public T value;
	public A yield;
	
	private static Map<?,?> $result;
	
	static void offer(Map<?,?> result) {
		if ($result != null) throw new AssertionError();
		$result = result;
	}
	
	@SuppressWarnings("unchecked")
	public static <A,E> Map<A,Collection<E>> result() {
		Object result = $result;
		$result = null;
		return (Map<A,Collection<E>>) result;
	}
	
	private static class Query<A,E> extends For<E,GroupedBy<A,E>> {
	
		protected GroupedBy<A,E> each;
		private Map<A,Collection<E>> result;
	
		public Query(Collection<E> source) {
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
			each = new GroupedBy<A,E>();
			result = new HashMap<A,Collection<E>>();
		}

		@Override
		protected GroupedBy<A,E> nextEach(E next) {
			each.value = next;
			each.yield = null;
			return each;
		}

		@Override
		protected void offerResult() {
			GroupedBy.offer(result);
		}
			
	}
	
	public static void main(String[] args) {
		
		Collection<String> in = Examples.sample();
		
		for (GroupedBy<Integer,String> each : GroupedBy.from(Integer.class,in)) {
			each.yield = each.value.length();
		}
		Map<Integer,Collection<String>> out = GroupedBy.result();

		System.out.println(out);
	}

	private static <A,E> Query<A,E> from(Class<A> type, Collection<E> sample) {
		return new Query<A,E>(sample);
	}
	
}
