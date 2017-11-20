package com.example.ahmed.opengl2lesson.graphics.gl_internals;

import android.opengl.GLES20;

import com.example.ahmed.opengl2lesson.graphics.gl_internals.memory.FloatBufferBasedArray;

import java.nio.Buffer;
import java.util.Stack;

/**
 * Created by ahmed on 11/19/17.
 * <p>
 * Manages sending data to GPU
 */

public class ShaderDataLoader {
    private Stack<Integer> handleStack;
    private States state;

    /**
     * How many bytes per float.
     */
    private static final int FLOAT_SIZE = 4;

    private enum States {
        Started,
        Closed,
    }

    public ShaderDataLoader() {
        handleStack = new Stack<>();
        state = States.Closed;
    }

    /**
     * Put the machine in the start state
     */
    public void start() {
        assertInState(States.Closed);

        if (handleStack.size() > 0) {
            throw new RuntimeException("Attribute pointer wasn't closed, " +
                    "call disableHandles() first");
        }

        state = States.Started;
    }

    /**
     * Load data to shaders
     *
     * @param handle              handle of the shader variable
     * @param itemLength          number of items in an instance i.e 3 floats/location point
     * @param type                GL_FLOAT...atc
     * @param normalized          Determines whether the values are norm
     * @param strideInBytes       Number of bytes to jump when reading to get a value
     * @param singleContentBuffer Buffer containing data
     */
    void loadData(int handle, int itemLength,
                  int type, boolean normalized, int strideInBytes,
                  Buffer singleContentBuffer) {
        assertInState(States.Started);

        singleContentBuffer.position(0);
        GLES20.glVertexAttribPointer(handle, itemLength, type, normalized,
                strideInBytes, singleContentBuffer);
        ErrorChecker.checkGlError("LOAD_DATA:glVertexAttribPointer");

        GLES20.glEnableVertexAttribArray(handle);
        ErrorChecker.checkGlError("LOAD_DATA:glEnableVertexAttribArray");

        handleStack.push(handle);
    }

    public void loadData(FloatBufferBasedArray array) {
        assertInState(States.Started);


        GLES20.glVertexAttribPointer(array.getGlHandle(), array.getItemLength(),
                array.getType(), array.isNormalized(), array.getStride(), array.getBuffer());
        ErrorChecker.checkGlError("LOAD_DATA:glVertexAttribPointer");

        GLES20.glEnableVertexAttribArray(array.getGlHandle());
        ErrorChecker.checkGlError("LOAD_DATA:glEnableVertexAttribArray");

        handleStack.push(array.getGlHandle());
    }

    /**
     * Handles to data should be disabled after drawing
     */
    public void disableHandles() {
        assertInState(States.Started);

        while (!handleStack.empty()) {
            GLES20.glDisableVertexAttribArray(handleStack.pop());
        }

        state = States.Closed;
    }

    private void assertInState(States expected) {
        if (state != expected) {
            throw new RuntimeException("Invalid state, Expected to be in [" + expected + "]");
        }
    }
}
