package ch.akuhn.foreach;

import static ch.akuhn.foreach.Result.ThreadLocal;

import java.util.Arrays;

public class ForEach {

	public static <T> T result() {
		return ThreadLocal.result();
	}

	public static <T> Query<T,All<T>> all(Iterable<T> all) {
		return Query.with(new All<T>(), all);
	}

	public static <T> Query<T,All<T>> all(T[] all) {
		return Query.with(new All<T>(), all);
	}

	public static <T> Query<T,Any<T>> any(Iterable<T> all) {
		return Query.with(new Any<T>(), all);
	}

	public static <T> Query<T,Any<T>> any(T[] all) {
		return Query.with(new Any<T>(), all);
	}

	public static <T,K> Query<T,Collect<T,K>> collect(Class<K> key, Iterable<T> all) {
		return Query.with(new Collect<T,K>(), all);
	}

	public static <T,K> Query<T,Collect<T,K>> collect(Class<K> key, T[] all) {
		return Query.with(new Collect<T,K>(), all);
	}

	public static <T> Query<T,Collect1<T>> collect1(Iterable<T> all) {
		return Query.with(new Collect1<T>(), all);
	}

	public static <T> Query<T,Collect1<T>> collect1(T[] all) {
		return Query.with(new Collect1<T>(), all);
	}

	public static <T> Query<T,Count<T>> count(Iterable<T> all) {
		return Query.with(new Count<T>(), all);
	}

	public static <T> Query<T,Count<T>> count(T[] all) {
		return Query.with(new Count<T>(), all);
	}

	public static <T> Query<T,Detect<T>> detect(Iterable<T> all) {
		return Query.with(new Detect<T>(), all);
	}

	public static <T> Query<T,Detect<T>> detect(T[] all) {
		return Query.with(new Detect<T>(), all);
	}

	public static <T> Query<T,Find<T>> find(Iterable<T> all) {
		return Query.with(new Find<T>(), all);
	}

	public static <T> Query<T,Find<T>> find(T[] all) {
		return Query.with(new Find<T>(), all);
	}

	public static <T> Query<Pair<T,T>,Fold<T>> fold(Iterable<T> all) {
		return Query.with(new Fold<T>(), Iterators.pairs(all));
	}

	public static <T> Query<Pair<T,T>,Fold<T>> fold(T[] all) {
		return ForEach.fold(Arrays.asList(all));
	}

	public static <T> Query<T,GroupBy<T>> groupBy(Iterable<T> all) {
		return Query.with(new GroupBy<T>(), all);
	}

	public static <T> Query<T,GroupBy<T>> groupBy(T[] all) {
		return Query.with(new GroupBy<T>(), all);
	}

	public static <T> Query<T,IndexOf<T>> indexOf(Iterable<T> all) {
		return Query.with(new IndexOf<T>(), all);
	}

	public static <T> Query<T,IndexOf<T>> indexOf(T[] all) {
		return Query.with(new IndexOf<T>(), all);
	}

	public static <T,R> Query<T,Inject<T,R>> inject(R seed, Iterable<T> all) {
		return Query.with(new Inject<T,R>(seed), all);
	}

	public static <T,R> Query<T,Inject<T,R>> inject(R seed, T[] all) {
		return Query.with(new Inject<T,R>(seed), all);
	}

	public static <T> Query<T,Partition<T>> partition(Iterable<T> all) {
		return Query.with(new Partition<T>(), all);
	}

	public static <T> Query<T,Partition<T>> partition(T[] all) {
		return Query.with(new Partition<T>(), all);
	}

	public static <T> Query<T,Reject<T>> reject(Iterable<T> all) {
		return Query.with(new Reject<T>(), all);
	}

	public static <T> Query<T,Reject<T>> reject(T[] all) {
		return Query.with(new Reject<T>(), all);
	}

	public static <T> Query<T,Select<T>> select(Iterable<T> all) {
		return Query.with(new Select<T>(), all);
	}

	public static <T> Query<T,Select<T>> select(T[] all) {
		return Query.with(new Select<T>(), all);
	}

	public static <T> Query<Pair<T,T>,Split<T>> split(Iterable<T> all) {
		return Query.with(new Split<T>(), Iterators.pairs(all));
	}

	public static <T> Query<Pair<T,T>,Split<T>> split(T[] all) {
		return ForEach.split(Arrays.asList(all));
	}

	public static <T> Query<T,Sum<T>> sum(Iterable<T> all) {
		return Query.with(new Sum<T>(), all);
	}

	public static <T> Query<T,Sum<T>> sum(T[] all) {
		return Query.with(new Sum<T>(), all);
	}

}
