package ch.akuhn.util.query;

public class Fold<Each> extends ForPair<Each, Fold<Each>> {

	public Each element;
	public Each yield;

	@Override
	protected void afterEach() {
	}

	@Override
	protected Object afterLoop() {
		return yield;
	}

	@Override
	protected void beforeEach(Each previous, Each next) {
		element = next;
	}

	@Override
	protected void beforeLoop(Each first) {
		yield = first;
	}

}
