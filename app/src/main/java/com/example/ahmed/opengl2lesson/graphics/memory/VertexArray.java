package com.example.ahmed.opengl2lesson.graphics.memory;

import java.nio.FloatBuffer;

/**
 * Provides an abstraction layer on top of shader objects
 */

public class VertexArray extends FloatBufferBasedArray {
    public VertexArray() {
        floatsPerItem = 3;
    }

    public void addVertex(VertexInstance vertex) {
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

    public void addVertices(Iterable<VertexInstance> vertices) {
        for (VertexInstance instance : vertices) {
            super.addItem(instance.x);
            super.addItem(instance.y);
            super.addItem(instance.z);
        }
    }

    public FloatBuffer getBuffer() {
        return super.getBuffer();
    }
}
