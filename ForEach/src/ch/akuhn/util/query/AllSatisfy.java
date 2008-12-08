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

/**
 * Checks if a predicate yields `true` for all elements. This class is to be
 * used in a for-each loop as follows:
 * 
 * <pre>
 * for (AllSatisfy&lt;E&gt; each: Query.satisfy(elements)) {
 *     each.yield = &amp;hellip each.value &amp;hellip;
 * }
 * boolean result = Query.$result();
 * </pre>
 * <p>
 * The body of the loop should implement a predicate. The current element is
 * provided in `each.value`. The result of the predicate must be stored to
 * `each.yield`. The body of the loop is evaluated for each element of the
 * collection. The result of the entire loop is stored in $result, a thread
 * local variable.
 * <ul>
 * <li>If all elements yield `true`, the loop results `true`.
 * <li>If an element yields `false` or nothing, the loop stops and results
 * `false`.
 * </ul>
 * <p>
 * 
 * @param value
 *            (in) current element of the collection. No effect if assigned.
 * @param yield
 *            (out) result of the predicate. Defaults to `false` if not
 *            assigned.
 *            <p>
 * @author Adrian Kuhn
 * 
 */
public class AllSatisfy<Each> extends For<Each,AllSatisfy<Each>> {

    public Each element;
    public boolean yield;

    @Override
    protected void afterEach() {
        if (!yield) this.abort();
    }

    @Override
    protected Object afterLoop() {
        return yield;
    }

    @Override
    protected void beforeEach(Each each) {
        element = each;
        yield = false;
    }

    @Override
    protected void beforeLoop() {
    }

}