package com.maxi.chatdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.maxi.chatdemo.R;
import com.maxi.chatdemo.adapter.ChatRecyclerAdapter;
import com.maxi.chatdemo.animator.SlideInOutBottomItemAnimator;
import com.maxi.chatdemo.common.ChatConst;
import com.maxi.chatdemo.ui.base.BaseActivity;
import com.maxi.chatdemo.utils.AudioManager;
import com.maxi.chatdemo.utils.KeyBoardUtils;
import com.maxi.chatdemo.widget.AudioRecordButton;

import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;
import net.openmob.mobileimsdk.server.protocal.ErrorCode;
import net.openmob.mobileimsdk.server.protocal.Protocal;
import net.openmob.mobileimsdk.server.protocal.ProtocalFactory;
import net.openmob.mobileimsdk.server.protocal.ProtocalType;
import net.openmob.mobileimsdk.server.protocal.c.PGroupMsg;
import net.openmob.mobileimsdk.server.protocal.c.PP2PMsg;

import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.com.hiss.www.multilib.IosPosition;
import cn.com.hiss.www.multilib.db.ChatDbManager;
import cn.com.hiss.www.multilib.db.DbChatMessageBean;
import cn.com.hiss.www.multilib.db.DbChatMessageBeanDao;
import cn.com.hiss.www.multilib.db.DbGetChatGroupMembers;
import cn.com.hiss.www.multilib.db.DbGetChatGroupMembersDao;
import cn.com.hiss.www.multilib.db.DbGetChatGroups;
import cn.com.hiss.www.multilib.db.DbGetChatGroupsDao;
import cn.com.hiss.www.multilib.db.DbGetFriends;
import cn.com.hiss.www.multilib.db.DbGetFriendsDao;
import cn.com.hiss.www.multilib.db.DbGetStudent;
import cn.com.hiss.www.multilib.db.DbRecentlyUser;
import cn.com.hiss.www.multilib.db.DbRecentlyUserDao;
import cn.com.hiss.www.multilib.db.HissDbManager;
import cn.com.hiss.www.multilib.db.StuImage;
import cn.com.hiss.www.multilib.db.base.BaseManager;
import cn.com.hiss.www.multilib.oss.manager.OssFile;
import cn.com.hiss.www.multilib.oss.manager.OssFileUploadThread;
import cn.com.hiss.www.multilib.oss.manager.OssSetting;
import cn.com.hiss.www.multilib.ui.HissLinearLayoutManager;
import cn.com.hiss.www.multilib.utils.CacheData;
import cn.com.hiss.www.multilib.utils.HissDate;
import cn.com.hiss.www.multilib.utils.HissFileService;
import cn.com.hiss.www.multilib.utils.HissNotification;
import cn.com.hiss.www.multilib.utils.HissSystemBarTint;
import cn.com.hiss.www.multilib.utils.HissThumbnail;
import cn.com.hiss.www.multilib.utils.Schecker;

public class RecyclerViewChatActivity extends BaseActivity {
    private static final String TAG = RecyclerViewChatActivity.class.getSimpleName();
    private ChatRecyclerAdapter tbAdapter;
    private SendMessageHandler sendMessageHandler;

    public SendMessageHandler getSendMessageHandler() {
        return sendMessageHandler;
    }

    public static RecyclerViewChatActivity instance = null;

    enum MsgType {
        PERSON, GROUP
    }

    private static OnLogChangedListener listener;

    public static void setListener(OnLogChangedListener listener) {
        RecyclerViewChatActivity.listener = listener;
    }

    public interface OnLogChangedListener {
        void onLogChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        HissSystemBarTint.setColor(this, getResources().getColor(R.color.title_bar_color));
    }

    @Override

    protected void findView() {
        super.findView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        tblist.clear();
        tbAdapter.notifyDataSetChanged();
        sendMessageHandler.removeCallbacksAndMessages(null);
        instance = null;
        sendMessageHandler = null;
        //防止录音不止
        AudioManager.cancelRecording();
        super.onDestroy();
    }

