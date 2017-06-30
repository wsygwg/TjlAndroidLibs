package com.maxi.chatdemo.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.maxi.chatdemo.R;
import com.maxi.chatdemo.adapter.DataAdapter;
import com.maxi.chatdemo.adapter.ExpressionAdapter;
import com.maxi.chatdemo.adapter.ExpressionPagerAdapter;
import com.maxi.chatdemo.common.ChatConst;
import com.maxi.chatdemo.utils.AudioManager;
import com.maxi.chatdemo.utils.FileSaveUtil;
import com.maxi.chatdemo.utils.ScreenUtil;
import com.maxi.chatdemo.utils.SmileUtils;
import com.maxi.chatdemo.widget.AudioRecordButton;
import com.maxi.chatdemo.widget.ChatBottomView;
import com.maxi.chatdemo.widget.ExpandGridView;
import com.maxi.chatdemo.widget.GifEditText;
import com.maxi.chatdemo.widget.HeadIconSelectorView;
import com.maxi.chatdemo.widget.MediaManager;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.hiss.www.multilib.HissAmapPosition;
import cn.com.hiss.www.multilib.db.ChatDbManager;
import cn.com.hiss.www.multilib.db.DbChatMessageBean;
import cn.com.hiss.www.multilib.db.DbGetChatGroups;
import cn.com.hiss.www.multilib.hissviews.HissTitleBar;
import cn.com.hiss.www.multilib.hissviews.HissTitleBar4ActionBar;
import cn.com.hiss.www.multilib.oss.manager.OssSetting;
import cn.com.hiss.www.multilib.ui.AddressMapActivity;
import cn.com.hiss.www.multilib.ui.HissCameraActivity;
import cn.com.hiss.www.multilib.utils.CacheData;
import cn.com.hiss.www.multilib.utils.FontHelper;
import cn.com.hiss.www.multilib.utils.ImageCompress;
import cn.com.hiss.www.multilib.utils.InfoBrData;
import cn.com.hiss.www.multilib.utils.KeyBoardUtils;
import cn.com.hiss.www.multilib.utils.PicCrop;
import cn.com.hiss.www.multilib.utils.PicDecorator;
import cn.com.hiss.www.multilib.utils.PicPath;
import cn.com.hiss.www.multilib.utils.TBroadcast;
import cn.com.hiss.www.sharephoto.util.Bimp;
import cn.com.hiss.www.sharephoto.util.ImageItem;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

/**
 * Created by Mao Jiqing on 2016/10/20.
 */
