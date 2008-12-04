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


/** Static methods to create queries and poll results. 
 * 
 * @author akuhn
 *
 */
@SuppressWarnings("unchecked")
public class Query {

	private static ThreadLocal result = new ThreadLocal();
	
	static void offer(Object value) {
		result.set(value);
	}
	
	public static <T> T $result() {
		return (T) result.get();
	}
	
	public static <T> AllSatisfy.Query<T> allSatisfy(Iterable<T> iter) {
		AllSatisfy.Query query = new AllSatisfy.Query();
		query.with(iter);
		return query;
	}	
	
	public static <T> AnySatisfy.Query<T> anySatisfy(Iterable<T> iter) {
		AnySatisfy.Query query = new AnySatisfy.Query();
		query.with(iter);
		return query;
	}
	
	public static <A,T> Collect.Query<A,T> collect(Class<A> type, Iterable<T> iter) {
		Collect.Query query = new Collect.Query();
		query.with(iter);
		return query;
	}
	
	public static <T> Collect.Query<T,T> collect(Iterable<T> iter) {
		Collect.Query query = new Collect.Query();
		query.with(iter);
		return query;
	}
	
	public static <T> Count.Query<T> count(Iterable<T> iter) {
		Count.Query query = new Count.Query();
		query.with(iter);
		return query;
	}

	public static <T> Cardinal.Query<T> cardinal(Iterable<T> iter) {
		Cardinal.Query query = new Cardinal.Query();
		query.with(iter);
		return query;
	}

	public static <T> Detect.Query<T> detect(Iterable<T> iter) {
		Detect.Query query = new Detect.Query();
		query.with(iter);
		return query;
	}
	
	public static <A,T> GroupedBy.Query<A,T> groupedBy(Class<A> type, Iterable<T> iter) {
		GroupedBy.Query query = new GroupedBy.Query();
		query.with(iter);
		return query;
	}

	public static <T> IndexOf.Query<T> indexOf(Iterable<T> iter) {
		IndexOf.Query query = new IndexOf.Query();
		query.with(iter);
		return query;
	}
	
	public static <A,T> InjectInto.Query<A,T> injectInto(A value, Iterable<T> iter) {
		InjectInto.Query query = new InjectInto.Query();
		query.with(iter);
		query.initalValue(value);
		return query;
	}
	
	public static <T> Reject.Query<T> reject(Iterable<T> iter) {
		Reject.Query query = new Reject.Query();
		query.with(iter);
		return query;
	}
	
	public static <T> Select.Query<T> select(Iterable<T> iter) {
		Select.Query query = new Select.Query();
		query.with(iter);
		return query;
	}
	
	public static <T> CutPieces.Query<T> cutPieces(Iterable<T> iter) {
		CutPieces.Query query = new CutPieces.Query();
		query.with(iter);
		return query;
	}
	
	public static <T> Fold.Query<T> fold(Iterable<T> iter) {
		Fold.Query query = new Fold.Query();
		query.with(iter);
		return query;
	}
	
}
