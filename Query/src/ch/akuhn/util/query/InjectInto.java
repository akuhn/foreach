package ch.akuhn.util.query;

import java.util.Collection;

public class InjectInto<V,E> extends AbstractQuery<E,Sum<V,E>> {

	private V value;

	public InjectInto(V value, Collection<E> source) {
		super(source);
		this.value = value;
	}

	@Override
	protected void apply() {
		this.value = each.yield;
	}

	@Override
	protected void initialize() {
		// do nothing
	}

	@Override
	protected Object release() {
		return value;
	}

	@Override
	protected Sum<V, E> createEach() {
		return new Sum<V,E>();
	}

}
