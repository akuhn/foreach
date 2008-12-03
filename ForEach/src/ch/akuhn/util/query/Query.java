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

public class Query {

	private static Object $result;
	
	@SuppressWarnings("unchecked")
	public static <T> T $result() {
		return (T) $result;
	}
	
	public static <T> AllSatisfy.Query<T> allSatisfy(Collection<T> collection) {
		return AllSatisfy.query(collection);
	}	
	
	public static <T> AnySatisfy.Query<T> anySatisfy(Collection<T> collection) {
		return AnySatisfy.query(collection);
	}
	
	public static <A,T> Collect.Query<A,T> collect(Class<A> type, Collection<T> collection) {
		return Collect.query(type, collection);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Collect.Query<T,T> collect(Collection<T> collection) {
		Class<T> type = (Class<T>) collection.iterator().next().getClass();
		return Collect.query(type, collection);
	}
	
	public static <T> Detect.Query<T> detect(Collection<T> collection) {
		return Detect.query(collection);
	}
	
	public static <A,T> GroupedBy.Query<A,T> groupedBy(Class<A> type, Collection<T> collection) {
		return GroupedBy.query(type, collection);
	}

	public static <T> IndexOf.Query<T> indexOf(Collection<T> collection) {
		return IndexOf.query(collection);
	}
	
	public static <A,T> InjectInto.Query<A,T> injectInto(A value, Collection<T> collection) {
		return InjectInto.query(value, collection);
	}
	
	static void offer(Object result) {
		$result = result;
	}
	
	public static <T> Reject.Query<T> reject(Collection<T> collection) {
		return Reject.query(collection);
	}
	
	public static <T> Select.Query<T> select(Collection<T> collection) {
		return Select.query(collection);
	}
	
	
}
