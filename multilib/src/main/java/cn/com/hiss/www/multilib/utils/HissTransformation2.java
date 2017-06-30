package cn.com.hiss.www.multilib.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

/**
 * Created by wuyanzhe on 2017/3/31.
 */

public class HissTransformation2 implements Transformation {

    private static final String TAG = HissTransformation2.class.getSimpleName();

    private ImageView mImg;

    public HissTransformation2(ImageView mImg) {
        this.mImg = mImg;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        try{
            int targetWidth = mImg.getWidth();
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }catch (Exception e){
//            e.printStackTrace();
            return source;
        }
    }

    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }
}
