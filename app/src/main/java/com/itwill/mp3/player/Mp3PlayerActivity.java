package com.itwill.mp3.player;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Mp3PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton playIB;
    ImageButton pauseIB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_player);
        playIB=(ImageButton)findViewById(R.id.playIB);
        pauseIB=(ImageButton)findViewById(R.id.pauseIB);
        //event
        pauseIB.setOnClickListener(this);
        playIB.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int bid = v.getId();

        if(bid==R.id.playIB){
            Intent intent=new Intent();
            intent.setPackage("com.itwill.mp3.player");
            intent.setAction("com.itwill.mp3.player.MP3_PLAYER");
            //intent.setClass(getApplicationContext(),Mp3PlayerService.class);
            intent.putExtra("command","PLAY");
            startService(intent);

        }else if(bid==R.id.pauseIB){
            Intent intent=new Intent();
            intent.setPackage("com.itwill.mp3.player");
            intent.setAction("com.itwill.mp3.player.MP3_PLAYER");
            // intent.setClass(getApplicationContext(),Mp3PlayerService.class);
            intent.putExtra("command","PAUSE");
            startService(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(ServiceStatus.status!=1){
            Intent intent=new Intent();
            intent.setPackage("com.itwill.mp3.player");
            intent.setAction("com.itwill.mp3.player.MP3_PLAYER");
            stopService(intent);
        }

    }

}
