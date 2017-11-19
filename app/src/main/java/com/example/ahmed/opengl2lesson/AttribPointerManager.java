package com.example.ahmed.opengl2lesson;

import android.opengl.GLES20;

import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by ahmed on 11/19/17.
 */

public class AttribPointerManager {
    private ArrayList<Integer> handleList;

    public AttribPointerManager() {
        handleList = new ArrayList<>();
    }

    public void loadData(int handle, int sizeInBytes,
                         int type, boolean normalized, int strideInBytes, Buffer buffer) {

        if (handleList.size() > 0) {
            throw new RuntimeException("Attribute pointer wasn't closed, call disableHandles() first");
        }

        GLES20.glVertexAttribPointer(handle, sizeInBytes, type, normalized,
                strideInBytes, buffer);
        GLES20.glEnableVertexAttribArray(handle);
    }

    public void disableHandles() {
        for (int handle : handleList) {
            GLES20.glDisableVertexAttribArray(handle);
        }

        handleList.clear();
    }
}
