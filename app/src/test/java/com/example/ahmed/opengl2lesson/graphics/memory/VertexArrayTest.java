package com.example.ahmed.opengl2lesson.graphics.memory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testing vertex array
 * - It should report when number of vertices isn't divisible by 3
 */
public class VertexArrayTest {
    @Test
    public void add_item_isCorrect() throws Exception {
        VertexArray vertexArray = new VertexArray();

        vertexArray.addVertex(new VertexInstance(0, 0.41f, 0));
        float[] arr = vertexArray.getBuffer().array();

        assertArrayEquals("Failed to convert to float array",
                new float[]{0f, 0.41f, 0f}, arr, 0.1f);
    }
}