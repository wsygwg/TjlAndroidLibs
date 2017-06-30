package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Mao Jiqing on 2016/9/28.
 */
public class KeyBoardUtils {
    public static void hideKeyBoard(Context context, View view) {
        try{
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
        }catch (Exception e){
//            e.printStackTrace();
        }
    }

    public static void showKeyBoard(Context context, View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.findFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isShowSoftInput(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //获取状态信息
        return imm.isActive();//true 打开
    }
}
