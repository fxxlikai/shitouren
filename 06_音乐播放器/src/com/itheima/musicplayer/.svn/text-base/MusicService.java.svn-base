package com.itheima.musicplayer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

public class MusicService extends Service {

	MediaPlayer player;
	private Timer timer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MusicController();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		player = new MediaPlayer();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		player.release();
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}
	
	class MusicController extends Binder implements MusicControllerInterface{
		@Override
		public void play(){
			MusicService.this.play();
		}
		
		@Override
		public void pause(){
			MusicService.this.pause();
		}

		@Override
		public void continuePlay() {
			MusicService.this.continuePlay();
			
		}

		@Override
		public void seekTo(int progress) {
			MusicService.this.seekTo(progress);
			
		}
	}
	
	public void play(){
		player.reset();
		try {
			player.setDataSource("sdcard/zxmzf.mp3");
//			player.setDataSource("http://192.168.20.82:8080/bzj.mp3");
			
			//同步准备
//			player.prepare();
			//异步准备
			player.prepareAsync();
			player.setOnPreparedListener(new OnPreparedListener() {
				//当准备完毕时调用，此时player进入准备状态
				@Override
				public void onPrepared(MediaPlayer mp) {
					player.start();
					addTimer();
				}
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pause(){
		player.pause();
	}
	
	public void continuePlay(){
		player.start();
	}
	
	public void seekTo(int progress){
		//改变音乐播放进度
		player.seekTo(progress);
	}
	
	public void addTimer(){
		if(timer == null){
			//创建一个计时器
			timer = new Timer();
			//启动一个计时任务
			timer.schedule(new TimerTask() {
				//此方法会在子线程执行
				@Override
				public void run() {
					//获取音乐的总时长
					int duration = player.getDuration();
					//获取音乐的当前播放进度
					int currentPosition= player.getCurrentPosition();
					
					Message msg = MainActivity.handler.obtainMessage();
					//使用bundle把数据封装至消息中
					Bundle data = new Bundle();
					data.putInt("duration", duration);
					data.putInt("currentPosition", currentPosition);
					msg.setData(data);
					
					MainActivity.handler.sendMessage(msg);
				}
			}, 5, //启动计时任务后，延时多少毫秒，开始执行run方法
			500);//每多少毫秒执行一次run方法
		}
	}
}
