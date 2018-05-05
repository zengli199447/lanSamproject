package com.example.administrator.landapplication.ui.activity.main;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.PersonalToolsBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.ui.adapter.PersonalAdapter;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.ui.view.CircleImageView;
import com.example.administrator.landapplication.utils.AlbumUtils;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.AlbumCacheClass;
import com.example.administrator.landapplication.widget.CustomPopupWindow;
import com.example.administrator.landapplication.widget.DockingSymbol;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.durban.Durban;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/12.
 * 个人中心
 */

public class PersonalActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CustomPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.setting_view)
    ImageButton setting_view;
    @BindView(R.id.photo_view)
    CircleImageView photo_view;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.online_status)
    TextView online_status;
    @BindView(R.id.user_type)
    TextView user_type;
    @BindView(R.id.personal_listview)
    ListView personal_listview;
    private DataClass dataClass;
    private ArrayList<PersonalToolsBean> personalToolsBeen;
    private PersonalAdapter personalAdapter;
    private ShowDialog showDialog;
    private CustomPopupWindow customPopupWindow;
    private AlbumCacheClass albumCacheClass;
    private AlbumUtils albumUtils;
    private LoginUserInfo loginUserInfo;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OPEN_PICTURE:
                albumUtils.singlePictureSelect(this, true);
                break;
            case EventCode.COMMITE_UPPASSWORD:
                toastUtil.showToast("暂未开放");
                break;
            case EventCode.COMMITE_UPPHONENUMBER:
                dataClass.PhoneNumber = commonevent.getTemp_str();
                personalAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initClass() {
        dataClass = new DataClass();
        showDialog = ShowDialog.newInstance();
        customPopupWindow = new CustomPopupWindow(this);
        albumCacheClass = new AlbumCacheClass();
        albumUtils = AlbumUtils.newInstance();
    }

    @Override
    protected void initData() {
        loginUserInfo = dataManager.queryLoginUserInfo(UserBean.userName);
        personalToolsBeen = dataClass.initPersonalTools();
        personalAdapter = new PersonalAdapter(personalToolsBeen, this);

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.personal_center));
        if (loginUserInfo.getUserPhoto() != null && !loginUserInfo.getUserPhoto().isEmpty())
            Glide.with(this).load(loginUserInfo.getUserPhoto()).into(photo_view);
        user_name.setText(UserBean.userCName);
        online_status.setText(" 在线 ");
//        user_type.setText(UserBean.manType);
        personal_listview.setAdapter(personalAdapter);

    }

    @Override
    protected void initListener() {
        setting_view.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
        personal_listview.setOnItemClickListener(this);
        photo_view.setOnClickListener(this);
        customPopupWindow.setOnItemClickListener(this);
        customPopupWindow.setOnDismissListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.setting_view:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.photo_view:
                customPopupWindow.showAtLocation(findViewById(R.id.personal_listview), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                SystemUtil.windowToDark(this);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PersonalToolsBean personalToolsBean = personalToolsBeen.get(i);
        switch (personalToolsBean.getType()) {
            case 0:
                showDialog.showPhoneDialog(this, dataManager);
                break;
            case 1:
                showDialog.showPasswordDialog(this, dataManager);
                break;
        }

    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_select:
                albumUtils.singlePictureSelect(this, true);
                break;
            case R.id.id_btn_take_photo:
                albumUtils.TakePhoto(this);
                break;
            case R.id.id_btn_cancelo:
                break;
        }
        customPopupWindow.dismiss();
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "resultCode : " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case DockingSymbol.TAKEPHOTO:
                    String picturePath = data.getStringExtra("picturePath");
                    ArrayList<AlbumFile> albumFileselectList = albumCacheClass.getAlbumFileselectList();
                    ArrayList<String> pictureFiles = albumCacheClass.getPictureFiles();
                    pictureFiles.clear();
                    AlbumFile albumFile = new AlbumFile();
                    albumFile.setPath(picturePath);
                    albumFileselectList.add(albumFile);
                    pictureFiles.add(picturePath);
                    albumUtils.tailoring(pictureFiles, this);
                    break;
                case DockingSymbol.ALBUM_CODE_TAILORING:
                    String photoFile = Durban.parseResult(data).get(0);
                    Glide.with(this).load(photoFile).into(photo_view);
                    dataManager.UpDataLoginUserInfo(new LoginUserInfo(loginUserInfo.getId(), loginUserInfo.getUsername(), loginUserInfo.getPassword(), photoFile, loginUserInfo.getLoginIndex()));
                    RxBus.getDefault().post(new CommonEvent(EventCode.CAMERA_PICTURE, photoFile));
                    break;

            }
        }
    }


}
