package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;

public class Collect<E> extends AbstractQuery<E,Each<E>> {

	private Collection<Object> result;

	public Collect(Collection<E> source) {
		super(source);
	}

	@Override
	protected void apply() {
		result.add(each.yield);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() {
		try {
			result = source.getClass().newInstance();
		} catch (InstantiationException e) {
			result = new ArrayList<Object>();
		} catch (IllegalAccessException e) {
			result = new ArrayList<Object>();
		}
	}

	@Override
	protected Object release() {
		return result;
	}

	@Override
	protected Each<E> createEach() {
		return new Each<E>();
	}

}
