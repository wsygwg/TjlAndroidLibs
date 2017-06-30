package cn.com.hiss.www.multilib.oss.manager;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

import net.openmob.mobileimsdk.server.protocal.c.PP2PMsg;

/**
 * Created by junliang on 2017/2/28.
 */

public class OssSetting {
    // OSS PARAMETER 运行sample前需要配置以下字段为有效的值
    public static final String protocol = "http://";
    private static final String endpoint = "oss-cn-shanghai.aliyuncs.com";
    private static final String accessKeyId = "LTAIqe0wtY2w1m53";
    private static final String accessKeySecret = "CqtR59Z7WZEXenQASke5QWzxTLYxY1";
    public static final String bucketNameImage = "dwgroup-image";
    public static final String bucketNameAudio = "dwgroup-audio";
    public static final String bucketNameVideo = "dwgroup-video";
    public static final String protocol_bucket_endpoint_image = (protocol + bucketNameImage + "." + endpoint);
    public static final String protocol_bucket_endpoint_audio = (protocol + bucketNameAudio + "." + endpoint);
    public static final String protocol_bucket_endpoint_video = (protocol + bucketNameVideo + "." + endpoint);
    public static final String region = "oss-cn-shanghai";

    //    private static final String downloadObject = "sampleObject";
    public enum HissResType {
        IMAGE(bucketNameImage, protocol_bucket_endpoint_image, PP2PMsg.IMAGE), AUDIO(bucketNameAudio, protocol_bucket_endpoint_audio, PP2PMsg.VOICE), VIDEO(bucketNameVideo, protocol_bucket_endpoint_video, PP2PMsg.VIDEO);
        String str;
        String url;
        String pp2pmsgType;

        HissResType(String bucketName, String url,String pp2pmsgType) {
            this.str = str;
            this.url = url;
            this.pp2pmsgType = pp2pmsgType;
        }

        public String getStr() {
            return str;
        }

        public String getUrl() {
            return url;
        }

        public String getPp2pmsgType() {
            return pp2pmsgType;
        }

        public static HissResType getTypeByStr(String typeStr){
            if(typeStr == null){
                return null;
            }else if(typeStr.equals(HissResType.IMAGE.getPp2pmsgType())){
                return HissResType.IMAGE;
            }else if(typeStr.equals(HissResType.AUDIO.getPp2pmsgType())){
                return HissResType.AUDIO;
            }else if(typeStr.equals(HissResType.VIDEO.getPp2pmsgType())){
                return HissResType.VIDEO;
            }else{
                return null;
            }
        }
    }

    public enum HissTextType{
        TXT(PP2PMsg.TXT),POSITION(PP2PMsg.POSITION);
        private String pp2pmsgType;
        HissTextType(String pp2pmsgType){
            this.pp2pmsgType = pp2pmsgType;
        }

        public String getPp2pmsgType() {
            return pp2pmsgType;
        }
    }

    public synchronized static OSS getOss(Context con) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        OSS oss = new OSSClient(con, protocol + endpoint, credentialProvider, conf);
        return oss;
    }
}
