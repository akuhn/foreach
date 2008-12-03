package ch.akuhn.util.query;

import java.util.Collection;
import java.util.Iterator;


/**
 * 
 * @author Adrian Kuhn
 *
 * @param <E>
 */
public class Query {
	
	private static Object $result;
	
	public static <K,T> GroupedBy<K,T> groupedBy(Class<K> type, Collection<T> sample) {
		return new GroupedBy<K,T>(sample);
	}
	
	public static <T> Select<T> select(Collection<T> sample) {
		return new Select<T>(sample);
	}

	static void offer(Object result) {
		$result = result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T result() {
		Object result = $result;
		$result = null;
		return (T) result;
	}
	
	public static <T> Collect<T> collect(Collection<T> sample) {
		return new Collect<T>(sample);
	}

	public static <V,T> InjectInto<V,T> injectInto(V value, Collection<T> sample) {
		return new InjectInto<V,T>(value, sample);
	}
	
}