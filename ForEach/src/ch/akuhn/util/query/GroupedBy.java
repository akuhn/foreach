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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;


public class GroupedBy<E> extends For<E,GroupedBy<E>> {

	public E value;
	public Object yield;
	private Map<Object,Collection<E>> groups;
	
	@Override
	protected void afterEach() {
		Collection<E> group = groups.get(yield);
		if (group == null) {
			group = new ArrayList<E>();
			groups.put(yield, group);
		}
		group.add(value);
	}
	
	@Override
	protected Object afterLoop() {
		return groups;
	}

	@Override
	protected void beforeLoop() {
		groups = new HashMap<Object,Collection<E>>();
	}
	
	@Override
	protected void beforeEach(E element) {
		value = element;
		yield = null;
	}
	
}