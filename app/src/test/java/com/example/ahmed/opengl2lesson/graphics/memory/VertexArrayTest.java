package com.example.ahmed.opengl2lesson.graphics.memory;

import junit.framework.Assert;

import org.junit.Test;

import java.nio.FloatBuffer;

import static org.junit.Assert.*;

/**
 * Testing vertex array
 * - It should report when number of vertices isn't divisible by 3
 */
public class VertexArrayTest {
    @Test
    public void add_item_isCorrect() {
        VertexArray vertexArray = new VertexArray(new float[]{0, 0.41f, 0});
        FloatBuffer vertexBuffer = vertexArray.getBuffer();

        float[] trueValues = new float[]{0f, 0.41f, 0f};
        int i = 0;
        while (vertexBuffer.hasRemaining()) {
            Assert.assertEquals(vertexBuffer.get(), trueValues[i++]);
        }
    }
}