package com.imagecompress.activity;


import android.Manifest;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.imagecompress.R;
import com.imagecompress.base.BaseActivity;
import com.imagecompress.dialog.ExitDialog;
import com.imagecompress.permission.imp.OnPermissionsResult;
import com.imagecompress.toast.Toasts;
import android.widget.Toast;

import java.util.List;

import utils.FileUtils;

public class MainActivity extends AppCompatActivity {
    /*private ExitDialog mExitDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initPermission() {

        requestPermission(new OnPermissionsResult() {
            @Override
            public void onAllow(List<String> allowPermissions) {
                MainActivity.super.initPermission();
            }

            @Override
            public void onNoAllow(List<String> noAllowPermissions) {
                Toasts.show("内存卡读写为必要权限");
                finish();
            }

            @Override
            public void onForbid(List<String> noForbidPermissions) {
                showForbidPermissionDialog("读写内存卡");
                finish();
            }

            @Override
            public void onLowVersion() {
                MainActivity.super.initPermission();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void initEvent() {
        findViewById(R.id.btn_sing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SingChoiceImageActivity.class));
            }
        });

        findViewById(R.id.btn_sharpening).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SharpeningActivity.class));
            }
        });

        /*findViewById(R.id.btn_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MultipleChoiceImageActivity.class));
            }
        });

        findViewById(R.id.btn_coloring).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ChangeColorActivity.class));
            }
        });

        findViewById(R.id.btn_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FilterActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (mExitDialog == null) {
            mExitDialog = new ExitDialog(this);
        }
        if (!mExitDialog.isShowing())
            mExitDialog.show();
        mExitDialog.setOnExitDialogClickListener(new ExitDialog.OnExitDialogClickListener() {
            @Override
            public void onConfirmListener(boolean isChecked) {
                if (isChecked){
                    FileUtils.deleteAllFile(FileUtils.getFileDirectorHead(MainActivity.this.getApplicationContext()));
                }
                MainActivity.super.onBackPressed();
            }
        });
    }*/

    CircleMenu circleMenu;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleMenu = findViewById(R.id.circle_menu);
        constraintLayout = findViewById(R.id.constraint_layout);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.menu,R.mipmap.cancel)
                .addSubMenu(Color.parseColor("#88BEF5"),R.mipmap.color)
                .addSubMenu(Color.parseColor("#83E85A"),R.mipmap.compress)
                .addSubMenu(Color.parseColor("#FF4B32"),R.mipmap.filter)
                .addSubMenu(Color.parseColor("#BA53DE"),R.mipmap.process)
                .addSubMenu(Color.parseColor("#FF8A5C"),R.mipmap.exit)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        switch (index) {
                            case 0:
                                Toast.makeText(MainActivity.this, "色彩调整", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#ECFFFB"));
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this, "图像压缩", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#96F7D2"));
                                break;
                            case 2:
                                Toast.makeText(MainActivity.this, "滤镜", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#FAC4A2"));
                                break;
                            case 3:
                                Toast.makeText(MainActivity.this, "图像处理", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#D3CDE6"));
                                break;
                            case 4:
                                Toast.makeText(MainActivity.this, "退出", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#FFF591"));
                                break;
                            }
                        }
                    }
                );
    }
}

