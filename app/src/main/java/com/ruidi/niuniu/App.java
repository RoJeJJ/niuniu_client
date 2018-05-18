package com.ruidi.niuniu;

import android.app.Application;

import com.ruidi.niuniu.utils.BitmapUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BitmapUtil.init(this);
    }
}
