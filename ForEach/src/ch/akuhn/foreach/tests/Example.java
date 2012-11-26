package ch.akuhn.foreach.tests;

import java.util.Collection;
import java.util.Map;

import ch.akuhn.foreach.Collect;
import ch.akuhn.foreach.CutPieces;
import ch.akuhn.foreach.ForEach;
import ch.akuhn.foreach.GroupedBy;

/**
 * Random piece of Smalltalk code, rewritten in Java using ForEach DSL.
 * 
 * <pre>
 * | bag |
 * bag := Bag new.
 *   self fileHistories do: [ :history |
 *   | weeks groups |
 *   weeks := history fileVersions piecesCutWhere: [ :a :b |
 *     (a timestamp dayOfYear / 7) ~= (b timestamp dayOfYear / 7) ].
 *     groups := weeks groupedBy: [ :week |
 *       (week collectAsSet: #author) size ] affect: #size.
 *     groups keysAndValuesDo: [ :key :value |
 *       bag add: key withOccurrences: value ]].
 *   ^bag associations
 * </pre>
 * 
 * @author akuhn
 * 
 */
public class Example {

	interface Bag<A> {

		void add(A value, int occurrences);

	}

	interface FileHistory {

		Collection<FileVersion> fileVersions();

	}

	interface FileVersion {

		String author();

		int week();

	}

	interface Project {

		Collection<FileHistory> fileHistories();

	}

	@SuppressWarnings("null")
	public Bag<Integer> howManyAuthorsPerWeekAndFile(Project project) {
		Bag<Integer> bag = null;
		for (FileHistory history: project.fileHistories()) {
			for (CutPieces<FileVersion> each: ForEach.cutPieces(history.fileVersions()))
				each.yield = each.value.week() != each.next.week();
			Collection<Collection<FileVersion>> weeks = ForEach.result();
			for (GroupedBy<Collection<FileVersion>> week: ForEach.groupedBy(weeks)) {
				for (Collect<FileVersion, String> each: ForEach.collect(String.class, week.value))
					each.yield = each.value.author();
				Collection<String> authors = ForEach.result();
				week.yield = authors.size();
			}
			Map<Integer, Collection<FileVersion>> groups = ForEach.result();
			for (Integer key: groups.keySet()) {
				bag.add(key, groups.get(key).size());
			}
		}
		return bag;
	}
}
