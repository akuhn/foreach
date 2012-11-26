package ch.akuhn.foreach;

public class ForEach {

	public static <T> T result() {
		return Query.result();
	}

	public static <T> Query<T, AllSatisfy<T>> allSatisfy(Iterable<T> all) {
		return Query.with(new AllSatisfy<T>(), all);
	}

	public static <T> Query<T, AnySatisfy<T>> anySatisfy(Iterable<T> all) {
		return Query.with(new AnySatisfy<T>(), all);
	}

	public static <T> Query<T, Collect2<T, T>> collect(Iterable<T> all) {
		return Query.with(new Collect2<T, T>(), all);
	}

	public static <T, K> Query<T, Collect2<T, K>> collect2(Class<K> key, Iterable<T> all) {
		return Query.with(new Collect2<T, K>(), all);
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

	public static <T> Query<T, AllSatisfy<T>> allSatisfy(T[] all) {
		return Query.with(new AllSatisfy<T>(), all);
	}

	public static <T> Query<T, AnySatisfy<T>> anySatisfy(T[] all) {
		return Query.with(new AnySatisfy<T>(), all);
	}

	public static <T> Query<T, Collect2<T, T>> collect(T[] all) {
		return Query.with(new Collect2<T, T>(), all);
	}

	public static <T, K> Query<T, Collect2<T, K>> collect2(Class<K> key, T[] all) {
		return Query.with(new Collect2<T, K>(), all);
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
