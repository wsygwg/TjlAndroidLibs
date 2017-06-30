package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;

/**
 * Created by wuyanzhe on 2017/4/5.
 */

public class Mp3TimeGetter extends Thread {
//
//    private Context context;
//    private File file;
//    private UrlFileDownloader.DownloadListener downloadListener;
//
//    public Mp3TimeGetter(Context context, File file, UrlFileDownloader.DownloadListener downloadListener) {
//        this.context = context;
//        this.file = file;
//        this.downloadListener = downloadListener;
//    }
//
//    @Override
//    public void run() {
//        super.run();
//        try {
//            MediaPlayer player = MediaPlayer.create(context, Uri.fromFile(file));
//            int time = player.getDuration();
//            int ret = time / 1000;
//            if(player != null){
//                player.release();
//            }
//            if(downloadListener != null){
//                downloadListener.onDownloadFinished(ret);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
