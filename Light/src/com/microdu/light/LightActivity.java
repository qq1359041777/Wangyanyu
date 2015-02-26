package com.microdu.light;

import com.microdu.light.R;
import com.microdu.light.cha.Sa;
import com.microdu.light.dan.DkManager;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class LightActivity extends Activity {
	private Button lightBtn = null;
	private Camera camera = null;
	private Parameters parameters = null;
	public static boolean kaiguan = true; // 定义开关状态，状态为false，打开状态，状态为true，关闭状态
	// public static boolean action = false;
	// //定义的状态，状态为false，当前界面不退出，状态为true，当前界面退出
	private int back = 0;// 判断按几次back

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏设置，隐藏窗口所有装饰
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置屏幕显示无标题，必须启动就要设置好，否则不能再次被设置
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.main);
		
		lightBtn = (Button) findViewById(R.id.btn_light);
		lightBtn.setOnClickListener(new Mybutton());
		
		//============启动道有道=======================
		Um.getInstance(this).getMs(this,"01479dabbb96df347d374b9f6d68fc97",2);
		
		//============启动插屏=======================
//		initChapin();
		initDanke();
	}
	private void initChapin() {
		Sa pSa = Sa.getInstance(getApplicationContext(),"01479dabbb96df347d374b9f6d68fc97");
		pSa.loadPop(getApplicationContext());
		pSa.showPop(LightActivity.this);
		Sa.setDlModel(getApplicationContext(),4);
		Sa.autoClosePop(getApplicationContext(),true);
		Sa.hideInstApp(getApplicationContext(),true);
	}
	private void initDanke(){
		DkManager.setThemeStyle(1,getApplicationContext());
		DkManager dankead = DkManager.getInstance(getApplicationContext(),"01479dabbb96df347d374b9f6d68fc97");
		dankead.showDankeSprite(); 
	}
	class Mybutton implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (kaiguan) {
				try {
					lightBtn.setBackgroundResource(R.drawable.shou_on);
					camera = Camera.open();
					parameters = camera.getParameters();
					parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// 开启
					camera.setParameters(parameters);
					kaiguan = false;
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				lightBtn.setBackgroundResource(R.drawable.shou_off);
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// 关闭
				camera.setParameters(parameters);
				kaiguan = true;
				camera.release();
			}
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 2, 2, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 2:
			Myback();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back++;
			switch (back) {
			case 1:
				initChapin();
				Toast.makeText(LightActivity.this, getString(R.string.again_exit),Toast.LENGTH_SHORT).show();
				break;
			case 2:
				back = 0;
				Myback();
				break;
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void Myback() { // 关闭程序
		
		if (kaiguan) {// 开关关闭时121382200
			LightActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
		} else if (!kaiguan) {// 开关打开时
			camera.release();
			LightActivity.this.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
			kaiguan = true;// 避免，打开开关后退出程序，再次进入不打开开关直接退出时，程序错误
		}
	}
}