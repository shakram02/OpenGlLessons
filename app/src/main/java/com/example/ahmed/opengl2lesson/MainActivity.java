package com.example.ahmed.opengl2lesson;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.example.ahmed.opengl2lesson.graphics.BasicRenderer;
import com.example.ahmed.opengl2lesson.graphics.CustomSurfaceView;

public class MainActivity extends AppCompatActivity {

    /**
     * Hold a reference to our GLSurfaceView
     */
    private CustomSurfaceView mGLSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

        if (!supportsEs2) {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            return;
        }

        mGLSurfaceView = new CustomSurfaceView(this);
        mGLSurfaceView.setEGLContextClientVersion(2);
        // Request an OpenGL ES 2.0 compatible context.
        mGLSurfaceView.setRenderer(new BasicRenderer());

        setContentView(mGLSurfaceView);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mGLSurfaceView.onResume();
    }
}
