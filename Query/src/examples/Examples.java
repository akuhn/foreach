package examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Examples {

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
	
}

