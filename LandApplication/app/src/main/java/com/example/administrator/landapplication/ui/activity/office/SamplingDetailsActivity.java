package com.example.administrator.landapplication.ui.activity.office;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.AlbumUtils;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.DockingSymbol;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/20.
 * 采样详情
 */

public class SamplingDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.before_sampling)
    ImageView before_sampling;
    @BindView(R.id.in_the_sampling)
    ImageView in_the_sampling;
    @BindView(R.id.after_sampling)
    ImageView after_sampling;
    @BindView(R.id.the_northwest)
    ImageView the_northwest;
    @BindView(R.id.true_north)
    ImageView true_north;
    @BindView(R.id.the_northeast)
    ImageView the_northeast;
    @BindView(R.id.illicit_affair)
    ImageView illicit_affair;
    @BindView(R.id.east)
    ImageView east;
    @BindView(R.id.affair_south)
    ImageView affair_south;
    @BindView(R.id.south)
    ImageView south;
    @BindView(R.id.the_southeast)
    ImageView the_southeast;
    @BindView(R.id.text_the_northwest)
    TextView text_the_northwest;
    @BindView(R.id.text_true_north)
    TextView text_true_north;
    @BindView(R.id.text_the_northeast)
    TextView text_the_northeast;
    @BindView(R.id.text_illicit_affair)
    TextView text_illicit_affair;
    @BindView(R.id.text_east)
    TextView text_east;
    @BindView(R.id.text_affair_south)
    TextView text_affair_south;
    @BindView(R.id.text_south)
    TextView text_south;
    @BindView(R.id.text_the_southeast)
    TextView text_the_southeast;

    private int takePhotoInsertTag = 0;
    private AlbumUtils albumUtils;
    private HashMap<Integer, Integer> viewHashMap;
    private HashMap<Integer, Integer> viewTextHashMap;
    private HashMap<String, String> photoCongtentHashMap;
    private ShowDialog showDialog;
    private String[] PhotoKey = {"", "采样前", "采样中", "采样后", "西北", "正北", "东北", "正西", "正东", "西南", "正南", "东南"};


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.DICTIONARY_SAMPLINGDETAILS:
                if (!commonevent.getTemp_str().isEmpty()) {
                    initTakePhotoViewText(commonevent.getTemp_str());
                    albumUtils.TakePhoto(this);
                }
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sampling_details;
    }

    @Override
    protected void initClass() {
        albumUtils = AlbumUtils.newInstance();
        showDialog = ShowDialog.newInstance();
        viewHashMap = new HashMap<>();
        viewTextHashMap = new HashMap<>();
        photoCongtentHashMap = new HashMap<>();
    }

    @Override
    protected void initData() {
        viewHashMap.put(1, R.id.before_sampling);
        viewHashMap.put(2, R.id.in_the_sampling);
        viewHashMap.put(3, R.id.after_sampling);
        viewHashMap.put(4, R.id.the_northwest);
        viewHashMap.put(5, R.id.true_north);
        viewHashMap.put(6, R.id.the_northeast);
        viewHashMap.put(7, R.id.illicit_affair);
        viewHashMap.put(8, R.id.east);
        viewHashMap.put(9, R.id.affair_south);
        viewHashMap.put(10, R.id.south);
        viewHashMap.put(11, R.id.the_southeast);

        viewTextHashMap.put(4, R.id.text_the_northwest);
        viewTextHashMap.put(5, R.id.text_true_north);
        viewTextHashMap.put(6, R.id.text_the_northeast);
        viewTextHashMap.put(7, R.id.text_illicit_affair);
        viewTextHashMap.put(8, R.id.text_east);
        viewTextHashMap.put(9, R.id.text_affair_south);
        viewTextHashMap.put(10, R.id.text_south);
        viewTextHashMap.put(11, R.id.text_the_southeast);
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.sampling_details));
        img_btn_black.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        for (int i = 1; i < viewHashMap.size() + 1; i++) {
            findViewById(viewHashMap.get(i)).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
        int key = getKey(view.getId());
        takePhotoInsertTag = key;
        if (key > 0 && key < 4) {
            albumUtils.TakePhoto(this);
        } else if (key > 3 && key < 12) {
            showDialog.showAttributeTypeDialog(this, dataManager, EventCode.DICTIONARY_SAMPLINGDETAILS, getString(R.string.peripheral_location));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case DockingSymbol.TAKEPHOTO:
                    String picturePath = data.getStringExtra("picturePath");
                    initTakePhotoView(picturePath);
                    break;
            }
        }
    }

    private void initTakePhotoView(String picturePath) {
        ImageView imageView = (ImageView) findViewById(viewHashMap.get(takePhotoInsertTag));
        Glide.with(this).load(picturePath).into(imageView);
        photoCongtentHashMap.put(PhotoKey[takePhotoInsertTag], picturePath);
    }

    private void initTakePhotoViewText(String pictureContent) {
        TextView textView = (TextView) findViewById(viewTextHashMap.get(takePhotoInsertTag));
        SystemUtil.textMagicTool(this, textView, new StringBuffer().append(textView.getText().toString()).append(": ").toString(), pictureContent);
    }

    private Integer getKey(Integer value) {
        Integer key = 0;
        for (Integer getKey : viewHashMap.keySet()) {
            if (getKey != null && viewHashMap.get(getKey) != null && viewHashMap.get(getKey).equals(value)) {
                key = getKey;
            }
        }
        return key;
    }


}
