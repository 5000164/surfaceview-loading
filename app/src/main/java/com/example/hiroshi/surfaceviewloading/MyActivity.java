package com.example.hiroshi.surfaceviewloading;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {

    LoadingSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mSurfaceView = new LoadingSurfaceView(this);
        setContentView(this.mSurfaceView);
    }
}
