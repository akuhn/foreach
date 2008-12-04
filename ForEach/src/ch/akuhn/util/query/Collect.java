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

/** Evaluates an expression for each element of a collection, returning a new
 * collection containing the values yielded by the expression.
 * This class is to be used in a for-each loop as follows:
 * <pre>
 * for (Collect&lt;E,T&gt; each: Query.collect(elements, T.class)) {
 *     each.yield = &hellip; each.value &hellip;;
 * }
 * Collection&lt;T&gt; copy = Query.$result();</pre>
 * <p>
 * The body of the loop should implement an expression.
 * The current element is provided in `each.value`.
 * The result of the expression must be stored to `each.yield`.
 * The body of the loop is evaluated for each element of the collection.
 * The entire loop results in a new collection containing the values yielded by the expression.
 * Upon termination of the loop the new collection is assigned to $result (a thread local variable).
 * <p>
 * @param value (in) current element of the collection.
 * @param cut_if (out) result of the expression. Defaults to `null` if not assigned.
 * <p>
 * @author Adrian Kuhn
 *
 */
public class Collect<R,E> extends For.Each<E> {

	public E value;
	public R yield;
	
	public static class Query<R,E> extends For<Collect<R,E>,E> {
	
		protected Collect<R,E> each;
		private ArrayList<R> result;
	
		@Override
		public void apply() {
			result.add(each.yield);
		}

		@Override
		protected void initialize() {
			each = new Collect<R,E>();
			result = new ArrayList<R>();
		}

		@Override
		protected Collect<R,E> nextEach(E next) {
			each.value = next;
			each.yield = null;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}
			
	}
	
}
