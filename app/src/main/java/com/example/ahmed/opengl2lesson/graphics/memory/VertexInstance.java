package com.example.ahmed.opengl2lesson.graphics.memory;

/**
 * Instance of a vertex
 */

class VertexInstance {
    private static final int FLOATS_PER_INSTANCE = 3;
    final float x;
    final float y;
    final float z;

    VertexInstance(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    float[] array() {
        return new float[]{x, y, z};
    }

    static VertexInstance[] fromFloats(float[] array) {
        int vertexCount = array.length / FLOATS_PER_INSTANCE;
        VertexInstance[] packed = new VertexInstance[vertexCount];

        for (int i = 0, j = 0; j < vertexCount; i += 3, j++) {
            packed[j] = new VertexInstance(array[i], array[i + 1], array[i + 2]);
        }

        return packed;
    }
}
