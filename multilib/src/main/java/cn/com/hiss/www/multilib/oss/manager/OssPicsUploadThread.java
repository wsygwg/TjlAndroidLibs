package cn.com.hiss.www.multilib.oss.manager;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import cn.com.hiss.www.multilib.oss.PutObjectSamples;

import java.io.File;
import java.util.ArrayList;

import cn.com.hiss.www.multilib.utils.HissFileService;
import cn.com.hiss.www.sharephoto.util.ImageItem;

/**
 * Created by junliang on 2017/2/28.
 */

public class OssPicsUploadThread extends Thread {
    private static final String TAG = OssPicsUploadThread.class.getSimpleName();
    ArrayList<ImageItem> pics;
    Context con;
    OnUploadImageFinished listener;

    public OssPicsUploadThread(Context con, ArrayList<ImageItem> pics, OnUploadImageFinished listener) {
        this.con = con;
        this.pics = pics;
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();
        try {
            if (pics == null || pics.size() == 0) {
                return;
            }
            int i = 0;
            int successNum = 0;
            for (ImageItem pic : pics) {
                i++;
                long start = System.currentTimeMillis();
                Log.e(TAG, "Picture number " + i + " : startTime = " + System.currentTimeMillis());
                String imageName = HissFileService.getHissSportImageName(pic.getImagePath());
                PutObjectResult result = new PutObjectSamples(OssSetting.getOss(con), OssSetting.bucketNameImage, imageName, pic.getImagePath()).putObjectFromLocalFile();
                if(result != null){
                    Log.e(TAG,"图片" + i + "上传成功~~~~~");
                    successNum++;
                    pic.setOssUrl(OssSetting.protocol_bucket_endpoint_image + File.separator + imageName);
                }
                long end = System.currentTimeMillis();
                Log.e(TAG, "Picture number " + i + " : endTime = " + end);
                Log.e(TAG, (end - start) + " ms is taken.");
                Log.e(TAG,"IMAGE URL = " + pic.getOssUrl());
            }
            if(listener != null){
                if(successNum == pics.size()){
                    listener.onSuccess(pics);
                }else{
                    listener.onFailed(pics);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnUploadImageFinished{
        void onSuccess(ArrayList<ImageItem> items);
        void onFailed(ArrayList<ImageItem> items);
    }

}
