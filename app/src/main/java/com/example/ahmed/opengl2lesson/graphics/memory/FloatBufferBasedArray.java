package com.example.ahmed.opengl2lesson.graphics.memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Represents a class that can be sent over to the GPU using a buffer. Maintaining the state
 * of the buffer is handled by the user
 */

public abstract class FloatBufferBasedArray {
    private ArrayList<Float> raw;
    private boolean isDirty = true;
    private FloatBuffer cache;
    int floatsPerItem = 0;

    private static final int FLOAT_SIZE = 4;

    FloatBufferBasedArray() {
        raw = new ArrayList<>();
    }


    /**
     * @return The number of logical instances in the array (i.e position)
     */
    private int getItemCount() {
        return raw.size() / floatsPerItem;
    }

    /**
     * @return Size of all objects in the array in bytes
     */
    public int getSizeInBytes() {
        return getItemCount() * FLOAT_SIZE;
    }


    public FloatBuffer getBuffer() {
        if (isDirty) {
            reallocateBuffer();
        }

        return cache;
    }

    void addItem(float a) {
        isDirty = true;

        this.raw.add(a);
    }

    /**
     * Updates the value of the member variable 'cache' when
     * the state is dirty
     */
    private void reallocateBuffer() {
        isDirty = false;

        FloatBuffer buffer = ByteBuffer
                .allocateDirect(raw.size() * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        for (float f : raw) {
            buffer.put(f);
        }

        cache = buffer;
    }

    void assertLength() {
        if (raw.size() % floatsPerItem != 0) {
            throw new RuntimeException("Invalid points were added");
        }
    }

    void addAll(Iterable<Float> items) {
        for (float v : items) {
            this.addItem(v);
        }

        assertLength();
    }

    void addAll(float[] items) {
        for (float v : items) {
            this.addItem(v);
        }

        assertLength();
    }
}
