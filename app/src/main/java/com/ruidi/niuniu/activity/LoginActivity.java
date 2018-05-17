package com.ruidi.niuniu.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruidi.niuniu.R;
import com.ruidi.niuniu.listener.SFSClient;
import com.ruidi.niuniu.model.Account;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import org.apache.commons.lang.StringUtils;

import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.LoginRequest;

public class LoginActivity extends AppCompatActivity{
    private boolean autoLogin = false;
    private IEventListener connectionListener;
    private IEventListener LoginListener;
    private IEventListener userInfoListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initViews();
//        addSFSListener();
//        if (!SFSClient.instance().isConnected())
//            SFSClient.connect();
    }
//    private void initViews() {
//        TextView ver = findViewById(R.id.version);
//        String versionName = "";
//        try {
//            versionName = getPackageManager().getPackageInfo(getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        ver.setText(getString(R.string.version, versionName));
//        ImageView login = findViewById(R.id.login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (SFSClient.instance().isConnected()){
//                    login();
//                }else {
//                    autoLogin = true;
//                    SFSClient.connect();
//                }
//            }
//        });
//    }

    private void login(){
        ISFSObject object = new SFSObject();
        object.putInt("sex",1);
        object.putUtfString("nickname","abc");
        object.putUtfString("headimg","https://img2.woyaogexing.com/2018/04/12/3b2d982c87bbef9a!400x400_big.jpg");
        SFSClient.instance().send(new LoginRequest("sdfpdsf-324jfdgfd34",
                "&*JDRfd%)5Fdl", "serverZone", object));

    }
    private void addSFSListener() {
        SFSClient.instance().addEventListener(SFSEvent.CONNECTION, connectionListener = new IEventListener() {
            @Override
            public void dispatch(BaseEvent baseEvent) throws SFSException {
                boolean success = (boolean) baseEvent.getArguments().get("success");
                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, getString(R.string.conn_success), Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (autoLogin)
                        login();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,getString(R.string.conn_fail),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        SFSClient.instance().addEventListener(SFSEvent.LOGIN, LoginListener = new IEventListener() {
            @Override
            public void dispatch(BaseEvent baseEvent) throws SFSException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT).show();
                       SFSClient.instance().send(new ExtensionRequest("userInfo",new SFSObject()));
                    }
                });
            }
        });
        SFSClient.instance().addEventListener(SFSEvent.EXTENSION_RESPONSE, userInfoListener = new IEventListener() {
            @Override
            public void dispatch(BaseEvent baseEvent) throws SFSException {
                String cmd = (String) baseEvent.getArguments().get("cmd");
                if (StringUtils.equals(cmd,"userInfo")){
                    ISFSObject userInfo = (ISFSObject) baseEvent.getArguments().get("params");
                    String json = userInfo.toJson();
                    Account account = new Gson().fromJson(json,Account.class);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("acc",account);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.i("tag","onDestroy");
        if (SFSClient.instance().isConnected())
            SFSClient.instance().disconnect();
        SFSClient.instance().removeEventListener(SFSEvent.CONNECTION,connectionListener);
        SFSClient.instance().removeEventListener(SFSEvent.LOGIN, LoginListener);
        SFSClient.instance().removeEventListener(SFSEvent.EXTENSION_RESPONSE, userInfoListener);
        super.onDestroy();
    }

}