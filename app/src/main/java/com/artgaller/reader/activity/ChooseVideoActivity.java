package com.artgaller.reader.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.aphidmobile.flip.FlipViewController;
import com.artgaller.reader.R;
import com.artgaller.reader.utils.PreferencesUtils;
import com.artgaller.reader.views.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class ChooseVideoActivity extends BaseActivity implements View.OnClickListener{

    private List mVecFile;
    private ListView mLvshow;
    private FlipViewController flipView;
    private Button mHuoDongBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_video);
        initView();
        initData();
    }

    private void initView() {
        mLvshow = (ListView) findViewById(R.id.lv_show);
        mHuoDongBack = (Button)findViewById(R.id.huodong_back);

        mHuoDongBack.setOnClickListener(this);
        ActionBar actionBar = getActionBar();
        actionBar.show();actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    private void initData() {
        getFileName();
        RemindEatAdapter remindEatAdapter = new RemindEatAdapter();
        mLvshow.setAdapter(remindEatAdapter);
    }

    private void getFileName() {
//        String fileAbsolutePath = Environment.getExternalStorageDirectory()
//                .toString() + "/video/";
//        String fileAbsolutePath = "/sdcard/xUtils/";
        String fileAbsolutePath =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/xxx/ooo/";
        mVecFile = new ArrayList();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if (subFile == null){
            Toast.makeText(getApplicationContext(),"还没有视频", Toast.LENGTH_SHORT).show();
            return ;
        }else {
//            Toast.makeText(getApplicationContext(),subFile.length+"有视频", Toast.LENGTH_SHORT).show();
            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();
                    // 判断是否为MP4结尾
                    if (filename.trim().toLowerCase().endsWith(".mp4")) {
                        mVecFile.add(filename);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.huodong_back:
                finish();
                break;
        }
    }

    class RemindEatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mVecFile.size();
        }

        @Override
        public Object getItem(int position) {
            return mVecFile.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ChooseVideoActivity.ViewHolder holder = null;
            if (convertView == null) {
                holder = new ChooseVideoActivity.ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.view_video, null);
                convertView.setTag(holder);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.image = (ImageView)convertView.findViewById(R.id.iv);
            } else {
                holder = (ChooseVideoActivity.ViewHolder) convertView.getTag();
            }
            //根据文件名改名
            String name = (String) mVecFile.get(position);
            String filename = PreferencesUtils.getString(getApplicationContext(), name);
            holder.tvName.setText(filename);
//            holder.tvName.setText(name);
            holder.tvName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String url =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/xxx/ooo/"+(String) mVecFile.get(position);
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    String type = "video/mp4";
//                    Uri name = Uri.parse(url);
//                    intent.setDataAndType(name, type);
//                    intent.setClassName("com.han.hanhan.video", "com.han.hanhan.video.acitivity");
//                    intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                   startActivity(intent);
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://"+url), "video/*");
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
    class ViewHolder {
        public TextView tvName;
        public ImageView image;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // TODO Auto-generated method stub
//        getMenuInflater().inflate(R.menu.list, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // TODO Auto-generated method stub
//        switch (item.getItemId()) {
//            case R.id.action_top:
//                flipView.setSelection(0);
//                break;
//            case android.R.id.home:
//                finish();
//                Intent intent = new Intent();
//                intent.setClass(this, MainMenuActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
