package com.maxi.chatdemo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.maxi.chatdemo.R;
import com.maxi.chatdemo.common.ChatConst;
import com.maxi.chatdemo.ui.ImageViewActivity;
import com.maxi.chatdemo.ui.RecyclerViewChatActivity;
import com.maxi.chatdemo.ui.base.BaseActivity;
import com.maxi.chatdemo.utils.AudioManager;
import com.maxi.chatdemo.utils.FileSaveUtil;
import com.maxi.chatdemo.widget.BubbleImageView;
import com.maxi.chatdemo.widget.CustomShapeTransformation;
import com.maxi.chatdemo.widget.GifTextView;
import com.maxi.chatdemo.widget.MediaManager;

import net.openmob.mobileimsdk.server.protocal.c.PP2PMsg;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import cn.com.hiss.www.multilib.HissAmapPosition;
import cn.com.hiss.www.multilib.db.DbChatMessageBean;
import cn.com.hiss.www.multilib.db.DbGetChatGroupMembers;
import cn.com.hiss.www.multilib.db.DbGetStudent;
import cn.com.hiss.www.multilib.db.HissDbManager;
import cn.com.hiss.www.multilib.ui.AddressMapActivity;
import cn.com.hiss.www.multilib.ui.LocalVideoActivity;
import cn.com.hiss.www.multilib.utils.CacheData;
import cn.com.hiss.www.multilib.utils.FloatDecorator;
import cn.com.hiss.www.multilib.utils.FontHelper;
import cn.com.hiss.www.multilib.utils.HissDate;
import cn.com.hiss.www.multilib.utils.HissFileDownloader;
import cn.com.hiss.www.multilib.utils.HissFileService;
import cn.com.hiss.www.multilib.utils.HissMedia;
import cn.com.hiss.www.multilib.utils.HissThumbnail;
import cn.com.hiss.www.multilib.utils.InfoBrData;
import cn.com.hiss.www.multilib.utils.Mp3TimeGetAsyncTask;
import cn.com.hiss.www.multilib.utils.PicDecorator;
import cn.com.hiss.www.multilib.utils.Schecker;
import cn.com.hiss.www.multilib.utils.TBroadcast;
import cn.com.hiss.www.multilib.utils.UrlFileDownloader;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import io.github.rockerhieu.emojicon.emoji.Objects;

/**
 * Created by Mao Jiqing on 2016/9/29.
 */
public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ChatRecyclerAdapter.class.getSimpleName();
    private Context context;
    private RecyclerViewChatActivity recyclerViewChatActivity;
    private List<DbChatMessageBean> userList = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<String>();  //用来存图片链接
