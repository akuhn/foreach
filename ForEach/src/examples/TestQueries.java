package examples;

import static ch.akuhn.util.query.Query.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import ch.akuhn.util.query.AllSatisfy;
import ch.akuhn.util.query.AnySatisfy;
import ch.akuhn.util.query.Cardinal;
import ch.akuhn.util.query.Collect;
import ch.akuhn.util.query.Count;
import ch.akuhn.util.query.CutPieces;
import ch.akuhn.util.query.Detect;
import ch.akuhn.util.query.Fold;
import ch.akuhn.util.query.GroupedBy;
import ch.akuhn.util.query.IndexOf;
import ch.akuhn.util.query.Inject;
import ch.akuhn.util.query.Reject;
import ch.akuhn.util.query.Select;

@SuppressWarnings("serial")
public class TestQueries {

	public static final Collection<String> $fox = new ArrayList<String>() {{
		add("The");add("quick");add("brown");add("fox");add("jumps");
		add("over");add("the");add("lazy");add("dog");
	}};
	
	public static void main(String[] args) {
		$verbose = true;
		TestQueries eg = new TestQueries();
		eg.exampleAllSatisfy();
		eg.exampleAllSatisfy2();
		eg.exampleAnySatisfy();
		eg.exampleAnySatisfy2();
		eg.exampleCollect();
		eg.exampleCollect2();
		eg.exampleCount();
		eg.exampleCutPieces();
		eg.exampleDetect();
		eg.exampleDetect2();
		eg.exampleFold();
		eg.exampleIndexOf();
		eg.exampleIndexOf2();
		eg.exampleInjectInto();
		eg.exampleSelect();
		eg.exampleReject();
	}

	private static boolean $verbose = false;

	@Test
	public void exampleAllSatisfy() {
		for (AllSatisfy<String> each : all($fox)) {
			each.yield = each.value.length() > 2;
		}
		puts($result());
		assertEquals(true,$result());
	}

	@Test
	public void exampleAllSatisfy2() {
		for (AllSatisfy<String> each : all($fox)) {
			each.yield = each.value.length() > 3;
		}
		puts($result());
		assertEquals(false,$result());
	}

	@Test
	public void exampleAnySatisfy() {
		for (AnySatisfy<String> each : any($fox)) {
			each.yield = each.value.length() == 4;
		}
		puts($result());
		assertEquals(true,$result());
	}

	@Test
	public void exampleAnySatisfy2() {
		for (AnySatisfy<String> each : any($fox)) {
			each.yield = each.value.length() == 2;
		}
		puts($result());
		assertEquals(false,$result());
	}

	@Test
	public void exampleCollect() {
		for (Collect<String,Integer> each : collect($fox,Integer.class)) {
			each.yield = each.value.length();
		}
		puts($result());
		assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]",$result().toString());
	}

	@Test
	public void exampleCardinal() {
		for (Cardinal<String> each : cardinal($fox)) {
			each.yield = each.value.length();
		}
		puts($result());
		assertEquals(3,$result());
	}

	@Test
	public void exampleCount() {
		for (Count<String> each : count($fox)) {
			each.yield = each.value.length() == 3;
		}
		puts($result());
		assertEquals(4,$result());
	}

	@Test
	public void exampleCollect2() {
		for (Collect<String,String> each : collect($fox)) {
			each.yield = each.value.toUpperCase();
		}
		puts($result());
		assertEquals("[THE, QUICK, BROWN, FOX, JUMPS, OVER, THE, LAZY, DOG]",$result().toString());
	}
	
	@Test
	public void exampleDetect() {
		for (Detect<String> each : detect($fox)) {
			each.yield = each.value.length() == 4;
		}
		puts($result());
		assertEquals("over",$result());
	}

	@Test
	public void exampleDetect2() {
		for (Detect<String> each : detect($fox)) {
			each.yield = each.value.length() == 2;
		}
		puts($result());
		assertEquals(null,$result());
	}

	@Test
	public void exampleInjectInto() {
		for (Inject<String,Integer> each : inject($fox, 0)) {
			each.yield = each.yield + each.value.length();
		}
		puts($result());
		assertEquals(35,$result());
	}
	
	@Test
	public void exampleIndexOf() {
		for (IndexOf<String> each : indexOf($fox)) {
			each.yield = each.value.length() == 4;
		}
		puts($result());
		assertEquals(6,$result());
	}

	@Test
	public void exampleIndexOf2() {
		for (IndexOf<String> each : indexOf($fox)) {
			each.yield = each.value.length() == 2;
		}
		puts($result());
		assertEquals(-1,$result());
	}

	@Test
	public void exampleCutPieces() {
		for (CutPieces<String> each : cut($fox)) {
			each.yield = each.prev.length() > each.next.length();
		}
		puts($result());
		assertEquals("[[The, quick, brown], [fox, jumps], [over], [the, lazy], [dog]]",
				$result().toString());
	}
		
	@Test
	public void exampleReject() {
		for (Reject<String> each : reject($fox)) {
			each.yield = each.value.length() > 3;
		}
		puts($result());
		assertEquals("[The, fox, the, dog]",$result().toString());
	}

	@Test
	public void exampleGroupedBy() {
		for (GroupedBy<String> each : groupedBy($fox)) {
			each.yield = each.value.length();
		}
		puts($result());
		assertEquals("{3=[The, fox, the, dog], 4=[over, lazy], 5=[quick, brown, jumps]}",
				$result().toString());
	}

	@Test
	public void exampleSelect() {
		for (Select<String> each: select($fox)) {
			each.yield = each.value.length() > 3;
		}
		puts($result());
		assertEquals("[quick, brown, jumps, over, lazy]",$result().toString());
	}
	
	@Test
	public void exampleFold() {
		for (Fold<String> each: fold($fox)) {
			each.yield = each.yield + " " + each.value;
		}
		puts($result());
		assertEquals("The quick brown fox jumps over the lazy dog",$result());
	}

	private void puts(Object object) {
		if ($verbose ) System.out.println(object);
	}
	
}

