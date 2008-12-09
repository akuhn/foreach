package ch.akuhn.util.query;

import static ch.akuhn.util.query.State.ABORTED;
import static ch.akuhn.util.query.State.EACH;
import static ch.akuhn.util.query.State.FIRST;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public abstract class ForPair<Each,This extends ForPair<Each,This>> {

    private final class Iter implements Iterator<This>, Iterable<This> {

        @Override
        public boolean hasNext() {
            if (state == FIRST) state = EACH;
            else ForPair.this.afterEach();
            if (state != ABORTED && iterator.hasNext()) return true;
            Query.offer(ForPair.this.afterLoop());
            return false;
        }

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

    }

    private Iterator<Each> iterator;
    private Each previous;

    private State state = FIRST;

    protected final void abort() {
        state = ABORTED;
    }

    protected abstract void afterEach();

    protected abstract Object afterLoop();

    protected abstract void beforeEach(Each previous, Each element);

    protected abstract void beforeLoop(Each first);

    protected final Iterable<This> iterable() {
        return new Iter();
    }

    protected This with(Iterable<Each> elements) {
        this.iterator = elements.iterator();
        return (This) this;
    }

}