//    private HashMap<Integer, Integer> imagePosition = new HashMap<Integer, Integer>();

    private static final int INFO_IN_TXT = 500;
    private static final int INFO_IN_IMAGE = 501;
    private static final int INFO_IN_VOICE = 502;
    private static final int INFO_IN_VIDEO = 503;
    private static final int INFO_IN_POSITION = 504;
    private static final int INFO_OUT_TXT = 600;
    private static final int INFO_OUT_IMAGE = 601;
    private static final int INFO_OUT_VOICE = 602;
    private static final int INFO_OUT_VIDEO = 603;
    private static final int INFO_OUT_POSITION = 604;

    private int mMinItemWith;// 设置对话框的最大宽度和最小宽度
    private int mMaxItemWith;
    public Handler handler;
    private Animation an;
    private SendErrorListener sendErrorListener;
    private VoiceIsRead voiceIsRead;
    public List<String> unReadPosition = new ArrayList<String>();
    private int voicePlayPosition = -1;
    private LayoutInflater mLayoutInflater;
    private boolean isGif = true;
    public boolean isPicRefresh = true;

    public static final int TIME_SHOW_EXTEND = 10;//两个消息间隔5分钟以上时显示时间

    public interface SendErrorListener {
        public void onClick(int position);
    }

    public void setSendErrorListener(SendErrorListener sendErrorListener) {
        this.sendErrorListener = sendErrorListener;
    }

    public interface VoiceIsRead {
        public void voiceOnClick(int position);
    }

    public void setVoiceIsReadListener(VoiceIsRead voiceIsRead) {
        this.voiceIsRead = voiceIsRead;
    }

    public ChatRecyclerAdapter(Context context, List<DbChatMessageBean> userList) {
        this.context = context;
        try {
            recyclerViewChatActivity = (RecyclerViewChatActivity) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.userList = userList;
        mLayoutInflater = LayoutInflater.from(context);
        // 获取系统宽度
        WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWith = (int) (outMetrics.widthPixels * 0.5f);
        mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
        handler = new Handler();
    }

    public void setIsGif(boolean isGif) {
        this.isGif = isGif;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

//    public void setImagePosition(HashMap<Integer, Integer> imagePosition) {
//        this.imagePosition = imagePosition;
//    }

    @Override
    public int getItemViewType(int position) {
        int ret = INFO_IN_TXT;
        DbChatMessageBean bean = userList.get(position);
        if (bean.getTrans().equals(PP2PMsg.TRANS_IN)) {
            if (bean.getType().equals(PP2PMsg.TXT)) {
                ret = INFO_IN_TXT;
            } else if (bean.getType().equals(PP2PMsg.IMAGE)) {
                ret = INFO_IN_IMAGE;
            } else if (bean.getType().equals(PP2PMsg.VOICE)) {
                ret = INFO_IN_VOICE;
            } else if (bean.getType().equals(PP2PMsg.VIDEO)) {
                ret = INFO_IN_VIDEO;
            } else if (bean.getType().equals(PP2PMsg.POSITION)) {
                ret = INFO_IN_POSITION;
            }
        } else if (bean.getTrans().equals(PP2PMsg.TRANS_OUT)) {
            if (bean.getType().equals(PP2PMsg.TXT)) {
                ret = INFO_OUT_TXT;
            } else if (bean.getType().equals(PP2PMsg.IMAGE)) {
                ret = INFO_OUT_IMAGE;
            } else if (bean.getType().equals(PP2PMsg.VOICE)) {
                ret = INFO_OUT_VOICE;
            } else if (bean.getType().equals(PP2PMsg.VIDEO)) {
                ret = INFO_OUT_VIDEO;
            } else if (bean.getType().equals(PP2PMsg.POSITION)) {
                ret = INFO_OUT_POSITION;
            }
        }
        return ret;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case INFO_IN_TXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_msgfrom_list_item, parent, false);
                holder = new FromUserMsgViewHolder(view);
                break;
            case INFO_IN_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_imagefrom_list_item, parent, false);
                holder = new FromUserImageViewHolder(view);
                break;
            case INFO_IN_VOICE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_voicefrom_list_item, parent, false);
                holder = new FromUserVoiceViewHolder(view);
                break;
            case INFO_IN_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_videofrom_list_item, parent, false);
                holder = new FromUserVideoViewHolder(view);
                break;
            case INFO_IN_POSITION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_positionfrom_list_item, parent, false);
                holder = new FromUserPositionViewHolder(view);
                break;
            case INFO_OUT_TXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_msgto_list_item, parent, false);
                holder = new ToUserMsgViewHolder(view);
                break;
            case INFO_OUT_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_imageto_list_item, parent, false);
                holder = new ToUserImgViewHolder(view);
                break;
            case INFO_OUT_VOICE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_voiceto_list_item, parent, false);
                holder = new ToUserVoiceViewHolder(view);
                break;
            case INFO_OUT_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_videoto_list_item, parent, false);
                holder = new ToUserVideoViewHolder(view);
                break;
            case INFO_OUT_POSITION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout_positionto_list_item, parent, false);
                holder = new ToUserPositionViewHolder(view);
                break;
        }
        return holder;
    }

    /**
     * TODO:页面初始化
     * TODO:页面初始化
     * TODO:页面初始化
     * TODO:页面初始化
     * TODO:页面初始化
     * TODO:页面初始化
     * TODO:页面初始化
     * TODO:页面初始化
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //强制关闭复用
        holder.setIsRecyclable(false);
        DbChatMessageBean tbub = userList.get(position);
        int itemViewType = getItemViewType(position);

        switch (itemViewType) {
            case INFO_IN_TXT:
                fromMsgUserLayout((FromUserMsgViewHolder) holder, tbub, position);
                break;
            case INFO_IN_IMAGE:
                fromImgUserLayout((FromUserImageViewHolder) holder, tbub, position);
                break;
            case INFO_IN_VOICE:
                fromVoiceUserLayout((FromUserVoiceViewHolder) holder, tbub, position);
                break;
            case INFO_IN_VIDEO:
                fromVideoUserLayout((FromUserVideoViewHolder) holder, tbub, position);
                break;
            case INFO_IN_POSITION:
                fromPositionUserLayout((FromUserPositionViewHolder) holder, tbub, position);
                break;
            case INFO_OUT_TXT:
                toMsgUserLayout((ToUserMsgViewHolder) holder, tbub, position);
                break;
            case INFO_OUT_IMAGE:
                toImgUserLayout((ToUserImgViewHolder) holder, tbub, position);
                break;
            case INFO_OUT_VOICE:
                toVoiceUserLayout((ToUserVoiceViewHolder) holder, tbub, position);
                break;
            case INFO_OUT_VIDEO:
                toVideoUserLayout((ToUserVideoViewHolder) holder, tbub, position);
                break;
            case INFO_OUT_POSITION:
                toPositionUserLayout((ToUserPositionViewHolder) holder, tbub, position);
                break;
            default:
                fromMsgUserLayout((FromUserMsgViewHolder) holder, tbub, position);
                break;
        }
    }

    /*******************************************************************************************************  ViewHolders  *************************************************************************************************************************************/
    public interface HolderFunction {
        ImageView getErrorView();
    }

    public interface TimeFunction {
        TextView getTimeView();
    }


    class FromUserMsgViewHolder extends RecyclerView.ViewHolder implements TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private TextView fromName;
        private EmojiconTextView content;

        public FromUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            fromName = (TextView) view.findViewById(R.id.chat_user_name);
            content = (EmojiconTextView) view.findViewById(R.id.content);
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class FromUserImageViewHolder extends RecyclerView.ViewHolder implements TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private TextView fromName;
        private BubbleImageView image_Msg;

        public FromUserImageViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            fromName = (TextView) view.findViewById(R.id.chat_user_name);
            image_Msg = (BubbleImageView) view.findViewById(R.id.image_message);
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class FromUserVoiceViewHolder extends RecyclerView.ViewHolder implements TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private TextView fromName;
        private LinearLayout voice_group;
        private TextView voice_time;
        private FrameLayout voice_image;
        private View receiver_voice_unread;
        private View voice_anim;

        public FromUserVoiceViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            fromName = (TextView) view.findViewById(R.id.chat_user_name);
            voice_group = (LinearLayout) view.findViewById(R.id.voice_group);
            voice_time = (TextView) view.findViewById(R.id.voice_time);
            receiver_voice_unread = (View) view.findViewById(R.id.receiver_voice_unread);
            voice_image = (FrameLayout) view.findViewById(R.id.voice_receiver_image);
            voice_anim = (View) view.findViewById(R.id.id_receiver_recorder_anim);
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class FromUserVideoViewHolder extends RecyclerView.ViewHolder implements TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private TextView fromName;
        private BubbleImageView image_Msg;
        private TextView signTv;

        public FromUserVideoViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            fromName = (TextView) view.findViewById(R.id.chat_user_name);
            image_Msg = (BubbleImageView) view.findViewById(R.id.image_message);
            signTv = (TextView) view.findViewById(R.id.sign_tv);
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class FromUserPositionViewHolder extends RecyclerView.ViewHolder implements TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private TextView fromName;
        private BubbleImageView image_Msg;

        public FromUserPositionViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            fromName = (TextView) view.findViewById(R.id.chat_user_name);
            image_Msg = (BubbleImageView) view.findViewById(R.id.image_message);
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class ToUserMsgViewHolder extends RecyclerView.ViewHolder implements HolderFunction, TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private EmojiconTextView content;
        private ImageView sendFailImg;

        public ToUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view.findViewById(R.id.mychat_time);
            content = (EmojiconTextView) view.findViewById(R.id.mycontent);
            sendFailImg = (ImageView) view.findViewById(R.id.mysend_fail_img);
        }

        @Override
        public ImageView getErrorView() {
            return sendFailImg;
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class ToUserImgViewHolder extends RecyclerView.ViewHolder implements HolderFunction, TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private LinearLayout image_group;
        private BubbleImageView image_Msg;
        private ImageView sendFailImg;

        public ToUserImgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view.findViewById(R.id.mychat_time);
            sendFailImg = (ImageView) view.findViewById(R.id.mysend_fail_img);
            image_group = (LinearLayout) view.findViewById(R.id.image_group);
            image_Msg = (BubbleImageView) view.findViewById(R.id.image_message);
        }

        @Override
        public ImageView getErrorView() {
            return sendFailImg;
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class ToUserVoiceViewHolder extends RecyclerView.ViewHolder implements HolderFunction, TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private LinearLayout voice_group;
        private TextView voice_time;
        private FrameLayout voice_image;
        private View receiver_voice_unread;
        private View voice_anim;
        private ImageView sendFailImg;

        public ToUserVoiceViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view.findViewById(R.id.mychat_time);
            voice_group = (LinearLayout) view.findViewById(R.id.voice_group);
            voice_time = (TextView) view.findViewById(R.id.voice_time);
            voice_image = (FrameLayout) view.findViewById(R.id.voice_image);
            voice_anim = (View) view.findViewById(R.id.id_recorder_anim);
            sendFailImg = (ImageView) view.findViewById(R.id.mysend_fail_img);
        }

        @Override
        public ImageView getErrorView() {
            return sendFailImg;
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class ToUserVideoViewHolder extends RecyclerView.ViewHolder implements HolderFunction, TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private BubbleImageView image_Msg;
        private ImageView sendFailImg;
        private TextView signTv;

        public ToUserVideoViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view.findViewById(R.id.mychat_time);
            sendFailImg = (ImageView) view.findViewById(R.id.mysend_fail_img);
            image_Msg = (BubbleImageView) view.findViewById(R.id.image_message);
            signTv = (TextView) view.findViewById(R.id.sign_tv);
        }

        @Override
        public ImageView getErrorView() {
            return sendFailImg;
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    class ToUserPositionViewHolder extends RecyclerView.ViewHolder implements HolderFunction, TimeFunction {
        private ImageView headicon;
        private TextView chat_time;
        private LinearLayout image_group;
        private BubbleImageView image_Msg;
        private ImageView sendFailImg;

        public ToUserPositionViewHolder(View view) {
            super(view);
            headicon = (ImageView) view.findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view.findViewById(R.id.mychat_time);
            sendFailImg = (ImageView) view.findViewById(R.id.mysend_fail_img);
            image_group = (LinearLayout) view.findViewById(R.id.image_group);
            image_Msg = (BubbleImageView) view.findViewById(R.id.image_message);
        }

        @Override
        public ImageView getErrorView() {
            return sendFailImg;
        }

        @Override
        public TextView getTimeView() {
            return chat_time;
        }
    }

    private static final String UNKOWN_NAME = "未知";

    private void timeShower(final TimeFunction holder, final DbChatMessageBean tbub, final int position) {
        /* time */
        if (position != 0) {
            String showTime = getTime(tbub.getCreateDate(), userList.get(position - 1).getCreateDate());
            if (showTime != null) {
                holder.getTimeView().setVisibility(View.VISIBLE);
                holder.getTimeView().setText(showTime);
            } else {
                holder.getTimeView().setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getCreateDate(), null);
            holder.getTimeView().setVisibility(View.VISIBLE);
            holder.getTimeView().setText(showTime);
        }
    }

    /**
     * TODO:显示当前信息传输状态
     * TODO:显示当前信息传输状态
     *
     * @param holder
     * @param tbub
     * @param position
     */
    private void signShower(final HolderFunction holder, final DbChatMessageBean tbub, final int position) {
        ImageView sendFailImg = holder.getErrorView();
        switch (tbub.getSendState()) {
//            case ChatConst.SENDING:
//                an = AnimationUtils.loadAnimation(context, R.anim.update_loading_progressbar_anim);
//                LinearInterpolator lin = new LinearInterpolator();
//                an.setInterpolator(lin);
//                an.setRepeatCount(-1);
//                sendFailImg.setBackgroundResource(R.mipmap.xsearch_loading);
//                sendFailImg.startAnimation(an);
//                an.startNow();
//                sendFailImg.setVisibility(View.VISIBLE);
//                break;
            case ChatConst.COMPLETED:
                sendFailImg.clearAnimation();
                sendFailImg.setVisibility(View.GONE);
                break;
            case ChatConst.SENDERROR:
                sendFailImg.clearAnimation();
                sendFailImg.setBackgroundResource(R.mipmap.msg_state_fail_resend_pressed);
                sendFailImg.setVisibility(View.VISIBLE);
                sendFailImg.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (sendErrorListener != null) {
                            sendErrorListener.onClick(position);
                        }
                    }

                });
                break;
            default:
                break;
        }
    }

    /******************************************************************  View Initiation  ****************************************************************************/
    private void fromMsgUserLayout(final FromUserMsgViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetChatGroupMembers member = HissDbManager.getMemberByMessage(tbub);
        if (CacheData.isGroupChat()) {
            PicDecorator.glideRoundCornerPic(context, PicDecorator.getMemberDefaultErrorPic(member), holder.headicon, member != null ? member.getImgUrl() : "");
            String name = UNKOWN_NAME;
            if (member != null) {
                name = member.getRealName();
            }
            holder.fromName.setVisibility(View.VISIBLE);
            holder.fromName.setText(name);
            setupIvAction(holder.headicon, tbub.getUserId());
        } else {
            holder.headicon.setVisibility(View.GONE);
        }
        timeShower(holder, tbub, position);
        holder.content.setVisibility(View.VISIBLE);
        holder.content.setText(tbub.getContent());
//        holder.content.setSpanText(handler, tbub.getContent(), isGif);
    }

    private void fromImgUserLayout(final FromUserImageViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetChatGroupMembers member = HissDbManager.getMemberByMessage(tbub);
        if (CacheData.isGroupChat()) {
            PicDecorator.glideRoundCornerPic(context, PicDecorator.getMemberDefaultErrorPic(member), holder.headicon, member != null ? member.getImgUrl() : "");
            String name = UNKOWN_NAME;
            if (member != null) {
                name = member.getRealName();
            }
            holder.fromName.setVisibility(View.VISIBLE);
            holder.fromName.setText(name);
            setupIvAction(holder.headicon, tbub.getUserId());
        } else {
            holder.headicon.setVisibility(View.GONE);
        }
        timeShower(holder, tbub, position);
        final String imageSrc = tbub.getContent();
        int res;
        res = R.drawable.chatfrom_bg_focused;
        Glide.with(context).load(imageSrc).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopPlayVoice();
                Intent intent = new Intent(context, ImageViewActivity.class);
                intent.putStringArrayListExtra("images", imageList);
                intent.putExtra("clickedIndex", getPicIndexByContent(tbub));
                context.startActivity(intent);
            }

        });
    }

    private int getPicIndexByContent(DbChatMessageBean tbub) {
        for (int i = 0; i < imageList.size(); i++) {
            if (tbub.getContent().equals(imageList.get(i))) {
                return i;
            }
        }
        return 0;
    }

    private void fromVoiceUserLayout(final FromUserVoiceViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetChatGroupMembers member = HissDbManager.getMemberByMessage(tbub);
        if (CacheData.isGroupChat()) {
            PicDecorator.glideRoundCornerPic(context, PicDecorator.getMemberDefaultErrorPic(member), holder.headicon, member != null ? member.getImgUrl() : "");
            String name = UNKOWN_NAME;
            if (member != null) {
                name = member.getRealName();
            }
            holder.fromName.setVisibility(View.VISIBLE);
            holder.fromName.setText(name);
            setupIvAction(holder.headicon, tbub.getUserId());
        } else {
            holder.headicon.setVisibility(View.GONE);
        }
        timeShower(holder, tbub, position);

        holder.voice_group.setVisibility(View.VISIBLE);
        if (holder.receiver_voice_unread != null)
            holder.receiver_voice_unread.setVisibility(View.GONE);
        if (holder.receiver_voice_unread != null && unReadPosition != null) {
            for (String unRead : unReadPosition) {
                if (unRead.equals(position + "")) {
                    holder.receiver_voice_unread.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        AnimationDrawable drawable;
        holder.voice_anim.setId(position);
        if (position == voicePlayPosition) {
            //            holder.voice_anim.setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
            holder.voice_anim.setBackgroundResource(R.drawable.voice_play_receiver);
            drawable = (AnimationDrawable) holder.voice_anim.getBackground();
            drawable.start();
        } else {
            holder.voice_anim.setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
        }
        holder.voice_group.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (holder.receiver_voice_unread != null)
                    holder.receiver_voice_unread.setVisibility(View.GONE);
                holder.voice_anim.setBackgroundResource(R.mipmap.receiver_voice_node_playing003);

                int currentPosition = holder.voice_anim.getId();
                if (currentPosition == voicePlayPosition) {
                    if (MediaManager.isPause()) {
                        //如果已被停止，则恢复播放
                        resumePlayVoice(holder);
                        AnimationDrawable drawable;
                        holder.voice_anim.setBackgroundResource(R.drawable.voice_play_receiver);
                        drawable = (AnimationDrawable) holder.voice_anim.getBackground();
                        drawable.start();
                    } else {
                        //如果正在播放，则停止
                        stopPlayVoice();
                    }
                    return;
                } else {
                    //再次执行下停止，主要目的是停止其他音频控件的动画
                    stopPlayVoice();
                }

                voicePlayPosition = holder.voice_anim.getId();
                AnimationDrawable drawable;
                holder.voice_anim.setBackgroundResource(R.drawable.voice_play_receiver);
                drawable = (AnimationDrawable) holder.voice_anim.getBackground();
                drawable.start();
                String voicePath = tbub.getContent();
                if (voiceIsRead != null) {
                    voiceIsRead.voiceOnClick(position);
                }
                MediaManager.playSound(voicePath, new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        voicePlayPosition = -1;
                        holder.voice_anim.setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
                    }
                });
            }

        });

        Log.e(TAG, "VOICE POSITION = " + position);
        holder.voice_time.setText("");
        final String storageFileName = AudioManager.getAudioSavePath() + File.separator + HissFileService.getOssFileName(tbub.getContent());
        File mp3File = new File(storageFileName);
        if (mp3File.exists()) {
            try {
                holder.voice_time.setText(FloatDecorator.getFloatStr(HissMedia.getMusicTime(mp3File)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            HissFileDownloader.startDownload(tbub.getContent(), storageFileName, new HissFileDownloader.DownloadListener() {
                @Override
                protected void completed(BaseDownloadTask task) {
                    Log.e(TAG, "音频下载成功！！！");
                    ((FragmentActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                File downloadedFile = new File(storageFileName);
                                holder.voice_time.setText(FloatDecorator.getFloatStr(HissMedia.getMusicTime(downloadedFile)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                protected void error(BaseDownloadTask task, Throwable e) {
                    Log.e(TAG, "音频下载失败..." + e.getMessage());
                }
            });
        }
        ViewGroup.LayoutParams lParams = holder.voice_image.getLayoutParams();
        lParams.width = (int) (mMinItemWith + mMaxItemWith / 60f * 10);//tbub.getUserVoiceTime());
        holder.voice_image.setLayoutParams(lParams);
    }

    /**
     * 传入视频
     *
     * @param holder
     * @param tbub
     * @param position
     */
    private void fromVideoUserLayout(final FromUserVideoViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetChatGroupMembers member = HissDbManager.getMemberByMessage(tbub);
        if (CacheData.isGroupChat()) {
            PicDecorator.glideRoundCornerPic(context, PicDecorator.getMemberDefaultErrorPic(member), holder.headicon, member != null ? member.getImgUrl() : "");
            String name = UNKOWN_NAME;
            if (member != null) {
                name = member.getRealName();
            }
            holder.fromName.setVisibility(View.VISIBLE);
            holder.fromName.setText(name);
            setupIvAction(holder.headicon, tbub.getUserId());
        } else {
            holder.headicon.setVisibility(View.GONE);
        }
        timeShower(holder, tbub, position);
        final String videoUrl = tbub.getContent();
        final int res = R.drawable.chatfrom_video_bg_focused;
        final String thumbName = HissFileService.getOssFileName(videoUrl).replace(".", "") + ".png";
        File f = new File(HissThumbnail.getVideoThumbDir(), thumbName);

        if (f.exists()) {
            Log.e(TAG, "缩略图" + thumbName + "已存在，直接显示");
            Glide.with(context).load(f).error(R.drawable.hiss_video_sign).dontAnimate().transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        } else {
//            Glide.with(context).load(R.drawable.hiss_video_sign).placeholder(R.drawable.hiss_video_sign).error(R.drawable.hiss_video_sign).dontAnimate().transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    Log.e(TAG, "!@#$$$$$$$$$$$ 获得缩略图 start time = " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
                    File f = HissThumbnail.videoThmbnailFile(context, videoUrl, thumbName);
                    Log.e(TAG, "!@#$$$$$$$$$$$ 获得缩略图 end time = " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
                    return f;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    try {
                        Log.e(TAG, "!@#$$$$$$$$$$$ 显示缩略图 at time = " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
                        Glide.with(context).load((File) o).error(R.drawable.hiss_video_sign).dontAnimate().transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }

        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopPlayVoice();
                showVideo(videoUrl, tbub.getDbId() + "");
            }

        });
        FontHelper.injectFont(holder.signTv);
    }

    /**
     * 传入位置
     *
     * @param holder
     * @param tbub
     * @param position
     */
    private void fromPositionUserLayout(final FromUserPositionViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetChatGroupMembers member = HissDbManager.getMemberByMessage(tbub);
        if (CacheData.isGroupChat()) {
            PicDecorator.glideRoundCornerPic(context, PicDecorator.getMemberDefaultErrorPic(member), holder.headicon, member != null ? member.getImgUrl() : "");
            String name = UNKOWN_NAME;
            if (member != null) {
                name = member.getRealName();
            }
            holder.fromName.setVisibility(View.VISIBLE);
            holder.fromName.setText(name);
            setupIvAction(holder.headicon, tbub.getUserId());
        } else {
            holder.headicon.setVisibility(View.GONE);
        }
        timeShower(holder, tbub, position);
        final String positionPoint = tbub.getContent();
        int res;
        res = R.drawable.chatfrom_bg_focused;
        Glide.with(context).load(R.drawable.hiss_gaode_map).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopPlayVoice();
//                Toast.makeText(context, "显示地图地点:" + positionPoint, Toast.LENGTH_SHORT).show();
                Intent mapActivity = new Intent(context, AddressMapActivity.class);
                HissAmapPosition position = HissAmapPosition.fromImStr(positionPoint);
                mapActivity.putExtra(AddressMapActivity.POSITION_OBJECT_KEY, position);
                mapActivity.putExtra(AddressMapActivity.POSITION_PURPOSE_KEY, AddressMapActivity.SHOW_POSITION);
                context.startActivity(mapActivity);
            }

        });
    }

    /**
     * 传出TXT信息
     *
     * @param holder
     * @param tbub
     * @param position
     */
    private void toMsgUserLayout(final ToUserMsgViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetStudent myInfo = CacheData.getMyData();
        signShower(holder, tbub, position);
        timeShower(holder, tbub, position);
        holder.content.setVisibility(View.VISIBLE);
        holder.content.setText(tbub.getContent());
//        holder.content.setSpanText(handler, tbub.getContent(), isGif);
    }

    private void toImgUserLayout(final ToUserImgViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetStudent myInfo = CacheData.getMyData();
        signShower(holder, tbub, position);
        timeShower(holder, tbub, position);
        holder.headicon.setImageDrawable(context.getResources().getDrawable(R.mipmap.grzx_tx_s));
        holder.image_group.setVisibility(View.VISIBLE);
        final String imageSrc = tbub.getContent();
        int res;
        res = R.drawable.chatto_bg_focused;
        Glide.with(context).load(imageSrc).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopPlayVoice();
                Intent intent = new Intent(context, ImageViewActivity.class);
                intent.putStringArrayListExtra("images", imageList);
                intent.putExtra("clickedIndex", getPicIndexByContent(tbub));
                context.startActivity(intent);
            }

        });
    }

    private void toVoiceUserLayout(final ToUserVoiceViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetStudent myInfo = CacheData.getMyData();
        signShower(holder, tbub, position);
        timeShower(holder, tbub, position);
        holder.headicon.setImageDrawable(context.getResources().getDrawable(R.mipmap.grzx_tx_s));
        holder.voice_group.setVisibility(View.VISIBLE);
        if (holder.receiver_voice_unread != null)
            holder.receiver_voice_unread.setVisibility(View.GONE);
        if (holder.receiver_voice_unread != null && unReadPosition != null) {
            for (String unRead : unReadPosition) {
                if (unRead.equals(position + "")) {
                    holder.receiver_voice_unread.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        AnimationDrawable drawable;
        holder.voice_anim.setId(position);
        if (position == voicePlayPosition) {
            //            holder.voice_anim.setBackgroundResource(R.mipmap.adj);
            holder.voice_anim.setBackgroundResource(R.drawable.voice_play_send);
            drawable = (AnimationDrawable) holder.voice_anim.getBackground();
            drawable.start();
        } else {
            holder.voice_anim.setBackgroundResource(R.mipmap.adj);
        }
        holder.voice_group.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (holder.receiver_voice_unread != null)
                    holder.receiver_voice_unread.setVisibility(View.GONE);
                holder.voice_anim.setBackgroundResource(R.mipmap.adj);

                int currentPosition = holder.voice_anim.getId();
                if (currentPosition == voicePlayPosition) {
                    if (MediaManager.isPause()) {
                        //如果已被停止，则恢复播放
                        resumePlayVoice(holder);
                        AnimationDrawable drawable;
                        holder.voice_anim.setBackgroundResource(R.drawable.voice_play_send);
                        drawable = (AnimationDrawable) holder.voice_anim.getBackground();
                        drawable.start();
                    } else {
                        //如果正在播放，则停止
                        stopPlayVoice();
                    }
                    return;
                } else {
                    //再次执行下停止，主要目的是停止其他音频控件的动画
                    stopPlayVoice();
                }

                voicePlayPosition = holder.voice_anim.getId();
                AnimationDrawable drawable;
                holder.voice_anim.setBackgroundResource(R.drawable.voice_play_send);
                drawable = (AnimationDrawable) holder.voice_anim.getBackground();
                drawable.start();
                String voicePath = tbub.getContent();
                if (voiceIsRead != null) {
                    voiceIsRead.voiceOnClick(position);
                }
                MediaManager.playSound(voicePath, new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        voicePlayPosition = -1;
                        holder.voice_anim.setBackgroundResource(R.mipmap.adj);
                    }
                });
            }
        });

        Log.e(TAG, "VOICE POSITION = " + position);
        holder.voice_time.setText("");
        final String storageFileName = AudioManager.getAudioSavePath() + File.separator + HissFileService.getOssFileName(tbub.getContent());
        File mp3File = new File(storageFileName);
        if (mp3File.exists()) {
            try {
                holder.voice_time.setText(FloatDecorator.getFloatStr(HissMedia.getMusicTime(mp3File)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            HissFileDownloader.startDownload(tbub.getContent(), storageFileName, new HissFileDownloader.DownloadListener() {
                @Override
                protected void completed(BaseDownloadTask task) {
                    Log.e(TAG, "音频下载成功！！！");
                    ((FragmentActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                File downloadedFile = new File(storageFileName);
                                holder.voice_time.setText(FloatDecorator.getFloatStr(HissMedia.getMusicTime(downloadedFile)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                protected void error(BaseDownloadTask task, Throwable e) {
                    Log.e(TAG, "音频下载失败..." + e.getMessage());
                }
            });
        }
        ViewGroup.LayoutParams lParams = holder.voice_image.getLayoutParams();
        lParams.width = (int) (mMinItemWith + mMaxItemWith / 60f * 10);//tbub.getUserVoiceTime());
        holder.voice_image.setLayoutParams(lParams);
    }

    /**
     * 传出视频
     *
     * @param holder
     * @param tbub
     * @param position
     */
    private void toVideoUserLayout(final ToUserVideoViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetStudent myInfo = CacheData.getMyData();
        signShower(holder, tbub, position);
        timeShower(holder, tbub, position);
        holder.headicon.setImageDrawable(context.getResources().getDrawable(R.mipmap.grzx_tx_s));
        final String videoUrl = tbub.getContent();
        final int res = R.drawable.chatto_video_bg_focused;
        final String thumbName = HissFileService.getOssFileName(videoUrl).replace(".", "") + ".png";
        File f = new File(HissThumbnail.getVideoThumbDir(), thumbName);

        if (f.exists()) {
            Log.e(TAG, "缩略图" + thumbName + "已存在，直接显示");
            Glide.with(context).load(f).error(R.drawable.hiss_video_sign).dontAnimate().transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        } else {
//            Glide.with(context).load(R.drawable.hiss_video_sign).placeholder(R.drawable.hiss_video_sign).error(R.drawable.hiss_video_sign).dontAnimate().transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    Log.e(TAG, "!@#$$$$$$$$$$$ 获得缩略图 start time = " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
                    File f = HissThumbnail.videoThmbnailFile(context, videoUrl, thumbName);
                    Log.e(TAG, "!@#$$$$$$$$$$$ 获得缩略图 end time = " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
                    return f;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    try {
                        Log.e(TAG, "!@#$$$$$$$$$$$ 显示缩略图 at time = " + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
                        if (o == null) {
                            Glide.with(context).load(R.drawable.hiss_video_sign).dontAnimate().transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
                        } else {
                            Glide.with(context).load((File) o).error(R.drawable.hiss_video_sign).dontAnimate().transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }

        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopPlayVoice();
                showVideo(videoUrl, tbub.getDbId() + "");
            }

        });
        FontHelper.injectFont(holder.signTv);
    }

    /**
     * 传出位置
     *
     * @param holder
     * @param tbub
     * @param position
     */
    private void toPositionUserLayout(final ToUserPositionViewHolder holder, final DbChatMessageBean tbub, final int position) {
        DbGetStudent myInfo = CacheData.getMyData();
        signShower(holder, tbub, position);
        timeShower(holder, tbub, position);
        holder.image_group.setVisibility(View.VISIBLE);
        final String positionPoint = tbub.getContent();
        int res;
        res = R.drawable.chatto_bg_focused;
        Glide.with(context).load(R.drawable.hiss_gaode_map).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopPlayVoice();
//                Toast.makeText(context, "显示地图地点:" + positionPoint, Toast.LENGTH_SHORT).show();
                Intent mapActivity = new Intent(context, AddressMapActivity.class);
                HissAmapPosition position = HissAmapPosition.fromImStr(positionPoint);
                mapActivity.putExtra(AddressMapActivity.POSITION_OBJECT_KEY, position);
                mapActivity.putExtra(AddressMapActivity.POSITION_PURPOSE_KEY, AddressMapActivity.SHOW_POSITION);
                context.startActivity(mapActivity);
            }

        });
    }

    /*******************************************************************************************************  辅助方法  **************************************************************************************************/
    /**
     * 是否显示时间的逻辑
     *
     * @param time
     * @param before
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public String getTime(String time, String before) {
        String show_time = null;
        if (before != null) {
            try {
                DateFormat df = new SimpleDateFormat(HissDate.DATE_FORMAT_YMDHMS_DEFAULT);
                long nowLong, dateLong;
                if (time.contains(":")) {
                    nowLong = df.parse(time).getTime();
                } else {
                    nowLong = Long.valueOf(time);
                }
                if (before.contains(":")) {
                    dateLong = df.parse(before).getTime();
                } else {
                    dateLong = Long.valueOf(before);
                }
                long l = nowLong - dateLong;
                long day = l / (24 * 60 * 60 * 1000);
                long hour = (l / (60 * 60 * 1000) - day * 24);
                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                if (min >= TIME_SHOW_EXTEND) {
                    show_time = time.contains(":") ? time.substring(11) : HissDate.getFormatedDateByLong(Long.valueOf(time), HissDate.DATE_FORMAT_YMDHMS_DEFAULT, TimeZone.getDefault()).substring(11);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            show_time = time.contains(":") ? time.substring(11) : HissDate.getFormatedDateByLong(Long.valueOf(time), HissDate.DATE_FORMAT_YMDHMS_DEFAULT, TimeZone.getDefault()).substring(11);
        }
        String getDay = getDay(time);
        if (show_time != null && getDay != null)
            show_time = getDay + " " + show_time;
        return show_time;
    }

    @SuppressLint("SimpleDateFormat")
    public static String returnTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDay(String time) {
        String showDay = null;
        String nowTime = returnTime();
        try {
            DateFormat df = new SimpleDateFormat(HissDate.DATE_FORMAT_YMDHMS_DEFAULT);
            if (time.contains(":")) {
                java.util.Date now = df.parse(nowTime);
                java.util.Date date = df.parse(time);
                long l = now.getTime() - date.getTime();
                long day = l / (24 * 60 * 60 * 1000);
                if (day >= 365) {
                    showDay = time.substring(0, 10);
                } else if (day >= 1 && day < 365) {
                    showDay = time.substring(5, 10);
                }
            } else {
                java.util.Date now = df.parse(nowTime);
                long l = now.getTime() - Long.valueOf(time);
                long day = l / (24 * 60 * 60 * 1000);
                if (day >= 365) {
                    showDay = time.substring(0, 10);
                } else if (day >= 1 && day < 365) {
                    showDay = time.substring(5, 10);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showDay;
    }

    /**
     * 点击image,position,voice,video的时候，都会执行此方法，有4*2 = 8处<br>
     * 另外，开始录音是也会调用此方法
     */
    public void stopPlayVoice() {
        if (voicePlayPosition != -1) {
            View voicePlay = (View) ((Activity) context).findViewById(voicePlayPosition);
            if (voicePlay != null) {
                //                if (getItemViewType(voicePlayPosition) == Integer.valueOf(PP2PMsg.VOICE) && userList.get(voicePlayPosition).getTrans().equals(PP2PMsg.TRANS_IN)) {
                if (userList.get(voicePlayPosition).getTrans().equals(PP2PMsg.TRANS_IN)) {
                    voicePlay.setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
                } else {
                    voicePlay.setBackgroundResource(R.mipmap.adj);
                }
            }
            MediaManager.pause();
            //            voicePlayPosition = -1;
        }
    }

    public void resumePlayVoice(RecyclerView.ViewHolder holder) {
        AnimationDrawable drawable;
        if (holder instanceof ToUserVoiceViewHolder) {
            ToUserVoiceViewHolder toUserVoiceViewHolder = (ToUserVoiceViewHolder) holder;
            toUserVoiceViewHolder.voice_anim.setBackgroundResource(R.drawable.voice_play_send);
            drawable = (AnimationDrawable) toUserVoiceViewHolder.voice_anim.getBackground();
            drawable.start();
        } else if (holder instanceof FromUserVoiceViewHolder) {
            FromUserVoiceViewHolder fromUserVoiceViewHolder = (FromUserVoiceViewHolder) holder;
            fromUserVoiceViewHolder.voice_anim.setBackgroundResource(R.drawable.voice_play_receiver);
            drawable = (AnimationDrawable) fromUserVoiceViewHolder.voice_anim.getBackground();
            drawable.start();
        }
        View voicePlay = (View) ((Activity) context).findViewById(voicePlayPosition);
        if (voicePlay != null) {
            if (getItemViewType(voicePlayPosition) == Integer.valueOf(PP2PMsg.VOICE) && userList.get(voicePlayPosition).getTrans().equals(PP2PMsg.TRANS_IN)) {
                voicePlay.setBackgroundResource(R.mipmap.receiver_voice_node_playing003);
            } else {
                voicePlay.setBackgroundResource(R.mipmap.adj);
            }
        }
        MediaManager.resume();
    }

    private void showVideo(String url, String dbid) {
        Intent intent = new Intent(context, LocalVideoActivity.class);
        intent.putExtra(LocalVideoActivity.KEY_VIDEO_PATH, url);
        intent.putExtra(LocalVideoActivity.KEY_VIDEO_DBID, dbid);
        Log.e(TAG, "video url = " + url);
        context.startActivity(intent);
    }

    private void sendBroadcast2showInfo(Context con, String id, boolean isStudent) {
        TBroadcast.sendLocalParcelableBc(con, TBroadcast.BROADCAST_FRAGMENT_AFTER_CHAT_ACTIVITY, TBroadcast.KEY_INFO_ID, isStudent ? new InfoBrData(id, null) : new InfoBrData(null, id));
    }

    private void setupIvAction(ImageView iv, final String userId) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                if (now - BaseActivity.infoClickTime > 1000 * 2) {
                    BaseActivity.infoClickTime = now;
                    //每两秒只允许点击头像一次，给广播充分的时间
                    if (!Schecker.isStringNull(userId)) {
                        sendBroadcast2showInfo(context, userId, true);
                    } else {
                        Toast.makeText(context, "用户信息无效", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
