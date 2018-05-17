package com.ruidi.niuniu.listener;


import sfs2x.client.SmartFox;

public class SFSClient {
    private static String ip = "192.168.0.114";
    private static int port = 9933;
    private static SmartFox smartFox;
    public static SmartFox instance(){
        if (smartFox == null){
            synchronized (SFSClient.class){
                if (smartFox == null) {
                    smartFox = new SmartFox(false);
                }
            }
        }
        return smartFox;
    }
    public static void  connect(){
        instance().connect(ip,port);
    }
}
