package com.example.ahmed.opengl2lesson.graphics;

import android.opengl.GLES20;

import java.nio.Buffer;
import java.util.Stack;

/**
 * Created by ahmed on 11/19/17.
 */

class ShaderDataLoader {
    private Stack<Integer> handleStack;
    private States state;

    private enum States {
        Started,
        Closed,
    }

    ShaderDataLoader() {
        handleStack = new Stack<>();
        state = States.Closed;
    }

    void start() {
        assertInState(States.Closed);

        if (handleStack.size() > 0) {
            throw new RuntimeException("Attribute pointer wasn't closed, call disableHandles() first");
        }

        this.state = States.Started;
    }

    void loadData(int handle, int length,
                  int type, boolean normalized, int strideInBytes, Buffer buffer) {
        assertInState(States.Started);

        GLES20.glVertexAttribPointer(handle, length, type, normalized,
                strideInBytes, buffer);
        ErrorChecker.checkGlError("LOAD_DATA:glVertexAttribPointer");

        GLES20.glEnableVertexAttribArray(handle);
        ErrorChecker.checkGlError("LOAD_DATA:glEnableVertexAttribArray");

        handleStack.push(handle);
    }

    void disableHandles() {
        assertInState(States.Started);

        while (!handleStack.empty()) {
            GLES20.glDisableVertexAttribArray(handleStack.pop());
        }

        this.state = States.Closed;
    }

    private void assertInState(States expected) {
        if (state != expected) {
            throw new RuntimeException("Invalid state, Expected to be in [" + expected + "]");
        }
    }
}
