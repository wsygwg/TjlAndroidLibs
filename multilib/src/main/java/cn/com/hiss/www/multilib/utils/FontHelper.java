package cn.com.hiss.www.multilib.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by wuyanzhe on 2017/3/9.
 */

public class FontHelper {
    public static final String FONTS_DIR = "fonts/";
    public static final String DEF_FONT = FONTS_DIR + "iconfont.ttf";

    public static final void injectFont(View rootView) {
        injectFont(rootView, Typeface.createFromAsset(rootView.getContext().getAssets(), DEF_FONT));
    }

    public static final void injectFont(View rootView, Typeface tf) {
        try{
            if (rootView instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) rootView;
                int count = group.getChildCount();
                for (int i = 0; i < count; i++) {
                    injectFont(group.getChildAt(i), tf);
                }
            } else if (rootView instanceof android.widget.TextView && !(rootView instanceof EditText)) {
                ((TextView) rootView).setTypeface(tf);
            } else if (rootView instanceof com.rey.material.widget.TextView) {
                ((TextView) rootView).setTypeface(tf);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
