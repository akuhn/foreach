package examples.benchmarks;



public class Benchmark {

    public static final int LEN = 31622;
    //public static final int LEN = 3162;
    //public static final boolean BUSY = true;
    public static final boolean BUSY = false;
    public static final boolean ARRAY = true; 
    
    public static void main(String... args) {

        // -XX:+PrintCompilation
        // -XX:+PrintGC
        // -XX:+PrintGCDetails
        // -XX:+TraceGen0Time
        // -XX:+TraceGen1Time
        
        System.err.println("For each pixel in " 
                + LEN*LEN + " do "
                + (BUSY ? "dist((x,y),(value,value))" : "nothing") 
                + (ARRAY ? " and retrieve values from an array" : "") 
                + ":");
        new PlainForLoop().run();
        new OneRunningPixel().run();
        new OneBillionPixels().run();
        new OneBillionFinalPixels().run();
        
    }

    
    
    public Benchmark(Object name) {
        System.err.println(name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private long time;
    
    public void begin() {
        time = System.nanoTime();
    }
    
    public void end() {
        System.err.println("# time = " + (System.nanoTime() - time) / 1e9);
    }

    static final double[][] RANDOM = new double[LEN][];
    static { 
        if (ARRAY) {
            double[] row = new double[LEN];
            for (int n = 0; n < LEN; n++) {
                row[n] = Math.random();
                RANDOM[n] = row;
            }
        }
    }
    private static double n = Math.PI;
    public static double next() {
        return n += 1e-3;
    }
    
}
