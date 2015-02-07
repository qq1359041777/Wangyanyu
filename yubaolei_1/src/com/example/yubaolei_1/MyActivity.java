package com.example.yubaolei_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MyActivity extends Activity implements OnClickListener {

	Button b1, b2;
	ImageView imageView1;
	private final int RESULT_CODE = 1;
	private final int SAVE_CODE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.b1:
			Intent intent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, RESULT_CODE);
			break;
		case R.id.b2:
			Intent intent1 = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent1, SAVE_CODE);
			break;

		default:
			break;
		}
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RESULT_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");
				imageView1.setImageBitmap(bitmap);
			}

			break;
		case SAVE_CODE:
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				imageView1.setImageURI(uri);
			}
			break;
		default:
			break;
		}
	}

}
