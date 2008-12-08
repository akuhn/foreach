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

/**
 * Static methods to create queries and poll results.
 * 
 * @author akuhn
 * 
 */
public class Query {

    private static ThreadLocal<Object> result = new ThreadLocal<Object>();

    @SuppressWarnings("unchecked")
    public static <T> T $result() {
        return (T) result.get();
    }

    public static <T> Iterable<AllSatisfy<T>> allSatisfy(Iterable<T> elements) {
        return new AllSatisfy<T>().with(elements).iterable();
    }

    public static <T> Iterable<AnySatisfy<T>> anySatisfy(Iterable<T> elements) {
        return new AnySatisfy<T>().with(elements).iterable();
    }

    public static <T> Iterable<Cardinal<T>> cardinal(Iterable<T> elements) {
        return new Cardinal<T>().with(elements).iterable();
    }

    public static <T> Iterable<Collect<T,T>> collect(Iterable<T> elements) {
        return new Collect<T,T>().with(elements).iterable();
    }

    public static <T,R> Iterable<Collect<T,R>> collect(Iterable<T> elements, Class<R> type) {
        return new Collect<T,R>().with(elements).iterable();
    }

    public static <T> Iterable<Count<T>> count(Iterable<T> elements) {
        return new Count<T>().with(elements).iterable();
    }

    public static <T> Iterable<CutPieces<T>> cutPieces(Iterable<T> elements) {
        return new CutPieces<T>().with(elements).iterable();
    }

    public static <T> Iterable<Detect<T>> detect(Iterable<T> elements) {
        return new Detect<T>().with(elements).iterable();
    }

    public static <T> Iterable<Fold<T>> fold(Iterable<T> elements) {
        return new Fold<T>().with(elements).iterable();
    }

    public static <T> Iterable<GroupedBy<T>> groupedBy(Iterable<T> elements) {
        return new GroupedBy<T>().with(elements).iterable();
    }

    public static <T> Iterable<IndexOf<T>> indexOf(Iterable<T> elements) {
        return new IndexOf<T>().with(elements).iterable();
    }

    public static <T,R> Iterable<Inject<T,R>> inject(Iterable<T> elements, R value) {
        return new Inject<T,R>().with(elements).initial(value).iterable();
    }

    public static void offer(Object value) {
        result.set(value);
    }

    public static <T> Iterable<Reject<T>> reject(Iterable<T> elements) {
        return new Reject<T>().with(elements).iterable();
    }

    public static <T> Iterable<Select<T>> select(Iterable<T> elements) {
        return new Select<T>().with(elements).iterable();
    }

    public static <T> Iterable<Sum<T>> sum(Iterable<T> elements) {
        return new Sum<T>().with(elements).iterable();
    }

}
