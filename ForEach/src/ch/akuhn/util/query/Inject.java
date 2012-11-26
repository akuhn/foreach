package ch.akuhn.util.query;

import ch.akuhn.util.query.For.Each;

public class Inject<Each, R> extends For<Each, Inject<Each, R>> {

	public Each element;
	public R yield;

	@Override
	protected void afterEach() {
	}

	@Override
	protected Object afterLoop() {
		return yield;
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
	}

	@Override
	protected void beforeLoop() {
	}

	protected Inject<Each, R> initial(R value) {
		yield = value;
		return this;
	}

}