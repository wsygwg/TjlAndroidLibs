package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;

/**
 * Created by wuyanzhe on 2017/4/17.
 */

public class UriTools {
    private static final String TAG = UriTools.class.getSimpleName();
    private Context con;

    public UriTools(Context con) {
        this.con = con;
    }

    /**
     * 查找在于SDcard中的Audio文件对应于MediaStore  的uri
     *
     * @param file 音频文件
     * @return
     */
    public Uri queryUriforAudio(File file) {
        final String where = MediaStore.Audio.Media.DATA + "='" + file.getAbsolutePath() + "'";
        Cursor cursor = con.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, where, null, null);
        if (cursor == null) {
            return null;
        }
        int id = -1;
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        if (id == -1) {
            return null;
        }
        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
    }

    /**
     * 查找视频文件对应于MediaStore的Uri
     *
     * @param file 视频文件
     * @return
     */

    private Uri queryUriForVideo(File file) {
        int id = getId(file);
        if (id == -1) {
            return null;
        }
        return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
    }

    /**
     * 获得 指定视频文件F在MediaStore中对应的ID
     *
     * @param f 视频文件
     * @return 对应ID
     */

    private int getId(File f) {
        int id = -1;
        // MediaStore.Video.Media.DATA：视频文件路径；
        // MediaStore.Video.Media.DISPLAY_NAME : 视频文件名，如 testVideo.mp4
        // MediaStore.Video.Media.TITLE: 视频标题 : testVideo
        String[] mediaColumns = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.DISPLAY_NAME};
        final String where = MediaStore.Video.Media.DATA + "=" + "?";
        Cursor cursor = con.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, where, new String[]{f.getAbsolutePath()}, null);
        if (cursor == null) {
//            Toast.makeText(con, "没有找到可播放视频文件", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"没有找到可播放视频文件");
            return -1;
        }
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                //sysVideoList.add(info);
            } while (cursor.moveToNext());
        }
        return id;
    }
}
