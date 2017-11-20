package com.example.ahmed.opengl2lesson.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.example.ahmed.opengl2lesson.graphics.gl_internals.memory.ColorArray;
import com.example.ahmed.opengl2lesson.graphics.gl_internals.FrustumManager;
import com.example.ahmed.opengl2lesson.graphics.gl_internals.ShaderDataLoader;
import com.example.ahmed.opengl2lesson.graphics.gl_internals.memory.GLProgram;
import com.example.ahmed.opengl2lesson.graphics.gl_internals.memory.VertexArray;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ahmed on 11/19/17.
 * <p>
 * OpenGL lesson 1
 */

public class BasicRenderer implements GLSurfaceView.Renderer {

    /**
     * Store our model data in a float buffer.
     */
    private ColorArray mTriangleColors;

    private VertexArray mTriangleVertices;

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];

    /**
     * Store the projection matrix. This is used to project the scene onto a 2D viewport.
     */
    private float[] mProjectionMatrix = new float[16];

    /**
     * Store the model matrix. This matrix is used to move models
     * from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    public BasicRenderer() {

    }

    private GLProgram program;
    private String mvpMatrixVariableName = "u_MVPMatrix";

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background clear color to gray.
        GLES20.glClearColor(0.15f, 0.15f, 0.15f, 0.15f);

        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX,
                lookY, lookZ, upX, upY, upZ);

        String fragmentShader = "precision mediump float;       \n"
                // This is the color from the vertex shader interpolated across the
                + "varying vec4 v_Color;          \n"
                // triangle per fragment.
                + "void main()                    \n"
                + "{                              \n"
                + "   gl_FragColor = v_Color;     \n"
                + "}                              \n";
        String vertexShader = "uniform mat4 u_MVPMatrix;      \n"
                // Per-vertex position information we will pass in.
                + "attribute vec4 a_Position;     \n"

                // Per-vertex color information we will pass in.
                + "attribute vec4 a_Color;        \n"

                // This will be passed into the fragment shader.
                + "varying vec4 v_Color;          \n"

                + "void main()                    \n"
                + "{                              \n"

                // Pass the color through to the fragment shader.
                + "   v_Color = a_Color;          \n"

                // gl_Position is a special variable used to store the final position.
                // Multiply the vertex by the matrix to get the final point in
                // normalized screen coordinates.
                + "   gl_Position = u_MVPMatrix   \n"
                + "               * a_Position;   \n"
                + "}                              \n";

        program = new GLProgram(vertexShader, fragmentShader);
        String positionVariableName = "a_Position";
        program.declareAttribute(positionVariableName);
        String colorVariableName = "a_Color";
        program.declareAttribute(colorVariableName);
        program.declareUniform(mvpMatrixVariableName);

        // Tell OpenGL to use this program when rendering.
        program.activate();

        // This triangle is red, green, and blue.
        final float[] triangle1VerticesData = {
                // X, Y, Z,
                // R, G, B, A
                -0.5f, -0.25f, 0.0f,
                0.5f, -0.25f, 0.0f,
                0.0f, 0.559016994f, 0.0f,
        };

        final float[] triangleColorData = {
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f
        };

        mTriangleVertices = new VertexArray(triangle1VerticesData,
                program.getVariableHandle(positionVariableName), true);
        mTriangleColors = new ColorArray(triangleColorData,
                program.getVariableHandle(colorVariableName), true);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mProjectionMatrix = FrustumManager.createFrustum(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        // Do a complete rotation every 10 seconds.
        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

        // Draw the triangle facing straight on.
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);

        drawTriangle();
    }

    /**
     * Allocate storage for the final combined matrix.
     * This will be passed into the shader program.
     */
    private float[] mMVPMatrix = new float[16];

    private final ShaderDataLoader shaderDataLoader = new ShaderDataLoader();

    private void drawTriangle() {
        shaderDataLoader.start();
        shaderDataLoader.loadData(mTriangleVertices);
        shaderDataLoader.loadData(mTriangleColors);

        // This multiplies the view matrix by the model matrix, and stores the
        // result in the MVP matrix (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(program.getVariableHandle(mvpMatrixVariableName),
                1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        shaderDataLoader.disableHandles();
    }
}
