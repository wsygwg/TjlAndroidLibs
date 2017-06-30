package com.maxi.chatdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxi.chatdemo.R;

import cn.com.hiss.www.multilib.utils.FontHelper;

public class ChatBottomView extends LinearLayout {
    private View baseView;

    private TextView cameraTv;
    private TextView videoTv;
    private TextView imageTv;
    private TextView positionTv;

    private TextView phraseTv;
    private TextView collectionTv;
    private TextView more2Tv;
    private TextView more3Tv;
    private HeadIconSelectorView.OnHeadIconClickListener onHeadIconClickListener;
    public static final int FROM_CAMERA = 1;
    public static final int FROM_GALLERY = 2;
    public static final int FROM_POSITION = 3;
    public static final int FROM_COLLECTION = 4;
    public static final int FROM_PHRASE = 5;
    public static final int FROM_CAMERA_VIDEO = 6;
    public static final int FROM_MORE1 = 7;
    public static final int FROM_MORE2 = 8;

    public ChatBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        findView();
        init();
    }

    private void findView() {
        baseView = LayoutInflater.from(getContext()).inflate(R.layout.chat_layout_tongbaobottom, this);
        cameraTv = (TextView) baseView.findViewById(R.id.camera_group);
        videoTv = (TextView) baseView.findViewById(R.id.camera_group_video);
        imageTv = (TextView) baseView.findViewById(R.id.image_bottom_group);
        positionTv = (TextView) baseView.findViewById(R.id.position_group);

        phraseTv = (TextView) baseView.findViewById(R.id.phrase_group);
        collectionTv = (TextView) baseView.findViewById(R.id.collection_group);
        more2Tv = (TextView) baseView.findViewById(R.id.more2_group);
        more3Tv = (TextView) baseView.findViewById(R.id.more3_group);
        FontHelper.injectFont(cameraTv);
        FontHelper.injectFont(videoTv);
        FontHelper.injectFont(imageTv);
        FontHelper.injectFont(positionTv);
        FontHelper.injectFont(phraseTv);
        FontHelper.injectFont(collectionTv);
        FontHelper.injectFont(more2Tv);
        FontHelper.injectFont(more3Tv);
    }

    private void init() {
        cameraTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_CAMERA);
                }
            }
        });
        imageTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_GALLERY);
                }
            }
        });
        positionTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_POSITION);
                }
            }
        });
        collectionTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_COLLECTION);
                }
            }
        });
        phraseTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_PHRASE);
                }
            }
        });
        videoTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_CAMERA_VIDEO);
                }
            }
        });
        more2Tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_MORE1);
                }
            }
        });
        more3Tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (null != onHeadIconClickListener) {
                    onHeadIconClickListener.onClick(FROM_MORE2);
                }
            }
        });
    }

    public void setOnHeadIconClickListener(HeadIconSelectorView.OnHeadIconClickListener onHeadIconClickListener) {
        this.onHeadIconClickListener = onHeadIconClickListener;
    }
}
