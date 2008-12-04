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

/** Breaks the collection into pieces. Evaluates the loop for successive pairs
 * of the collection's elements, breaking the collection into pieces between
 * elements where the loop yields `true`. Returns a list of those pieces. 
 * To be used in a foreach loop as follows:
 * <pre>
 * for (CutPieces<T> each: cutPieces(elements)) {
 *     each.yield = &hellip; each.value &hellip; each.next &hellip; ;
 * }
 * List<Collection<T>> pieces = $result;</pre>
 * <p>
 * The body of the loop should implement a predicate.
 * The current pair of elements is provided in `each.value` and `each.next`.
 * The result of the predicate must be stored to `each.yield`.
 * The body of the loop is evaluated for consecutive pairs of the collection.
 * The loop cuts the collection into pieces and returns those.
 * <ul>
 * <li>If a pair yields `true`, the collection is cut between those elements.
 * <li>If a pair yields `false` or nothing, no cut happens.
 * </ul>
 * <p>
 * @param value (in) first element of the current pair. No effect if assigned.
 * @param next (in/out) second element of the current pair. If assigned, the new value is used in the resulting pieces. 
 * @param yield (out) result of the predicate. If `true` the collection is cut 
 * between the elements of the current pair. Defaults to `false` if not assigned.
 * <P>
 * @author Adrian Kuhn
 *
 */
public class CutPieces<E> extends ForPair.Each<E> {

	public E value;
	public E next;
	public boolean yield;
	
	public static class Query<E> extends ForPair<CutPieces<E>,E> {
	
		protected CutPieces<E> each;
		private Collection<Collection<E>> result;
		private Collection<E> current;
	
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
