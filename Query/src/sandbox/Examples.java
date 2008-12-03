package sandbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import ch.akuhn.util.query.Each;
import ch.akuhn.util.query.Filter;
import ch.akuhn.util.query.Query;
import ch.akuhn.util.query.Select;
import ch.akuhn.util.query.Both;
import ch.akuhn.util.query.Sum;

public class Examples {

	public static void main(String[] args) {
		System.out.println(newInjectInto());
		System.out.println(newCollect());
		System.out.println(newSelect());
		System.out.println(newGroupedBy());
	}
	
	public static Collection<String> sample() {
		Collection<String> $ = new ArrayList<String>();
		$.add("A");
		$.add("quick");
		$.add("brown");
		$.add("fox");
		$.add("jumps");
		$.add("over");
		return $;
	}
	
	public static void collect() {
		Collection<String> result = new ArrayList<String>();
		for (String each : sample()) {
			result.add(each.toUpperCase());
		}
		System.out.println(result);
	}

	public static void select() {
		Collection<String> result = new ArrayList<String>();
		for (String each : sample()) {
			if (each.length() > 3) result.add(each);
		}
		System.out.println(result);
	}
	
	public static void injectInto() {
		String result = "";
		for (String each : sample()) {
			result = result + each + " ";
		}
		System.out.println(result);
	}
	
	static void oldGroupedBy() {
		Map<Integer, Collection<String>> groups = new HashMap<Integer, Collection<String>>();
		for (String each : sample()) {
			Integer key = each.length();
			Collection<String> group = groups.get(key);
			if (group == null) {
				group = new LinkedList<String>();
				groups.put(key, group);
			}
			group.add(each);
		}
		System.out.println(groups);
	}
	
	static Map<Integer,Collection<String>> newGroupedBy() {
		for (Both<Integer,String> word : Query.groupedBy(Integer.class, sample())) {
			word.yield = word.each.length();
		}
		return Query.result();
	}
	
	static Collection<String> newSelect() {
		for (Filter<String> word : Select.from(sample())) {
			word.yield = word.each.length() > 3;
		}
		return Query.result();
	}

	static Collection<String> newCollect() {
		for (Each<String> word : Query.collect(sample())) {
			word.yield = word.each.length();
		}
		return Query.result();
	}

	static Collection<String> newInjectInto() {
		for (Sum<String,String> word : Query.injectInto("String:", sample())) {
			word.yield = word.value + " " + word.each;
		}
		return Query.result();
	}

}

