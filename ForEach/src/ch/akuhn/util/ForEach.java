package ch.akuhn.util;

import ch.akuhn.util.query.AllSatisfy;
import ch.akuhn.util.query.AnySatisfy;
import ch.akuhn.util.query.Collect2;
import ch.akuhn.util.query.Count;
import ch.akuhn.util.query.CutPieces;
import ch.akuhn.util.query.Detect;
import ch.akuhn.util.query.GroupedBy;
import ch.akuhn.util.query.IndexOf;
import ch.akuhn.util.query.Inject;
import ch.akuhn.util.query.Query;
import ch.akuhn.util.query.Reject;

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

	public static <T, K> Query<T, GroupedBy<T, K>> groupedBy(Class<K> key, Iterable<T> all) {
		return Query.with(new GroupedBy<T, K>(), all);
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
		throw new UnsupportedOperationException(); // Query.with(new
													// CutPieces<T>(), all);
	}

}
