/*******************************************************************************
 * Copyright (c) 2015 by dennis Corporation all right reserved.
 * 2015-2-2 
 * 
 *******************************************************************************/ 
package com.example.viewpager;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * <pre>
 * ҵ����:
 * ����˵��: 
 * ��д����:	2015-2-2
 * ����:	 ������
 * 
 * ��ʷ��¼
 * 1���޸����ڣ�
 *    �޸��ˣ�
 *    �޸����ݣ�
 * </pre>
 */
public class PhotoUpload extends Activity {
    private String newName ="image.jpg";
    private String uploadFile ="/sdcard/image.JPG";
    private String actionUrl ="http://www.yasite.net/api/browser/?path=2015-02-02";
    private TextView mText1;
    private TextView mText2;
    private Button mButton;
    @Override
      public void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        mText1 = (TextView) findViewById(R.id.btn_tuku);
        //"�ļ�·����\n"+
        mText1.setText(uploadFile);	
        mText2 = (TextView) findViewById(R.id.btn_Camera);
        //"�ϴ���ַ��\n"+
        mText2.setText(actionUrl);
        /* ����mButton��onClick�¼����� */    
        mButton = (Button) findViewById(R.id.close);
        mButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View v)
          {
            uploadFile();
          }
        });
      }
      /* �ϴ��ļ���Server�ķ��� */
      private void uploadFile()
      {
        String end ="\r\n";
        String twoHyphens ="--";
        String boundary ="*****";
        try
        {
          URL url =new URL(actionUrl);
          HttpURLConnection con=(HttpURLConnection)url.openConnection();
          /* ����Input��Output����ʹ��Cache */
          con.setDoInput(true);
          con.setDoOutput(true);
          con.setUseCaches(false);
          /* ���ô��͵�method=POST */
          con.setRequestMethod("POST");
          /* setRequestProperty */
          con.setRequestProperty("Connection", "Keep-Alive");
          con.setRequestProperty("Charset", "UTF-8");
          con.setRequestProperty("Content-Type",
                             "multipart/form-data;boundary="+boundary);
          /* ����DataOutputStream */
          DataOutputStream ds =
            new DataOutputStream(con.getOutputStream());
          ds.writeBytes(twoHyphens + boundary + end);
          ds.writeBytes("Content-Disposition: form-data; "+
                        "name=\"file1\";filename=\""+
                        newName +"\""+ end);
          ds.writeBytes(end);  
          /* ȡ���ļ���FileInputStream */
          FileInputStream fStream =new FileInputStream(uploadFile);
          /* ����ÿ��д��1024bytes */
          int bufferSize =1024;
          byte[] buffer =new byte[bufferSize];
          int length =-1;
          /* ���ļ���ȡ������������ */
          while((length = fStream.read(buffer)) !=-1)
          {
            /* ������д��DataOutputStream�� */
            ds.write(buffer, 0, length);
          }
          ds.writeBytes(end);
          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
          fStream.close();
          ds.flush();
          /* ȡ��Response���� */
          InputStream is = con.getInputStream();
          int ch;
          StringBuffer b = new StringBuffer();
          while( ( ch = is.read() ) !=-1 )
          {
            b.append( (char)ch );
          }
          /* ��Response��ʾ��Dialog */
          showDialog("�ϴ��ɹ�"+b.toString().trim());
          /* �ر�DataOutputStream */
          ds.close();
        }
        catch(Exception e)
        {
          showDialog("�ϴ�ʧ��"+e);
        }
      }
      
      
      /* �ϴ��ļ���װ*/
    	  private void uploadFole1(){ 
    		  String path;
    		  String picpath;
    	  }
      /* ��ʾDialog��method */
      private void showDialog(String mess)
      {
        new AlertDialog.Builder(PhotoUpload.this).setTitle("Message")
         .setMessage(mess)
         .setNegativeButton("ȷ��",new DialogInterface.OnClickListener()
         {
           public void onClick(DialogInterface dialog, int which)
           {          
           }
         })
         .show();
      }
    }
