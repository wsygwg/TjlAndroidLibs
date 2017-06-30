package cn.com.hiss.www.multilib.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.UUID;

import cn.com.hiss.www.sharephoto.util.Bimp;

/**
 * https://github.com/glassLake/CropUtils
 * <p>
 * compile 'com.yalantis:ucrop:2.2.0
 * <p>
 * Created by Administrator on 2016/9/23 0004.
 */
public class PicCrop {
    private static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final int REQUEST_CAMERA = 0x03;
    private static final int REQUEST_CAMERA_NO_CROP = 0x05;
    private static final int REQUEST_SELECT_PICTURE_NO_CROP = 0x07;

    private static final String EXTRA_VIEW_TAG = "viewTag";//同一个页面多个地方需要选择图片时，config里tag字段用于标识

    public static final int TYPE_AVATAR = 1;
    public static final int TYPE_NORMAL = 2;
    private static final String CAPTURE_PATH = Environment.getExternalStorageDirectory() + File.separator + "HissSport" + File.separator + "capture" + File.separator;
    public static final String CROP_PATH = Environment.getExternalStorageDirectory() + File.separator + "HissSport" + File.separator + "crop" + File.separator;

    public static Uri getUri() {
        return uri;
    }

    private static Uri uri;
    private static CropConfig config = new CropConfig();

    private static Uri buildUri() {
        File cacheFolder = new File(CAPTURE_PATH);
        if (!cacheFolder.exists()) {
            try {
                boolean result = cacheFolder.mkdirs();
                Log.d("uri", "generateUri " + cacheFolder + " result: " + (result ? "succeeded" : "failed"));
            } catch (Exception e) {
                Log.e("uri", "generateUri failed: " + cacheFolder, e);
            }
        }
        String name = String.format("imagecrop-%s.jpg", HissDate.getCurrentDate(HissDate.DATE_FORMAT_YMDHMS_DEFAULT));
        uri = Uri.fromFile(cacheFolder).buildUpon().appendPath(name).build();
        Log.e("crop", uri.toString());
        return uri;
    }

    private static Uri buildUriNoCrop() {
        File cacheFolder = new File(CAPTURE_PATH);
        if (!cacheFolder.exists()) {
            try {
                boolean result = cacheFolder.mkdirs();
                Log.d("uri", "generateUri " + cacheFolder + " result: " + (result ? "succeeded" : "failed"));
            } catch (Exception e) {
                Log.e("uri", "generateUri failed: " + cacheFolder, e);
            }
        }
        String name = CacheData.getMyData().getMemberId() + File.separator + Bimp.currentPurpose.getValue() + File.separator + UUID.randomUUID() + ".jpg";
        uri = Uri.fromFile(cacheFolder).buildUpon().appendPath(name).build();
        Log.e("crop", uri.toString());
        return uri;
    }

    private static Uri buildCropUri(Bimp.PURPOSE type, String studentID) {
        File cacheFolder = new File(CROP_PATH + studentID + File.separator + type.getValue() + File.separator);
        if (!cacheFolder.exists()) {
            try {
                boolean result = cacheFolder.mkdirs();
                Log.d("uri", "generateUri " + cacheFolder + " result: " + (result ? "succeeded" : "failed"));
            } catch (Exception e) {
                Log.e("uri", "generateUri failed: " + cacheFolder, e);
            }
        }
        String name = "";//studentID + ".jpg";
        if (type == Bimp.PURPOSE.ICON) {
            name = studentID;
        } else if (type == Bimp.PURPOSE.DESK) {
            name = studentID;
        } else if (type == Bimp.PURPOSE.SNS) {
            name = UUID.randomUUID() + "";
        } else if (type == Bimp.PURPOSE.SHOP) {
            name = UUID.randomUUID() + "";
        } else if (type == Bimp.PURPOSE.IM) {
            name = UUID.randomUUID() + "";
        }
        uri = Uri.fromFile(cacheFolder).buildUpon().appendPath(name + ".jpg").build();
        Log.e("crop", uri.toString());
        return uri;
    }

    public static void cropAvatarFromGallery(Activity context) {
        cropFromGallery(context, null, TYPE_AVATAR);
    }

