package examples;

import static ch.akuhn.util.query.Query.$result;
import static ch.akuhn.util.query.Query.allSatisfy;
import static ch.akuhn.util.query.Query.anySatisfy;
import static ch.akuhn.util.query.Query.cardinal;
import static ch.akuhn.util.query.Query.collect;
import static ch.akuhn.util.query.Query.count;
import static ch.akuhn.util.query.Query.cutPieces;
import static ch.akuhn.util.query.Query.detect;
import static ch.akuhn.util.query.Query.fold;
import static ch.akuhn.util.query.Query.groupedBy;
import static ch.akuhn.util.query.Query.indexOf;
import static ch.akuhn.util.query.Query.inject;
import static ch.akuhn.util.query.Query.reject;
import static ch.akuhn.util.query.Query.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    public static final Iterable<String> FOX = new ArrayList<String>() {
        {
            add("The");
            add("quick");
            add("brown");
            add("fox");
            add("jumps");
            add("over");
            add("the");
            add("lazy");
            add("dog");
        }
    };

    private static boolean VERBOSE = false;

    public static void main(String[] args) {
        VERBOSE = true;
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

    @Test
    public void exampleAllSatisfy() {
        for (AllSatisfy<String> each : allSatisfy(FOX)) {
            each.yield = each.element.length() > 2;
        }
        puts($result());
        assertEquals(true, $result());
    }

    @Test
    public void exampleAllSatisfy2() {
        for (AllSatisfy<String> each : allSatisfy(FOX)) {
            each.yield = each.element.length() > 3;
        }
        puts($result());
        assertEquals(false, $result());
    }

    @Test
    public void exampleAnySatisfy() {
        for (AnySatisfy<String> each : anySatisfy(FOX)) {
            each.yield = each.element.length() == 4;
        }
        puts($result());
        assertEquals(true, $result());
    }

    @Test
    public void exampleAnySatisfy2() {
        for (AnySatisfy<String> each : anySatisfy(FOX)) {
            each.yield = each.element.length() == 2;
        }
        puts($result());
        assertEquals(false, $result());
    }

    @Test
    public void exampleCardinal() {
        for (Cardinal<String> each : cardinal(FOX)) {
            each.yield = each.element.length();
        }
        puts($result());
        assertEquals(3, $result());
    }

    @Test
    public void exampleCollect() {
        for (Collect<String,Integer> each : collect(FOX, Integer.class)) {
            each.yield = each.element.length();
        }
        puts($result());
        assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", $result().toString());
    }

    @Test
    public void exampleCollect2() {
        for (Collect<String,String> each : collect(FOX)) {
            each.yield = each.element.toUpperCase();
        }
        puts($result());
        assertEquals("[THE, QUICK, BROWN, FOX, JUMPS, OVER, THE, LAZY, DOG]", $result().toString());
    }

    @Test
    public void exampleCount() {
        for (Count<String> each : count(FOX)) {
            each.yield = each.element.length() == 3;
        }
        puts($result());
        assertEquals(4, $result());
    }

    @Test
    public void exampleCutPieces() {
        for (CutPieces<String> each : cutPieces(FOX)) {
            each.yield = each.prev.length() > each.next.length();
        }
        puts($result());
        assertEquals("[[The, quick, brown], [fox, jumps], [over], [the, lazy], [dog]]", $result().toString());
    }

    @Test
    public void exampleDetect() {
        for (Detect<String> each : detect(FOX)) {
            each.yield = each.element.length() == 4;
        }
        puts($result());
        assertEquals("over", $result());
    }

    @Test
    public void exampleDetect2() {
        for (Detect<String> each : detect(FOX)) {
            each.yield = each.element.length() == 2;
        }
        puts($result());
        assertEquals(null, $result());
    }

    @Test
    public void exampleFold() {
        for (Fold<String> each : fold(FOX)) {
            each.yield = each.yield + " " + each.element;
        }
        puts($result());
        assertEquals("The quick brown fox jumps over the lazy dog", $result());
    }

    @Test
    public void exampleGroupedBy() {
        for (GroupedBy<String> each : groupedBy(FOX)) {
            each.yield = each.element.length();
        }
        puts($result());
        assertTrue($result().toString().contains("3=[The, fox, the, dog]"));
        assertTrue($result().toString().contains("4=[over, lazy]"));
        assertTrue($result().toString().contains("5=[quick, brown, jumps]"));
    }

    @Test
    public void exampleIndexOf() {
        for (IndexOf<String> each : indexOf(FOX)) {
            each.yield = each.element.length() == 4;
        }
        puts($result());
        assertEquals(6, $result());
    }

    @Test
    public void exampleIndexOf2() {
        for (IndexOf<String> each : indexOf(FOX)) {
            each.yield = each.element.length() == 2;
        }
        puts($result());
        assertEquals(-1, $result());
    }

    @Test
    public void exampleInjectInto() {
        for (Inject<String,Integer> each : inject(FOX, 0)) {
            each.yield = each.yield + each.element.length();
        }
        puts($result());
        assertEquals(35, $result());
    }

    @Test
    public void exampleReject() {
        for (Reject<String> each : reject(FOX)) {
            each.yield = each.element.length() > 3;
        }
        puts($result());
        assertEquals("[The, fox, the, dog]", $result().toString());
    }

    @Test
    public void exampleSelect() {
        for (Select<String> each : select(FOX)) {
            each.yield = each.element.length() > 3;
        }
        puts($result());
        assertEquals("[quick, brown, jumps, over, lazy]", $result().toString());
    }

    private void puts(Object object) {
        if (VERBOSE) System.out.println(object);
    }

}
