package com.example.ahmed.opengl2lesson.graphics.memory;

import com.example.ahmed.opengl2lesson.graphics.memory.FloatBufferBasedArray;

/**
 * Provides an abstraction layer on top of shader objects
 */

public class VertexArray extends FloatBufferBasedArray {
    public VertexArray() {
        floatsPerItem = 3;
    }

    public void addVertex(float x, float y, float z) {
        super.addItem(x);
        super.addItem(y);
        super.addItem(z);
        super.assertLength();
    }

    public void addVertices(float[] vertices) {
        super.addAll(vertices);
    }

    public void addVertices(Iterable<Float> vertices) {
        super.addAll(vertices);
    }
}
