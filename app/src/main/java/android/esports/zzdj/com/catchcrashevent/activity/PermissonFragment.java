package android.esports.zzdj.com.catchcrashevent.activity;

import android.Manifest;
import android.esports.zzdj.com.catchcrashevent.utils.MyPermissionUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.esports.zzdj.com.catchcrashevent.R;

/**
 *
 */
public class PermissonFragment extends Fragment {

    public static final String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String CAMERA = Manifest.permission.CAMERA;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int permissionCheck;//0同意 1拒绝后不再显示 -1拒绝
        int hasStoragePermission = MyPermissionUtil.with(PermissonFragment.this).has(STORAGE);
        int hasCamerPermission = MyPermissionUtil.with(PermissonFragment.this).has(CAMERA);
        Log.i("LOG","hasStoragePermission:"+hasStoragePermission+"\n"+
                "hasCamerPermission:"+hasCamerPermission);
        return inflater.inflate(R.layout.fragment_permisson, container, false);
    }
}
