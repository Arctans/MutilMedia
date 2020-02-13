package com.example.mutilmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import util.FileTypeUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private String musicPath = GlobalVar.EXTERNPATH+"/12530/download/";
    private ListView listCover;
    private List<String> localList = new ArrayList<>();
    private List<String> recentList = new ArrayList<>();
    private List<String> favoriteList = new ArrayList<>();
    private int[] permissionCheck = new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0x123);
        startPlayer();
    }
    public void myClick(View v){
        switch (v.getId()){
            case R.id.favoriteId:
                favoriteClick();
                Log.d(TAG, "myClick: favorte");
                break;
            case R.id.localId:
                Log.d(TAG, "myClick: local");
                localClick();
                break;
            case R.id.recentId:
                recentClick();
                Log.d(TAG, "myClick: recent");
                break;
        }
    }

    private void localClick(){

        if(permissionCheck[0]==1){

        }else{
            Toast.makeText(MainActivity.this,
                    R.string.write_permission,Toast.LENGTH_LONG).show();
            return ;
        }
        File file = new File(musicPath);
        if(file.exists()){
            File[] files = file.listFiles();
            List<File> fileList = findMusicFile(files);
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.song_list,null);
            ListView listSong = v.findViewById(R.id.song_list);
            
            listSong.setAdapter(new MyAdapter(MainActivity.this,fileList));
            listSong.setOnItemClickListener(((parent, view, position, id) -> {
                startPlay(fileList.get(position).getName());
            }));
            setContentView(v);
        }else{
            Toast.makeText(MainActivity.this,
                            R.string.file_not_exit_tip,Toast.LENGTH_LONG).show();
        }

    }
    private void favoriteClick(){

    }
    private void recentClick(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0x123){
            if(grantResults!=null&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                permissionCheck[0]=1;
            }
        }
    }

    public class MyAdapter extends BaseAdapter{
        Context context;
        List<File> fileList;
        public MyAdapter(Context context,List<File> files){
            this.fileList = files;
            this.context=context;
        }

        @Override
        public int getCount() {
            return fileList.size();
        }

        @Override
        public Object getItem(int position) {
            return fileList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.song_item,null);
                TextView textView = view.findViewById(R.id.songnameId);
                textView.setText(fileList.get(position).getName());
            }else {
                view = convertView;
                TextView tv = view.findViewById(R.id.songnameId);
                tv.setText(fileList.get(position).getName());
            }

            return view;
        }
    }
    public List<File> findMusicFile(File[] files){
        List<File> fileList = new ArrayList<>();
        for(File f :files)
        {
            if(f.isDirectory()){
                continue;
            }else if(FileTypeUtil.isAudioFileType(
                    FileTypeUtil.getFileType(f.getName()).fileType)){
                fileList.add(f);
            }

        }

        return fileList;
    }

    private void startPlayer(){
        Intent intentService = new Intent(MainActivity.this,MusicService.class);
        startService(intentService);
    }
    private void startPlay(String name){


        Intent intentReciver = new Intent();
        intentReciver.setAction(GlobalVar.MUSIC_SREVICE_RECIVER_ACTION);
        intentReciver.setPackage(GlobalVar.MUSIC_RECIVER_PACKAGE);
        intentReciver.putExtra(GlobalVar.MUSIC_RECIVER_PLAY,musicPath+name);
        sendBroadcast(intentReciver);
    }


}
