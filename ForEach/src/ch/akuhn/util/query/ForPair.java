package ch.akuhn.util.query;

import static ch.akuhn.util.query.State.ABORTED;
import static ch.akuhn.util.query.State.FIRST;
import static ch.akuhn.util.query.State.EACH;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public abstract class ForPair<E,This extends ForPair<E,This>> {

	private Iterator<E> iterator;
	private State state = FIRST;
	private E previous;
	
	
	protected This with(Iterable<E> elements) {
		this.iterator = elements.iterator();
		return (This) this;
	}
	
	protected final void abort() {
		state = ABORTED;
	}
	
	protected abstract void beforeLoop(E first);
	
	protected abstract void beforeEach(E previous, E element);
	
	protected abstract void afterEach();
	
	protected abstract Object afterLoop();
	
	protected final Iterable<This> iterable() {
		return new Iter();
	}
	
	private final class Iter implements Iterator<This>, Iterable<This> {
	
		@Override
		public Iterator<This> iterator() {
			previous = iterator.hasNext() ? iterator.next() : null;
			ForPair.this.beforeLoop(previous);
			return this;
		}
		
		@Override
		public This next() {
			beforeEach(previous, previous = iterator.next());
			return (This) ForPair.this;
		}
	
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	
		@Override
		public boolean hasNext() {
			if (state == FIRST) state = EACH;
			else ForPair.this.afterEach();
			if (state != ABORTED && iterator.hasNext()) return true;
			Query.offer(ForPair.this.afterLoop());
			return false;
		}
		
	}

}