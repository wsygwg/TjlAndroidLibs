package cn.com.hiss.www.multilib.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.CameraFragmentApi;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentControlsAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentStateAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentVideoRecordTextAdapter;
import com.github.florent37.camerafragment.widgets.CameraSettingsView;
import com.github.florent37.camerafragment.widgets.CameraSwitchView;
import com.github.florent37.camerafragment.widgets.FlashSwitchView;
import com.github.florent37.camerafragment.widgets.MediaActionSwitchView;
import com.github.florent37.camerafragment.widgets.RecordButton;

import java.io.File;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.UUID;

import cn.com.hiss.www.multilib.R;
import cn.com.hiss.www.multilib.utils.CacheData;
import cn.com.hiss.www.multilib.utils.Schecker;
import cn.com.hiss.www.sharephoto.util.Bimp;

public class HissCameraActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "camera";
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    private static final int REQUEST_PREVIEW_CODE = 1001;

    private int purpose = REQUEST_CODE_GET_VIDEO;
    public static final int REQUEST_CODE_GET_VIDEO = 1234;
    public static final int REQUEST_CODE_GET_PHOTO = 1235;
    public static final String RESULT_PATH_VIDEO = "RESULT_PATH_VIDEO";
    public static final String RESULT_PATH_PHOTO = "RESULT_PATH_PHOTO";
    CameraSettingsView settingsView;//设置清晰度，去掉
    FlashSwitchView flashSwitchView;//设置闪光灯
    CameraSwitchView cameraSwitchView;//设置前后摄像头
    RecordButton recordButton;
    MediaActionSwitchView mediaActionSwitchView;//设置拍照还是摄像，去掉
    TextView recordDurationText;
    TextView recordSizeText;
    View cameraLayout;
    View addCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);
        initViews();
    }

    private long recentlyRecordTime = 0;

    private void initViews() {
        settingsView = (CameraSettingsView) findViewById(R.id.settings_view);
        flashSwitchView = (FlashSwitchView) findViewById(R.id.flash_switch_view);
        cameraSwitchView = (CameraSwitchView) findViewById(R.id.front_back_camera_switcher);
        recordButton = (RecordButton) findViewById(R.id.record_button);
        mediaActionSwitchView = (MediaActionSwitchView) findViewById(R.id.photo_video_camera_switcher);
        recordDurationText = (TextView) findViewById(R.id.record_duration_text);
        recordSizeText = (TextView) findViewById(R.id.record_size_mb_text);
        cameraLayout = (View) findViewById(R.id.cameraLayout);
        addCameraButton = (View) findViewById(R.id.addCameraButton);

        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettingsClicked();
            }
        });

        flashSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFlashSwitcClicked();
            }
        });

        cameraSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSwitchCameraClicked();
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                //两次点击录像按钮的间隔时间不需短于3秒
                if(currentTime - recentlyRecordTime > 1000 * 3){
                    recentlyRecordTime = currentTime;
                    isRecordStarted = true;
                    onRecordButtonClicked();
                }
            }
        });

        mediaActionSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMediaActionSwitchClicked();
            }
        });

        addCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCameraClicked();
            }
        });

        //跳过点击添加摄像头按钮
        addCameraButton.setVisibility(View.GONE);
        onAddCameraClicked();
        //隐藏切换摄像头按钮
        mediaActionSwitchView.setVisibility(View.GONE);
        //        onMediaActionSwitchClicked();
    }

    private boolean isRecordStarted = false;

    public void onSettingsClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.openSettingDialog();
        }
    }

    public void onFlashSwitcClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.toggleFlashMode();
        }
    }

    public void onSwitchCameraClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.switchCameraTypeFrontBack();
        }
    }

    public void onRecordButtonClicked() {
        try{
            final CameraFragmentApi cameraFragment = getCameraFragment();
            if (cameraFragment != null) {
                cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultAdapter() {
                    @Override
                    public void onVideoRecorded(String filePath) {
                        //                    Toast.makeText(getBaseContext(), "onVideoRecorded " + filePath, Toast.LENGTH_SHORT).show();
                        isRecordStarted = false;
                        chooseVideoAlertDialog("",filePath);
                    }

                    @Override
                    public void onPhotoTaken(byte[] bytes, String filePath) {
                        //                    Toast.makeText(getBaseContext(), "onPhotoTaken " + filePath, Toast.LENGTH_SHORT).show();
                        sendDataBack(filePath);
                    }
                }, getCameraFilePath(), UUID.randomUUID().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    AlertDialog dialog;

    private void chooseVideoAlertDialog(String content,final String filePath) {
        if (dialog != null && dialog.isShowing()) {

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("提示").setMessage(Schecker.isStringNull(content)?"确定将此视频上传吗？":content).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendDataBack(filePath);
                    Toast.makeText(HissCameraActivity.this,"视频上传中，请稍等...",Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog = builder.create();
            dialog.show();
        }
    }

    void sendDataBack(String dataPath) {
        if (purpose == REQUEST_CODE_GET_VIDEO) {
            Intent intent = getIntent();
            intent.putExtra(RESULT_PATH_VIDEO, dataPath);
            setResult(REQUEST_CODE_GET_VIDEO, intent);
        } else if (purpose == REQUEST_CODE_GET_PHOTO) {
            Intent intent = getIntent();
            intent.putExtra(RESULT_PATH_VIDEO, dataPath);
            setResult(REQUEST_CODE_GET_VIDEO, intent);
        }
        finish();
    }


    @Override
    public void onBackPressed() {
        if (isRecordStarted) {
            onRecordButtonClicked();
        } else {
            super.onBackPressed();
        }
    }

    public static String getCameraFilePath() {
        return Environment.getExternalStorageDirectory() + File.separator + "HissSport" + File.separator + CacheData.getMyData().getMemberId() + File.separator + Bimp.PURPOSE.IM.getValue();
    }

    public void onMediaActionSwitchClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.switchActionPhotoVideo();
        }
    }

    public void onAddCameraClicked() {
        if (Build.VERSION.SDK_INT > 15) {
            final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

            final List<String> permissionsToRequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CAMERA_PERMISSIONS);
            } else
                addCamera();
        } else {
            addCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0) {
            addCamera();
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void addCamera() {
        addCameraButton.setVisibility(View.GONE);
        cameraLayout.setVisibility(View.VISIBLE);

        final CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder().setCamera(Configuration.CAMERA_FACE_REAR).build());
        getSupportFragmentManager().beginTransaction().replace(R.id.content, cameraFragment, FRAGMENT_TAG).commitAllowingStateLoss();

        if (cameraFragment != null) {
            //cameraFragment.setResultListener(new CameraFragmentResultListener() {
            //    @Override
            //    public void onVideoRecorded(String filePath) {
            //        Intent intent = PreviewActivity.newIntentVideo(HissCameraActivity.this, filePath);
            //        startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            //    }
            //
            //    @Override
            //    public void onPhotoTaken(byte[] bytes, String filePath) {
            //        Intent intent = PreviewActivity.newIntentPhoto(HissCameraActivity.this, filePath);
            //        startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            //    }
            //});

            cameraFragment.setStateListener(new CameraFragmentStateAdapter() {

                @Override
                public void onCurrentCameraBack() {
                    cameraSwitchView.displayBackCamera();
                }

                @Override
                public void onCurrentCameraFront() {
                    cameraSwitchView.displayFrontCamera();
                }

                @Override
                public void onFlashAuto() {
                    flashSwitchView.displayFlashAuto();
                }

                @Override
                public void onFlashOn() {
                    flashSwitchView.displayFlashOn();
                }

                @Override
                public void onFlashOff() {
                    flashSwitchView.displayFlashOff();
                }

                @Override
                public void onCameraSetupForPhoto() {
                    mediaActionSwitchView.displayActionWillSwitchVideo();

                    recordButton.displayPhotoState();
                    flashSwitchView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCameraSetupForVideo() {
                    mediaActionSwitchView.displayActionWillSwitchPhoto();

                    recordButton.displayVideoRecordStateReady();
                    flashSwitchView.setVisibility(View.GONE);
                }

                @Override
                public void shouldRotateControls(int degrees) {
                    ViewCompat.setRotation(cameraSwitchView, degrees);
                    ViewCompat.setRotation(mediaActionSwitchView, degrees);
                    ViewCompat.setRotation(flashSwitchView, degrees);
                    ViewCompat.setRotation(recordDurationText, degrees);
                    ViewCompat.setRotation(recordSizeText, degrees);
                }

                @Override
                public void onRecordStateVideoReadyForRecord() {
                    recordButton.displayVideoRecordStateReady();
                }

                @Override
                public void onRecordStateVideoInProgress() {
                    recordButton.displayVideoRecordStateInProgress();
                }

                @Override
                public void onRecordStatePhoto() {
                    recordButton.displayPhotoState();
                }

                @Override
                public void onStopVideoRecord() {
                    recordSizeText.setVisibility(View.GONE);
                    cameraSwitchView.setVisibility(View.VISIBLE);
//                    settingsView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onStartVideoRecord(File outputFile) {
                }
            });

            cameraFragment.setControlsListener(new CameraFragmentControlsAdapter() {
                @Override
                public void lockControls() {
                    cameraSwitchView.setEnabled(false);
                    recordButton.setEnabled(false);
                    settingsView.setEnabled(false);
                    flashSwitchView.setEnabled(false);
                }

                @Override
                public void unLockControls() {
                    cameraSwitchView.setEnabled(true);
                    recordButton.setEnabled(true);
                    settingsView.setEnabled(true);
                    flashSwitchView.setEnabled(true);
                }

                @Override
                public void allowCameraSwitching(boolean allow) {
                    cameraSwitchView.setVisibility(allow ? View.VISIBLE : View.GONE);
                }

                @Override
                public void allowRecord(boolean allow) {
                    recordButton.setEnabled(allow);
                }

                @Override
                public void setMediaActionSwitchVisible(boolean visible) {
//                    mediaActionSwitchView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                }
            });

            cameraFragment.setTextListener(new CameraFragmentVideoRecordTextAdapter() {
                @Override
                public void setRecordSizeText(long size, String text) {
                    recordSizeText.setText(text);
                }

                @Override
                public void setRecordSizeTextVisible(boolean visible) {
                    recordSizeText.setVisibility(visible ? View.VISIBLE : View.GONE);
                }

                @Override
                public void setRecordDurationText(String text) {
                    recordDurationText.setText(text);
                    try{
                        int timeSecond = Integer.valueOf(text.charAt(text.length()-1));
                        if(timeSecond == 8){
                            Toast.makeText(HissCameraActivity.this,"最长录像时间为8秒",Toast.LENGTH_SHORT).show();
                            onRecordButtonClicked();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void setRecordDurationTextVisible(boolean visible) {
                    recordDurationText.setVisibility(visible ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    private CameraFragmentApi getCameraFragment() {
        return (CameraFragmentApi) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
}
