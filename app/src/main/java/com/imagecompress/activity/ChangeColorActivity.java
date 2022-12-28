package com.imagecompress.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.imagecompress.R;
import com.imagecompress.utils.ImageHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeColorActivity extends AppCompatActivity implements SeekProgressBar.OnSeekBarChangeListener {

    private static final String TAG = "MainActivity";

    private ImageView mChangeColorIv;
    private SeekProgressBar mHueSeekBar, mSaturationSeekBar, mLumSeekBar;
    private Button mChooseButton, mSaveButton;

    private Bitmap mBitmap = null;

    private float mHue = 0, mSaturation = 1f, mLum = 1f;
    private static final int MID_VALUE = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);


        mChangeColorIv = (ImageView) findViewById(R.id.change_color_iv);
        mHueSeekBar = (SeekProgressBar) findViewById(R.id.hue_seek_bar);
        mHueSeekBar.setOnSeekBarChangeListener(this);
        mSaturationSeekBar = (SeekProgressBar) findViewById(R.id.saturation_seek_bar);
        mSaturationSeekBar.setOnSeekBarChangeListener(this);
        mLumSeekBar = (SeekProgressBar) findViewById(R.id.lum_seek_bar);
        mSaturationSeekBar.setOnSeekBarChangeListener(this);
        mChooseButton = (Button) findViewById(R.id.choose_btn);
        mChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });
        mSaveButton = (Button) findViewById(R.id.save_btn);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBitmap != null) {
                    mChangeColorIv.buildDrawingCache(true);
                    mChangeColorIv.buildDrawingCache();
                    Bitmap saveBitmap = mChangeColorIv.getDrawingCache();
                    String path = Environment.getExternalStorageDirectory().getPath() + "/DCIM/magicimage/";
                    File temp = new File(path);
                    if (!temp.exists()) {
                        temp.mkdirs();
                    }
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
                    String randStr = sdf.format(date);
                    String fileName = path + "img_" + randStr + ".jpg";
                    File file = new File(fileName);
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                        saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        bos.flush();
                        bos.close();
                        Toast.makeText(getApplicationContext(), "图片已保存到[ExternalStorageDirectory]/DCIM/magicimage/", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mChangeColorIv.setDrawingCacheEnabled(false);
                } else {
                    Toast.makeText(getApplicationContext(), "请先选择图片", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //获得图片资源
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ayaki);
//        mChangeColorIv.setImageBitmap(mBitmap);

        //对seekBar设置监听
        //mHueSeekBar.setOnSeekBarChangeListener(this);
        //mSaturationSeekBar.setOnSeekBarChangeListener(this);
        //mLumSeekBar.setOnSeekBarChangeListener(this);
        //SeekProgressBar.setProgress(mHueSeekBar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && null != data) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                mChangeColorIv.setImageBitmap(mBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onProgressChanged(int id) {

        switch (id) {
            case R.id.hue_seek_bar:
                //色相的范围是正负180
                mHue = 360*mHueSeekBar.getProgress()-180;
                Log.d(TAG, "onTouchEvent mHueSeekBar:" + mHueSeekBar.getProgress());
                Log.d(TAG, "onTouchEvent mHue:" + mHue);
                break;
            case R.id.saturation_seek_bar:
                //范围是0-2;
                mSaturation = 2*mSaturationSeekBar.getProgress();
                Log.d(TAG, "onTouchEvent mSaturationSeekBar:" + mSaturationSeekBar.getProgress());
                Log.d(TAG, "onTouchEvent mSaturation:" + mSaturation);
                break;
            case R.id.lum_seek_bar:
                mLum = mLumSeekBar.getProgress();
                Log.d(TAG, "onTouchEvent mLumSeekBar:" + mLumSeekBar.getProgress());
                Log.d(TAG, "onTouchEvent mLum:" + mLum);
                break;
        }

        if (mBitmap == null) {
            Toast.makeText(getApplicationContext(), "请先选择图片", Toast.LENGTH_SHORT).show();
        }

        if (mBitmap != null) {
            Bitmap bitmap = ImageHelper.getChangedBitmap(mBitmap, mHue, mSaturation, mLum);
            mChangeColorIv.setImageBitmap(bitmap);
        }

    }
}

