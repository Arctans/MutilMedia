package com.example.mutilmedia;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();
    private String filePath;
    private MusicReciver musicReciver;
    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        musicReciver = new MusicReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalVar.MUSIC_SREVICE_RECIVER_ACTION);
        registerReceiver(musicReciver,filter);
        Log.d(TAG, "onCreate: MusiceService2");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicReciver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MusicReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String str = intent.getStringExtra(GlobalVar.MUSIC_RECIVER_PLAY);
            Log.d(TAG, "onReceive: "+str);
            //if(action.equals())
            try{
                mediaPlayer.reset();
                mediaPlayer.setDataSource(str);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
