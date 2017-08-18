package com.artgaller.reader.activity;

import com.artgaller.reader.R;
import com.artgaller.reader.adapter.MainMenuAdapter;
import com.artgaller.reader.data.CameraBeen;
import com.artgaller.reader.data.JieKouBeen;
import com.artgaller.reader.utils.BookDBHelper;
import com.artgaller.reader.utils.PreferencesUtils;
import com.artgaller.reader.views.BaseActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainMenuActivity extends BaseActivity implements OnClickListener {

	// private FlipViewController flipView;
//	private ImageButton btn1;
//	private ImageButton btn2;
//	private ImageButton btn3;
//	private ImageButton btn4;
//	private ImageButton btn5;
//	private ImageButton btn6;
//	private ImageButton btn7;
//	private ImageButton btn8;
//	private ImageButton btn9;
	private Button personal;
	private Context context;
	
	private GridView gridView;
	
	private SharedPreferences sp;
	private TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_menu);
		context = this;

		this.sp = context.getSharedPreferences("SP", Context.MODE_PRIVATE);
		this.tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		jiekou();
//		btn1 = (ImageButton) findViewById(R.id.menu_Btn1);
//		btn2 = (ImageButton) findViewById(R.id.menu_Btn2);
//		btn3 = (ImageButton) findViewById(R.id.menu_Btn3);
//		btn4 = (ImageButton) findViewById(R.id.menu_Btn4);
//		btn5 = (ImageButton) findViewById(R.id.menu_Btn5);
//		btn6 = (ImageButton) findViewById(R.id.menu_Btn6);
//		btn7 = (ImageButton) findViewById(R.id.menu_Btn7);
//		btn8 = (ImageButton) findViewById(R.id.menu_Btn8);
//		btn9 = (ImageButton) findViewById(R.id.menu_Btn9);
//		btn1.setOnClickListener(this);
//		btn2.setOnClickListener(this);
//		btn3.setOnClickListener(this);
//		btn4.setOnClickListener(this);
//		btn5.setOnClickListener(this);
//		btn6.setOnClickListener(this);
//		btn7.setOnClickListener(this);
//		btn8.setOnClickListener(this);
//		btn9.setOnClickListener(this);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		TextView daohanlan = (TextView) findViewById(R.id.daohanglan);
        daohanlan.setText("群众文化");

		personal = (Button) findViewById(R.id.main_menu_list_set);
		personal.setOnClickListener(this);
		
		gridView = (GridView) findViewById(R.id.mm_gridview);
		MainMenuAdapter adapter = new MainMenuAdapter(context);
		gridView.setAdapter(adapter);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (sp.getBoolean("menuChange", false)) {
			MainMenuAdapter adapter = new MainMenuAdapter(context);
			gridView.setAdapter(adapter);
			Editor editor = sp.edit();
			editor.putBoolean("menuChange", false);
			editor.commit();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Dialog dialog = new AlertDialog.Builder(MainMenuActivity.this)
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	SharedPreferences sp = context.getSharedPreferences("SP",
            				context.MODE_PRIVATE);
                	BookDBHelper bookDBHelper = new BookDBHelper(getApplicationContext());
                	Cursor cursor = bookDBHelper.select(null, "book_dlflag=? and username=?", new String[]{"false", sp.getString("UserName", "")});
                	while(cursor.moveToNext()){
                		System.out.println("退出重下");
                		bookDBHelper.update(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                				cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                				"false", "true", sp.getString("UserName", ""));
                	}
                	((MainMenuActivity)MainMenuActivity.this).quit();
                }
            })//设置确定按钮
            .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            })//设置取消按钮
            .create();
			dialog.show();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		Uri uri;
		switch (v.getId()) {
//		case R.id.menu_Btn1:
//			intent.setClass(this, LocalShuJiaActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn2:
//			intent.setClass(this, KechengActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn3:
//			intent.setClass(this, ZiLiaoJiaActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn4:
////			intent.setClass(this, XiaoXiActivity.class);
//			intent.setClass(this, ZhiChangListActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn5:
//			intent.setClass(this, YaoYiYaoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn6:
//			intent.setClass(this, MipcaActivityCapture.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn7:
//			intent.setClass(this, ClassicBooksMainActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn8:
//			intent.setClass(this, PublicClassMainActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn9:
//			intent.setClass(this, HuoDongListActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
//		case R.id.menu_Btn10:
//			bundle = new Bundle();
//			bundle.putString("url", "http://data.iego.net:85/act/actLIst.aspx?"
//					+ "n=" + sp.getString("UserName", "")
//					+ "&d=" + sp.getString("KEY","" )
//					+ "&imei=" + tm.getDeviceId());
//			intent.putExtras(bundle);
//			intent.setClass(this, NetActivity.class);
//			startActivity(intent);
			
//			intent.setClass(this, HuoDongListActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			break;
		case R.id.main_menu_list_set:
			intent.setClass(this, Personal_Settings.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}
		overridePendingTransition(R.anim.my_scale_action,
				R.anim.my_alpha_action);
	}
	
	public void quit(){
		this.finish();
	}

	//请求接口
	private void jiekou() {
		try {
//			+"&unit=" + URLEncoder.encode(sp.getString("DanWei", ""), "UTF-8")
			SharedPreferences sp = context.getSharedPreferences("SP",
					context.MODE_PRIVATE);
			String url = "http://data.iego.net:85/qyg/routejson.ashx?"	+"&unit=" + URLEncoder.encode(sp.getString("DanWei", ""), "UTF-8");
//			OkHttpClient okHttpClient = new OkHttpClient();
//			Request.Builder requestBuilder = new Request.Builder().url(url);
//			requestBuilder.method("GET",null);
//			Request request = requestBuilder.build();
//			Call mcall= okHttpClient.newCall(request);
//			mcall.enqueue(new Callback() {
//				@Override
//				public void onFailure(Call call, IOException e) {
//
//				}
//
//				@Override
//				public void onResponse(Call call, Response response) throws IOException {
//					String str;
//					if (null != response.cacheResponse()) {
//						str = response.cacheResponse().toString();
//					} else {
//						response.body().string();
//						str = response.networkResponse().toString();
//					}
//					gsonforResult(str);
//				}
//			});


			OkHttpUtils
					.get()
					.url(url)
					.build()
					.execute(new StringCallback() {
						@Override
						public void onError(com.squareup.okhttp.Request request, Exception e) {

						}

						@Override
						public void onResponse(String response) {
							String s = response.toString();
//							gsonforResult(s);
							jsonArray(s);
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void jsonArray(String s) {
		try {
			JSONArray array = new JSONArray(s);
			JSONObject jsonObject = array.getJSONObject(0);
			String server = jsonObject.getString("server");
			PreferencesUtils.putString(getApplicationContext(),"server",server);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void gsonforResult(String result) {
		Gson gson = new Gson();
		JieKouBeen jieKouBeen = gson.fromJson(result, JieKouBeen.class);
		String server = jieKouBeen.getServer();
		PreferencesUtils.putString(getApplicationContext(),"server",server);

	}
}
