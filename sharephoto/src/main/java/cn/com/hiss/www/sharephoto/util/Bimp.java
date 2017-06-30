package cn.com.hiss.www.sharephoto.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cn.com.hiss.www.sharephoto.activity.AlbumActivity;

public class Bimp {
//    public static int max = 0;
    public static PURPOSE currentPurpose = PURPOSE.SNS;

    /**
     * ICON,DESK唯一，用学生ID命名
     * SNS,SHOP用UUID命名
     */
    public enum PURPOSE {
        ICON("ICON"), DESK("DESK"), SNS("SNS"), SHOP("SHOP"),IM("IM");
        private String value;

        PURPOSE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static int ALBUM_MAX_PIC_NUMBER = 9;
    public static int INFO_MAX_PIC_NUMBER = 1;

    public static final int TAKE_PICTURE = 0x000001;
//    public static final int GET_PIC_FROM_ALBUM = 0x000002;

    public static int GET_MAX_PIC_NUMBER() {
        if(currentPurpose == PURPOSE.SNS){
            return ALBUM_MAX_PIC_NUMBER;
        }else if(currentPurpose == PURPOSE.ICON){
            return INFO_MAX_PIC_NUMBER;
        }else if(currentPurpose == PURPOSE.DESK){
            return INFO_MAX_PIC_NUMBER;
        }
        return ALBUM_MAX_PIC_NUMBER;
    }

    public static ArrayList<ImageItem> TEMP_PIC_STORAGE() {
        if(currentPurpose == PURPOSE.SNS){
            return tempSelectBitmap;
        }else if(currentPurpose == PURPOSE.ICON){
            return avatar;
        }else if(currentPurpose == PURPOSE.DESK){
            return bgPic;
        }else if(currentPurpose == PURPOSE.IM){
            return imPic;
        }else if(currentPurpose == PURPOSE.SHOP){
            return shopPic;
        }
        return tempSelectBitmap;
    }

    public static void clearAllTempStorages(){
        try{
            if (Bimp.tempSelectBitmap != null) {
                Bimp.tempSelectBitmap.clear();
            }
            if (Bimp.avatar != null) {
                Bimp.avatar.clear();
            }
            if (Bimp.bgPic != null) {
                Bimp.bgPic.clear();
            }
            if (Bimp.snsPic != null) {
                Bimp.snsPic.clear();
            }
            if (Bimp.imPic != null) {
                Bimp.imPic.clear();
            }
            if (Bimp.shopPic != null) {
                Bimp.shopPic.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //选择的图片的临时列表
    public static ArrayList<ImageItem> avatar = new ArrayList<ImageItem>();   //学生头像图片
    public static ArrayList<ImageItem> bgPic = new ArrayList<ImageItem>();   //学生背景图片
    public static ArrayList<ImageItem> snsPic = new ArrayList<ImageItem>();   //学生背景图片
    public static ArrayList<ImageItem> imPic = new ArrayList<ImageItem>();   //IM
    public static ArrayList<ImageItem> shopPic = new ArrayList<ImageItem>();   //shop

    public static Bitmap revisionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    public static String finishButtonText() {
        return Res.getString("finish") + "(" + Bimp.TEMP_PIC_STORAGE().size() + "/" + Bimp.GET_MAX_PIC_NUMBER() + ")";
    }

    public static void refreshPicList(Context context,boolean refresh){
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(context);
        AlbumActivity.contentList = helper.getImagesBucketList(refresh);
        AlbumActivity.dataList = new ArrayList<ImageItem>();
        for (int i = 0; i < AlbumActivity.contentList.size(); i++) {
            AlbumActivity.dataList.addAll(AlbumActivity.contentList.get(i).imageList);
        }
    }

    public static void addPicByPath(Context context,String path){
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(context);
    }

}
