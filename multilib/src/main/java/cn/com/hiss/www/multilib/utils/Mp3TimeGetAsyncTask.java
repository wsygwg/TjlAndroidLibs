package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.File;

/**
 * Created by wuyanzhe on 2017/4/14.
 */

public class Mp3TimeGetAsyncTask extends AsyncTask {

    private Context context;
    private File file;
    private TextView tv;
    private static final Byte lock = new Byte("1");

    public Mp3TimeGetAsyncTask(Context context, File file,TextView tv) {
        this.context = context;
        this.file = file;
        this.tv = tv;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            synchronized (lock){
                MediaPlayer player = MediaPlayer.create(context, Uri.fromFile(file));
                float time = player.getDuration();
                final float ret = time / 1000;
                if(player != null){
                    if(player.isPlaying())
                        player.stop();
                    player.reset();
                    player.release();
                }
                return ret + "\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try{
            tv.setText(o.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
