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
			
			//ͬ��׼��
//			player.prepare();
			//�첽׼��
			player.prepareAsync();
			player.setOnPreparedListener(new OnPreparedListener() {
				//��׼�����ʱ���ã���ʱplayer����׼��״̬
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
		//�ı����ֲ��Ž���
		player.seekTo(progress);
	}
	
	public void addTimer(){
		if(timer == null){
			//����һ����ʱ��
			timer = new Timer();
			//����һ����ʱ����
			timer.schedule(new TimerTask() {
				//�˷����������߳�ִ��
				@Override
				public void run() {
					//��ȡ���ֵ���ʱ��
					int duration = player.getDuration();
					//��ȡ���ֵĵ�ǰ���Ž���
					int currentPosition= player.getCurrentPosition();
					
					Message msg = MainActivity.handler.obtainMessage();
					//ʹ��bundle�����ݷ�װ����Ϣ��
					Bundle data = new Bundle();
					data.putInt("duration", duration);
					data.putInt("currentPosition", currentPosition);
					msg.setData(data);
					
					MainActivity.handler.sendMessage(msg);
				}
			}, 5, //������ʱ�������ʱ���ٺ��룬��ʼִ��run����
			500);//ÿ���ٺ���ִ��һ��run����
		}
	}
}
