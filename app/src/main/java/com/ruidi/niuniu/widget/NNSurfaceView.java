package com.ruidi.niuniu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class NNSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    private SurfaceHolder mHolder;
    //子线程标志位
    private boolean mIsDrawing;
    private Canvas mCanvas;
    public static final int TIME_IN_FRAME = 30;
    public static final Object LOCK = new Object();
    public NNSurfaceView(Context context) {
        this(context,null);
    }

    public NNSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NNSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            long startTime = System.currentTimeMillis();
            synchronized (LOCK){
                mCanvas = mHolder.lockCanvas();
                draw();
                mHolder.unlockCanvasAndPost(mCanvas);
            }
            long endTime = System.currentTimeMillis();
            long diffTime = endTime - startTime;
            while (diffTime <= TIME_IN_FRAME){
                diffTime = System.currentTimeMillis() - startTime;
                //线程等待
                Thread.yield();
            }
            draw(mCanvas);
        }
    }
    public void draw(){

    }
}
