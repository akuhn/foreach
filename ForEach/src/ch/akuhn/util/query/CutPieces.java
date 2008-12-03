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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;


public class CutPieces<E> extends ForEach.Pair<E> {

	public E value;
	public E next;
	public boolean yield;
	
	public static <E> Query<E> query(Collection<E> collection) {
		return new Query<E>(collection);
	}
	
	public static class Query<E> extends ForEach<E,CutPieces<E>> {
	
		protected CutPieces<E> each;
		private Collection<Collection<E>> result;
		private Collection<E> current;
	
		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			if (each.yield) {
				current = new ArrayList<E>();
				result.add(current);
			}
			current.add(each.next);
		}

		@Override
		protected void initialize(E first) {
			each = new CutPieces<E>();
			result = new LinkedList<Collection<E>>();
			current = new ArrayList<E>();
			result.add(current);
			current.add(first);
		}

		@Override
		protected CutPieces<E> nextPair(E previous, E next) {
			each.value = previous;
			each.next = next;
			each.yield = false;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}
		
	}
	
}
