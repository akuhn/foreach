package ch.akuhn.util.query;

import java.util.Collection;
import java.util.Iterator;

import ch.akuhn.util.query.For.Each;

public abstract class For<E,X extends Each<E>> 
		implements Iterator<X>, Iterable<X> {
	
	private enum State { UNUSED, READY, NEXT, DEAD }
	
	public static class Each<T> {
		
	}
	
	protected X each;
	private final Iterator<E> iter;
	private State state = State.UNUSED;
	
	public For(Collection<E> source) {
		this.iter = source.iterator();
		this.state = State.UNUSED;
		this.initialize();
	}
	
	@Override
	public boolean hasNext() {
		assert state != State.UNUSED;
		if (state == State.NEXT) {
			this.apply();
			state = State.READY;
		}
		boolean hasNext = iter.hasNext();
		if (!hasNext && state != State.DEAD) {
			this.offerResult();
			state = State.DEAD;
		}
		return hasNext;
	}

	@Override
	public Iterator<X> iterator() {
		assert state == State.UNUSED;
		state = State.READY;
		return this;
	}

	@Override
	public X next() {
		assert state == State.READY;
		each = nextEach(iter.next());
		state = State.NEXT;
		return each;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	protected abstract void initialize();

	protected abstract X nextEach(E next);

	protected abstract void offerResult();
		
	public abstract void apply();
			
}
