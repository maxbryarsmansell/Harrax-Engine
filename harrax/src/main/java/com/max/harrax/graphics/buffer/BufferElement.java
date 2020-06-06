package com.max.harrax.graphics.buffer;

public class BufferElement {

    private String name;
    private int offset;
    private ShaderDataType type;
    private int size;
    private boolean normalized;

    public BufferElement(ShaderDataType type, String name, boolean normalized) {
        this.name = name;
        this.type = type;
        this.offset = 0;
        this.size = type.bytesSize();
        normalized = false;
    }

    public BufferElement(ShaderDataType type, String name) {
        this(type, name, false);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getOffset() {
        return offset;
    }

    public ShaderDataType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public int getComponentCount() {
        return type.componentCount();
    }
}