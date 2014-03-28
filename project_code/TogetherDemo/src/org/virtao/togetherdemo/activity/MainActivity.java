package org.virtao.togetherdemo.activity;


import org.virtao.togetherdemo.R;
import org.virtao.togetherdemo.R.id;
import org.virtao.togetherdemo.R.layout;
import org.virtao.togetherdemo.graphics.BokehPoint;
import org.virtao.togetherdemo.graphics.Color;
import org.virtao.togetherdemo.views.BokehView;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	BokehView mMainBokehView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mMainBokehView = (BokehView) findViewById(R.id.bokeh_surface);
		BokehPoint tmp1 = new BokehPoint(600, 800, 0, 0, 100000, new Color(255, 255, 255, 255), new Color(0, 0, 0, 127));
		mMainBokehView.addBokehPoint(tmp1);
		
	}
}
