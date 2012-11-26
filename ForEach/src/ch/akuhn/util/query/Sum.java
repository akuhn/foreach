package ch.akuhn.util.query;

import ch.akuhn.util.query.For.Each;

public class Sum<Each> extends For<Each, Sum<Each>> {

	public Each element;
	public int sum;

	@Override
	protected void afterEach() {
	}

	@Override
	protected Object afterLoop() {
		return sum;
	}

	@Override
	protected void beforeEach(Each each) {
		this.element = each;
	}

	@Override
	protected void beforeLoop() {
		sum = 0;
	}

}