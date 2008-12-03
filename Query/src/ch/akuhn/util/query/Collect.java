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
import java.util.Collection;



public class Collect<A,E> extends For.Each<E> {

	public E value;
	public A yield;
	
	static class Query<A,E> extends For<E,Collect<A,E>> {
	
		protected Collect<A,E> each;
		private ArrayList<A> result;
	
		private Query(Collection<E> source) {
			super(source);
		}

		@Override
		public void apply() {
			result.add(each.yield);
		}

		@Override
		protected void initialize() {
			each = new Collect<A,E>();
			result = new ArrayList<A>();
		}

		@Override
		protected Collect<A,E> nextEach(E next) {
			each.value = next;
			each.yield = null;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}
			
	}
	
	public static <A,E> Query<A,E> query(Class<A> type, Collection<E> sample) {
		return new Query<A,E>(sample);
	}
	
}
