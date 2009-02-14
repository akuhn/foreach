package examples;

import org.junit.Test;

import ch.akuhn.util.EachXY;

public class ForEachXYTest {

    @Test
    public void test() {
        EachXY range = new EachXY(4,3);
        for (EachXY each: range) {
            System.out.println(each);
        }
    }
    
}
