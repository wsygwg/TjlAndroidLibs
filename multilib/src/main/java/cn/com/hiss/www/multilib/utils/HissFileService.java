package cn.com.hiss.www.multilib.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import cn.com.hiss.www.multilib.oss.manager.OssSetting;

/**
 * class name：FileService<BR>
 * class description：android文件的一些读取操作<BR>
 */
public class HissFileService {

    private static final String TAG = "FileService --->>> ";
    private Context context;
    private Throwable totalThrowable;

    public static String LOG_FILE_PATH = Environment.getExternalStorageDirectory() + "/HissST/log_" + HissDate.getCurrentDate("yyyyMMdd") + ".txt";
    public static String LOCK_PW_FILE = Environment.getExternalStorageDirectory() + "/cms/ys.txt";

    public HissFileService(Context c, Throwable e) {
        this.context = c;
        totalThrowable = e;
        try {
            if (HissFileService.makeLog(c)) {
                writeDateFile(LOG_FILE_PATH, ("\n\n时间：" + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT) + "\n" + getErrorInfoFromThrowable(totalThrowable)).getBytes());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public HissFileService(Context c, String str) {
        this.context = c;
        str = "\n\n时间：" + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT) + "\n" + str;
        try {
            if (HissFileService.makeLog(c)) {
                writeDateFile(LOG_FILE_PATH, str.getBytes());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public HissFileService(Context c) {
        this.context = c;
    }

    public synchronized static String getFileNameByPath(String pathWithName) {
        try {
            int start = pathWithName.lastIndexOf("/");
            int end = pathWithName.lastIndexOf(".");
            if (start != -1 && end != -1) {
                return pathWithName.substring(start + 1, end);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized static String getHissSportImageName(String pathWithName) {
        try {
            String[] strs = pathWithName.split(File.separator);
            int length = strs.length;
            String ret = strs[length - 3] + File.separator + strs[length - 2] + File.separator + strs[length - 1];
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized static String getHissSportOssFileName(String pathWithName) {
        try {
            String[] strs = pathWithName.split(File.separator);
            int length = strs.length;
            String ret = strs[length - 3] + File.separator + strs[length - 2] + File.separator + strs[length - 1];
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized static String getHissSportImageOssFileName(String pathWithName) {
        try {
            String[] strs = pathWithName.split(File.separator);
            int length = strs.length;
            String ret = strs[length - 1];
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将EXCEPTION转换为字符串
     *
     * @param e
     * @return
     */
    public static String getErrorInfoFromThrowable(Throwable e) {
        try {
            String errorTime = "<错误产生时间：>" + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return errorTime + "\r\n" + sw.toString() + "\r\n\n\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromThrowable";
        }
    }

    /**
     * 创建出错日志
     */
    public static boolean makeLog(Context con) {
        try {
            HissFileService fs = new HissFileService(con);
            File f_l = new File(HissFileService.LOG_FILE_PATH);
            if (!f_l.exists()) {
                //如果柜子号文件不存在，则创建它
                if (HissFileService.CreateFile(HissFileService.LOG_FILE_PATH) != null) {
                    //尝试向出错日志文件中写入日志文件开始时间
                    String log_start = "\n日志文件开始(" + HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT) + ")：";
                    try {
                        fs.writeSDCardFile(HissFileService.LOG_FILE_PATH, log_start.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    Log.i(TAG, "出错,日志文件创建失败。。。");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建锁密码文件
     */
    public static void makeLockFile(Context con, String lockPw) {
        try {
            HissFileService fs = new HissFileService(con);
            File f_l = new File(HissFileService.LOCK_PW_FILE);
            if (f_l.exists()) {
                f_l.delete();
            }
            if (HissFileService.CreateFile(HissFileService.LOCK_PW_FILE) != null) {
                try {
                    fs.writeSDCardFile(HissFileService.LOCK_PW_FILE, lockPw.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    /**
    //     * 返回本地文件存储的锁密码
    //     *
    //     * @return
    //     */
    //    public static String getLocalPw() {
    //        String ret = "";
    //        try {
    //            ret = new String(HissAES.decrypt(HissAES.parseHexStr2Byte(new HissFileService(HissApplication.mContext).readSDCardFile(HissFileService.LOCK_PW_FILE)),HissAES.AES_PW));
    //            Log.e(TAG,"本地锁密码为：" + ret);
    //        } catch (Exception e) {
    //            Log.e(TAG,"读取本地锁密码失败");
    //        }
    //        return ret;
    //    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     *
     * @param filePath
     */
    public static void readTxtFile(String filePath) {
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

    public static File CreateFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            Log.i(TAG, "创建单个文件" + destFileName + "失败，目标文件已存在！");
            return file;
        }
        if (destFileName.endsWith(File.separator)) {
            Log.i(TAG, "创建单个文件" + destFileName + "失败，目标不能是目录！");
            return null;
        }
        if (!file.getParentFile().exists()) {
            Log.i(TAG, "目标文件所在路径不存在，准备创建。。。");
            if (!file.getParentFile().mkdirs()) {
                Log.i(TAG, "创建目录文件所在的目录失败！");
                return null;
            }
        }

        // 创建目标文件
        try {
            if (file.createNewFile()) {
                Log.i(TAG, "创建单个文件" + destFileName + "成功！");
                return file;
            } else {
                Log.i(TAG, "创建单个文件" + destFileName + "失败！");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "创建单个文件" + destFileName + "失败！");
            return null;
        }
    }

    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            Log.i(TAG, "创建目录" + destDirName + "失败，目标目录已存在！");
            return false;
        }
        if (!destDirName.endsWith(File.separator))
            destDirName = destDirName + File.separator;
        // 创建单个目录
        if (dir.mkdirs()) {
            Log.i(TAG, "创建目录" + destDirName + "成功！");
            return true;
        } else {
            Log.i(TAG, "创建目录" + destDirName + "成功！");
            return false;
        }
    }

    //递归删除文件夹
    public static void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
    }

    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        try {
            if (dirName == null) {
                // 在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                return tempFile.getCanonicalPath();
            } else {
                File dir = new File(dirName);
                // 如果临时文件所在目录不存在，首先创建
                if (!dir.exists()) {
                    if (!HissFileService.createDir(dirName)) {
                        System.out.println("创建临时文件失败，不能创建临时文件所在目录！");
                        return null;
                    }
                }
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建临时文件失败" + e.getMessage());
            return null;
        }
    }

    // 在res目录下建立一个raw资源文件夹，这里的文件只能读不能写入。。。
    public String readRawFile(int fileId) throws IOException {
        // 取得输入流
        InputStream is = context.getResources().openRawResource(fileId);
        String result = streamRead(is);// 返回一个字符串
        return result;
    }

    private String streamRead(InputStream is) throws IOException {
        int buffersize = is.available();// 取得输入流的字节长度
        byte buffer[] = new byte[buffersize];
        is.read(buffer);// 将数据读入数组
        is.close();// 读取完毕后要关闭流。
        String result = EncodingUtils.getString(buffer, "UTF-8");// 设置取得的数据编码，防止乱码
        return result;
    }

    // 在assets文件夹下的文件，同样是只能读取不能写入
    public String readAssetsFile(String filename) throws IOException {
        // 取得输入流
        InputStream is = context.getResources().getAssets().open(filename);
        String result = streamRead(is);// 返回一个字符串
        return result;
    }

    // 读取sd中的文件
    public String readSDCardFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        String result = streamRead(fis);
        return result;
    }

    // 往sd卡中写入文件
    public void writeSDCardFile(String path, byte[] buffer) throws IOException {
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(buffer);// 写入buffer数组。如果想写入一些简单的字符，可以将String.getBytes()再写入文件;
        fos.close();
    }

    // 读取应用的data/data的files目录下文件数据
    public String readDateFile(String fileName) throws Exception {
        FileInputStream fis = context.openFileInput(fileName);
        String result = streamRead(fis);// 返回一个字符串
        return result;
    }

    //日志文件记录
    public void writeDateFile(String fileName, byte[] buffer) throws Exception {
        byte[] buf = fileName.getBytes("iso8859-1");
        fileName = new String(buf, "utf-8");
        FileOutputStream fos = new FileOutputStream(fileName, true);// true,新内容添加在文件后面
        fos.write(buffer);
        fos.close();
    }

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static File getCacheDirectory(Context context, String type) {
        File appCacheDir = getExternalCacheDirectory(context, type);
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type);
        }

        if (appCacheDir == null) {
            Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        return appCacheDir;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link android.os.Environment#DIRECTORY_MUSIC},
     *                {@link android.os.Environment#DIRECTORY_PODCASTS},
     *                {@link android.os.Environment#DIRECTORY_RINGTONES},
     *                {@link android.os.Environment#DIRECTORY_ALARMS},
     *                {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link android.os.Environment#DIRECTORY_PICTURES}, or
     *                {@link android.os.Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)) {
                appCacheDir = context.getExternalCacheDir();
            } else {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null) {// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache/" + type);
            }

            if (appCacheDir == null) {
                Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard unknown exception !");
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        } else {
            Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (TextUtils.isEmpty(type)) {
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else {
            appCacheDir = new File(context.getFilesDir(), type);// /data/data/app_package_name/files/type
        }

        if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
            Log.e("getInternalDirectory", "getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }

    public static String getOssFileName(String ossUrl) {
        try {
            if (ossUrl != null && ossUrl.contains("/")) {
                int index = ossUrl.lastIndexOf("/");
                //                System.out.println("index = " + index);
                String ret = ossUrl.substring(index + 1, ossUrl.length());
                //                System.out.println("ret = " + ret);
                return ret;
            } else {
                return ossUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
