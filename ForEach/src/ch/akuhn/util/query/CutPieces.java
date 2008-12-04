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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/** Breaks the collection into pieces. Evaluates the loop for successive pairs
 * of the collection's elements, breaking the collection into pieces between
 * elements where the loop yields `true`. Returns a list of those pieces. 
 * To be used in a foreach loop as follows:
 * <pre>
 * for (CutPieces&lt;T&gt; each: cutPieces(elements)) {
 *     each.cutIf = &hellip; each.value &hellip; each.next &hellip; ;
 * }
 * List&lt;Collection&lt;T&gt;&gt; pieces = $result;</pre>
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
 * <i>Example:</i> splitting a series of events into clusters that are no more
 * than 2 minutes apart.
 * <pre>
 * SortedList&lt;Events&gt; events = &hellip; ;
 * for (CutPieces&lt;Event&gt; $: cutPieces(events)) {
 *     $.cutIf = ($.next.time() - $.value.time()) &gt; 120000;
 * }
 * List&lt;List&lt;Event&gt;&gt; clusters = $result;</pre>
 * <p>
 * @param value (in) first element of the current pair. No effect if assigned.
 * @param next (in/out) second element of the current pair. If assigned, the new value is used in the resulting pieces. 
 * @param cutIf (out) result of the predicate. If `true` the collection is cut 
 * between the elements of the current pair. Defaults to `false` if not assigned.
 * <P>
 * @author Adrian Kuhn
 *
 */
public class CutPieces<Each> extends ForPair<Each,CutPieces<Each>> {

	public Each prev;
	public Each next;
	public boolean yield;

	private Collection<Collection<Each>> result;
	private Collection<Each> current;
	
	@Override
	protected void afterEach() {
		if (yield) {
			current = new ArrayList<Each>();
			result.add(current);
		}
		current.add(next);
	}

	@Override
	protected Object afterLoop() {
		return result;
	}

	@Override
	protected void beforeEach(Each previous, Each element) {
		prev = previous;
		next = element;
		yield = false;
	}

	@Override
	protected void beforeLoop(Each first) {
		result = new LinkedList<Collection<Each>>();
		current = new ArrayList<Each>();
		result.add(current);
		current.add(first);
	}
	
}
