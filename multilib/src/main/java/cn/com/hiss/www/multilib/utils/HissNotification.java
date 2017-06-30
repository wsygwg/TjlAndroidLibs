package cn.com.hiss.www.multilib.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.NotificationTarget;
import com.google.gson.Gson;

import net.openmob.mobileimsdk.server.protocal.c.PP2PMsg;

import java.util.TimeZone;

import br.com.goncalves.pugnotification.interfaces.ImageLoader;
import br.com.goncalves.pugnotification.notification.PugNotification;
import cn.com.hiss.www.multilib.R;
import cn.com.hiss.www.multilib.common.HissNotificationType;
import cn.com.hiss.www.multilib.db.DbGetChatGroups;
import cn.com.hiss.www.multilib.db.DbGetFriends;
import cn.com.hiss.www.multilib.db.DbGetStudent;
import cn.com.hiss.www.multilib.db.DbRecentlyUser;
import cn.com.hiss.www.multilib.db.base.BaseManager;

/**
 * Created by wuyanzhe on 2017/4/4.
 */

public class HissNotification {
    private static final String TAG = HissNotification.class.getSimpleName();
    private Notification mNotification;
    private NotificationManager mNotificationManager;

    private static long recently_alert_time = 0;
    private static DbRecentlyUser currentDbRecentlyUser = null;
    private static String currentMessageId = "";    //日志的ID，用户或群ID

    public static String getCurrentMessageId() {
        return currentMessageId;
    }

