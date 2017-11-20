package com.example.ahmed.opengl2lesson.graphics.memory;

import android.support.annotation.NonNull;

/**
 * Three dimensional data point
 */

public interface ThreeDimensional<T> {
    @NonNull
    T getX();

    @NonNull
    T getY();

    @NonNull
    T getZ();
}