public abstract class BaseActivity extends AppCompatActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private static final int SDK_PERMISSION_REQUEST = 127;
    private static final int IMAGE_SIZE = 100 * 1024;// 300kb
    public static final int SEND_OK = 0x1110;
    public static final int REFRESH = 0x0001;
    public static final int RECERIVE_OK = 0x1111;
    public static final int PULL_TO_REFRESH_DOWN = 0x0111;
    public static final int GO2LAST_ITEM = 0x0011;
    public static final int REMOVE_OK = 0x11110011;
    public RecyclerView recyclerView;
    public PullRefreshLayout pullList;
    private HissTitleBar4ActionBar titleBar;
    //文字录入
    public EmojiconEditText mEditTextContent;
    //显示录音控件
    public TextView voiceTv;
    //录音控件
    public AudioRecordButton voiceBtn;
    //显示表情控件
    public TextView emoji;
    //表情发送按钮
    //    public TextView send_emoji_icon;
    //表情控件布局
    public LinearLayout emoji_group;
    //    public ViewPager expressionViewpager;
    //显示更多控件
    public TextView mess_tv;
    //更多控件布局
    public ChatBottomView tbbv;
    //常用语
    public ListView mess_lv;
    //常用语列表adapter
    private DataAdapter adapter;
    public String item[] = {"你好!", "我正忙着呢,等等", "有啥事吗？", "有时间聊聊吗", "再见！"};
    //聊天页面根LAYOUT
    public View activityRootView;
    public List<DbChatMessageBean> tblist = new ArrayList<DbChatMessageBean>();
    public List<DbChatMessageBean> pagelist = new ArrayList<DbChatMessageBean>();
    public ArrayList<String> imageList = new ArrayList<String>();//adapter图片数据
    //    private List<String> reslist;
    private File mCurrentPhotoFile;
    private Toast mToast;
    public String userName = "test";//聊天对象昵称
    private String permissionInfo;
    //    private String camPicPath;
    public int page = 0;
    //    public int totalChatBeanNum = 0;
    public int number = 10; //每页记录的条数
    public boolean isDown = false;
    private boolean CAN_WRITE_EXTERNAL_STORAGE = true;
    private boolean CAN_RECORD_AUDIO = true;
    public int position; //加载滚动刷新位置
    public int bottomStatusHeight = 0;
    public int listSlideHeight = 0;//滑动距离

    public ChatDbManager mChatDbManager;

    private HissAmapPosition hissPosition;

    public static long infoClickTime = 0;

    /**
     * 发送文本消息
     */
    protected abstract void sendTxtMessage(String txt, String type);


    /**
     * 发送图片文件
     *
     * @param filePath
     */
    protected abstract void sendFile(String filePath, OssSetting.HissResType type);

    protected abstract void loadRecords();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_chat);
        try {
//            View v = getWindow().getDecorView();
//            FontHelper.injectFont(v);
            findView();
            initTitleBar();
            initpop();
            init();
            //            getPersimmions();
            setEmojiconFragment(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTitleBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        titleBar = new HissTitleBar4ActionBar(this, null);
        //        titleBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(titleBar, layoutParams);
        Toolbar parent = (Toolbar) titleBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        titleBar.backImage.setImageResource(R.drawable.pic_login_titlebar_back_cross);
        titleBar.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView ivOption = titleBar.showImageOption();
        ivOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                if (now - BaseActivity.infoClickTime > 1000 * 2) {
                    infoClickTime = now;
                    //每两秒只允许点击头像一次，给广播充分的时间
                    if (CacheData.isGroupChat()) {
                        sendBroadcast2showInfo(BaseActivity.this, CacheData.getToGroup().getGroupId(), false);
                    } else {
                        sendBroadcast2showInfo(BaseActivity.this, CacheData.getToUser().getMemberId(), true);
                    }
                }
            }
        });
        String titleText = "";
        try {
            if (CacheData.isGroupChat()) {
                DbGetChatGroups group = CacheData.getToGroup();
                titleText = group.getGroupName();
                PicDecorator.glideRoundCornerPic(this, R.drawable.hiss_pic_placeholder, ivOption, group.getPicUrl());
            } else {
                titleText = CacheData.getToUser().getRealName();
                PicDecorator.glideRoundCornerPic(this, PicDecorator.getMemberDefaultErrorPic(CacheData.getToUser().getSex()), ivOption, CacheData.getToUser().getStuImage().getHeadUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleBar.title.setText(titleText);
    }

    private void sendBroadcast2showInfo(Context con, String id, boolean isStudent) {
        TBroadcast.sendLocalParcelableBc(con, TBroadcast.BROADCAST_FRAGMENT_AFTER_CHAT_ACTIVITY, TBroadcast.KEY_INFO_ID, isStudent ? new InfoBrData(id, null) : new InfoBrData(null, id));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void findView() {
        recyclerView = (RecyclerView) findViewById(R.id.id_hiss_recyclerView);
        pullList = (PullRefreshLayout) findViewById(R.id.content_lv);
        activityRootView = findViewById(R.id.layout_tongbao_rl);
        mEditTextContent = (EmojiconEditText) findViewById(R.id.mess_et);

        voiceTv = (TextView) findViewById(R.id.voice_tv);
        emoji = (TextView) findViewById(R.id.emoji);
        mess_tv = (TextView) findViewById(R.id.mess_tv);
        //        expressionViewpager = (ViewPager) findViewById(R.id.vPager);
        voiceBtn = (AudioRecordButton) findViewById(R.id.voice_btn);
        //        voiceBtn.setSaveDir(FileSaveUtil.FILE_BASE_PATH + CacheData.getMyData().getMemberId() + FileSaveUtil.IM_FILE_PATH);
        emoji_group = (LinearLayout) findViewById(R.id.emoji_group);
        //        send_emoji_icon = (TextView) findViewById(R.id.send_emoji_icon);
        tbbv = (ChatBottomView) findViewById(R.id.other_lv);

        FontHelper.injectFont(voiceTv);
        FontHelper.injectFont(emoji);
        FontHelper.injectFont(mess_tv);
    }

    enum HissChatWidgetBtn {
        Voice, Emoji, Additional
    }

    /**
     * voiceTv
     * emoji
     * mess_tv
     *
     * @param widgetBtn
     */
    private void setWidgetColorState(HissChatWidgetBtn widgetBtn) {
        if (widgetBtn == HissChatWidgetBtn.Voice) {
            voiceTv.setTextColor(getResources().getColor(R.color.color_icon_red));
            emoji.setTextColor(getResources().getColor(R.color.color_icon_grey));
            mess_tv.setTextColor(getResources().getColor(R.color.color_icon_grey));
        } else if (widgetBtn == HissChatWidgetBtn.Emoji) {
            voiceTv.setTextColor(getResources().getColor(R.color.color_icon_grey));
            emoji.setTextColor(getResources().getColor(R.color.color_icon_red));
            mess_tv.setTextColor(getResources().getColor(R.color.color_icon_grey));
        } else if (widgetBtn == HissChatWidgetBtn.Additional) {
            voiceTv.setTextColor(getResources().getColor(R.color.color_icon_grey));
            emoji.setTextColor(getResources().getColor(R.color.color_icon_grey));
            mess_tv.setTextColor(getResources().getColor(R.color.color_icon_red));
        }
    }

    private void resetWigetColor() {
        voiceTv.setTextColor(getResources().getColor(R.color.color_icon_black));
        emoji.setTextColor(getResources().getColor(R.color.color_icon_black));
        mess_tv.setTextColor(getResources().getColor(R.color.color_icon_black));
    }

    private void hideBottomWigets() {
        voiceBtn.setVisibility(View.GONE);
        emoji_group.setVisibility(View.GONE);
        tbbv.setVisibility(View.GONE);
        mess_lv.setVisibility(View.GONE);
        mEditTextContent.setVisibility(View.VISIBLE);
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager().beginTransaction().replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault)).commit();
    }

    protected void init() {
        mEditTextContent.setOnKeyListener(onKeyListener);
        mChatDbManager = ChatDbManager.getInstance();
        pullList.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullList.post(new Runnable() {
                    @Override
                    public void run() {
                        loadRecords();
                        pullList.setRefreshing(false);
                    }
                });
            }
        });

        voiceTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                KeyBoardUtils.hideKeyBoard(BaseActivity.this, mEditTextContent);
                if (voiceBtn.getVisibility() == View.GONE) {
                    //如果录音控件没有显示，则显示录音控件，隐藏text控件
                    hideBottomWigets();
                    mEditTextContent.setVisibility(View.GONE);
                    voiceBtn.setVisibility(View.VISIBLE);
                    setWidgetColorState(HissChatWidgetBtn.Voice);
                } else {
                    //显示text控件
                    hideBottomWigets();
                    mEditTextContent.setVisibility(View.VISIBLE);
                    resetWigetColor();
                    //防止录音不止
                    AudioManager.cancelRecording();
                }
            }
        });
        emoji.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyBoard(BaseActivity.this, mEditTextContent);
                if (emoji_group.getVisibility() == View.GONE) {
                    //如果emoji页面没有显示，则显示emoji页面
                    hideBottomWigets();
                    mEditTextContent.setVisibility(View.VISIBLE);   //emoji页面有text输入显示
                    emoji_group.setVisibility(View.VISIBLE);
                    setWidgetColorState(HissChatWidgetBtn.Emoji);
                } else {
                    hideBottomWigets();
                    mEditTextContent.setVisibility(View.VISIBLE);   //emoji页面有text输入显示
                    emoji_group.setVisibility(View.GONE);
                    resetWigetColor();
                }
            }
        });
        //显示更多控件的按钮的点击
        mess_tv.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyBoard(BaseActivity.this, mEditTextContent);
                if (tbbv.getVisibility() == View.GONE && mess_lv.getVisibility() == View.GONE) {
                    //如果更多控件与常用语页面都没有在显示，则显示更多控件
                    //                    mess_tv.setFocusable(true);
                    hideBottomWigets();
                    mEditTextContent.setVisibility(View.VISIBLE);
                    tbbv.setVisibility(View.VISIBLE);
                    setWidgetColorState(HissChatWidgetBtn.Additional);
                } else {
                    //如果常用语界面正在显示中
                    hideBottomWigets();
                    mEditTextContent.setVisibility(View.VISIBLE);
                    resetWigetColor();
                }
            }
        });
        //更多控件的点击效果与页面展示
        tbbv.setOnHeadIconClickListener(new HeadIconSelectorView.OnHeadIconClickListener() {

            @SuppressLint("InlinedApi")
            @Override
            public void onClick(int from) {
                switch (from) {
                    case ChatBottomView.FROM_CAMERA:
                        //拍照
                        Bimp.currentPurpose = Bimp.PURPOSE.IM;
                        customPhoto();
                        break;
                    case ChatBottomView.FROM_CAMERA_VIDEO:
                        Intent cameraIntent = new Intent(BaseActivity.this, HissCameraActivity.class);
                        startActivityForResult(cameraIntent, HissCameraActivity.REQUEST_CODE_GET_VIDEO);
                        break;
                    case ChatBottomView.FROM_GALLERY:
                        //选取相册图片
                        Bimp.currentPurpose = Bimp.PURPOSE.IM;
                        PicCrop.cropFromGalleryNoCropAction(BaseActivity.this);
                        break;
                    case ChatBottomView.FROM_POSITION:
                        //开始定位
                        showToast("请稍等...");
                        Intent mapActivity = new Intent(BaseActivity.this, AddressMapActivity.class);
                        mapActivity.putExtra(AddressMapActivity.POSITION_PURPOSE_KEY, AddressMapActivity.POSITIONING);
                        startActivityForResult(mapActivity, AddressMapActivity.POSITIONING);
                        break;
                    case ChatBottomView.FROM_COLLECTION:
                        showToast("暂无收藏");
                        break;
                    case ChatBottomView.FROM_PHRASE:
                        hideBottomWigets();
                        mess_lv.setVisibility(View.VISIBLE);
                        break;
                    case ChatBottomView.FROM_MORE1:
                        showToast("更多1");
                        break;
                    case ChatBottomView.FROM_MORE2:
                        showToast("更多2");
                        break;
                }
            }
        });
        mEditTextContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                reset();
            }

        });
        mess_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mEditTextContent.setText(item[arg2]);
                sendTxtMessage(mEditTextContent.getText().toString(), OssSetting.HissTextType.TXT.getPp2pmsgType());
            }

        });
        bottomStatusHeight = ScreenUtil.getBottomStatusHeight(this);
        initListData();
    }

    public static final int REQUEST_SELECT_PICTURE = 0x01;
    private void selectPicFromGallery(Activity context){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    public void initListData() {
        Log.e(TAG, "###################initListData");
        String otherSideId = CacheData.getOtherSideId();
        if (tblist != null && tblist.size() > 0) {
            tblist.clear();
        }
        page = (int) mChatDbManager.getPages(number, CacheData.getMyData().getMemberId(), otherSideId);
        loadRecords();
    }

    public void customPhoto() {
        try {
            PicCrop.cropFromCameraNoCropAction(BaseActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示高德地图
     */
    private void showAliMap() {
        showToast("显示高德地图");
    }

    //    @TargetApi(23)
    //    protected void getPersimmions() {
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    //            ArrayList<String> permissions = new ArrayList<String>();
    //            // 读写权限
    //            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
    //                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
    //            }
    //            // 麦克风权限
    //            if (addPermission(permissions, Manifest.permission.RECORD_AUDIO)) {
    //                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
    //            }
    //            if (permissions.size() > 0) {
    //                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
    //            }
    //        }
    //    }
    //
    //    @TargetApi(23)
    //    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
    //        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
    //            if (shouldShowRequestPermissionRationale(permission)) {
    //                return true;
    //            } else {
    //                permissionsList.add(permission);
    //                return false;
    //            }
    //
    //        } else {
    //            return true;
    //        }
    //    }
    //
    //    @TargetApi(23)
    //    @Override
    //    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //        switch (requestCode) {
    //            case SDK_PERMISSION_REQUEST:
    //                Map<String, Integer> perms = new HashMap<String, Integer>();
    //                // Initial
    //                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
    //                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
    //                // Fill with results
    //                for (int i = 0; i < permissions.length; i++)
    //                    perms.put(permissions[i], grantResults[i]);
    //                // Check for ACCESS_FINE_LOCATION
    //                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
    //                    // Permission Denied
    //                    CAN_WRITE_EXTERNAL_STORAGE = false;
    //                    Toast.makeText(this, "禁用图片权限将导致发送图片功能无法使用！", Toast.LENGTH_SHORT).show();
    //                }
    //                if (perms.get(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
    //                    CAN_RECORD_AUDIO = false;
    //                    Toast.makeText(this, "禁用录制音频权限将导致语音功能无法使用！", Toast.LENGTH_SHORT).show();
    //                }
    //                break;
    //            default:
    //                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //        }
    //    }

    @Override
    protected void onDestroy() {
        MediaManager.pause();
        MediaManager.release();
        cancelToast();
        Bimp.clearAllTempStorages();
        super.onDestroy();
    }

    /**
     * 常用语列表初始化
     */
    @SuppressLint({"NewApi", "InflateParams"})
    private void initpop() {
        mess_lv = (ListView) findViewById(R.id.mess_lv);
        adapter = new DataAdapter(this, item);
        mess_lv.setAdapter(adapter);
    }

    /**
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * TODO:拍照，获得图片的返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == AddressMapActivity.POSITIONING) {
                Bundle bundle = data.getExtras();
                hissPosition = bundle.getParcelable(AddressMapActivity.POSITION_OBJECT_KEY);
                Log.e(TAG, "lat = " + hissPosition.getLatLonPoint().getLatitude() + " ; lon = " + hissPosition.getLatLonPoint().getLongitude());
                sendTxtMessage(HissAmapPosition.toImStr(hissPosition), OssSetting.HissTextType.POSITION.getPp2pmsgType());
            } else if (requestCode == HissCameraActivity.REQUEST_CODE_GET_VIDEO) {
                String videoPath = data.getStringExtra(HissCameraActivity.RESULT_PATH_VIDEO);
                if (!TextUtils.isEmpty(videoPath)) {
                    sendFile(videoPath, OssSetting.HissResType.VIDEO);
                }
            } else {
                PicCrop.onActivityResult(requestCode, resultCode, data, BaseActivity.this, Bimp.currentPurpose, CacheData.getMyData().getMemberId(), new PicCrop.CropHandler() {
                    @Override
                    public void handleCropResult(Uri uri, int tag) {
                        Log.e(TAG, "uri = " + uri + "tag = " + tag);
                        String path = PicPath.getImageAbsolutePath(BaseActivity.this, uri);
                        Log.e(TAG, "path = " + path);
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setImagePath(path);
                        Log.e(TAG, takePhoto.toString());
                        if (Bimp.TEMP_PIC_STORAGE().size() > 0) {
                            //只存一张群组图片
                            Bimp.TEMP_PIC_STORAGE().clear();
                        }
                        Bimp.TEMP_PIC_STORAGE().add(takePhoto);
                        sendFile(takePhoto.getImagePath(), OssSetting.HissResType.IMAGE);
                        showToast("图片上传开始");
                    }

                    @Override
                    public void handleCropError(Intent data) {
                        Log.e(TAG, "crop error");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 界面复位
     */
    protected void reset() {
        hideBottomWigets();
        resetWigetColor();
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * TODO:生成传输出去的BEAN，同时进行对话日志存储
     *
     * @param trans
     * @param type
     * @param Content
     * @param sendState
     * @return
     */
    public DbChatMessageBean getTbub(String trans, String type, String Content, @ChatConst.SendState int sendState) {
        DbChatMessageBean tbub = new DbChatMessageBean();
        String time = returnTime();
        tbub.setCreateDate(time);
        tbub.setTrans(trans);
        tbub.setType(type);
        tbub.setContent(Content);
        tbub.setSendState(sendState);
        tbub.setMyId(CacheData.getMyData().getMemberId());
        if (CacheData.getToGroup() != null) {
            tbub.setGroupId(CacheData.getToGroup().getGroupId());
        } else {
            tbub.setUserId(CacheData.getToUser().getMemberId());
            tbub.setGroupId("");
        }
        mChatDbManager.insert(tbub);
        return tbub;
    }

    public DbChatMessageBean getTbubNoStorage(String trans, String type, String Content, @ChatConst.SendState int sendState) {
        DbChatMessageBean tbub = new DbChatMessageBean();
        String time = returnTime();
        tbub.setCreateDate(time);
        tbub.setTrans(trans);
        tbub.setType(type);
        tbub.setContent(Content);
        tbub.setSendState(sendState);
        tbub.setMyId(CacheData.getMyData().getMemberId());
        if (CacheData.getToGroup() != null) {
            tbub.setGroupId(CacheData.getToGroup().getGroupId());
        } else {
            tbub.setUserId(CacheData.getToUser().getMemberId());
            tbub.setGroupId("");
        }
        //        mChatDbManager.insert(tbub);
        return tbub;
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                sendTxtMessage(mEditTextContent.getText().toString(), OssSetting.HissTextType.TXT.getPp2pmsgType());
                return true;
            }
            return false;
        }
    };

    @SuppressLint("SimpleDateFormat")
    public static String returnTime() {
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = sDateFormat.format(new java.util.Date());
//        return date;
        return System.currentTimeMillis() + "";
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditTextContent, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditTextContent);
    }
}
