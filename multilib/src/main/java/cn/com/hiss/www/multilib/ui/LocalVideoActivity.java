package cn.com.hiss.www.multilib.ui;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import cn.com.hiss.www.multilib.R;
import cn.com.hiss.www.multilib.hissviews.HissVideoView;
import cn.com.hiss.www.multilib.utils.HissFileDownloader;
import cn.com.hiss.www.multilib.utils.HissFileService;
import cn.com.hiss.www.multilib.utils.HissThumbnail;
import cn.com.hiss.www.multilib.utils.UrlFileDownloader;

public class LocalVideoActivity extends AppCompatActivity {
    private static final String TAG = LocalVideoActivity.class.getSimpleName();
    private HissVideoView videoView;
    private ImageView iv;
    public static final String KEY_VIDEO_PATH = "KEY_VIDEO_PATH";
    public static final String KEY_VIDEO_DBID = "KEY_VIDEO_DBID";
    private String path = "";
    private String dbId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_shower);
        path = getIntent().getExtras().getString(KEY_VIDEO_PATH);
        dbId = getIntent().getExtras().getString(KEY_VIDEO_DBID);
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "无效的视频地址", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        initViews();
        try{
            //判断视频是否在本地存在
            File videoFile = new File(HissCameraActivity.getCameraFilePath(), HissFileService.getOssFileName(path));
            if(videoFile.exists()){
                //若存在，直接播放本地视频
                Log.e(TAG,"直接播放本地视频");
                Uri uri = Uri.fromFile(videoFile);
                play(uri);
            }else{
                final String storageFileName = HissCameraActivity.getCameraFilePath() + File.separator + HissFileService.getOssFileName(path);
                HissFileDownloader.startDownload(path,storageFileName, new HissFileDownloader.DownloadListener() {
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e(TAG,"视频下载成功！！！");
                        Uri uri = Uri.fromFile(new File(storageFileName));
                        play(uri);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e(TAG,"视频下载失败..." + e.getMessage());
                        Toast.makeText(LocalVideoActivity.this, "视频下载失败，请检查您的网络", Toast.LENGTH_SHORT).show();
                        LocalVideoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            //网络视频
            Log.e(TAG,"播放网络视频 because of exception");
            String videoUrl2 = path;
            Uri uri = Uri.parse(videoUrl2);
            play(uri);
        }
        showloadingDialog();
        Glide.with(this).load(HissThumbnail.videoThmbnailFile(this, path, dbId + ".png")).centerCrop().into(iv);
    }

    private void initViews(){
        videoView = (HissVideoView) this.findViewById(R.id.videoView);
        iv = (ImageView)findViewById(R.id.videoThumbnail);
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));
        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                dismissLoadingDialog();
                iv.setVisibility(View.GONE);
            }
        });
    }

    private void play(Uri uri){
        //设置视频路径
        videoView.setVideoURI(uri);
        //开始播放视频
        videoView.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(LocalVideoActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 正在加载
     */
    private AlertDialog loadingDialog;

    /**
     * 初始化正在加载对话框
     */
    private void initLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.TransparentDialog);
        View loadingView = View.inflate(this, R.layout.page_loading, null);
        SpinKitView spinKitView = (SpinKitView) loadingView.findViewById(R.id.loading_icon);
        Style style = Style.values()[7];
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
        builder.setView(loadingView);
        builder.setCancelable(false);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
        loadingDialog = builder.create();
    }

    public boolean isDialogShowing() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public void showloadingDialog() {
        try {
            if (loadingDialog == null) {
                initLoadingDialog();
            }
            loadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
