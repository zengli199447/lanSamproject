package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.utils.SystemUtil;


/**
 * Created by Administrator on 2018/3/19.
 */

public class ShowDialog {

    private static ShowDialog instance = new ShowDialog();

    private ShowDialog() {
    }

    public static ShowDialog newInstance() {
        return instance;
    }

    public ProgressDialog showProgressStatus(final Context context, String content) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.dialog, content);
        progressDialog.setCanceledOnTouchOutside(true);
        Window window = progressDialog.getWindow();
        window.setGravity(Gravity.CENTER);//此处可以设置dialog显示的位置
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //设置遮罩颜色
//                SystemUtil.windowToLight(context);
            }
        });
        return progressDialog;
    }

    public void showStatus(final Context context, int status, String content) {
        final StatusDialog statusDialog = new StatusDialog(context, R.style.dialog, status, content);
        statusDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showCacheDialog(final Context context, DataManager mDataManager) {
        final CacheDialog cacheDialog = new CacheDialog(context, R.style.dialog, mDataManager);
        cacheDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showVersionDialog(final Context context, String content) {
        final VersionDialog cacheDialog = new VersionDialog(context, R.style.dialog,content);
        cacheDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showPushDialog(final Context context, DataManager mDataManager) {
        final PushDialog pushDialog = new PushDialog(context, R.style.dialog, mDataManager);
        pushDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showPasswordDialog(final Context context, DataManager mDataManager) {
        final PasswordDialog passwordDialog = new PasswordDialog(context, R.style.dialog, mDataManager);
        passwordDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showSuperInputDialog(final Context context, int status, String title, String hint) {
        final SuperInputDialog superInputDialog = new SuperInputDialog(context, R.style.dialog, status, title, hint);
        superInputDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showPhoneDialog(final Context context, DataManager mDataManager) {
        final PhoneDialog phoneDialog = new PhoneDialog(context, R.style.dialog, mDataManager);
        phoneDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showSelectTypeDialog(final Context context, DataManager mDataManager, String status) {
        final SelectTypeDialog selectTypeDialog = new SelectTypeDialog(context, R.style.dialog, mDataManager, status);
        selectTypeDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showAttributeTypeDialog(final Context context, DataManager mDataManager, int status ,String aboutContent) {
        final AttributeTypeDialog attributeTypeDialog = new AttributeTypeDialog(context, R.style.dialog, mDataManager, status,aboutContent);
        attributeTypeDialog.show();
        SystemUtil.windowToDark(context);
    }

}