    public static void cancel(Context context, HissNotificationType type) {
        try {
            Log.e(TAG, "清除ID为" + type.getNotificationId() + "的notification");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type.getNotificationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartNotification(Context context) {
        try {
            Log.e("HissStickyService", "restartNotification~~~~~~start");
            RemoteViews customView = new RemoteViews(context.getPackageName(), R.layout.activity_notification_lo);
            customView.setTextViewText(R.id.id_notification_name, "温馨提醒");
            customView.setTextViewText(R.id.id_notification_history, "别忘了查看新消息哦，亲~");
            customView.setTextViewText(R.id.id_notification_name, HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
            int notificationId = 0;

            //notification点击广播
            Intent intentClick = new Intent(context, NotificationBroadcastReceiver.class);
            intentClick.setAction("notification_service_clicked");
            intentClick.putExtra(NotificationBroadcastReceiver.TYPE, notificationId);
            PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
            //notification消除广播
            Intent intentCancel = new Intent(context, NotificationBroadcastReceiver.class);
            intentCancel.setAction("notification_service_cancelled");
            intentCancel.putExtra(NotificationBroadcastReceiver.TYPE, notificationId);
            PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    // 设置小图标
                    .setSmallIcon(R.drawable.img_small_icon)
                    // 设置状态栏文本标题
                    .setTicker("您有新的消息")
                    //设置自定义布局
                    .setContent(customView)
                    // 设置ContentIntent
                    .setContentIntent(pendingIntentClick).setDeleteIntent(pendingIntentCancel)
                    // 点击Notification的时候自动移除
                    .setAutoCancel(true);
            long time = System.currentTimeMillis();
            if (time - recently_alert_time > 1000) {
                // 设置Notification提示铃声为系统默认铃声
                recently_alert_time = time;
                builder.setSound(RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION));
            }
            mNotification = builder.build();
                /*
                 * 当API level 低于14的时候使用setContent()方法是没有用的
                 * 需要对contentView字段直接赋值才能生效
                 */
            if (Build.VERSION.SDK_INT < 14) {
                mNotification.contentView = customView;
            }
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(notificationId, mNotification);
            Log.e("HissStickyService", "restartNotification~~~~~~end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * id_notification_avatar
     * <p>
     * id_notification_name
     * id_notification_history
     * id_notification_time
     *
     * @param context
     * @param log
     */
    public synchronized void ordinaryNotification(Context context, DbRecentlyUser log) {
        try {
            currentDbRecentlyUser = log;
            RemoteViews customView = new RemoteViews(context.getPackageName(), R.layout.activity_notification_lo);
            Gson gson = new Gson();
            int notificationId = HissNotificationType.MessageP2P.getNotificationId();
            if (log.getType().equals("0")) {
                //个人
                final DbGetStudent studentInfo = gson.fromJson(log.getDbGetStudentStr(), DbGetStudent.class);
                currentMessageId = studentInfo.getMemberId();
                StringBuilder historyTextSb = new StringBuilder();
                historyTextSb.append(log.getUnreadCount().equals("0") ? "" : "(" + log.getUnreadCount() + "条)");
                if (log.getContentType().equals(PP2PMsg.TXT + "")) {
                    historyTextSb.append(log.getContentData());
                } else if (log.getContentType().equals(PP2PMsg.IMAGE + "")) {
                    historyTextSb.append("[图片]");
                } else if (log.getContentType().equals(PP2PMsg.POSITION + "")) {
                    historyTextSb.append("[位置]");
                } else if (log.getContentType().equals(PP2PMsg.VIDEO + "")) {
                    historyTextSb.append("[视频]");
                } else if (log.getContentType().equals(PP2PMsg.VOICE + "")) {
                    historyTextSb.append("[语音]");
                }
                customView.setTextViewText(R.id.id_notification_name, studentInfo.getRealName());
                customView.setTextViewText(R.id.id_notification_history, historyTextSb.toString());
                customView.setTextViewText(R.id.id_notification_time, HissDate.getFormatedDateByLong(log.getRecentTime(), HissDate.DATE_FORMAT_IM_RECENT, TimeZone.getDefault()));
                if (studentInfo.getSex().equals("1")) {
                    customView.setImageViewResource(R.id.id_notification_avatar, R.drawable.img_avatar_default_boy);
                } else {
                    customView.setImageViewResource(R.id.id_notification_avatar, R.drawable.img_avatar_default_girl);
                }
            } else {
                notificationId = HissNotificationType.MessageGroup.getNotificationId();
                //群组
                final DbGetChatGroups group = gson.fromJson(log.getDbGetChatGroupsStr(), DbGetChatGroups.class);
                currentMessageId = group.getGroupId();
                StringBuilder historyTextSb = new StringBuilder();
                historyTextSb.append(log.getUnreadCount().equals("0") ? "" : "(" + log.getUnreadCount() + "条)");
                if (log.getContentType().equals(PP2PMsg.TXT + "")) {
                    historyTextSb.append(log.getContentData());
                } else if (log.getContentType().equals(PP2PMsg.IMAGE + "")) {
                    historyTextSb.append("[图片]");
                } else if (log.getContentType().equals(PP2PMsg.POSITION + "")) {
                    historyTextSb.append("[位置]");
                } else if (log.getContentType().equals(PP2PMsg.VIDEO + "")) {
                    historyTextSb.append("[视频]");
                } else if (log.getContentType().equals(PP2PMsg.VOICE + "")) {
                    historyTextSb.append("[语音]");
                }
                customView.setTextViewText(R.id.id_notification_name, group.getGroupName());
                customView.setTextViewText(R.id.id_notification_history, historyTextSb.toString());
                customView.setTextViewText(R.id.id_notification_time, HissDate.getFormatedDateByLong(log.getRecentTime(), HissDate.DATE_FORMAT_IM_RECENT, TimeZone.getDefault()));
                customView.setImageViewResource(R.id.id_notification_avatar, R.drawable.img_avatar_default_group);
            }

            //notification点击广播
            Intent intentClick = new Intent(context, NotificationBroadcastReceiver.class);
            intentClick.setAction("notification_clicked");
            intentClick.putExtra(NotificationBroadcastReceiver.TYPE, notificationId);
            PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
            //notification消除广播
            Intent intentCancel = new Intent(context, NotificationBroadcastReceiver.class);
            intentCancel.setAction("notification_cancelled");
            intentCancel.putExtra(NotificationBroadcastReceiver.TYPE, notificationId);
            PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    // 设置小图标
                    .setSmallIcon(R.drawable.img_small_icon)
                    // 设置状态栏文本标题
                    .setTicker("您有新的消息")
                    //设置自定义布局
                    .setContent(customView)
                    // 设置ContentIntent
                    .setContentIntent(pendingIntentClick).setDeleteIntent(pendingIntentCancel)
                    // 点击Notification的时候自动移除
                    .setAutoCancel(true);
            long time = System.currentTimeMillis();
            if (time - recently_alert_time > 1000) {
                // 设置Notification提示铃声为系统默认铃声
                recently_alert_time = time;
                builder.setSound(RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION));
            }
            mNotification = builder.build();
                /*
                 * 当API level 低于14的时候使用setContent()方法是没有用的
                 * 需要对contentView字段直接赋值才能生效
                 */
            if (Build.VERSION.SDK_INT < 14) {
                mNotification.contentView = customView;
            }

            String imgUrl = "";
            int errorPic = R.drawable.img_avatar_default_group;
            if (log.getType().equals("0")) {
                //个人
                final DbGetStudent studentInfo = gson.fromJson(log.getDbGetStudentStr(), DbGetStudent.class);
                if (studentInfo != null) {
                    imgUrl = studentInfo.getStuImage().getHeadUrl();
                    errorPic = studentInfo.getSex().equals("1") ? R.drawable.img_avatar_default_boy : R.drawable.img_avatar_default_girl;
                }
            } else {
                //群组
                final DbGetChatGroups group = gson.fromJson(log.getDbGetChatGroupsStr(), DbGetChatGroups.class);
                if (group != null) {
                    imgUrl = group.getPicUrl();
                }
                errorPic = R.drawable.img_avatar_default_group;
            }
            if (!Schecker.isStringNull(imgUrl)) {
                NotificationTarget notificationTarget = new NotificationTarget(context, customView, R.id.id_notification_avatar, mNotification, notificationId);
                Glide.with(context).load(imgUrl).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(errorPic).into(notificationTarget);
            }

            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(notificationId, mNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void pushNotification(Context context, HissNotificationType type, String fromName, String toName, boolean isSuccessful) {
        try {
            RemoteViews customView = new RemoteViews(context.getPackageName(), R.layout.activity_notification_lo);
            customView.setTextViewText(R.id.id_notification_name, type.getTitle());
            StringBuffer stringBuffer = new StringBuffer();
            if (type == HissNotificationType.FriendAdd) {
                stringBuffer.append(fromName).append("申请添加您为好友");
            } else if (type == HissNotificationType.FriendAddResp) {
                stringBuffer.append("您申请添加好友").append(fromName).append(isSuccessful ? "已通过" : "被拒绝");
            } else if (type == HissNotificationType.GroupAdd) {
                stringBuffer.append(fromName).append("申请进入群组").append(toName);
            } else if (type == HissNotificationType.GroupAddResp) {
                stringBuffer.append("您申请添加群组").append(fromName).append(isSuccessful ? "已通过" : "被拒绝");
            } else if (type == HissNotificationType.FriendDeleted) {
                stringBuffer.append("您被").append(fromName).append("从好友列表中移除");
//            }else if(type == HissNotificationType.GroupMemberAdd){
//                stringBuffer.append("群组" + toName + "添加了新成员：").append(fromName);
//            }else if(type == HissNotificationType.GroupMemberLeave){
//                stringBuffer.append(fromName).append("离开了群组" + toName);
            } else if (type == HissNotificationType.GroupDismiss) {
                stringBuffer.append("群组" + fromName + "已解散");
            } else {
                return;
            }
            customView.setTextViewText(R.id.id_notification_history, stringBuffer.toString());
            customView.setTextViewText(R.id.id_notification_time, HissDate.getFormatedDateByLong(System.currentTimeMillis(), HissDate.DATE_FORMAT_IM_RECENT, TimeZone.getDefault()));
            customView.setImageViewResource(R.id.id_notification_avatar, R.drawable.img_avatar_default_group);

            int notificationId = type.getNotificationId();

            //notification点击广播
            Intent intentClick = new Intent(context, NotificationBroadcastReceiver.class);
            intentClick.setAction("notification_clicked");
            intentClick.putExtra(NotificationBroadcastReceiver.TYPE, notificationId);
            PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
            //notification消除广播
            Intent intentCancel = new Intent(context, NotificationBroadcastReceiver.class);
            intentCancel.setAction("notification_cancelled");
            intentCancel.putExtra(NotificationBroadcastReceiver.TYPE, notificationId);
            PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    // 设置小图标
                    .setSmallIcon(R.drawable.img_small_icon)
                    // 设置状态栏文本标题
                    .setTicker("您有新的通知")
                    //设置自定义布局
                    .setContent(customView)
                    // 设置ContentIntent
                    .setContentIntent(pendingIntentClick).setDeleteIntent(pendingIntentCancel)
                    // 点击Notification的时候自动移除
                    .setAutoCancel(true);
            long time = System.currentTimeMillis();
            if (time - recently_alert_time > 1000) {
                // 设置Notification提示铃声为系统默认铃声
                recently_alert_time = time;
                builder.setSound(RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION));
            }
            mNotification = builder.build();
                /*
                 * 当API level 低于14的时候使用setContent()方法是没有用的
                 * 需要对contentView字段直接赋值才能生效
                 */
            if (Build.VERSION.SDK_INT < 14) {
                mNotification.contentView = customView;
            }
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(notificationId, mNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showSimpleNotification(Context context, String title, String message) {
        PugNotification.with(context).load().title(title).message(message)
                //                .bigTextStyle(bigtext)
                .smallIcon(R.drawable.pugnotification_ic_launcher).largeIcon(R.drawable.pugnotification_ic_launcher).flags(Notification.DEFAULT_ALL).simple().build();
    }

    //    public static void showProgressNotification(){
    //        PugNotification.with(context)
    //                .load()
    //                .identifier(identifier)
    //                .smallIcon(R.drawable.pugnotification_ic_launcher)
    //                .progress()
    //                .value(progress,max, indeterminate)
    //                .build();
    //        PugNotification.with(context)
    //                .load()
    //        .identifier(identifier)
    //        .smallIcon(R.drawable.pugnotification_ic_launcher)
    //        .progress()
    //        .update(identifier,progress,max, indeterminate)
    //        .build();
    //    }

    public static void showCustomNotification(Context context, String title, String message, String bgUrl, ImageLoader callback) {
        PugNotification.with(context).load().title(title).message(message)
                //                .bigTextStyle(bigtext)
                .smallIcon(R.drawable.pugnotification_ic_launcher).largeIcon(R.drawable.pugnotification_ic_launcher).flags(Notification.DEFAULT_ALL).color(android.R.color.background_dark).custom().background(bgUrl).setImageLoader(callback).setPlaceholder(R.drawable.pugnotification_ic_placeholder).build();
    }
}