    public static void cropAvatarFromGallery(Fragment context) {
        cropFromGallery(context, null, TYPE_AVATAR);
    }

    public static void cropAvatarFromCamera(Activity context) {
        cropFromCamera(context, null, TYPE_AVATAR);
    }

    public static void cropAvatarFromCamera(Fragment context) {
        cropFromCamera(context, null, TYPE_AVATAR);
    }

    public static void cropFromGallery(Activity context) {
        cropFromGallery(context, null, 0);
    }

    public static void cropFromGalleryNoCropAction(Activity context) {
        cropFromGalleryNoCropAction(context, null, 0);
    }

    public static void cropFromGallery(Fragment context) {
        cropFromGallery(context, null, 0);
    }

    public static void cropFromCamera(Activity context) {
        cropFromCamera(context, null, 0);
    }

    public static void cropFromCameraNoCropAction(Activity context) {
        cropFromCameraNoCropAction(context, null, 0);
    }

    public static void cropFromCamera(Fragment context) {
        cropFromCamera(context, null, 0);
    }

    public static void cropFromGallery(Activity context, CropConfig config, int type) {
        if (config != null) {
            PicCrop.config = config;//怎么避免前后两次config
        } else {
            PicCrop.config = new CropConfig();
        }

        setType(type);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    private static void cropFromGalleryNoCropAction(Activity context, CropConfig config, int type) {
        if (config != null) {
            PicCrop.config = config;//怎么避免前后两次config
        } else {
            PicCrop.config = new CropConfig();
        }

        setType(type);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE_NO_CROP);
    }

    public static void cropFromGallery(Fragment context, CropConfig config, int type) {
        if (config != null) {
            PicCrop.config = config;//怎么避免前后两次config
        } else {
            PicCrop.config = new CropConfig();
        }

        setType(type);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    private static void setType(int type) {
        if (type == TYPE_AVATAR) {
            config.isOval = true;
            config.aspectRatioX = 1;
            config.aspectRatioY = 1;
            config.hideBottomControls = true;
            config.showGridLine = false;
            config.showOutLine = false;
            config.maxHeight = 400;
            config.maxWidth = 400;
        } else if (type == TYPE_NORMAL) {//什么都不用做


        } else {

        }
    }

    public static void cropFromCamera(Activity context, CropConfig config, int type) {
        if (config != null) {
            PicCrop.config = config;
        } else {
            PicCrop.config = new CropConfig();
        }

        setType(type);

        Uri mDestinationUri = buildUri();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, mDestinationUri);
        context.startActivityForResult(intent, REQUEST_CAMERA);
    }

