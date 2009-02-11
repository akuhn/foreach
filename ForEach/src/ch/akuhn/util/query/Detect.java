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

import java.util.Collection;

/**
 * Returns the first elements for which a predicate yields true. This class is
 * to be used in a for-each loop as follows:
 * 
 * <pre>
 * for (Detect&lt;E&gt; each: Query.detect(elements)) {
 *     each.yield = &amp;hellip each.value &amp;hellip;
 * }
 * T found = Query.$result();
 * </pre>
 * <p>
 * The body of the loop should implement a predicate. The current element is
 * provided in `each.value`. The result of the predicate must be stored to
 * `each.yield`. The body of the loop is evaluated for each element of the
 * collection. The result of the entire loop is stored in $result, a thread
 * local variable.
 * <ul>
 * <li>If an elements yields `true`, the loop stops and results that element.
 * <li>If all element yield `false` or nothing, the loop results `null`.
 * </ul>
 * <p>
 * 
 * @param value
 *            (in/out) current element of the collection. Is used as the loop's
 *            result, if yield is assigned `true`.
 * @param yield
 *            (out) result of the predicate. If assigned `true` the loop stops
 *            and results the current value. Defaults to `false` if not
 *            assigned.
 *            <p>
 * @author Adrian Kuhn
 * 
 */
public class Detect<Each> extends For<Each,Detect<Each>> {

    public Each element;
    public boolean yield;

    @Override
    protected void afterEach() {
        if (yield) this.abort();
    }

    @Override
    protected Object afterLoop() {
        return yield ? element : null;
    }

    @Override
    protected void beforeEach(Each each) {
        element = each;
        yield = false;
    }

    @Override
    protected void beforeLoop() {
    }

    public static <T> Detect<T> from(Collection<T> elements) {
        return new Detect<T>().with(elements);
    }

    public Each result() {
        return yield ? (Each) element : null;
    }
    
    public Each resultIfNone(Each defaultValue) {
        return yield ? (Each) element : defaultValue;
    }

}
