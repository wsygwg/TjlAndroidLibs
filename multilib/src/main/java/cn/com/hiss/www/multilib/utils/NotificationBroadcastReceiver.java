package cn.com.hiss.www.multilib.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wuyanzhe on 2017/4/4.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationBroadcastReceiver.class.getSimpleName();

    public static final String TYPE = "type"; //这个type是为了Notification更新信息的，这个不明白的朋友可以去搜搜，很多

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra(TYPE, -1);

        if (type != -1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if (action.equals("notification_clicked")) {
            //处理点击事件
//            Toast.makeText(context,"@@@@@@@@@@@@notification_clicked at " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT),Toast.LENGTH_SHORT).show();
//            Log.e(TAG,"@@@@@@@@@@@@notification_clicked at " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
            TBroadcast.sendLocalStringBc(context,TBroadcast.BROADCAST_APP_START_BY_NOTIFICATION,"");
        }

        if (action.equals("notification_cancelled")) {
            //处理滑动清除和点击删除事件
//            Toast.makeText(context,"###########notification_cancelled at " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT),Toast.LENGTH_SHORT).show();
//            Log.e(TAG,"###########notification_cancelled at " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
//            TBroadcast.sendLocalStringBc(context,TBroadcast.BROADCAST_APP_START_BY_NOTIFICATION,"");
        }

        if (action.equals("notification_service_clicked") || action.equals("notification_service_cancelled")) {
            //处理滑动清除和点击删除事件
            //            Toast.makeText(context,"###########notification_cancelled at " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT),Toast.LENGTH_SHORT).show();
            //            Log.e(TAG,"###########notification_cancelled at " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
            TBroadcast.sendLocalStringBc(context,TBroadcast.BROADCAST_SERVICE_START_BY_NOTIFICATION,"");
        }
    }
}
