package cn.com.hiss.www.multilib.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.MODE_APPEND;

/**
 * Created by wuyanzhe on 2017/4/5.
 * 不是很好用，文件下载有缺陷（设置过于简单）
 */
public class UrlFileDownloader extends Thread {
    private static final String TAG = UrlFileDownloader.class.getSimpleName();
    private Context context;
    private String urlStr = null;
    private String filePath;
    private OnCompleteListener onCompleteListener;

    public interface OnCompleteListener {
        public void onComplete(File downloadedFile);
    }

    public UrlFileDownloader(Context context, String urlStr, String filePath, OnCompleteListener onCompleteListener) {
        this.context = context;
        this.urlStr = urlStr;
        this.filePath = filePath;
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    public void run() {
        OutputStream output = null;
        InputStream input = null;
        File file = new File(filePath);
        if (file.exists()) {
            if (onCompleteListener != null) {
                onCompleteListener.onComplete(file);
            }
        } else {
            file = HissFileService.CreateFile(filePath);
            try {
            /*
             * 通过URL取得HttpURLConnection
             * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
             * <uses-permission android:name="android.permission.INTERNET" />
             */
                URL url = new URL(urlStr);
                URLConnection conn = url.openConnection();
                //取得inputStream，并将流中的信息写入SDCard
            /*
             * 写前准备
             * 1.在AndroidMainfest.xml中进行权限配置
             * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
             * 取得写入SDCard的权限
             * 2.取得SDCard的路径： Environment.getExternalStorageDirectory()
             * 3.检查要保存的文件上是否已经存在
             * 4.不存在，新建文件夹，新建文件
             * 5.将input流中的信息写入SDCard
             * 6.关闭流
             */
                input = conn.getInputStream();
                output = new FileOutputStream(file);
                //读取大文件
                byte[] buffer = new byte[4 * 1024];
                while (input.read(buffer) != -1) {
                    output.write(buffer);
                }
                output.flush();
                output.close();
                input.close();
                System.out.println("success");
            } catch (Exception e) {
                System.out.println("fail");
                e.printStackTrace();
            } finally {
                if (onCompleteListener != null) {
                    onCompleteListener.onComplete(file);
                }
            }
        }
    }
}
