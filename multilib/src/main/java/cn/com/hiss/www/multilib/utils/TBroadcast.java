package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Map;

/**
 * 发送本地广播
 */
public class TBroadcast {
	
	private static final String TAG = "TBroadcast --->>> ";
	public static final String HISS_BOX_B2M_SHOW  = "HISS_BOX_B2M_SHOW";
	public static final String HISS_BOX_AUTO_B2M  = "HISS_BOX_AUTO_B2M";
	public static final String KEY_STR =  "string_info";

	public static final String BROADCAST_IM_MESSAGE_RECEIVED = "BROADCAST_IM_MESSAGE_RECEIVED";
	public static final String BROADCAST_IM_PERFIX = "BROADCAST_IM_PERFIX";
//	public static final String BROADCAST_IM_FRIEND_RELATED = "BROADCAST_IM_FRIEND_RELATED";
//	public static final String BROADCAST_IM_RECENT_LOG = "BROADCAST_IM_RECENT_LOG";

	public static final String BROADCAST_APP_START_BY_NOTIFICATION = "BROADCAST_APP_START_BY_NOTIFICATION";
	public static final String BROADCAST_SERVICE_START_BY_NOTIFICATION = "BROADCAST_SERVICE_START_BY_NOTIFICATION";
	public static final String BROADCAST_FRAGMENT_AFTER_CHAT_ACTIVITY = "BROADCAST_FRAGMENT_AFTER_CHAT_ACTIVITY";

	public static final String KEY_INFO_ID = "KEY_INFO_ID";

	/**
	 * 发送广播
	 * @param action 广播动作
	 * @param string_info	广播Intent的String格式Extra
	 */
	public synchronized static void sendLocalStringBc(Context con, String action, String string_info){
		try{
			Intent sendInfo2Service = new Intent();
			sendInfo2Service.setAction(action);
			sendInfo2Service.putExtra(KEY_STR, string_info);
			Log.e(TAG, "发送动作为<" + action + "> , String内容为<" + string_info + "> 的广播");
			LocalBroadcastManager.getInstance(con).sendBroadcast(sendInfo2Service);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public synchronized static void sendLocalParcelableBc(Context con, String action,String key, Parcelable parcelable){
		Intent sendInfo2Service = new Intent();
		sendInfo2Service.setAction(action);
		sendInfo2Service.putExtra(key, parcelable);
		LocalBroadcastManager.getInstance(con).sendBroadcast(sendInfo2Service);
	}
	
	/**
	 * 发送Map类型的广播（接收端需要解析Bundle，根据键值对可以查询值）
	 * @param action 广播动作
	 */
	public synchronized static void sendLocalMapBc(Context con, String action, Map<String, String> bcMap){
		Intent sendInfo2Service = new Intent();
		sendInfo2Service.setAction(action);
		Bundle bundle = new Bundle();
		StringBuffer sb = new StringBuffer();
		for(String key : bcMap.keySet()){
			bundle.putString(key, bcMap.get(key));
			sb.append(key + " : " + bcMap.get(key) + " ； ");
		}
		sendInfo2Service.putExtra("string_info", bundle);
		Log.e(TAG, "发送动作为<" + action + "> , Map内容为<" + sb.toString() + "> 的广播");
		LocalBroadcastManager.getInstance(con).sendBroadcast(sendInfo2Service);
	}

	public synchronized static void sendPublicStringBc(Context con, String action, String string_info){
		Intent sendInfo2Service = new Intent();
		sendInfo2Service.setAction(action);
		sendInfo2Service.putExtra(KEY_STR, string_info);
		Log.i(TAG, "发送动作为<" + action + "> , String内容为<" + string_info + "> 的public广播");
        con.sendBroadcast(sendInfo2Service);
	}
}
