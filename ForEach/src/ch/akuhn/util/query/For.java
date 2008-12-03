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

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import ch.akuhn.util.query.For.Each;

public abstract class For<E,X extends Each<E>> 
		implements Iterator<X>, Iterable<X> {
	
	private enum State { UNUSED, READY, NEXT, DEAD, ABORT }
	
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
		if (state == State.DEAD) return false;
		if (state == State.NEXT) {
			this.apply();
			if (state == State.ABORT) {
				Query.offer(this.getResult());
				state = State.DEAD;
				return false;
			}
			state = State.READY;
		}
		boolean hasNext = iter.hasNext();
		if (!hasNext && state != State.DEAD) {
			Query.offer(this.getResult());
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
		if (state == State.DEAD) throw new NoSuchElementException();
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

	protected abstract Object getResult();
		
	public abstract void apply();
			
	public void abort() {
		state = State.ABORT;
	}
	
}
