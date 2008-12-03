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
import java.util.HashSet;
import java.util.Set;


public class Count<E> extends For.Each<E> {

	public E value;
	public Object yield;
	
	public static class Query<E> extends For<E,Count<E>> {
	
		protected Count<E> each;
		private Set<Object> result;
	
		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			result.add(each.yield);
		}

		@Override
		protected void initialize() {
			each = new Count<E>();
			result = new HashSet<Object>();
		}

		@Override
		protected Count<E> nextEach(E next) {
			each.value = next;
			each.yield = null;
			return each;
		}

		@Override
		protected Object getResult() {
			return result.size();
		}
			
	}
	
	public static <E> Query<E> query(Collection<E> sample) {
		return new Query<E>(sample);
	}
	
}
