package ch.akuhn.util.query;

import java.util.Collection;

public class Query {

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
	
	public static <T> Select.Query<T> select(Collection<T> collection) {
		return Select.query(collection);
	}
	
	public static <A,T> Collect.Query<A,T> collect(Class<A> type, Collection<T> collection) {
		return Collect.query(type, collection);
	}
	
	public static <A,T> GroupedBy.Query<A,T> groupedBy(Class<A> type, Collection<T> collection) {
		return GroupedBy.query(type, collection);
	}
	
	public static <A,T> InjectInto.Query<A,T> injectInto(A value, Collection<T> collection) {
		return InjectInto.query(value, collection);
	}
	
	
}
