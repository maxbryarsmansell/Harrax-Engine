package com.max.harrax.graphics.buffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BufferLayout implements Iterable<BufferElement> {

    private ArrayList<BufferElement> elements;
    private int stride = 0;

    public BufferLayout(BufferElement... elements) {
        this.elements = new ArrayList<BufferElement>(Arrays.asList(elements));
        calculateOffsetAndStride();
    }

    public ArrayList<BufferElement> getElements() {
        return this.elements;
    }

    private void calculateOffsetAndStride() {
        int offset = 0;
        stride = 0;
        for (BufferElement element : elements) {
            element.setOffset(offset);
            offset += element.getSize();
            stride += element.getSize();
        }
    }

    public int getStride() {
        return stride;
    }

    @Override
    public Iterator<BufferElement> iterator() {
        return elements.iterator();
    }

}
