//  Copyright (c) 2008 Adrian Kuhn <akuhn(a)iam.unibe.ch>
//  
//  This file is part of "ForEach".
//  
//  "ForEach" is free software: you can redistribute it and/or modify it under
//  the terms of the GNU Lesser General Public License as published by the Free
//  Software Foundation, either version 3 of the License, or (at your option)
//  any later version.
//  
//  "ForEach" is distributed in the hope that it will be useful, but WITHOUT ANY
//  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
//  FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
//  details.
//  
//  You should have received a copy of the GNU Lesser General Public License
//  along with "ForEach". If not, see <http://www.gnu.org/licenses/>.
//  
package ch.akuhn.util.query;

import static ch.akuhn.util.query.State.EACH;
import static ch.akuhn.util.query.State.FIRST;
import static ch.akuhn.util.query.State.STOPPED;

import java.util.Iterator;

/*default*/ enum State {
    STOPPED, EACH, FIRST
}

/**
 * Superclass of single-element queries.
 * 
 * @author Adrian Kuhn
 * 
 */
@SuppressWarnings("unchecked")
public abstract class For<Each,This extends For<Each,This>> implements Iterable<This> {

    private final class Iter implements Iterator<This> {

        private Iterator<? extends Each> iterator = elements.iterator();
        
        //@Override
        public boolean hasNext() {
            if (state == FIRST) state = EACH;
            else For.this.afterEach();
            if (state != STOPPED && iterator.hasNext()) return true;
            Query.offerResult(For.this.afterLoop());
            return false;
        }

        //@Override
        public This next() {
            beforeEach(iterator.next());
            return (This) For.this;
        }

        //@Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private Iterable<? extends Each> elements;
    private State state = FIRST;

    protected final void abort() {
        state = STOPPED;
    }

    protected abstract void afterEach();

    protected abstract Object afterLoop();

    protected abstract void beforeEach(Each each);

    protected abstract void beforeLoop();

    protected This with(Iterable<? extends Each> elements) {
        this.elements = elements;
        return (This) this;
    }

    //@Override
    public Iterator<This> iterator() {
        state = FIRST;
        For.this.beforeLoop();
        return new Iter();
    }

}
