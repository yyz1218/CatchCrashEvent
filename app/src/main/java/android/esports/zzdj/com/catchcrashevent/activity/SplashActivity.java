package android.esports.zzdj.com.catchcrashevent.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.esports.zzdj.com.catchcrashevent.R;
import android.esports.zzdj.com.catchcrashevent.utils.Func;
import android.esports.zzdj.com.catchcrashevent.utils.MyPermissionUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String CAMERA = Manifest.permission.CAMERA;
    private static final int REQUEST_CODE_STORAGE = 2;
    private static final int REQUEST_CODE_BOTH = 3;
    private static final int REQUEST_CODE_CAMER = 4;
    private MyPermissionUtil.PermissionRequestObject mBothPermissionRequest;
    private MyPermissionUtil.PermissionRequestObject mContactsPermissionRequest;
    private MyPermissionUtil.PermissionRequestObject mStoragePermissionRequest;
    private MyPermissionUtil.PermissionRequestObject mCamerPermissionRequest;
    private Activity activity;
    private boolean isAgree = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        onAskBothPermissionsClick();
        onAskForStoragePermissionClick();
    }

    public void onAskBothPermissionsClick() {
        //0同意 1拒绝后不再显示 -1拒绝
//        int hasStoragePermission = MyPermissionUtil.with((AppCompatActivity) activity).has(WRITE_EXTERNAL_STORAGE);
//        int hasCamerPermission = MyPermissionUtil.with((AppCompatActivity) activity).has(CAMERA);
//        Log.i("LOG","hasStoragePermission:"+hasStoragePermission+"\n"+
//                "hasCamerPermission:"+hasCamerPermission);
//        if(hasCamerPermission!=  && hasStoragePermission){
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 2000);
//        }
// else{
//            mBothPermissionRequest = MyPermissionUtil.with(this)
//                    .request(WRITE_EXTERNAL_STORAGE,CAMERA)
//                    .onResult(new FuncMore() {
//                        @Override
//                        protected void call(int requestCode, String[] permissions, int[] grantResults) {
//                            Log.i("LOG"," requestCode值："+requestCode);
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }, 2000);
//                        }
//                    }).ask(REQUEST_CODE_BOTH);
//        }

    }
    //存储权限
    public void onAskForStoragePermissionClick() {
        mStoragePermissionRequest = MyPermissionUtil.with(this).request(WRITE_EXTERNAL_STORAGE).onAllGranted(new Func() {
            @Override protected void call() {
                onAskForCamerPermissionClick();
            }
        }).onAnyDenied(new Func() {
            @Override protected void call() {
                onAskForCamerPermissionClick();
            }
        }).ask(REQUEST_CODE_STORAGE);

    }

    public void onAskForCamerPermissionClick() {
        mCamerPermissionRequest = MyPermissionUtil.with(this).request(CAMERA);
        mCamerPermissionRequest.onAllGranted(new Func() {
            @Override protected void call() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        }).onAnyDenied(new Func() {
            @Override protected void call() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        }).ask(REQUEST_CODE_CAMER );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mStoragePermissionRequest != null) {
            mStoragePermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (mContactsPermissionRequest != null) {
            mContactsPermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (mBothPermissionRequest != null) {
            mBothPermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if (mCamerPermissionRequest != null) {
            mCamerPermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
