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

import static ch.akuhn.util.query.State.*;
import java.util.Iterator;

/** Superclass of single-element queries. 
* 
* @author Adrian Kuhn
*
*/
@SuppressWarnings("unchecked")
public abstract class For<E,This extends For<E,This>> {

	private Iterator<E> iterator;
	private State state = FIRST;
	
	protected This with(Iterable<E> elements) {
		this.iterator = elements.iterator();
		return (This) this;
	}
	
	protected final void abort() {
		state = ABORTED;
	}
	
	protected abstract void beforeLoop();
	
	protected abstract void beforeEach(E element);
	
	protected abstract void afterEach();
	
	protected abstract Object afterLoop();
	
	protected final Iterable<This> iterable() {
		return new Iter();
	}
	
	private final class Iter implements Iterator<This>, Iterable<This> {
	
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
	
		@Override
		public boolean hasNext() {
			if (state == FIRST) state = EACH;
			else For.this.afterEach();
			if (state != ABORTED && iterator.hasNext()) return true;
			Query.offer(For.this.afterLoop());
			return false;
		}
		
	}

}



