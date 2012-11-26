package ch.akuhn.util.query;

import java.util.HashSet;
import java.util.Set;

import ch.akuhn.util.query.For.Each;

public class Cardinal<Each> extends For<Each, Cardinal<Each>> {

	private Set<Object> count;
	public Each element;
	public Object yield;

	@Override
	protected void afterEach() {
		count.add(yield);
	}

	@Override
	protected Object afterLoop() {
		return count.size();
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
		yield = null;
	}

	@Override
	protected void beforeLoop() {
		count = new HashSet<Object>();
	}

}
