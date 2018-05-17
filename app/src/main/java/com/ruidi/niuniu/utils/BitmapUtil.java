package com.ruidi.niuniu.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.RawRes;
import android.util.LruCache;


public class BitmapUtil {
    private LruCache<String,Bitmap> cache;
    private Resources resources;
    private int screenWidth;
    private int screenHeight;
    public BitmapUtil(Context context){
        resources = context.getResources();
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int maxSize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxSize / 1024 / 8;
        cache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return super.sizeOf(key, value);
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }
    public Bitmap getBitmap(String key, @RawRes int res){
        Bitmap src = cache.get(key);
        if (src != null)
            return src;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        src = BitmapFactory.decodeResource(resources,res,options);
        Matrix matrix = new Matrix();
        matrix.postScale(1280f/screenWidth,720f/screenHeight);
        Bitmap scaleSrc = Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
        src.recycle();
        cache.put(key,scaleSrc);
        return scaleSrc;
    }
    private int calculateInSampleSize(BitmapFactory.Options op, int reqWidth, int reqheight) {
        int originalWidth = op.outWidth;
        int originalHeight = op.outHeight;
        int inSampleSize = 1;
        if (originalWidth > reqWidth || originalHeight > reqheight) {
            int halfWidth = originalWidth / 2;
            int halfHeight = originalHeight / 2;
            while ((halfWidth / inSampleSize > reqWidth)
                    &&(halfHeight / inSampleSize > reqheight)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