    public static void cropFromCameraNoCropAction(Activity context, CropConfig config, int type) {
        if (config != null) {
            PicCrop.config = config;
        } else {
            PicCrop.config = new CropConfig();
        }

        setType(type);

        Uri mDestinationUri = buildUriNoCrop();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, mDestinationUri);
        context.startActivityForResult(intent, REQUEST_CAMERA_NO_CROP);
    }

    public static void cropFromCamera(Fragment context, CropConfig config, int type) {
        if (config != null) {
            PicCrop.config = config;
        } else {
            PicCrop.config = new CropConfig();
        }

        setType(type);

        Uri mDestinationUri = buildUri();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, mDestinationUri);
        context.startActivityForResult(intent, REQUEST_CAMERA);
    }

    private static void startCropActivity(Activity context, Uri sourceUri, Bimp.PURPOSE type, String studentID) {
        Uri mDestinationUri = buildCropUri(type, studentID);
        UCrop uCrop = UCrop.of(sourceUri, mDestinationUri);

        uCrop.withAspectRatio(config.aspectRatioX, config.aspectRatioY);
        uCrop.withMaxResultSize(config.maxWidth, config.maxHeight);

        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.NONE);
        options.setCompressionQuality(config.quality);
        // options.setOvalDimmedLayer(config.isOval);
        options.setCircleDimmedLayer(config.isOval);
        options.setShowCropGrid(config.showGridLine);
        options.setHideBottomControls(config.hideBottomControls);
        options.setShowCropFrame(config.showOutLine);
        options.setToolbarColor(config.toolbarColor);
        options.setStatusBarColor(config.statusBarColor);

        uCrop.withOptions(options);

        uCrop.start(context);
    }

    private static void startCropActivity(Fragment fragment, Uri sourceUri, Bimp.PURPOSE type, String studentID) {
        Uri mDestinationUri = buildCropUri(type, studentID);
        UCrop uCrop = UCrop.of(sourceUri, mDestinationUri);

        uCrop.withAspectRatio(config.aspectRatioX, config.aspectRatioY);
        uCrop.withMaxResultSize(config.maxWidth, config.maxHeight);

        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.NONE);
        options.setCompressionQuality(config.quality);
        // options.setOvalDimmedLayer(config.isOval);
        options.setCircleDimmedLayer(config.isOval);
        options.setShowCropGrid(config.showGridLine);
        options.setHideBottomControls(config.hideBottomControls);
        options.setShowCropFrame(config.showOutLine);
        options.setToolbarColor(config.toolbarColor);
        options.setStatusBarColor(config.statusBarColor);

        uCrop.withOptions(options);

        uCrop.start(fragment.getActivity(), fragment);
    }

    public static class CropConfig {
        public int aspectRatioX = 1;
        public int aspectRatioY = 1;
        public int maxWidth = 1080;
        public int maxHeight = 1920;

        //options
        public int tag;
        public boolean isOval = false;//是否为椭圆
        public int quality = 80;

        public boolean hideBottomControls = true;//底部操作条
        public boolean showGridLine = true;//内部网格
        public boolean showOutLine = true;//最外面的矩形线

        public
        @ColorInt
        int toolbarColor = Color.BLUE;
        public
        @ColorInt
        int statusBarColor = Color.BLUE;


        public void setAspectRation(int x, int y) {
            this.aspectRatioX = x;
            this.aspectRatioY = y;
        }

        public void setMaxSize(int width, int height) {
            this.maxHeight = height;
            this.maxWidth = width;
        }

    }

    public interface CropHandler {
        void handleCropResult(Uri uri, int tag);

        void handleCropError(Intent data);
    }

    /**
     * 注意，调用时data为null的判断
     *
     * @param context
     * @param cropHandler
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data, Activity context, Bimp.PURPOSE type, String studentID, CropHandler cropHandler) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {//第一次，选择图片后返回
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(context, data.getData(), type, studentID);
                } else {
                    Toast.makeText(context, "Cannot retrieve selected image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {//第二次返回，图片已经剪切好
                Uri finalUri = UCrop.getOutput(data);
                cropHandler.handleCropResult(finalUri, config.tag);
            } else if (requestCode == REQUEST_CAMERA) {//第一次，拍照后返回，因为设置了MediaStore.EXTRA_OUTPUT，所以data为null，数据直接就在uri中
                startCropActivity(context, uri, type, studentID);
            } else if (requestCode == REQUEST_CAMERA_NO_CROP) {//第一次，拍照后返回，因为设置了MediaStore.EXTRA_OUTPUT，所以data为null，数据直接就在uri中
                cropHandler.handleCropResult(uri, 0);
            }else if (requestCode == REQUEST_SELECT_PICTURE_NO_CROP) {//第一次，选择图片后返回
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    cropHandler.handleCropResult(selectedUri, 0);
                } else {
                    Toast.makeText(context, "Cannot retrieve selected image", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            cropHandler.handleCropError(data);
        }
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data, Fragment context, Bimp.PURPOSE type, String studentID, CropHandler cropHandler) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {//第一次，选择图片后返回
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(context, data.getData(), type, studentID);
                } else {
                    Toast.makeText(context.getActivity(), "Cannot retrieve selected image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {//第二次返回，图片已经剪切好
                Uri finalUri = UCrop.getOutput(data);
                cropHandler.handleCropResult(finalUri, config.tag);
            } else if (requestCode == REQUEST_CAMERA) {//第一次，拍照后返回，因为设置了MediaStore.EXTRA_OUTPUT，所以data为null，数据直接就在uri中
                startCropActivity(context, uri, type, studentID);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            cropHandler.handleCropError(data);
        }
    }
}
