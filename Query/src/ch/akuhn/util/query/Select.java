package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;

public class Select<E> extends AbstractQuery<E,Filter<E>> {

	private Collection<E> result;

	public Select(Collection<E> source) {
		super(source);
	}

	@Override
	protected void apply() {
		if (each.yield) result.add(each.each);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() {
		try {
			result = source.getClass().newInstance();
		} catch (InstantiationException e) {
			result = new ArrayList<E>();
		} catch (IllegalAccessException e) {
			result = new ArrayList<E>();
		}
	}

	@Override
	protected Object release() {
		return result;
	}

	@Override
	protected Filter<E> createEach() {
		return new Filter<E>();
	}

	public static <T> Select<T> from(Collection<T> sample) {
		return new Select<T>(sample);
	}

}
