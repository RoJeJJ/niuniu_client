package com.ruidi.niuniu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
    private int screenWidth;
    private int screenHeight;
    private boolean reversal;
    private float xScale;
    private float yScale;
    private float logoTranslate;
    private float cupTranslate;
    private int[] uiweixi;
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
        logoTranslate = 6 * yScale;
        cupTranslate = 10 * yScale;
        uiweixi = new int[]{R.drawable.uiweixi_01,R.drawable.uiweixi_02,R.drawable.uiweixi_03,R.drawable.uiweixi_04,
                R.drawable.uiweixi_05,R.drawable.uiweixi_06,R.drawable.uiweixi_07,R.drawable.uiweixi_08,
                R.drawable.uiweixi_09,R.drawable.uiweixi_10,R.drawable.uiweixi_11,R.drawable.uiweixi_12,
                R.drawable.uiweixi_13,R.drawable.uiweixi_14,R.drawable.uiweixi_15,R.drawable.uiweixi_16};

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
        Bitmap bg = BitmapUtil.getBitmap("login_bg",R.drawable.denglujiemian01);
        Matrix matrix = new Matrix();
        x = ((float) screenWidth)/bg.getWidth();
        y = ((float)screenHeight)/bg.getHeight();
        matrix.postScale(x,y);
        mCanvas.drawBitmap(bg,matrix,mPaint);
    }
    private void drawLogo(){
        Bitmap logo = BitmapUtil.getBitmap("login_logo",R.drawable.denglujiemian05);
        Matrix matrix = new Matrix();
        float f = frame % 61;
        float y;
        float yt_frame = logoTranslate / 60f;
        if (!reversal){
            y = f * yt_frame;
        }else {
            y = (60 - f) * yt_frame;
        }
        matrix.postTranslate(190 * xScale,138 * yScale +y);
        matrix.postScale(x,this.y);
        mCanvas.drawBitmap(logo,matrix,mPaint);
        if (f == 60)
            reversal = !reversal;
    }
    private void drawCup(){
        Bitmap cup = BitmapUtil.getBitmap("logo_cup",R.drawable.denglujiemian03);
        Matrix matrix = new Matrix();
        float f = frame % 61;
        float y;
        float yt_frame = cupTranslate / 60f;
        if (!reversal){
            y = f * yt_frame;
        }else {
            y = (60 - f) * yt_frame;
        }
        matrix.postTranslate(0,246 * yScale - y);
        mCanvas.drawBitmap(cup,matrix,mPaint);
    }
    private void drawGold() {
        Bitmap gold = BitmapUtil.getBitmap("logo_gold",R.drawable.denglujiemian02);
        Matrix matrix = new Matrix();
        matrix.postTranslate(80 * xScale,338 * yScale);
        mCanvas.drawBitmap(gold,matrix,mPaint);
    }
    private void drawLoginFrame(){
        Bitmap loginFrame = BitmapUtil.getBitmap("login_frame",R.drawable.denglujiemian09);
        Matrix matrix = new Matrix();
        matrix.postScale(((float) screenWidth)/loginFrame.getWidth(),((float)screenHeight)/loginFrame.getHeight());
        mCanvas.drawBitmap(loginFrame,matrix,mPaint);
    }
    private float left;
    private float top;
    private float right;
    private float bottom;
    private boolean click = false;
    private void drawButton(){
        Bitmap btn;
        if (!click)
            btn = BitmapUtil.getBitmap("btn",R.drawable.denglujiemian10);
        else
            btn = BitmapUtil.getBitmap("btn_click",R.drawable.denglujiemian21);
        left = 504 * xScale;
        top = 530 * yScale;
        right = left + btn.getWidth();
        bottom = top + btn.getHeight();
        Matrix matrix = new Matrix();
        matrix.postTranslate(504 * xScale,530 * yScale);
        mCanvas.drawBitmap(btn,matrix,mPaint);
    }
    private void drawBtnFrame(){
        int f = (int) (frame % 42);
        f = f / 2;
        f = f>15?0:f;
        if (click)
            f = 0;
        Bitmap frameBitmap = BitmapUtil.getBitmap("uiweixi"+f,uiweixi[f]);
        Matrix matrix = new Matrix();
        matrix.postTranslate(440*xScale,474*yScale);
        mCanvas.drawBitmap(frameBitmap,matrix,mPaint);
    }

    public void myDraw(){
        drawBG();
        drawCup();
        drawGold();
        drawLogo();
        drawLoginFrame();
        drawButton();
        drawBtnFrame();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent","down");
                Log.i("onTouchEvent",""+x+","+y+","+left+","+right+","+top+","+bottom);
                if (x >= left && x <= right && y >= top && y <= bottom) {
                    click = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("onTouchEvent","move");
                if (x >= left && x <= right && y >= top && y <= bottom)
                    click = true;
                else
                    click = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.i("onTouchEvent","up");
                click = false;
                if (x >= left && x <= right && y >= top && y <= bottom){
                    Log.i("click","click");
                }
                break;
        }
        return true;
    }
}
