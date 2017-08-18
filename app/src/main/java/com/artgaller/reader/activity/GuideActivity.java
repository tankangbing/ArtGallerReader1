package com.artgaller.reader.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.artgaller.reader.views.BaseActivity;
import com.artgaller.reader.R;

public class GuideActivity extends BaseActivity{
	private ViewPager viewPager;
	private View view1,view2,view3;
	private List<View> viewList;//view数组 
	private Context context;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.guide_activity);
		context = this;
		sp = getSharedPreferences("SP", MODE_PRIVATE);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		viewPager = (ViewPager) findViewById(R.id.guide_vp);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater inflater=getLayoutInflater();  
        view1 = inflater.inflate(R.layout.guide1, null);  
        view2 = inflater.inflate(R.layout.guide2,null);  
//        view3 = inflater.inflate(R.layout.guide3, null);  
		
		viewList = new ArrayList<View>();
		viewList.add(view1);
		viewList.add(view2);
//		viewList.add(view3);
		
		PagerAdapter pagerAdapter = new PagerAdapter() {  
            
            @Override  
            public boolean isViewFromObject(View arg0, Object arg1) {  
                // TODO Auto-generated method stub  
                return arg0 == arg1;  
            }  
              
            @Override  
            public int getCount() {  
                // TODO Auto-generated method stub  
                return viewList.size();  
            }  
              
            @Override  
            public void destroyItem(ViewGroup container, int position,  
                    Object object) {  
                // TODO Auto-generated method stub  
                container.removeView(viewList.get(position));  
            }  
              
            @Override  
            public Object instantiateItem(ViewGroup container, int position) {  
                // TODO Auto-generated method stub  
                container.addView(viewList.get(position));  
                if (position == 1) {
                	Button button = (Button) viewList.get(position).findViewById(R.id.guide_btn);
                	button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Editor editor = sp.edit();
							editor.putBoolean("first", false);
							editor.commit();
							Intent intent = new Intent();
							intent.setClass(context, LoginActivity.class);
							startActivity(intent);
							finish();
						}
					});
				}
                  
                return viewList.get(position);  
            }  
        };  
          
        viewPager.setAdapter(pagerAdapter);  
	}
	
}
