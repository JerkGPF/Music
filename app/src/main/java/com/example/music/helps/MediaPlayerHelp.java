package com.example.music.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerHelp {
    private static MediaPlayerHelp instance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;
    private OnMeidaPlayerHelperListener onMeidaPlayerHelperListener;

    public static MediaPlayerHelp getInstance(Context context){
        if (instance == null){
            synchronized (MediaPlayerHelp.class){
                if (instance == null){
                    instance = new MediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }

    private MediaPlayerHelp(Context context){
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }
    /**
     * 1.setpath:当前需要播放的音乐
     * 2.start：播放音乐
     * 3.pause：暂停播放
     */

    public void setPath(String path){
        /**
         * 1.音乐正在播放，重置播放状态
         * 2.设置播放音乐路径
         * 3.准备播放
         */
        mPath = path;
        //1.音乐正在播放，重置播放状态
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.reset();
        }
        //2.设置播放音乐路径
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3.准备播放
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (onMeidaPlayerHelperListener != null){
                    onMeidaPlayerHelperListener.onPrepared(mediaPlayer);
                }
            }
        });
    }

    /**
     * 返回正在播放的音乐路径
     * @return
     */
    public String getPath(){
        return mPath;
    }

    /**
     * 播放音乐
     */
    public void start(){
        if (mMediaPlayer.isPlaying())return;
        mMediaPlayer.start();
    }
    //暂停播放
    public void pause(){
        mMediaPlayer.pause();
    }

    public void setOnMeidaPlayerHelperListener(OnMeidaPlayerHelperListener onMeidaPlayerHelperListener) {
        this.onMeidaPlayerHelperListener = onMeidaPlayerHelperListener;
    }

    public interface OnMeidaPlayerHelperListener{
        void onPrepared(MediaPlayer mediaPlayer);
    }
}
