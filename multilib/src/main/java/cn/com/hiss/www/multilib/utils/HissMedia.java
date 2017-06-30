package cn.com.hiss.www.multilib.utils;

import android.media.MediaMetadataRetriever;

import java.io.File;

/**
 * Created by wuyanzhe on 2017/4/17.
 */

public class HissMedia {

    private static MediaMetadataRetriever mmr;

    public synchronized static String getMusicTime(File file) {
        try{
            if(mmr == null){
                mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象mmr
            }
            mmr.setDataSource(file.getAbsolutePath());//设置mmr对象的数据源为上面file对象的绝对路径
            String ablumString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);//获得音乐专辑的标题
            String artistString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);//获取音乐的艺术家信息
            String titleString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);//获取音乐标题信息
            String mimetypeString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);//获取音乐mime类型
            String durationString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//获取音乐持续时间
            String bitrateString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);//获取音乐比特率，位率
            String dateString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);//获取音乐的日期				/* 设置文本的内容 */
            //        ablum.setText("专辑标题为：" + ablumString);
            //        artist.setText("艺术家名称为：" + artistString);
            //        title.setText("音乐标题为：" + titleString);
            //        mimetype.setText("音乐的MIME类型为：" + mimetypeString);
            //        duration.setText("duration为：" + durationString);
            //        bitrate.setText("bitrate为：" + bitrateString);
            //        date.setText("date为：" + dateString);
            return durationString;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
