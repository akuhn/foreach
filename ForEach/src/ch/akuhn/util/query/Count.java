package ch.akuhn.util.query;

public class Count<Each> extends For<Each,Count<Each>> {

	public Each value;
	public boolean yield;
	private int count;
	
	@Override
	protected void afterEach() {
		if (yield) count++;
	}
	
	@Override
	protected Object afterLoop() {
		return count;
	}
	@Override
	protected void beforeLoop() {
		count = 0;
	}
	
	@Override
	protected void beforeEach(Each element) {
		value = element;
		yield = false;
	}
	
}
