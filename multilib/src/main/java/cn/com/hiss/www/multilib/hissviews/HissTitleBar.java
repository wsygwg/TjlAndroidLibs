package cn.com.hiss.www.multilib.hissviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.hiss.www.multilib.R;

/**
 * Created by wuyanzhe on 2017/3/10.
 */

public class HissTitleBar extends RelativeLayout {

    //    private Unbinder unbinder;
    public ImageView backImage;
    private TextView backText;   //默认隐藏
    public TextView title;
    private ImageView optionImage;   //默认隐藏
    public TextView optionText;
    private View titleBarView;

    public HissTitleBar(Context context) {
        super(context);
    }

    public HissTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        titleBarView = LayoutInflater.from(context).inflate(R.layout.include_main_titlebar, this, true);
        //        unbinder = ButterKnife.bind(this);
        backImage = (ImageView) findViewById(R.id.id_main_titlebar_back);
        backText = (TextView) findViewById(R.id.id_main_titlebar_back_text);
        title = (TextView) findViewById(R.id.id_main_titlebar_title);
        optionImage = (ImageView) findViewById(R.id.id_main_titlebar_option_img);
        optionText = (TextView) findViewById(R.id.id_main_titlebar_option);
    }

    public HissTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //    public HissTitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //        super(context, attrs, defStyleAttr, defStyleRes);
    //    }


    public View getTitleBarView() {
        return titleBarView;
    }

    public TextView showTextBack() {
        backText.setVisibility(VISIBLE);
        backImage.setVisibility(GONE);
        return backText;
    }

    public ImageView showImageOption() {
        optionImage.setVisibility(VISIBLE);
        optionText.setVisibility(GONE);
        return optionImage;
    }

    public void setBackAction(OnClickListener listener) {
        if (listener != null) {
            backImage.setOnClickListener(listener);
        }
    }

    public void hideBack() {
        backText.setVisibility(GONE);
        backImage.setVisibility(GONE);
    }

    public void setOptionAction(OnClickListener listener) {
        if (listener != null) {
            optionText.setOnClickListener(listener);
        }
    }

    public void hideOption() {
        optionImage.setVisibility(GONE);
        optionText.setVisibility(GONE);
    }

    @Override
    protected void onDetachedFromWindow() {
        //        if(unbinder != null){
        //            unbinder.unbind();
        //        }
        super.onDetachedFromWindow();
    }
}
