package examples;

import java.util.Collection;
import java.util.Map;

import static ch.akuhn.util.query.Query.*;
import ch.akuhn.util.query.Cardinal;
import ch.akuhn.util.query.Count;
import ch.akuhn.util.query.CutPieces;
import ch.akuhn.util.query.GroupedBy;

/** Random piece of Smalltalk code, rewritten in Java using ForEach DSL.
 * 
 * @author akuhn
 *
 */
public class Example {


//	| bag |
//	bag :=  Bag new.
//	self fileHistories do: [ :history |
//	    | weeks groups |
//	    weeks := history fileVersions piecesCutWhere: [ :a :b | 
//	        (a timestamp dayOfYear / 7) ~= (b timestamp dayOfYear / 7) ].
//	    groups := weeks groupedBy: [ :week |
//	        (week collectAsSet: #author) size ] affect: #size.
//	    groups keysAndValuesDo: [ :key :value |
//	        bag add: key withOccurrences: value ]].
//	    ^bag associations
	
	@SuppressWarnings("null")
	public Bag<Integer> howManyAuthorsPerWeekAndFile(Project project) {
		Bag<Integer> bag = null;
		for(FileHistory history : project.fileHistories()) {
			for (CutPieces<FileVersion> each : cutPieces(history.fileVersions()))
				each.cutIf = each.value.week() != each.next.week();
			Collection<Collection<FileVersion>> weeks = $result();
			for (GroupedBy<Integer,Collection<FileVersion>> week : groupedBy(Integer.class, weeks)) {
				for (Cardinal<FileVersion> each : cardinal(week.value)) 
					each.yield = each.value.author();
				week.yield = $result();
			}
			Map<Integer,Collection<FileVersion>> groups = $result();
			for (Integer key : groups.keySet()) {
				bag.add(key, groups.get(key).size());
			}
		}
		return bag;
	}

	interface Bag<A> {

		void add(A value, int occurrences);
		
	}
	
	interface Project {
		
		Collection<FileHistory> fileHistories();
		
	}
	
	interface FileHistory {
		
		Collection<FileVersion> fileVersions();

	}
	
	interface FileVersion {

		int week();

		String author();
		
	}
	
}
