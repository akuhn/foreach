package examples.benchmarks;


public class Benchmark {

    //public static final int LEN = 31622;
    public static final int LEN = 3162;
    
    public static void main(String... args) {

        // -XX:+PrintCompilation
        // -XX:+PrintGC
        // -XX:+PrintGCDetails
        // -XX:+TraceGen0Time
        // -XX:+TraceGen1Time
        
        System.err.println("For each pixel in " + LEN*LEN + ":");
        new TightLoop().run();
        new OneRunningPixel().run();
        new OneBillionPixels().run();
        new OneBillionFinalPixels().run();
        new BusyLoop().run();
        
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

    //private static final double[] RANDOM = new double[100];
    //static { for (int n = 0; n < 100; n++) RANDOM[n] = Math.random(); }
    //private static int index = 0;
    private static double n = Math.PI;
    public static double next() {
        //return System.currentTimeMillis();
        //if (index == 100) index = 0;
        return n += 1e-8;//RANDOM[index++];
    }
    
}
