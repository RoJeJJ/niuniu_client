package com.ruidi.niuniu.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.ruidi.niuniu.R;
import com.ruidi.niuniu.utils.BitmapUtil;

public class LoginView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder mHolder;
    private boolean mIsDrawing;
    private Canvas mCanvas;
    private long frame;
    private Paint mPaint;
    private BitmapUtil mBUtil;
    private int screenWidth;
    private int screenHeight;
    private boolean reversal;
    private float xScale;
    private float yScale;
    public LoginView(Context context) {
        this(context,null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        xScale = screenWidth / 1280f;
        yScale = screenHeight / 720f;
        mBUtil = new BitmapUtil(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSPARENT);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(false);

        frame = 0;
        reversal = false;
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
        while (mIsDrawing){
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null){
                long startTime = System.currentTimeMillis();
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                frame++;
                myDraw();
                mHolder.unlockCanvasAndPost(mCanvas);
                long diffTime = System.currentTimeMillis() - startTime;
                while (diffTime < 33){ // fps 30;
                    Thread.yield();
                    diffTime = System.currentTimeMillis() - startTime;
                }
            }
        }
    }
    private void drawBG(){
        Bitmap bg = mBUtil.getBitmap("login_bg",R.raw.denglujiemian01);
        Rect rect = new Rect(0,0,screenWidth,screenHeight);
        mCanvas.drawBitmap(bg,null,rect,mPaint);
    }
    private void drawLogo(){
        Bitmap logo = mBUtil.getBitmap("login_logo",R.raw.denglujiemian05);
        Matrix matrix = new Matrix();
        float f = frame % 61;
        float y;
        if (!reversal){
            y = f * 0.16f;
        }else {
            y = (60 - f) * 0.16f;
        }
        matrix.postTranslate(186 * xScale,131 * yScale +y);
        mCanvas.drawBitmap(logo,matrix,mPaint);
        if (f == 60)
            reversal = !reversal;
    }
    private void drawCup(){
        Bitmap cup = mBUtil.getBitmap("logo_cup",R.raw.denglujiemian03);
        Matrix matrix = new Matrix();
        float f = frame % 61;
        float y;
        if (!reversal){
            y = f * 0.2f;
        }else {
            y = (60 - f) * 0.2f;
        }
        matrix.postTranslate(0,237 * yScale + y);
        mCanvas.drawBitmap(cup,matrix,mPaint);
    }
    private void drawGold() {
        Bitmap gold = mBUtil.getBitmap("logo_gold",R.raw.denglujiemian02);
        Matrix matrix = new Matrix();
        matrix.postTranslate(81 * xScale,328 * yScale);
        mCanvas.drawBitmap(gold,matrix,mPaint);
    }
    private void drawLoginFrame(){
        Bitmap loginFrame = mBUtil.getBitmap("login_frame",R.raw.denglujiemian09);
        Rect rect = new Rect(0,0,screenWidth,screenHeight);
        mCanvas.drawBitmap(loginFrame,null,rect,mPaint);
    }
    public void myDraw(){
        drawBG();
        drawCup();
        drawGold();
        drawLogo();
        drawLoginFrame();
    }
}
