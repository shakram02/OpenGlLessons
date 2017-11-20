package com.example.ahmed.opengl2lesson.graphics.memory;

import android.support.annotation.NonNull;

/**
 * Four dimensional data point
 */

public interface FourDimensional<T> extends ThreeDimensional<T> {
    @NonNull
    T getW();
}
