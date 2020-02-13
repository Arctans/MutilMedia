package com.example.mutilmedia;

import android.os.Environment;

public final  class GlobalVar {
    public static final String MUSIC_SREVICE_ACTION = "android.intent.action.SERVIC_ACTION";
    public static final String MUSIC_SREVICE_RECIVER_ACTION = "android.intent.action.SERVICE_RECIVER_ACTION";
    public static final String MUSIC_RECIVER_PACKAGE = "com.example.mutilmedia";
    public static final String MUSIC_RECIVER_PLAY = "PLAY";
    public static final String MUISC_RECIVER_STOP = "STOP";
    public static final String MUISC_RECIVER_NEXT = "NEXT";
    public static final String MUISC_RECIVER_PREV = "PREV";

    //Extern Stoarge Path
    public static final String EXTERNPATH = Environment.getExternalStorageDirectory().getPath();

}
