package com.ruidi.niuniu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruidi.niuniu.R;
import com.ruidi.niuniu.model.Account;

import org.apache.commons.lang.StringUtils;

public class MainActivity extends AppCompatActivity {
    private DisplayMetrics dm;
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = getResources().getDisplayMetrics();
        account = (Account) getIntent().getSerializableExtra("acc");
//        initViews();
    }

//    private void initViews() {
//        ImageView headimg = findViewById(R.id.headimg);
//        if (StringUtils.isNotBlank(account.getHeadimg())) {
//            Log.i("head",account.getHeadimg());
//            Glide.with(this).load(account.getHeadimg()).into(headimg);
//        }
//        headimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
}
