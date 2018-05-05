package com.example.administrator.landapplication.ui.activity.office;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.AboutDataBean;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.AlbumUtils;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.AboutPopupWindow;
import com.example.administrator.landapplication.widget.CustomPopupWindow;
import com.example.administrator.landapplication.widget.DockingSymbol;


import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/17.
 * 变更请求
 */

public class ChangeRequestActivity extends BaseActivity implements View.OnClickListener, CustomPopupWindow.OnItemClickListener, AboutPopupWindow.OnAboutItemClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.photo_view)
    ImageView photo_view;
    @BindView(R.id.change_the_reason)
    RelativeLayout change_the_reason;
    @BindView(R.id.note_edit)
    EditText note_edit;
    @BindView(R.id.button_submit)
    TextView button_submit;
    @BindView(R.id.tv_about)
    TextView tv_about;
    private ShowDialog showDialog;
    private CustomPopupWindow customPopupWindow;
    private AlbumUtils albumUtils;
    private HashMap<Integer, AboutDataBean> aboutDataBeenMap;
    private AboutPopupWindow aboutPopupWindow;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REFRESF_PICTURE:
                Glide.with(this).load(commonevent.getTemp_str()).into(photo_view);
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_change_request;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        customPopupWindow = new CustomPopupWindow(this);
        albumUtils = AlbumUtils.newInstance();
    }

    @Override
    protected void initData() {
        aboutDataBeenMap = new HashMap<>();
        aboutDataBeenMap.put(0, new AboutDataBean("无法挖掘", 0));
        aboutDataBeenMap.put(1, new AboutDataBean("点位不正确", 0));
        aboutDataBeenMap.put(2, new AboutDataBean("其他原因", 0));
        aboutPopupWindow = new AboutPopupWindow(this, aboutDataBeenMap);
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.the_change_request));
        img_btn_black.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        photo_view.setOnClickListener(this);
        change_the_reason.setOnClickListener(this);
        button_submit.setOnClickListener(this);
        customPopupWindow.setOnItemClickListener(this);
        aboutPopupWindow.setOnAboutItemClickListener(this);
        customPopupWindow.setOnDismissListener(this);
        aboutPopupWindow.setOnDismissListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.photo_view:
                customPopupWindow.showAtLocation(findViewById(R.id.button_submit), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                SystemUtil.windowToDark(this);
                break;
            case R.id.change_the_reason:
                aboutPopupWindow.showAtLocation(findViewById(R.id.button_submit), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                SystemUtil.windowToDark(this);
                break;
            case R.id.button_submit:

                break;

        }
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_select:
                albumUtils.singlePictureSelect(this, false);
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
    public void setOnAboutItemClick(View v, AboutDataBean aboutDataBean) {
        switch (v.getId()) {
            case R.id.cancle:
                aboutPopupWindow.dismiss();
                break;
            case R.id.commit:
                if (aboutDataBean != null) {
                    String content = aboutDataBean.getContent();
                    tv_about.setText(content);
                } else {
                    toastUtil.showToast("null");
                }
                aboutPopupWindow.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case DockingSymbol.TAKEPHOTO:
                    String picturePath = data.getStringExtra("picturePath");
                    Glide.with(this).load(picturePath).into(photo_view);
                    break;
            }
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

}
