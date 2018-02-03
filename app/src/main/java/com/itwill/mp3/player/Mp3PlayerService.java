package com.itwill.mp3.player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class Mp3PlayerService extends Service {

    MediaPlayer mediaPlayer;
    int status=0;
    /*
      0.START
      1.PLAY
      2.PAUSE
     */
    public Mp3PlayerService() {
        status=0;
        ServiceStatus.status=0;
    }
    @Override
    public void onCreate() {
        status=0;
        ServiceStatus.status=0;
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        addNotification();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String command = intent.getStringExtra("command");
        Log.e("Mp3PlayerService","cpmmand:"+command+",status:"+status);
        if(command.equals("PLAY")){
            if(status==0){
                //최초PLAY실행
                try {
                    mediaPlayer.setDataSource("/sdcard/test.mp3");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (Exception e){
                    e.printStackTrace();
                    if(mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                }
            }else if(status==1){
                //PLAY상태일때

            }else if(status==2){
                //PAUSE상태일때
                mediaPlayer.start();
            }
            status=1;
            ServiceStatus.status=1;

        }else if(command.equals("PAUSE")){
            if(status==0){

            }else if(status==1){
                mediaPlayer.pause();
            }else if(status==2){

            }
            status=2;
            ServiceStatus.status=2;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("Mp3PlayerService","onDestroy");
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
        removeNotification();
    }
    /*********************************/
    private void addNotification(){
        NotificationManager notificationManager=
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder notificationBuilder=new Notification.Builder(getApplicationContext());
        notificationBuilder.setAutoCancel(false);
        notificationBuilder.setSmallIcon(android.R.drawable.ic_media_play);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentTitle("타이틀");
        notificationBuilder.setContentText("텍스트");

        Intent intent = new Intent(getApplicationContext(), Mp3PlayerActivity.class);
        PendingIntent pendingIntent=
                PendingIntent.getActivity(getApplicationContext(),99, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.addAction(android.R.drawable.btn_star,"내부타이틀",pendingIntent);
        Notification notification = notificationBuilder.build();
        notificationManager.notify(1,notification);

    }
    private void removeNotification(){
        NotificationManager notificationManager=
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);

    }
    /*********************************/
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
