package com.artgaller.reader.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.artgaller.reader.R;
import com.artgaller.reader.utils.DownloadUtil;
import com.artgaller.reader.utils.PreferencesUtils;
import com.artgaller.reader.views.BaseActivity;


public class DownloadActivity extends BaseActivity implements View.OnClickListener{
    private Button mBtn_download;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        ActionBar actionBar = getActionBar();
        actionBar.show();actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        setContentView(R.layout.activity_download);
        mBtn_download = (Button) findViewById(R.id.btn_download);
        mProgressBar = (ProgressBar)findViewById(R.id.pg);
        mBtn_download.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_download:
            //下载
            download();
        break;
        default:
        break;
        }
    }

    private void download() {
        mProgressBar.setVisibility(View.VISIBLE);
        String url = PreferencesUtils.getString(getApplicationContext(), "url");
//        String url = "http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/video/2a65991e-9514-4790-9933-6a6fbb1f3063_ar_fcfaaf51f3deb48ff75f8456f71f3a292df57807.mp4";
//        String url ="http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/video/b846e4db-d1f5-404c-bb8f-abd04c059edf_ar_9d82d158ccbf6c815febdd6ebb3eb13532fa405c.mp4";
//        String name = "2a65991e-9514-4790-9933-6a6fbb1f3063_ar_fcfaaf51f3deb48ff75f8456f71f3a292df57807.mp4";
//        PreferencesUtils.putString(getApplicationContext(), name,"博物馆");
//        String url = "http://192.168.1.99/AugmentedRealitySystemFile/scenePhoto/video/eebab4ca-ac2a-46d2-9e3e-930fa8af73a0_ar_8326cffc1e178a8255cd4528f103738da877e841.mp4";
        String sdPath = "/xxx/ooo";
//        String sdPath = Environment.getExternalStorageDirectory()
//                .toString() + "/video/";
//        String sdPath =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/sdcard/xUtils/";
        DownloadUtil.get().download(url,sdPath, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {

                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        //更新UI
                        mProgressBar.setVisibility(View.GONE);
                        finish();
                    }

                });
            }
            @Override
            public void onDownloading(int progress) {
                mProgressBar.setProgress(progress);
            }
            @Override
            public void onDownloadFailed() {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }
}
