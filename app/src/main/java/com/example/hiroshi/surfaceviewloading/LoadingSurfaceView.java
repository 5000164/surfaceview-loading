package com.example.hiroshi.surfaceviewloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LoadingSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    SurfaceHolder mSurfaceHolder;
    Thread mThread;

    int mViewWidth;
    int mViewHeight;

    static final long FPS = 60;
    static final long FRAME_TIME = 1000 / FPS;

    public LoadingSurfaceView(Context context) {
        super(context);

        this.mSurfaceHolder = getHolder();
        this.mSurfaceHolder.addCallback(this);
    }

    @Override
    public void run() {

        Canvas canvas = null;
        Paint circlePaint = new Paint();
        Paint backgroundPaint = new Paint();

        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);
        circlePaint.setAntiAlias(true);
        backgroundPaint.setColor(Color.WHITE);

        int circleSize = 10;

        long startTime;
        long endTime;
        long lapTime;

        while (this.mThread != null) {
            startTime = System.currentTimeMillis();

            canvas = null;

            try {
                canvas = this.mSurfaceHolder.lockCanvas();

                canvas.drawRect(0, 0, this.mViewWidth, this.mViewHeight, backgroundPaint);
                canvas.drawCircle(this.mViewWidth / 2, this.mViewHeight / 2, circleSize, circlePaint);

                this.mSurfaceHolder.unlockCanvasAndPost(canvas);

                // ピタゴラスの定理より、円が画面外に出たら初期化
                if (circleSize > Math.sqrt(Math.pow(this.mViewWidth, 2) + Math.pow(this.mViewHeight, 2))) {
                    circleSize = 10;
                    Thread.sleep(300);
                } else {
                    circleSize += 7;
                }

                endTime = System.currentTimeMillis();
                lapTime = endTime - startTime;

                if (lapTime < FRAME_TIME) {
                    Thread.sleep(FRAME_TIME - lapTime);
                }
            } catch (Exception e) {
            }

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.mViewWidth = width;
        this.mViewHeight = height;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.mThread = new Thread(this);
        this.mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.mThread = null;
    }

}
