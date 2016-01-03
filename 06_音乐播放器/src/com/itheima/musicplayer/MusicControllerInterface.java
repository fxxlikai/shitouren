package com.itheima.musicplayer;

public interface MusicControllerInterface {

	void play();
	void pause();
	void continuePlay();
	void seekTo(int progress);
}
