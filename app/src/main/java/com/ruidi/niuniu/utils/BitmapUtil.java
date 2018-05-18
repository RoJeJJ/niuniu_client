package com.ruidi.niuniu.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.RawRes;
import android.util.Log;
import android.util.LruCache;


public class BitmapUtil {
    private static LruCache<String,Bitmap> cache;
    private static Resources resources;
    private static float xScale;
    private static float yScale;
    private BitmapUtil(){ }
    public static void init(Context context){
        resources = context.getResources();
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        xScale = ((float) screenWidth)/2560;
        yScale = ((float) screenHeight)/1440;
        int maxSize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxSize / 4;
        cache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }
    public static Bitmap getBitmap(String key, @RawRes int res){
        Bitmap src = cache.get(key);
        if (src != null)
            return src;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        src = BitmapFactory.decodeResource(resources,res,options);
        Matrix matrix = new Matrix();
        matrix.postScale(xScale,yScale);
        Bitmap scaleSrc = Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
        src.recycle();
        cache.put(key,scaleSrc);
        return scaleSrc;
    }

//    private int calculateInSampleSize(BitmapFactory.Options op, int reqWidth, int reqheight) {
//        int originalWidth = op.outWidth;
//        int originalHeight = op.outHeight;
//        int inSampleSize = 1;
//        if (originalWidth > reqWidth || originalHeight > reqheight) {
//            int halfWidth = originalWidth / 2;
//            int halfHeight = originalHeight / 2;
//            while ((halfWidth / inSampleSize > reqWidth)
//                    &&(halfHeight / inSampleSize > reqheight)) {
//                inSampleSize *= 2;
//            }
//        }
//        return inSampleSize;
//    }
}
