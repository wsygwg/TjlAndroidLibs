package cn.com.hiss.www.multilib.utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by tao on 2017/6/27.
 */

public class HissViewAction {

    private long recentClickedTime = 0;
    private long recentCommentsTime = 0;
    private static final long MIN_CLICK_INTERVAL = 2 * 1000;//两秒执行一次点击
    private static final long MIN_COMMENTS_INTERVAL = 5 * 1000;//五秒显示一次提示
    private static final String COMMENTS = "点击频率过快，请放慢点击频率";

    private void logic(View v, ViewDo viewDo) {
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - recentClickedTime > MIN_CLICK_INTERVAL) {
                recentClickedTime = currentTime;
                viewDo.action(v);
            } else {
                if (currentTime - recentCommentsTime > MIN_COMMENTS_INTERVAL) {
                    recentCommentsTime = currentTime;
//                    Toast.makeText(v.getContext(), COMMENTS, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private interface ViewDo {
        void action(View v);
    }

    public abstract class OnClickListener implements View.OnClickListener, ViewDo {
        @Override
        public void onClick(View v) {
            logic(v, OnClickListener.this);
        }
    }

    public abstract class OnLongClickListener implements View.OnLongClickListener, ViewDo {

        @Override
        public boolean onLongClick(View v) {
            logic(v, OnLongClickListener.this);
            return true;
        }
    }

    public abstract class OnItemClickListener implements AdapterView.OnItemClickListener, ViewDo {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            logic(view, OnItemClickListener.this);
        }
    }

    public abstract class OnItemLongClickListener implements AdapterView.OnItemLongClickListener, ViewDo {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            logic(view, OnItemLongClickListener.this);
            return true;
        }
    }

    //TODO;recyclerView需要在RecyclerView.Adapter中写回调接口，可以使用OnClickListener，OnLongClickListener做替换
}
