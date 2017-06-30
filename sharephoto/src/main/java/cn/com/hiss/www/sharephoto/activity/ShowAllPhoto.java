package cn.com.hiss.www.sharephoto.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import cn.com.hiss.www.sharephoto.adapter.AlbumGridViewAdapter;
import cn.com.hiss.www.sharephoto.util.Bimp;
import cn.com.hiss.www.sharephoto.util.ImageItem;
import cn.com.hiss.www.sharephoto.util.Res;

import static cn.com.hiss.www.sharephoto.util.Bimp.finishButtonText;

/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 */
public class ShowAllPhoto extends Activity {
    private GridView gridView;
    private ProgressBar progressBar;
    private AlbumGridViewAdapter gridImageAdapter;
    // 完成按钮
    private Button okButton;
    // 预览按钮
    private Button preview;
    // 返回按钮
    private Button back;
    // 取消按钮
    private Button cancel;
    // 标题
    private TextView headTitle;
    private Intent intent;
    private Context mContext;
    public static ArrayList<ImageItem> dataList = new ArrayList<ImageItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("plugin_camera_show_all_photo"));
        mContext = this;
        back = (Button) findViewById(Res.getWidgetID("showallphoto_back"));
        cancel = (Button) findViewById(Res.getWidgetID("showallphoto_cancel"));
        preview = (Button) findViewById(Res.getWidgetID("showallphoto_preview"));
        okButton = (Button) findViewById(Res.getWidgetID("showallphoto_ok_button"));
        headTitle = (TextView) findViewById(Res.getWidgetID("showallphoto_headtitle"));
        this.intent = getIntent();
        String folderName = intent.getStringExtra("folderName");
        if (folderName.length() > 8) {
            folderName = folderName.substring(0, 9) + "...";
        }
        headTitle.setText(folderName);
        cancel.setOnClickListener(new CancelListener());
        back.setOnClickListener(new BackListener(intent));
        preview.setOnClickListener(new PreviewListener());
        init();
        initListener();
        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (Bimp.TEMP_PIC_STORAGE().size() > 0) {
                intent.putExtra("position", "2");
                intent.setClass(ShowAllPhoto.this, GalleryActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private class BackListener implements OnClickListener {// 返回按钮监听
        Intent intent;

        public BackListener(Intent intent) {
            this.intent = intent;
        }

        public void onClick(View v) {
            intent.setClass(ShowAllPhoto.this, ImageFile.class);
            startActivity(intent);
            finish();
        }

    }

    private class CancelListener implements OnClickListener {// 取消按钮的监听

        public void onClick(View v) {
            //清空选择的图片
            Bimp.TEMP_PIC_STORAGE().clear();
            finish();
        }
    }

    private void init() {
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        progressBar = (ProgressBar) findViewById(Res.getWidgetID("showallphoto_progressbar"));
        progressBar.setVisibility(View.GONE);
        gridView = (GridView) findViewById(Res.getWidgetID("showallphoto_myGrid"));
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList, Bimp.TEMP_PIC_STORAGE());
        gridView.setAdapter(gridImageAdapter);
        okButton = (Button) findViewById(Res.getWidgetID("showallphoto_ok_button"));
    }

    private void initListener() {

        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
            public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked, Button button) {
                if (Bimp.TEMP_PIC_STORAGE().size() >= Bimp.ALBUM_MAX_PIC_NUMBER && isChecked) {
                    button.setVisibility(View.GONE);
                    toggleButton.setChecked(false);
                    Toast.makeText(ShowAllPhoto.this, Res.getString("only_choose_num"), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isChecked) {
                    button.setVisibility(View.VISIBLE);
                    Bimp.TEMP_PIC_STORAGE().add(dataList.get(position));
                    okButton.setText(finishButtonText());
                } else {
                    button.setVisibility(View.GONE);
                    Bimp.TEMP_PIC_STORAGE().remove(dataList.get(position));
                    okButton.setText(finishButtonText());
                }
                isShowOkBt();
            }
        });

        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                okButton.setClickable(false);
                finish();
            }
        });

    }

    public void isShowOkBt() {
        if (Bimp.TEMP_PIC_STORAGE().size() > 0) {
            okButton.setText(finishButtonText());
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
            preview.setTextColor(Color.WHITE);
        } else {
            okButton.setText(finishButtonText());
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            okButton.setTextColor(Color.parseColor("#E1E0DE"));
            preview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }

}
