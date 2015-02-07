package com.example.yubaolei_1;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static String requestURL = "http://www.yasite.net/api/upload.php";

	ImageView imageView1, image;
	PopupWindow pw;
	private final int RESULT_CODE = 1;
	private final int SAVE_CODE = 2;
	private String picPath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		image = (ImageView) findViewById(R.id.image);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inflater = getLayoutInflater();
				View view = inflater.inflate(R.layout.pop, null);
				pw = new PopupWindow(view, 150, 180);

				pw.showAsDropDown(view, 0, 140);
				pw.setFocusable(true);
				TextView textView = (TextView) view.findViewById(R.id.xiangji);
				TextView textView1 = (TextView) view.findViewById(R.id.tuku);

				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(MainActivity.this, "启动相机", 0).show();
						Intent intent = new Intent(
								android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intent, RESULT_CODE);
					}
				});

				textView1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "打开图库", 0).show();
						Intent intent1 = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(intent1, SAVE_CODE);
					}
				});
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && null != data) {

			Uri uri = data.getData();
			Log.e("<<<<<<<<<<<<<<", "uri = " + uri);
			try {
				String[] pojo = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(uri, pojo, null,
						null, null);
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);

					if (path.endsWith("jpg") || path.endsWith("png")) {
						picPath = path;
						Bitmap bitmap = BitmapFactory.decodeStream(cr
								.openInputStream(uri));
						image.setImageBitmap(bitmap);
						if (picPath == null) {
							Toast.makeText(MainActivity.this, "请选择图片！", 1000)
									.show();
						} else {
							new Thread() {
								public void run() {
									String result = HttpUtil.uploadFile(
											requestURL, picPath);
									Log.e("<<<<<<<<<<<<<<", result);
								};
							}.start();
						}
					} else {
						alert();
					}
				} else {
					alert();
				}

			} catch (Exception e) {
			}
		}
	}

	private void alert() {
		Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
				.setMessage("您选择的不是有效的图片")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						picPath = null;
					}
				}).create();
		dialog.show();
	}

}
