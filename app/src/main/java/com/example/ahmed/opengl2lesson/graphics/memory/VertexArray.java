package com.example.ahmed.opengl2lesson.graphics.memory;

import java.nio.FloatBuffer;

/**
 * Provides an abstraction layer on top of shader objects
 */

public class VertexArray extends FloatBufferBasedArray {
    private static final int FLOATS_PER_ITEM = 3;

    public VertexArray(float[] items, int glHandle, boolean normalized) {
        super(items, glHandle, FLOATS_PER_ITEM, normalized);
    }

    public VertexArray(float[] items, boolean normalized) {
        super(items, FLOATS_PER_ITEM, normalized);
    }

    public VertexArray(int glHandle, boolean normalized) {
        super(glHandle, FLOATS_PER_ITEM, normalized);
    }

    public VertexArray(boolean normalized) {
        super(FLOATS_PER_ITEM, normalized);
    }

    public VertexArray() {
        super(FLOATS_PER_ITEM, true);
    }

    void addVertex(VertexInstance vertex) {
        super.addItem(vertex.x);
        super.addItem(vertex.y);
        super.addItem(vertex.z);
    }


    public void addVertex(float x, float y, float z) {
        super.addItem(x);
        super.addItem(y);
        super.addItem(z);
    }

    public void addVertices(VertexInstance[] vertices) {
        for (VertexInstance instance : vertices) {
            super.addItem(instance.x);
            super.addItem(instance.y);
            super.addItem(instance.z);
        }

    }

    public void addVertices(float[] floats) {
        VertexInstance[] vertices = VertexInstance.fromFloats(floats);
        this.addVertices(vertices);
    }

    public FloatBuffer getBuffer() {
        return super.getBuffer();
    }


}
