package ch.akuhn.util.query;

public class Count<Each> extends For<Each,Count<Each>> {

	public Each element;
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
	protected void beforeEach(Each each) {
		element = each;
		yield = false;
	}
	
}