    /**
     * TODO:删除数据库中的相关数据
     *
     * @param tbub
     */
    private void clearFailedData(int clearPosition, DbChatMessageBean tbub) {
        if (tbub.getType().equals(PP2PMsg.IMAGE)) {
            for (int i = 0; i < imageList.size(); i++) {
                if (tbub.getContent().equals(imageList.get(i))) {
                    imageList.remove(i);
                    break;
                }
            }
        }
        DbChatMessageBeanDao dao = HissDbManager.getDaoSession(RecyclerViewChatActivity.this).getDbChatMessageBeanDao();
        List<DbChatMessageBean> list = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.CreateDate.eq(tbub.getCreateDate())).list();
        if (list != null) {
            dao.deleteInTx(list);
        }
        tblist.remove(clearPosition);
        sendMessageHandler.sendEmptyMessage(REFRESH);
    }

    /**
     * TODO:页面初始化设置
     * TODO:页面初始化设置
     * TODO:页面初始化设置
     * TODO:页面初始化设置
     * TODO:页面初始化设置
     * TODO:页面初始化设置
     * TODO:页面初始化设置
     * TODO:页面初始化设置
     */
    @Override
    protected void init() {
        try {
            tbAdapter = new ChatRecyclerAdapter(this, tblist);
            recyclerView.setLayoutManager(new HissLinearLayoutManager(this));
            recyclerView.setAdapter(tbAdapter);
            recyclerView.setItemAnimator(new SlideInOutBottomItemAnimator(recyclerView));
            sendMessageHandler = new SendMessageHandler(this);
            tbAdapter.isPicRefresh = true;
            tbAdapter.notifyDataSetChanged();
            tbAdapter.setSendErrorListener(new ChatRecyclerAdapter.SendErrorListener() {

                @Override
                public void onClick(int position) {
                    DbChatMessageBean tbub = tblist.get(position);
                    clearFailedData(position, tbub);
                    if (tbub.getType().equals(PP2PMsg.IMAGE) || tbub.getType().equals(PP2PMsg.VOICE) || tbub.getType().equals(PP2PMsg.VIDEO)) {
                        //判断是否已经成功上传到阿里云
                        if (tbub.getContent().contains("http")) {
                            //已经成功上传
                            sendTxtMessage(tbub.getContent(), tbub.getType());
                        } else {
                            OssSetting.HissResType resType = OssSetting.HissResType.getTypeByStr(tbub.getType());
                            if (resType != null) {
                                sendFile(tbub.getContent(), resType);
                            } else {
                                Log.e(TAG, "resType == null。。。。。。。。。。。。。。。。。");
                            }
                        }
                    } else {
                        sendTxtMessage(tbub.getContent(), tbub.getType());
                    }
                }
            });
            tbAdapter.setVoiceIsReadListener(new ChatRecyclerAdapter.VoiceIsRead() {

                @Override
                public void voiceOnClick(int position) {
                    for (int i = 0; i < tbAdapter.unReadPosition.size(); i++) {
                        if (tbAdapter.unReadPosition.get(i).equals(position + "")) {
                            tbAdapter.unReadPosition.remove(i);
                            break;
                        }
                    }
                }

            });
            voiceBtn.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {

                @Override
                public void onFinished(float seconds, String filePath) {
                    sendFile(filePath, OssSetting.HissResType.AUDIO);
                }

                @Override
                public void onStart() {
                    tbAdapter.stopPlayVoice();
                }
            });
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView view, int scrollState) {
                    switch (scrollState) {
                        case RecyclerView.SCROLL_STATE_IDLE:
                            tbAdapter.handler.removeCallbacksAndMessages(null);
                            tbAdapter.setIsGif(true);
                            tbAdapter.isPicRefresh = false;
                            tbAdapter.notifyDataSetChanged();
                            //                        controlKeyboardLayout(activityRootView, pullList);
                            break;
                        case RecyclerView.SCROLL_STATE_DRAGGING:
                            tbAdapter.handler.removeCallbacksAndMessages(null);
                            tbAdapter.setIsGif(false);
                            tbAdapter.isPicRefresh = true;
                            reset();
                            KeyBoardUtils.hideKeyBoard(RecyclerViewChatActivity.this, mEditTextContent);
                            //                        controlKeyboardLayout(activityRootView, pullList);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            super.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO;下拉加载历史数据
     * TODO;下拉加载历史数据
     * TODO;下拉加载历史数据
     * TODO;下拉加载历史数据
     * TODO;下拉加载历史数据
     * TODO;下拉加载历史数据
     */
    @Override
    protected void loadRecords() {
        isDown = true;
        if (pagelist != null) {
            pagelist.clear();
        }
        String otherSideId = CacheData.getOtherSideId();
        pagelist = mChatDbManager.loadPages(page, number, CacheData.getMyData().getMemberId(), otherSideId);
        if (page >= 0) {
            position = pagelist.size();
            pagelist.addAll(tblist);
            tblist.clear();
            tblist.addAll(pagelist);
            if (imageList != null) {
                imageList.clear();
            }
            int key = 0;
            int position = 0;
            for (DbChatMessageBean cmb : tblist) {
                if (cmb.getType().equals(PP2PMsg.IMAGE)) {
                    imageList.add(cmb.getContent());
                    position++;
                }
                key++;
            }
            tbAdapter.setImageList(imageList);
            sendMessageHandler.sendEmptyMessage(PULL_TO_REFRESH_DOWN);
            page--;
        } else {
            showToast("历史记录全部加载完毕");
        }
    }

    private static String getCurrentOtherSideId() {
        DbGetStudent currentStudent = CacheData.getToUser();
        DbGetChatGroups currentGroupData = CacheData.getToGroup();
        if (currentStudent != null) {
            return currentStudent.getMemberId();
        } else if (currentGroupData != null) {
            return currentGroupData.getGroupId();
        }
        return "";
    }

    private static String getChatMsgUserIdByProtocal(Protocal p) {
        MsgType msgType = getMsgTypeByProtocal(p);
        if (msgType == MsgType.PERSON) {
            return p.getFrom();
        } else if (msgType == MsgType.GROUP) {
            PGroupMsg groupMsg = getGroupMsgByProtocal(p);
            return groupMsg.getGroupId();
        } else {
            return "";
        }
    }

    private static boolean isMessageCurrentPage(Protocal p) {
        boolean ret = false;
        if (instance != null && instance.sendMessageHandler != null) {
            String currentOtherSideId = getCurrentOtherSideId();
            String msgUserId = getChatMsgUserIdByProtocal(p);
            if (Schecker.isStringNull(currentOtherSideId) || Schecker.isStringNull(msgUserId)) {

            } else {
                if (!currentOtherSideId.equals(msgUserId)) {
                    //TODO:传入的信息不属于此对话页面
                } else {
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * 显示个人或群信息，并存储
     *
     * @param con
     * @param p
     */
    public static void showData(Context con, Protocal p) {
        try {
            MsgType msgType = getMsgTypeByProtocal(p);
            if (msgType == null) {
                Log.e(TAG, "消息类型不可显示..");
                return;
            }
            storageRecentLog(con, p);
            if (isMessageCurrentPage(p)) {
                instance.receiveMessage(p);
            } else {
                if (instance == null) {
                    Log.e(TAG, "instance == null");
                } else {
                    Log.e(TAG, "instance.sendMessageHandler == null");
                }
                Log.e(TAG, "无对话页面，直接存储信息");
                saveChatBeanIntoDb(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DbRecentlyUserDao dao;

    private static DbRecentlyUserDao getLogDao(Context con) {
        if (dao == null) {
            dao = HissDbManager.getDaoSession(con).getDbRecentlyUserDao();
        }
        return dao;
    }

    private static PP2PMsg getMsgByProtocal(Protocal p) {
        Gson gson = new Gson();
        String data = p.getDataContent();
        PP2PMsg msg = gson.fromJson(data, PP2PMsg.class);
        return msg;
    }

    private static PGroupMsg getGroupMsgByProtocal(Protocal p) {
        Gson gson = new Gson();
        String data = p.getDataContent();
        PGroupMsg msg = gson.fromJson(data, PGroupMsg.class);
        return msg;
    }

    /**
     * 保存最近使用记录-追加个人或群信息
     *
     * @param con
     * @param p
     */
    private static void storageRecentLog(Context con, Protocal p) {
        if (dao == null) {
            RecyclerViewChatActivity.getLogDao(con);
        }
        MsgType msgType = getMsgTypeByProtocal(p);
        int unreadCount = 1;

        if (msgType == MsgType.PERSON) {
            //个人
//            PP2PMsg msg = getMsgByProtocal(p);
            DbRecentlyUser bean = dao.queryBuilder().where(DbRecentlyUserDao.Properties.LoginUserId.eq(p.getTo()), DbRecentlyUserDao.Properties.Id.eq(p.getFrom())).unique();
            DbRecentlyUser log = createLogByMsg(con, p);
            if (log != null) {
                unreadCount = (bean == null) ? 1 : (Integer.valueOf(bean.getUnreadCount()) + 1);
                setupUnreadCountWithNotification(log, p, unreadCount);
                if (bean == null) {
                    dao.insert(log);
                } else {
                    log.setDbId(bean.getDbId());
                    dao.updateInTx(log);
                }
            }
        } else if (msgType == MsgType.GROUP) {
            //群组
            PGroupMsg msg = getGroupMsgByProtocal(p);
            DbRecentlyUser bean = dao.queryBuilder().where(DbRecentlyUserDao.Properties.LoginUserId.eq(p.getTo()), DbRecentlyUserDao.Properties.Id.eq(msg.getGroupId())).unique();
            DbRecentlyUser log = createLogByMsg(con, p);
            if (log != null) {
                unreadCount = (bean == null) ? 1 : (Integer.valueOf(bean.getUnreadCount()) + 1);
                setupUnreadCountWithNotification(log, p, unreadCount);
                if (bean == null) {
                    dao.insert(log);
                } else {
                    log.setDbId(bean.getDbId());
                    dao.updateInTx(log);
                }
            }
        }

        try {
            //最后刷新最近消息列表的页面
            if (listener != null) {
                listener.onLogChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示自定义通知
     *
     * @param bean
     * @param p
     */
    private static void setupUnreadCountWithNotification(DbRecentlyUser bean, Protocal p, int unreadCount) {
        if (isMessageCurrentPage(p)) {
            //正在对话页面
            bean.setUnreadCount("0");
        } else {
            bean.setUnreadCount(String.valueOf(unreadCount));
            new HissNotification().ordinaryNotification(BaseManager.getContext(), bean);
        }
    }

    private static DbRecentlyUser createLogByMsg(Context con, Protocal p) {
        Gson gson = new Gson();
        DbRecentlyUser log = new DbRecentlyUser();
        log.setLoginUserId(p.getTo());
        log.setRecentTime(System.currentTimeMillis());
        MsgType msgType = getMsgTypeByProtocal(p);
        if (msgType == MsgType.PERSON) {
            //个人消息
            PP2PMsg msg = getMsgByProtocal(p);
            log.setId(p.getFrom());
            log.setType("0");
            log.setContentType(msg.getType() + "");
            if (msg.getContent() instanceof String) {
                log.setContentData((String) msg.getContent());
            } else {
                log.setContentData(msg.getContent().toString());
            }
            //判断是不是好友
            DbGetFriendsDao daoFriend = HissDbManager.getDaoSession(con).getDbGetFriendsDao();
            DbGetFriends friends = daoFriend.queryBuilder().where(DbGetFriendsDao.Properties.LoginUserId.eq(p.getTo()), DbGetFriendsDao.Properties.FriendId.eq(p.getFrom())).unique();
            String isFriend = (friends == null) ? "0" : "1";
            log.setIsFriend(isFriend);
            if (isFriend.equals("0")) {
                //不是好友，从群成员中查找数据
                DbGetChatGroupMembersDao daoMembers = HissDbManager.getDaoSession(con).getDbGetChatGroupMembersDao();
                List<DbGetChatGroupMembers> member = daoMembers.queryBuilder().where(DbGetChatGroupMembersDao.Properties.FriendId.eq(p.getFrom())).list();
                if (member != null && member.size() > 0) {
                    DbGetStudent student = friend2Student(DbGetChatGroupMembers.getFriendByMember(member.get(0)));
                    String str = gson.toJson(student);
                    log.setDbGetStudentStr(str);
                } else {
                    return null;
                }
            } else {
                //是好友,存储好友数据字符串
                DbGetStudent student = friend2Student(friends);
                String str = gson.toJson(student);
                log.setDbGetStudentStr(str);
            }
        } else if (msgType == MsgType.GROUP) {
            //群消息
            PGroupMsg msg = getGroupMsgByProtocal(p);
            log.setContentType(msg.getType() + "");
            if (msg.getContent() instanceof String) {
                log.setContentData((String) msg.getContent());
            } else {
                log.setContentData(msg.getContent().toString());
            }
            log.setId(msg.getGroupId());
            log.setType("1");
            log.setIsFriend("1");
            DbGetChatGroupsDao groupDao = HissDbManager.getDaoSession(con).getDbGetChatGroupsDao();
            List<DbGetChatGroups> group = groupDao.queryBuilder().where(DbGetChatGroupsDao.Properties.GroupId.eq(msg.getGroupId())).list();
            if (group == null) {
                return null;
            } else if (group.size() == 1) {
                String groupStr = gson.toJson(group.get(0));
                log.setDbGetChatGroupsStr(groupStr);
            } else {
                return null;
            }
        }
        return log;
    }

    private static DbGetStudent friend2Student(DbGetFriends friends) {
        DbGetStudent student = new DbGetStudent();
        StuImage image = new StuImage();
        image.setHeadUrl(friends.getImgUrl());
        student.setStuImage(image);
        student.setRealName(friends.getRealName());
        student.setSex(friends.getSex());
        student.setAge(friends.getAge());
        student.setUniversityName(friends.getUniversityName());
        student.setClassName(friends.getClassName());
        student.setMemberId(friends.getFriendId());
        return student;
    }

    /**
     * 将我发送出去的信息按照接收信息的方式存储最近历史记录
     *
     * @param p
     */
    private void changeSenderNstore(Protocal p) {
        final Protocal protocal = p.protocalClone();
        String from = new String(protocal.getTo());
        String to = new String(protocal.getFrom());
        protocal.setFrom(from);
        protocal.setTo(to);
        RecyclerViewChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                storageRecentLog(BaseManager.getContext(), protocal);
            }
        });
    }

    @Override
    protected void sendTxtMessage(final String txt, final String type) {
        try {
            if (type == null) {
                return;
            }
            String content = txt;
            if (CacheData.getMyData() == null) {
                Log.e(TAG, "CacheData.getMyData() == null");
                return;
            }
            boolean checker = (CacheData.getChatType().equals("0")) ? (CacheData.getToUser() != null) : (CacheData.getToGroup() != null);
            if (!checker) {
                Log.e(TAG, "CacheData.getToUser() == " + CacheData.getToUser() + " ; CacheData.getToGroup() == " + CacheData.getToGroup());
                return;
            }
            if (TextUtils.isEmpty(content)) {
                Log.e(TAG, "content为空，不传输");
                return;
            }
            final DbChatMessageBean addedBean = getTbub(PP2PMsg.TRANS_OUT, type, content, ChatConst.COMPLETED);
            tblist.add(addedBean);
            sendMessageHandler.sendEmptyMessage(SEND_OK);
            if (CacheData.getChatType().equals("0")) {
                //个人信息
                PP2PMsg msg = new PP2PMsg();
                msg.setType(type);
                msg.setCreateDate(System.currentTimeMillis() + "");
                msg.setContent(content);
                if (type.equals(PP2PMsg.POSITION)) {
                    Gson gson = new Gson();
                    IosPosition iosPosition = gson.fromJson(content, IosPosition.class);
                    JsonObject positionJson = new JsonObject();
                    positionJson.addProperty("longitude", iosPosition.getLongitude());
                    positionJson.addProperty("latitude", iosPosition.getLatitude());
                    msg.setContent(positionJson);
                } else if (type.equals(PP2PMsg.IMAGE)) {
                    imageList.add(addedBean.getContent());
                }
                Gson gson = new Gson();
                String msgStr = gson.toJson(msg);
                Protocal p = ProtocalFactory.createCommonData(msgStr, CacheData.getMyData().getMemberId(), CacheData.getToUser().getMemberId(), true, null);
                Log.e(TAG, "Send txt Protocal content:" + gson.toJson(p));
                new LocalUDPDataSender.SendCommonDataAsync(p) {

                    @Override
                    protected void onPostExecute(Integer paramInteger) {
                        callbackAction(paramInteger, addedBean);
                    }
                }.execute();
                changeSenderNstore(p);
            } else {
                //群信息
                PGroupMsg msg = new PGroupMsg();
                msg.setType(type);
                msg.setCreateDate(System.currentTimeMillis() + "");
                msg.setContent(content);
                msg.setGroupId(CacheData.getToGroup().getGroupId());
                if (type.equals(PP2PMsg.POSITION)) {
                    Gson gson = new Gson();
                    IosPosition iosPosition = gson.fromJson(content, IosPosition.class);
                    JsonObject positionJson = new JsonObject();
                    positionJson.addProperty("longitude", iosPosition.getLongitude());
                    positionJson.addProperty("latitude", iosPosition.getLatitude());
                    msg.setContent(positionJson);
                } else if (type.equals(PP2PMsg.IMAGE)) {
                    imageList.add(addedBean.getContent());
                }
                Gson gson = new Gson();
                String msgStr = gson.toJson(msg);
                Protocal p = ProtocalFactory.createCommonGroupData(msgStr, CacheData.getMyData().getMemberId(), CacheData.getToGroup().getGroupId(), true);
                Log.e(TAG, "Send txt Protocal content:" + gson.toJson(p));
                new LocalUDPDataSender.SendCommonDataAsync(p) {

                    @Override
                    protected void onPostExecute(Integer paramInteger) {
                        callbackAction(paramInteger, addedBean);
                    }
                }.execute();
                changeSenderNstore(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callbackAction(Integer num, DbChatMessageBean tbub) {
        Log.e(TAG, "SendCommonDataAsync paramInteger = " + num);
        if (num == ErrorCode.COMMON_CODE_OK) {
            //消息发送成功
        } else {
            //消息发送失败...将发送状态设置为发送失败
            DbChatMessageBeanDao dao = HissDbManager.getDaoSession(RecyclerViewChatActivity.this).getDbChatMessageBeanDao();
            List<DbChatMessageBean> list = dao.queryBuilder().where(DbChatMessageBeanDao.Properties.CreateDate.eq(tbub.getCreateDate())).list();
            if (list != null) {
                DbChatMessageBean bean = list.get(0);
                bean.setSendState(ChatConst.SENDERROR);
                dao.update(bean);
            }
            for (int i = 0; i < tblist.size(); i++) {
                if (tbub.getCreateDate().equals(tblist.get(i).getCreateDate())) {
                    tblist.get(i).setSendState(ChatConst.SENDERROR);
                }
            }
            sendMessageHandler.sendEmptyMessage(REFRESH);
        }
    }

    /**
     * 发送需要上传阿里云的文件路径
     */
    @Override
    protected void sendFile(final String filePath, final OssSetting.HissResType type) {
        sendFileAction(type, filePath);
    }

    private void sendFileAction(final OssSetting.HissResType type, final String filePath) {
        try {
            ArrayList<OssFile> array = new ArrayList<>();
            OssFile ii = new OssFile();
            ii.setFilePath(filePath);
            ii.setType(type);
            array.add(ii);
            OssFileUploadThread uploadThread = new OssFileUploadThread(RecyclerViewChatActivity.this, array, new OssFileUploadThread.OnUploadFilesFinished() {
                @Override
                public void onSuccess(ArrayList<OssFile> items) {
                    final String ossPath = items.get(0).getOssPath();
                    try {
                        URL u = new URL(ossPath);
                        HttpURLConnection urlcon = (HttpURLConnection) u.openConnection();
                        int fileLength = urlcon.getContentLength();
                        if (fileLength < 0) {
                            showToast("上传文件失败");
                            //经测试，即使返回文件长度大于0，也有可能无法显示
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sendTxtMessage(ossPath, type.getPp2pmsgType());

//                    final DbChatMessageBean addedBean = getTbub(PP2PMsg.TRANS_OUT, type.getPp2pmsgType(), ossPath, ChatConst.COMPLETED);
//                    tblist.add(addedBean);
//                    if (type.getPp2pmsgType().equals(PP2PMsg.IMAGE)) {
//                        imageList.add(addedBean.getContent());
//                    }
//                    sendMessageHandler.sendEmptyMessage(SEND_OK);
//
//                    if (CacheData.getChatType().equals("0")) {
//                        //个人信息
//                        PP2PMsg msg = new PP2PMsg();
//                        msg.setType(type.getPp2pmsgType());
//                        msg.setCreateDate(System.currentTimeMillis() + "");
//                        msg.setContent(ossPath);
//                        Gson gson = new Gson();
//                        String msgStr = gson.toJson(msg);
//                        Protocal p = ProtocalFactory.createCommonData(msgStr, CacheData.getMyData().getMemberId(), CacheData.getToUser().getMemberId(), true, null);
//                        Log.e(TAG, "Send file Protocal content:" + gson.toJson(p));
//                        new LocalUDPDataSender.SendCommonDataAsync(p) {
//
//                            @Override
//                            protected void onPostExecute(Integer paramInteger) {
//                                callbackAction(paramInteger, addedBean);
//                            }
//                        }.execute();
//                        changeSenderNstore(p);
//                    } else {
//                        //群信息
//                        PGroupMsg msg = new PGroupMsg();
//                        msg.setType(type.getPp2pmsgType());
//                        msg.setCreateDate(System.currentTimeMillis() + "");
//                        msg.setContent(ossPath);
//                        msg.setGroupId(CacheData.getToGroup().getGroupId());
//                        Gson gson = new Gson();
//                        String msgStr = gson.toJson(msg);
//                        Protocal p = ProtocalFactory.createCommonGroupData(msgStr, CacheData.getMyData().getMemberId(), CacheData.getToGroup().getGroupId(), true);
//                        Log.e(TAG, "Send file Protocal content:" + gson.toJson(p));
//                        new LocalUDPDataSender.SendCommonDataAsync(p) {
//
//                            @Override
//                            protected void onPostExecute(Integer paramInteger) {
//                                callbackAction(paramInteger, addedBean);
//                            }
//                        }.execute();
//                        changeSenderNstore(p);
//                    }
                }

                @Override
                public void onFailed(ArrayList<OssFile> items) {
                    try {
                        if (items != null && items.size() > 0) {
                            final DbChatMessageBean addedBean = getTbub(PP2PMsg.TRANS_OUT, type.getPp2pmsgType(), items.get(0).getFilePath(), ChatConst.SENDERROR);
                            if (type.getPp2pmsgType().equals(PP2PMsg.IMAGE)) {
                                imageList.add(addedBean.getContent());
                            }
                            tblist.add(addedBean);
                            sendMessageHandler.sendEmptyMessage(SEND_OK);
                            callbackAction(ErrorCode.COMMON_DATA_SEND_FAILD, addedBean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            uploadThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MsgType getMsgTypeByProtocal(Protocal p) {
        if (p.getType() == ProtocalType.C.FROM_CLIENT_TYPE_OF_COMMON$DATA) {
            return MsgType.PERSON;
        } else if (p.getType() == ProtocalType.C.FROM_CLIENT_TYPE_OF_GROUP) {
            return MsgType.GROUP;
        } else {
            return null;
        }
    }

    /**
     * TODO:
     *
     * @param bean
     * @param p
     */
    private static void initMessageBeanId(DbChatMessageBean bean, Protocal p) {
        try {
            MsgType msgType = getMsgTypeByProtocal(p);
            if (msgType == MsgType.PERSON) {
                bean.setUserId(p.getFrom());
                bean.setGroupId("");
            } else if (msgType == MsgType.GROUP) {
                PGroupMsg groupMsg = getGroupMsgByProtocal(p);
                bean.setUserId(p.getFrom());
                bean.setGroupId(groupMsg.getGroupId());
            }
            bean.setMyId(CacheData.getMyData().getMemberId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(final Protocal p) {
        RecyclerViewChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    DbChatMessageBean tbub = saveChatBeanIntoDb(p);
                    if (tbub == null) {
                        return;
                    }
                    tblist.add(tbub);
                    sendMessageHandler.sendEmptyMessage(RECERIVE_OK);
                    String jsonObj = p.getDataContent();
                    JSONObject obj = new JSONObject(jsonObj);
                    String messageType = obj.getString("type");
                    if (messageType.equals(PP2PMsg.IMAGE)) {
                        //将当前图片加入列表，并记录位置，以便进行图片预览
                        imageList.add(tbub.getContent());
                    } else if (messageType.equals(PP2PMsg.VOICE)) {
                        //记录语音位置，已显示未读的小圆点
                        tbAdapter.unReadPosition.add(tblist.size() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    RecyclerViewChatActivity.this.showToast("解析消息失败...");
                }
            }
        });
    }

    /**
     * 保存传入的chatBean
     *
     * @param p
     * @return
     */
    private static DbChatMessageBean saveChatBeanIntoDb(Protocal p) {
        DbChatMessageBean tbub = new DbChatMessageBean();
        try {
            MsgType msgType = getMsgTypeByProtocal(p);
            if (msgType == null) {
                Log.e(TAG, "消息类型不可显示..");
                return null;
            }
            String time = returnTime();
            String content = "";
            String jsonObj = p.getDataContent();
            JSONObject obj = new JSONObject(jsonObj);
            String messageType = obj.getString("type");
            if (Integer.valueOf(messageType) != Integer.valueOf(PP2PMsg.POSITION) && Integer.valueOf(messageType) != Integer.valueOf(PP2PMsg.TXT) && Integer.valueOf(messageType) != Integer.valueOf(PP2PMsg.IMAGE) && Integer.valueOf(messageType) != Integer.valueOf(PP2PMsg.VOICE) && Integer.valueOf(messageType) != Integer.valueOf(PP2PMsg.VIDEO)) {
                Log.e(TAG, "消息类型为 " + messageType + " , 不符合要求");
                return null;
            }
            if (msgType == MsgType.PERSON) {
                PP2PMsg msg = getMsgByProtocal(p);
                if (msg.getContent() instanceof String) {
                    tbub.setContent((String) msg.getContent());
                    if (msg.getType().equals(PP2PMsg.VIDEO)) {
                        createVideoThumbnail((String) msg.getContent(), p);
                    }
                } else {
                    if (msg.getType().equals(PP2PMsg.POSITION)) {
                        LinkedTreeMap a = (LinkedTreeMap) msg.getContent();
                        IosPosition iosPosition = new IosPosition();
                        iosPosition.setLatitude((Double) a.get("latitude"));
                        iosPosition.setLongitude((Double) a.get("longitude"));
                        Gson gson = new Gson();
                        String position = gson.toJson(iosPosition);
                        Log.e(TAG, "##############postion = " + position);
                        tbub.setContent(position);
                    } else {
                        Log.e(TAG, "未知类型OBJECT！！！！");
                    }
                }
            } else if (msgType == MsgType.GROUP) {
                PGroupMsg msg = getGroupMsgByProtocal(p);
                if (msg.getContent() instanceof String) {
                    tbub.setContent((String) msg.getContent());
                    if (msg.getType().equals(PP2PMsg.VIDEO)) {
                        createVideoThumbnail((String) msg.getContent(), p);
                    }
                } else {
                    if (msg.getType().equals(PP2PMsg.POSITION)) {
                        LinkedTreeMap a = (LinkedTreeMap) msg.getContent();
                        IosPosition iosPosition = new IosPosition();
                        iosPosition.setLatitude((Double) a.get("latitude"));
                        iosPosition.setLongitude((Double) a.get("longitude"));
                        Gson gson = new Gson();
                        String position = gson.toJson(iosPosition);
                        Log.e(TAG, "##############postion = " + position);
                        tbub.setContent(position);
                    } else {
                        Log.e(TAG, "未知类型OBJECT！！！！");
                    }
                }
            }
            tbub.setCreateDate(time);
            tbub.setTrans(PP2PMsg.TRANS_IN);
            tbub.setType(messageType);
            initMessageBeanId(tbub, p);
            ChatDbManager.getInstance().insert(tbub);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return tbub;
    }

    private synchronized static void createVideoThumbnail(final String videoUrl, final Protocal p) {
        new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    final int res = R.drawable.chatfrom_video_bg_focused;
                    final String thumbName = HissFileService.getOssFileName(videoUrl).replace(".", "") + ".png";
                    File fWanted = new File(HissThumbnail.getVideoThumbDir() + thumbName);
                    Log.e(TAG, "fWanted = " + HissThumbnail.getVideoThumbDir() + thumbName);
                    if (fWanted.exists()) {
                        Log.e(TAG, "fWanted exists");
                    } else {
                        Log.e(TAG, "fWanted is creating");
                        File f = HissThumbnail.videoThmbnailFile(BaseManager.getContext(), videoUrl, thumbName);
                        if (isMessageCurrentPage(p)) {
                            RecyclerViewChatActivity.instance.sendMessageHandler.sendEmptyMessage(REFRESH);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public static class SendMessageHandler extends Handler {
        WeakReference<RecyclerViewChatActivity> mActivity;

        SendMessageHandler(RecyclerViewChatActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                RecyclerViewChatActivity theActivity = mActivity.get();
                if (theActivity != null) {
                    switch (msg.what) {
                        case REFRESH:
                            theActivity.tbAdapter.isPicRefresh = true;
                            theActivity.tbAdapter.notifyDataSetChanged();
                            int position = theActivity.tbAdapter.getItemCount() - 1 < 0 ? 0 : theActivity.tbAdapter.getItemCount() - 1;
                            theActivity.recyclerView.smoothScrollToPosition(position);
                            break;
                        case SEND_OK:
                            theActivity.mEditTextContent.setText("");
                            theActivity.tbAdapter.isPicRefresh = true;
                            //                        theActivity.tbAdapter.notifyItemRangeRemoved(0, theActivity.tblist.size());
                            //                        int prevSize = theActivity.tblist.size();
                            //                        theActivity.tbAdapter.notifyItemRangeChanged(prevSize, theActivity.tblist.size() - prevSize);
                            theActivity.tbAdapter.notifyItemInserted(theActivity.tblist.size() - 1);
                            theActivity.recyclerView.smoothScrollToPosition(theActivity.tbAdapter.getItemCount() - 1);
                            break;
                        case RECERIVE_OK:
                            theActivity.tbAdapter.isPicRefresh = true;
                            //                        theActivity.tbAdapter.notifyItemRangeRemoved(0, theActivity.tblist.size());
                            //                        int prevSize1 = theActivity.tblist.size();
                            //                        theActivity.tbAdapter.notifyItemRangeChanged(prevSize1, theActivity.tblist.size() - prevSize1);
                            theActivity.tbAdapter.notifyItemInserted(theActivity.tblist.size() - 1);
                            theActivity.recyclerView.smoothScrollToPosition(theActivity.tbAdapter.getItemCount() - 1);
                            break;
                        case PULL_TO_REFRESH_DOWN:
                            theActivity.pullList.setRefreshing(false);
                            theActivity.tbAdapter.notifyDataSetChanged();
                            int showPosition = ((theActivity.position - 1) < 0) ? 0 : (theActivity.position - 1);
                            theActivity.recyclerView.smoothScrollToPosition(showPosition);
                            theActivity.isDown = false;
                            break;
                        case GO2LAST_ITEM:
                            theActivity.recyclerView.smoothScrollToPosition(theActivity.tbAdapter.getItemCount() - 1);
                            break;
                        case REMOVE_OK:
                            //TODO:会重影
                            theActivity.tbAdapter.isPicRefresh = true;
                            int deleteIndex = msg.arg1;
                            theActivity.tblist.remove(deleteIndex);
                            theActivity.tbAdapter.notifyItemRemoved(deleteIndex);
                            if (deleteIndex != theActivity.tblist.size()) {
                                theActivity.tbAdapter.notifyItemRangeChanged(deleteIndex, theActivity.tblist.size() - deleteIndex);
                            }
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
