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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class GroupedBy<R,E> extends For.Each<E> {

	public E value;
	public R yield;
	
	public static class Query<R,E> extends For<GroupedBy<R,E>,E> {
	
		protected GroupedBy<R,E> each;
		private Map<R,Collection<E>> result;
	
		@Override
		public void apply() {
			Collection<E> group = result.get(each.yield);
			if (group == null) {
				group = new LinkedList<E>();
				result.put(each.yield, group);
			}
			group.add(each.value);
		}

		@Override
		protected void initialize() {
			each = new GroupedBy<R,E>();
			result = new HashMap<R,Collection<E>>();
		}

		@Override
		protected GroupedBy<R,E> nextEach(E next) {
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
