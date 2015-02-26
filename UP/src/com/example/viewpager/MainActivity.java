package com.example.viewpager;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class MainActivity extends Activity {
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView);
		View root = this.getLayoutInflater().inflate(R.layout.popup, null);

		final PopupWindow popup = new PopupWindow(root, 360, 360);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup.showAtLocation(findViewById(R.id.imageView),
						Gravity.CENTER, 20, 20);
				
			}
		});
		root.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup.dismiss();
			}
		});
	}

}
