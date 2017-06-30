package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import cn.com.hiss.www.sharephoto.util.Bimp;

/**
 * Created by wuyanzhe on 2017/4/3.
 */

public class HissThumbnail {
    private static final String TAG = HissThumbnail.class.getSimpleName();

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    //    public static byte[] videoThmbnailBytes(String url, int width, int height) {
    //        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //        Bitmap bitmap = new HissThumbnail().createVideoThumbnail(url, width, height);
    //        if (bitmap != null) {
    //            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    //        }
    //        byte[] bytes = baos.toByteArray();
    //        return bytes;
    //    }

    public static File videoThmbnailFile(Context con, String url, String fileName) {
        return saveBitmap(con, url, fileName);
    }

    private static Bitmap createVideoThumbnail(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                if(url.startsWith("http")){
                    //此方法用于网络视频还可以，但是用于本地视频不灵
                    retriever.setDataSource(url, new HashMap<String, String>());
                }else{
                    //此方法可用于本地视频Build.VERSION.SDK_INT >= 10
                    retriever.setDataSource(url);
                }
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, WIDTH, HEIGHT, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            //            bitmap = resizeBitmap(50, 50, bitmap);
        }
        return bitmap;
    }

    //    private Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap) {
    //        Matrix matrix = new Matrix();
    //        matrix.postScale(newWidth / bitmap.getWidth(), newHeight / bitmap.getHeight());
    //        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    //        return newBitmap;
    //    }

    private static File saveBitmap(Context con, String url, String fileName) {
        File f = new File(getVideoThumbDir(), fileName);
        try {
            if (f.exists()) {

            } else {
                Log.e(TAG, "保存图片开始");
                Bitmap bm = createVideoThumbnail(url);
                f = HissFileService.CreateFile(getVideoThumbDir() + fileName);
                FileOutputStream out = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                bm.recycle();
                Log.i(TAG, "保存图片完毕");
            }
            return f;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "保存图片失败");
            return null;
        }
    }

    public static boolean hasBitmap(Context con, String url, String fileName) {
        File f = new File(getVideoThumbDir(), fileName);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getVideoThumbDir() {
        return Environment.getExternalStorageDirectory() + File.separator + "HissSport" + File.separator + "video_thumb" + File.separator;
    }
}
