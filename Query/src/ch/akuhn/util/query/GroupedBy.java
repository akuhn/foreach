/**
 * 
 */
package ch.akuhn.util.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class GroupedBy<K,E> extends AbstractQuery<E,Both<K,E>> {

	private HashMap<Object, Collection<E>> result;

	public GroupedBy(Collection<E> source) {
		super(source);
	}

	@Override
	protected void apply() {
		K key = each.yield;
		Collection<E> group = result.get(key);
		if (group == null) {
			group = new LinkedList<E>();
			result.put(key, group);
		}
		group.add(each.each);
	}

	@Override
	protected void initialize() {
		result = new HashMap<Object,Collection<E>>();
	}

	@Override
	protected Object release() {
		return result;
	}

	@Override
	protected Both<K, E> createEach() {
		return new Both<K,E>();
	}
	
}