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

import java.util.HashSet;
import java.util.Set;

public class Cardinal<Each> extends For<Each,Cardinal<Each>> {

    private Set<Object> count;
    public Each element;
    public Object yield;

    @Override
    protected void afterEach() {
        count.add(yield);
    }

    @Override
    protected Object afterLoop() {
        return count.size();
    }

    @Override
    protected void beforeEach(Each each) {
        element = each;
        yield = null;
    }

    @Override
    protected void beforeLoop() {
        count = new HashSet<Object>();
    }

}
