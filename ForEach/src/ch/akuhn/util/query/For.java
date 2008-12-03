//  Copyright (c) 2008 Adrian Kuhn <akuhn(a)iam.unibe.ch>
//  
//  This file is part of "ForEach".
//  
//  "ForEach" is free software: you can redistribute it and/or modify it under
//	the terms of the GNU Lesser General Public License as published by the Free
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

import ch.akuhn.util.query.For.Each;

/** Superclass of single-element queries. 
 * 
 * @author Adrian Kuhn
 *
 */
public abstract class For<Loop extends Each<E>,E> 
		implements Iterator<Loop>, Iterable<Loop> {
	
	public static class Each<T> {
		
	}
	
	protected Loop each;
	private Iterator<E> iter;
	private State state = NEW;
	
	/*default*/ void with(Iterable<E> iterable) {
		assertState(NEW);
		this.iter = iterable.iterator();
		this.initialize();
		this.state = State.INIT;
	}
	
	private void assertState(State valid) {
		if (state == valid) return;
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNext() {
		if (state != FIRST) {
			assertState(HASNEXT);
			this.apply();
			if (state == ABORT) {
				Query.offer(this.getResult());
				state = DEAD;
				return false;
			}
		}
		if (iter.hasNext()) {
			state = NEXT;
			return true;
		}
		Query.offer(this.getResult());
		state = DEAD;
		return false;
	}

	@Override
	public Iterator<Loop> iterator() {
		assertState(INIT);
		state = FIRST;
		return this;
	}

	@Override
	public Loop next() {
		assertState(NEXT);
		each = nextEach(iter.next());
		state = HASNEXT;
		return each;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	protected abstract void initialize();

	protected abstract Loop nextEach(E next);

	protected abstract Object getResult();
		
	public abstract void apply();
			
	public void abort() {
		state = ABORT;
	}
	
}
