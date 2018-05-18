package com.ruidi.niuniu.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
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
    private float yTranslate;
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
        xScale = screenWidth / 2560f;
        yScale = screenHeight / 1440f;
        yTranslate = 5 * yScale;
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
    private float x;
    private float y;
    private void drawBG(){
        Bitmap bg = mBUtil.getBitmap("login_bg",R.drawable.denglujiemian01);
        Matrix matrix = new Matrix();
        x = ((float) screenWidth)/bg.getWidth();
        y = ((float)screenHeight)/bg.getHeight();
        matrix.postScale(x,y);
        mCanvas.drawBitmap(bg,matrix,mPaint);
    }
    private void drawLogo(){
        Bitmap logo = mBUtil.getBitmap("login_logo",R.drawable.denglujiemian05);
        Matrix matrix = new Matrix();
        float f = frame % 61;
        float y;
        float yt_frame = yTranslate / 60f;
        if (!reversal){
            y = f * yt_frame;
        }else {
            y = (60 - f) * yt_frame;
        }
        matrix.postTranslate(222 * xScale,138 * yScale +y);
        matrix.postScale(x,this.y);
        mCanvas.drawBitmap(logo,matrix,mPaint);
        if (f == 60)
            reversal = !reversal;
    }
    private void drawCup(){
        Bitmap cup = mBUtil.getBitmap("logo_cup",R.drawable.denglujiemian03);
        Matrix matrix = new Matrix();
        float f = frame % 61;
        float y;
        if (!reversal){
            y = f * 0.2f;
        }else {
            y = (60 - f) * 0.2f;
        }
        matrix.postTranslate(0,246 * yScale + y);
        mCanvas.drawBitmap(cup,matrix,mPaint);
    }
    private void drawGold() {
        Bitmap gold = mBUtil.getBitmap("logo_gold",R.drawable.denglujiemian02);
        Matrix matrix = new Matrix();
        matrix.postTranslate(80 * xScale,338 * yScale);
        mCanvas.drawBitmap(gold,matrix,mPaint);
    }
    private void drawLoginFrame(){
        Bitmap loginFrame = mBUtil.getBitmap("login_frame",R.drawable.denglujiemian09);
        Matrix matrix = new Matrix();
        matrix.postScale(((float) screenWidth)/loginFrame.getWidth(),((float)screenHeight)/loginFrame.getHeight());
        mCanvas.drawBitmap(loginFrame,matrix,mPaint);
    }
    private void drawButton(){
        Bitmap btn = mBUtil.getBitmap("btn",R.drawable.denglujiemian10);
        Matrix matrix = new Matrix();
        matrix.postTranslate(504 * xScale,530 * yScale);
        mCanvas.drawBitmap(btn,matrix,mPaint);
    }
    public void myDraw(){
        drawBG();
        drawCup();
        drawGold();
        drawLogo();
        drawLoginFrame();
        drawButton();
    }
}
