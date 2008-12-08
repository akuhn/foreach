package ch.akuhn.util.query;

public class Count<Each> extends For<Each,Count<Each>> {

    private int count;
    public Each element;
    public boolean yield;

    @Override
    protected void afterEach() {
        if (yield) count++;
    }

    @Override
    protected Object afterLoop() {
        return count;
    }

    @Override
    protected void beforeEach(Each each) {
        element = each;
        yield = false;
    }

    @Override
    protected void beforeLoop() {
        count = 0;
    }

}
