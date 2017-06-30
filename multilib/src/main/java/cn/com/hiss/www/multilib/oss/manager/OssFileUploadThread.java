package cn.com.hiss.www.multilib.oss.manager;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import cn.com.hiss.www.multilib.oss.PutObjectSamples;
import cn.com.hiss.www.multilib.utils.CacheData;
import cn.com.hiss.www.multilib.utils.HissFileService;
import cn.com.hiss.www.multilib.utils.ImageCompress;
import cn.com.hiss.www.multilib.utils.PicCrop;
import cn.com.hiss.www.multilib.utils.PicPath;
import cn.com.hiss.www.sharephoto.util.Bimp;
import cn.com.hiss.www.sharephoto.util.ImageItem;

/**
 * Created by junliang on 2017/2/28.
 */

public class OssFileUploadThread extends Thread {
    private static final String TAG = OssFileUploadThread.class.getSimpleName();
    ArrayList<OssFile> ossFiles;
    Context con;
    OnUploadFilesFinished listener;

    public OssFileUploadThread(Context con, ArrayList<OssFile> ossFiles, OnUploadFilesFinished listener) {
        this.con = con;
        this.ossFiles = ossFiles;
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();
        try {
            if (ossFiles == null || ossFiles.size() == 0) {
                return;
            }
            int successNum = 0;
            for (int i = 0 ; i < ossFiles.size() ; i++) {
                OssFile ossFile = ossFiles.get(i);
                long start = System.currentTimeMillis();
                Log.e(TAG, "File number " + i + " : startTime = " + System.currentTimeMillis());
                String filePath = ossFile.getFilePath();
                String fileName = HissFileService.getHissSportOssFileName(ossFile.getFilePath());
                if(ossFile.getType() == OssSetting.HissResType.IMAGE){
                    if(!filePath.contains(PicCrop.CROP_PATH)){
                        Uri uri = Uri.fromFile(new File(filePath));
                        filePath = ImageCompress.dealAlbum(con,uri).getAbsolutePath();
                    }
                    fileName = CacheData.getMyData().getMemberId() + File.separator + Bimp.currentPurpose.getValue() + File.separator + HissFileService.getHissSportImageOssFileName(filePath);
                }

                String bucketName = OssSetting.bucketNameImage;
                String protocol_bucket_endpoint = OssSetting.protocol_bucket_endpoint_image + File.separator + fileName;
                if(ossFile.getType() == OssSetting.HissResType.IMAGE){

                }else if(ossFile.getType() == OssSetting.HissResType.AUDIO){
                    bucketName = OssSetting.bucketNameAudio;
                    protocol_bucket_endpoint = OssSetting.protocol_bucket_endpoint_audio + File.separator + fileName;
                }else if(ossFile.getType() == OssSetting.HissResType.VIDEO){
                    bucketName = OssSetting.bucketNameVideo;
                    protocol_bucket_endpoint = OssSetting.protocol_bucket_endpoint_video + File.separator + fileName;
                }
                PutObjectResult result = new PutObjectSamples(OssSetting.getOss(con), bucketName, fileName, filePath).putObjectFromLocalFile();
                if(result != null){
                    Log.e(TAG,"文件" + i + "上传成功~~~~~");
                    successNum++;
                    ossFile.setOssPath(protocol_bucket_endpoint);
                }
                long end = System.currentTimeMillis();
                Log.e(TAG, "File number " + i + " : endTime = " + end);
                Log.e(TAG, (end - start) + " ms is taken.");
                Log.e(TAG,"OSS FILE URL = " + ossFile.getOssPath());
            }
            if(listener != null){
                if(successNum == ossFiles.size()){
                    listener.onSuccess(ossFiles);
                }else{
                    listener.onFailed(ossFiles);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnUploadFilesFinished{
        void onSuccess(ArrayList<OssFile> ossFiles);
        void onFailed(ArrayList<OssFile> ossFiles);
    }

}
