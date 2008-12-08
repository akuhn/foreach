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

import static ch.akuhn.util.query.State.ABORTED;
import static ch.akuhn.util.query.State.EACH;
import static ch.akuhn.util.query.State.FIRST;

import java.util.Iterator;

/**
 * Superclass of single-element queries.
 * 
 * @author Adrian Kuhn
 * 
 */
@SuppressWarnings("unchecked")
public abstract class For<Each,This extends For<Each,This>> {

    private final class Iter implements Iterator<This>, Iterable<This> {

        @Override
        public boolean hasNext() {
            if (state == FIRST) state = EACH;
            else For.this.afterEach();
            if (state != ABORTED && iterator.hasNext()) return true;
            Query.offer(For.this.afterLoop());
            return false;
        }

        @Override
        public Iterator<This> iterator() {
            For.this.beforeLoop();
            return this;
        }

        @Override
        public This next() {
            beforeEach(iterator.next());
            return (This) For.this;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private Iterator<Each> iterator;

    private State state = FIRST;

    protected final void abort() {
        state = ABORTED;
    }

    protected abstract void afterEach();

    protected abstract Object afterLoop();

    protected abstract void beforeEach(Each each);

    protected abstract void beforeLoop();

    protected final Iterable<This> iterable() {
        return new Iter();
    }

    protected This with(Iterable<Each> elements) {
        this.iterator = elements.iterator();
        return (This) this;
    }

}
