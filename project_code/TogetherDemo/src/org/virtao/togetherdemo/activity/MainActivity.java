package org.virtao.togetherdemo.activity;


import org.virtao.togetherdemo.R;
import org.virtao.togetherdemo.R.id;
import org.virtao.togetherdemo.R.layout;
import org.virtao.togetherdemo.graphics.BokehPoint;
import org.virtao.togetherdemo.graphics.Color;
import org.virtao.togetherdemo.utils.Utils;
import org.virtao.togetherdemo.views.BokehView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	static final String TAG = "MainActivity";
	BokehView mMainBokehView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mMainBokehView = (BokehView) findViewById(R.id.bokeh_surface);		
		for (int i = 0; i < 500; i++) {
			mMainBokehView.addBokehPoint(Utils.randomBokeh());
		}
	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		//Log.v(TAG, Utils.formatString("View Size : height = %d, width = %d",  mMainBokehView.getViewHeight(), mMainBokehView.getViewWidth()));
		
		//BokehPoint tmp1 = new BokehPoint(mMainBokehView.getViewWidth(), mMainBokehView.getViewHeight(), 0, 0, 100000, new Color(255, 255, 255, 255), new Color(0, 0, 0, 127));


	}

	@Override
	protected void onResume() {
		super.onResume();
		//mMainBokehView.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMainBokehView.stop();
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		mMainBokehView.stop();
	}
}
