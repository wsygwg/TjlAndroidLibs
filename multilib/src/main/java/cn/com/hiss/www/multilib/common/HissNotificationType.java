package cn.com.hiss.www.multilib.common;

/**
 * Created by tao on 2017/6/8.
 */

public enum HissNotificationType {
    MessageP2P("", 0), MessageGroup("", 0), FriendAdd("好友通知", 1), FriendAddResp("好友通知", 1), GroupAdd("群通知", 1), GroupAddResp("群通知", 1), FriendDeleted("好友通知", 1), GroupMemberAdd("群通知", 1), GroupMemberLeave("群通知", 1), GroupDismiss("群通知", 1);
    private String title;
    private int notificationId;

    HissNotificationType(String title, int notificationId) {
        this.title = title;
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
