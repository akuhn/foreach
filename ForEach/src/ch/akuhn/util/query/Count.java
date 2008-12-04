package ch.akuhn.util.query;

public class Count<E> extends For<E,Count<E>> {

	public E value;
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
	protected void beforeEach(E element) {
		value = element;
		yield = false;
	}
	
}
