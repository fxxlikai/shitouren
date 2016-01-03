package com.itheima.musicplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	
	MusicControllerInterface mci;
	private static SeekBar sb;
	
	
	static Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bundle = msg.getData();
			int duration = bundle.getInt("duration");
			int currentPosition = bundle.getInt("currentPosition");
			
			sb.setMax(duration);
			sb.setProgress(currentPosition);
		}
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sb = (SeekBar) findViewById(R.id.sb);
        
        //设置seekbar的拖动侦听
        sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress = seekBar.getProgress();
				//改变音乐播放进度
				mci.seekTo(progress);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
        
        Intent intent = new Intent(this, MusicService.class);
        //开始服务让进程变成服务进程
        startService(intent);
        //绑定服务拿到中间人
        bindService(intent, new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				mci = (MusicControllerInterface) service;
				
			}
		}, BIND_AUTO_CREATE);
    }

    
    public void play(View v){
    	//调用服务中的play方法去播放音乐
    	mci.play();
    }
    public void pause(View v){
    	mci.pause();
    }
    
    public void continuePlay(View v){
    	mci.continuePlay();
    }
    
}
