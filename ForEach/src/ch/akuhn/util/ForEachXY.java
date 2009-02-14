package ch.akuhn.util;

import static java.lang.String.format;

import java.util.Iterator;


public abstract class ForEachXY<This extends ForEachXY<This>> implements Iterable<This> {

    public final int width;
    public final int height;
    public int x = 0;
    public int y = 0;
    
    public ForEachXY(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public Iterator<This> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<This> {
    
        private int x = 0;
        private int y = 0;
        
        public boolean hasNext() {
            return y < height;
        }
    
        @SuppressWarnings("unchecked")
        public This next() {
            This.this.x = x;
            This.this.y = y;
            if (++x >= width) {
                x = 0;
                y++;
            }
            return (This) This.this;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

    @Override
    public String toString() {
        return format("(%d,%d)", x, y);
    }
    
}
