package android.esports.zzdj.com.catchcrashevent.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.esports.zzdj.com.catchcrashevent.R;
import android.esports.zzdj.com.catchcrashevent.myinterface.BindView;
import android.esports.zzdj.com.catchcrashevent.utils.InjectUtils;
import android.esports.zzdj.com.catchcrashevent.utils.MyPermissionUtil;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String CAMERA = Manifest.permission.CAMERA;
    @BindView(id=R.id.btn1,clickable = true)
    private Button btn1;
    @BindView(id=R.id.btn2,clickable = true)
    private Button btn2;
    @BindView(id=R.id.viewpage)
    private ViewPager viewPager;
    // fragment对象集合
    private ArrayList<Fragment> fragmentsList;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        InjectUtils.inject(this); //--->必须在setContentView后调用这个方法
        fragmentsList = new ArrayList<Fragment>();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PermissonFragment();//我报名的
                fragmentsList.add(fragment);
                viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                        fragmentsList));
                viewPager.setCurrentItem(0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0同意 1拒绝后不再显示 -1拒绝
                int hasStoragePermission = MyPermissionUtil.with((AppCompatActivity) activity).has(WRITE_EXTERNAL_STORAGE);
                int hasCamerPermission = MyPermissionUtil.with((AppCompatActivity) activity).has(CAMERA);
                Log.i("LOG","hasStoragePermission:"+hasStoragePermission+"\n"+
                        "hasCamerPermission:"+hasCamerPermission);
                if(hasCamerPermission==1 ||hasStoragePermission==1){
                    //当拒绝了授权后，为提升用户体验，可以以弹窗的方式引导用户到设置中去进行设置
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    dialogBuilder.setMessage("需要开启存储和相机权限才能使用更新功能");
                    dialogBuilder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //引导用户到设置中去进行设置
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    dialogBuilder.create();
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.show();
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            ExitBy2Click();
        }
        return true;
    }
    /**
     * 双击退出程序
     */
    private boolean isExit = false;
    public void ExitBy2Click(){
        if (isExit == false) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出",
                    Toast.LENGTH_SHORT).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
