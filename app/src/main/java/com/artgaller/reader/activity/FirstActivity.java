package com.artgaller.reader.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.artgaller.reader.views.BaseActivity;

public class FirstActivity extends BaseActivity{
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("SP", MODE_PRIVATE);
		Intent intent = new Intent();
		if(sp.getBoolean("first", true)){
			intent.setClass(this, GuideActivity.class);
			startActivity(intent);
		}else{
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
		}
		finish();
	}
}
