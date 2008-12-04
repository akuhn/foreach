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

/** Evaluates a predicate for element of a collection, returning a copy
 * containing those elements for which the predicate yields `true`.
 * This class is to be used in a for-each loop as follows:
 * <pre>
 * for (Select&lt;E&gt; each: Query.select(elements)) {
 *     each.yield = &hellip; each.value &hellip;;
 * }
 * Collection&lt;E&gt; copy = Query.$result();</pre>
 * <p>
 * The body of the loop should implement a predicate.
 * The current element is provided in `each.value`.
 * The result of the predicate must be stored to `each.yield`.
 * The body of the loop is evaluated for each element of the collection.
 * The entire loop results in a copy of elements, which is populated as follows:
 * <ul>
 * <li>If an element yields `true`, append `each.value` to the result.
 * <li>If an element yields `false` or nothing, omit that element.
 * </ul>
 * Upon termination of the loop the resulting collection is stored in $result (a thread local variable).
 * <p>
 * @param value (in/out) current element of the collection. Is added to the result, if yield is assigned `true`.
 * @param cut_if (out) result of the predicate. Defaults to `false` if not assigned.
 * <p>
 * @author Adrian Kuhn
 *
 */
public class Select<E> extends For.Each<E> {

	public E value;
	public boolean yield;
	
	public static class Query<E> extends For<Select<E>,E> {
	
		protected Select<E> each;
		private ArrayList<E> result;
	
		@Override
		public void apply() {
			if (each.yield) result.add(each.value);
		}

		@Override
		protected void initialize() {
			each = new Select<E>();
			result = new ArrayList<E>();
		}

		@Override
		protected Select<E> nextEach(E next) {
			each.value = next;
			each.yield = false;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}
		
	}
	
}
