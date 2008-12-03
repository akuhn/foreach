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



public class InjectInto<R,E> extends For.Each<E> {

	public E value;
	public R yield;
	
	public static class Query<R,E> extends For<InjectInto<R,E>,E> {
	
		protected InjectInto<R,E> each;
		private R result;
	
		/*default*/ void initalValue(R value) {
			this.result = value;
		}
		
		@Override
		public void apply() {
			this.result = each.yield; 
		}

		@Override
		protected void initialize() {
			each = new InjectInto<R,E>();
		}

		@Override
		protected InjectInto<R,E> nextEach(E next) {
			each.value = next;
			each.yield = result;
			return each;
		}

		@Override
		protected Object getResult() {
			return result;
		}
	
		
	}
	
}
