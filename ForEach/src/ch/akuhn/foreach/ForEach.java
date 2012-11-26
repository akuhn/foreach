package ch.akuhn.foreach;

public class ForEach {

	public static <T> T result() {
		return Query.result();
	}

	public static <T> Query<T, All<T>> allSatisfy(Iterable<T> all) {
		return Query.with(new All<T>(), all);
	}

	public static <T> Query<T, Any<T>> anySatisfy(Iterable<T> all) {
		return Query.with(new Any<T>(), all);
	}

	public static <T> Query<T, Collect1<T>> collect1(Iterable<T> all) {
		return Query.with(new Collect1<T>(), all);
	}

	public static <T, K> Query<T, Collect<T, K>> collect(Class<K> key, Iterable<T> all) {
		return Query.with(new Collect<T, K>(), all);
	}

	public static <T> Query<T, Detect<T>> detect(Iterable<T> all) {
		return Query.with(new Detect<T>(), all);
	}

	public static <T> Query<T, Count<T>> count(Iterable<T> all) {
		return Query.with(new Count<T>(), all);
	}

	public static <T> Query<T, GroupedBy<T>> groupedBy(Iterable<T> all) {
		return Query.with(new GroupedBy<T>(), all);
	}

	public static <T> Query<T, IndexOf<T>> indexOf(Iterable<T> all) {
		return Query.with(new IndexOf<T>(), all);
	}

	public static <T, R> Query<T, Inject<T, R>> inject(R seed, Iterable<T> all) {
		return Query.with(new Inject<T, R>(seed), all);
	}

	public static <T> Query<T, Reject<T>> reject(Iterable<T> all) {
		return Query.with(new Reject<T>(), all);
	}

	public static <T> Query<T, CutPieces<T>> cutPieces(Iterable<T> all) {
		throw new UnsupportedOperationException();
	}

	public static <T> Query<T, All<T>> allSatisfy(T[] all) {
		return Query.with(new All<T>(), all);
	}

	public static <T> Query<T, Any<T>> anySatisfy(T[] all) {
		return Query.with(new Any<T>(), all);
	}

	public static <T> Query<T, Collect<T, T>> collect(T[] all) {
		return Query.with(new Collect<T, T>(), all);
	}

	public static <T, K> Query<T, Collect<T, K>> collect2(Class<K> key, T[] all) {
		return Query.with(new Collect<T, K>(), all);
	}

	public static <T> Query<T, Detect<T>> detect(T[] all) {
		return Query.with(new Detect<T>(), all);
	}

	public static <T> Query<T, Count<T>> count(T[] all) {
		return Query.with(new Count<T>(), all);
	}

	public static <T> Query<T, GroupedBy<T>> groupedBy(T[] all) {
		return Query.with(new GroupedBy<T>(), all);
	}

	public static <T> Query<T, IndexOf<T>> indexOf(T[] all) {
		return Query.with(new IndexOf<T>(), all);
	}

	public static <T, R> Query<T, Inject<T, R>> inject(R seed, T[] all) {
		return Query.with(new Inject<T, R>(seed), all);
	}

	public static <T> Query<T, Reject<T>> reject(T[] all) {
		return Query.with(new Reject<T>(), all);
	}

	public static <T> Query<T, CutPieces<T>> cutPieces(T[] all) {
		throw new UnsupportedOperationException();
	}

}
