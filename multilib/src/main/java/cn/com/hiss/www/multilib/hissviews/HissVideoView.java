package cn.com.hiss.www.multilib.hissviews;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

/**
 * Created by wuyanzhe on 2017/4/19.
 * 自定义videoview去除黑边
 */

public class HissVideoView extends VideoView {
    public HissVideoView(Context context) {
        super(context);

    }

    public HissVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HissVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //主要方法在这里
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}