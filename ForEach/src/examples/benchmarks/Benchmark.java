package examples.benchmarks;


public class Benchmark {

    public static final int LEN = (int) 1e4;
    
    public static void main(String... args) {

        System.err.println("For each pixel in " + LEN*LEN + ":");
        new TightLoop().run();
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
    
}
