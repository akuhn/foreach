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

/** Returns the first elements for which a predicate yields true. This class is
 * to be used in a for-each loop as follows:
 * <pre>
 * for (Detect&lt;E&gt; each: Query.detect(elements)) {
 *     each.yield = &hellip; each.value &hellip;;
 * }
 * T found = Query.$result();</pre>
 * <p>
 * The body of the loop should implement a predicate.
 * The current element is provided in `each.value`.
 * The result of the predicate must be stored to `each.yield`.
 * The body of the loop is evaluated for each element of the collection.
 * The result of the entire loop is stored in $result, a thread local variable.
 * <ul>
 * <li>If an elements yields `true`, the loop stops and results that element.
 * <li>If all element yield `false` or nothing, the loop results `null`.
 * </ul>
 * <p>
 * @param value (in/out) current element of the collection. Is used as the loop's result, if yield is assigned `true`.
 * @param cut_if (out) result of the predicate. If assigned `true` the loop stops and results the current value. Defaults to `false` if not assigned.
 * <p>
 * @author Adrian Kuhn
 *
 */
public class Detect<E> extends For.Each<E> {

	public E value;
	public boolean yield;
	
	public static class Query<E> extends For<Detect<E>,E> {
	
		protected Detect<E> each;
		private E result;
	
		@Override
		public void apply() {
			if (each.yield) {
				result = each.value;
				this.abort();
			}
		}

		@Override
		protected void initialize() {
			each = new Detect<E>();
			result = null;
		}

		@Override
		protected Detect<E> nextEach(E next) {
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
