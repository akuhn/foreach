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


public class InjectInto<V,E> extends For.Each<E> {

	public E value;
	public V yield;
	
	public static class Query<V,E> extends For<E,InjectInto<V,E>> {
	
		protected InjectInto<V,E> each;
		private V result;
	
		private Query(V value, Collection<E> source) {
			super(source);
			this.result = value;
		}

		@Override
		public void apply() {
			this.result = each.yield; 
		}

		@Override
		protected void initialize() {
			each = new InjectInto<V,E>();
		}

		@Override
		protected InjectInto<V,E> nextEach(E next) {
			each.value = next;
			each.yield = result;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}
	
		
	}
	
	public static <V,E> Query<V,E> query(V value, Collection<E> sample) {
		return new Query<V,E>(value, sample);
	}
	
}
