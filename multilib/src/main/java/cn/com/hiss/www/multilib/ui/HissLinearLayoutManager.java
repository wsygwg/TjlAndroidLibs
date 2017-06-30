package cn.com.hiss.www.multilib.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by wuyanzhe on 2017/4/14.
 * 使用此封装类，防止recyclerView在局部刷新时报错
 * java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position...
 * 即使所有list添加新Item都在UI线程，也容易造成以上错误，故此封装类很重要
 */

public class HissLinearLayoutManager extends LinearLayoutManager {

    public HissLinearLayoutManager(Context context) {
        super(context);
    }

    public HissLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public HissLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Magic here
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
