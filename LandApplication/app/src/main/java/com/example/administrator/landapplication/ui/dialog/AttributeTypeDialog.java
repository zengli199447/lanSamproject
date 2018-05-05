package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseDialog;
import com.example.administrator.landapplication.bean.AttributeTypeBean;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentChildInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.ui.adapter.AttributeTypeAdapter;
import com.example.administrator.landapplication.ui.view.AutoListView;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/21.
 * 通用类型选择
 */

public class AttributeTypeDialog extends BaseDialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;
    private int status;
    @BindView(R.id.about_edit)
    EditText about_edit;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.empty_input_view)
    View empty_input_view;
    @BindView(R.id.about_type)
    TextView about_type;
    @BindView(R.id.attrbute_type_list)
    AutoListView attrbute_type_list;
    @BindView(R.id.layout_view)
    RelativeLayout layout_view;

    private String beforeText;
    private ArrayList<AttributeTypeBean> attributeTypeBeanArrayList = new ArrayList<>();
    private AttributeTypeAdapter attributeTypeAdapter;
    private DataManager mDataManager;
    private final String[] tableNames = new String[]{"LandUse", "SoilType", "SoilAndTheClass", "CropTypes", "IrrigationWaterType", "Topography", "SamplingEquipment", "OrganicSample",
            "InorganicSample", "SoilColor", "SoilTexture", "SoilMoisture", "Weather", "PeripheralLocation"};
    private String aboutContent;

    protected AttributeTypeDialog(Context context, @StyleRes int themeResId, DataManager mDataManager, int status, String aboutContent) {
        super(context, themeResId);
        this.context = context;
        this.status = status;
        this.mDataManager = mDataManager;
        this.aboutContent = aboutContent;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_attribute_type;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        initSQLData();
    }

    @Override
    protected void initView() {
        btn_commit.setText("确认");
        attributeTypeAdapter = new AttributeTypeAdapter(attributeTypeBeanArrayList, context);
        attrbute_type_list.setAdapter(attributeTypeAdapter);
        about_edit.clearFocus();
    }

    @Override
    protected void initListener() {
        btn_commit.setOnClickListener(this);
        empty_input_view.setOnClickListener(this);
        about_edit.addTextChangedListener(TextChangedListener);
        attrbute_type_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                RxBus.getDefault().post(new CommonEvent(status, about_edit.getText().toString()));
                dismiss();
                break;
            case R.id.empty_input_view:
                about_edit.setText("");
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        RxBus.getDefault().post(new CommonEvent(status, attributeTypeBeanArrayList.get(i).getName()));
        dismiss();
    }

    private TextWatcher TextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            beforeText = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().isEmpty()) {
                empty_input_view.setVisibility(View.INVISIBLE);
            } else {
                empty_input_view.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

    private void initSQLData() {
        List<Object> ObjectList = new ArrayList<>();

        switch (status) {
            case EventCode.DICTIONARY_LAND_USE:
                about_type.setText(context.getString(R.string.land_use));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[0]));
                break;
            case EventCode.DICTIONARY_CROP_TYPES:
                about_type.setText(context.getString(R.string.crop_types));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[3]));
                break;
            case EventCode.DICTIONARY_IRRIGATION_WATER_TYPE:
                about_type.setText(context.getString(R.string.irrigation_water_type));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[4]));
                break;
            case EventCode.DICTIONARY_TOPOGRAPHY:
                about_type.setText(context.getString(R.string.topography));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[5]));
                break;
            case EventCode.DICTIONARY_SAMPLING_EQUIPMENT:
                about_type.setText(context.getString(R.string.sampling_equipment));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[6]));
                break;
            case EventCode.DICTIONARY_ORGANIC_SAMPLE:
                about_type.setText(context.getString(R.string.organic_sample));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[7]));
                break;
            case EventCode.DICTIONARY_INORGANIC_SAMPLE:
                about_type.setText(context.getString(R.string.inorganic_sample));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[7]));
                break;
            case EventCode.DICTIONARY_SOIL_TYPE:
                about_type.setText(context.getString(R.string.soil_type));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[1]));
                break;
            case EventCode.DICTIONARY_SOIL_AND_THE_CLASS:
                about_type.setText(context.getString(R.string.soil_and_the_class));
                List<DictionaryContentChildInfo> dictionaryContentChildInfos = mDataManager.queryDictionaryContentChildInfoInfo(aboutContent);
                List<DictionaryContentChildInfo> dictionaryContentChildInfos1 = mDataManager.loadDictionaryContentChildInfo();
                LogUtil.e(TAG, "dictionaryContentChildInfosALL.size : " + dictionaryContentChildInfos1.size());
                LogUtil.e(TAG, "aboutContent : " + aboutContent);
                LogUtil.e(TAG, "dictionaryContentChildInfos.size : " + dictionaryContentChildInfos.size());
                ObjectList.addAll(dictionaryContentChildInfos);
                break;
            case EventCode.DICTIONARY_SOIL_COLOR:
                about_type.setText(context.getString(R.string.soil_color));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[9]));
                break;
            case EventCode.DICTIONARY_SOIL_TEXTURE:
                about_type.setText(context.getString(R.string.soil_texture));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[10]));
                break;
            case EventCode.DICTIONARY_SOIL_MOISTURE:
                about_type.setText(context.getString(R.string.soil_moisture));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[11]));
                break;
            case EventCode.DICTIONARY_WEATHER:
                about_type.setText(context.getString(R.string.weather));
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[12]));
                break;
            case EventCode.DICTIONARY_SAMPLINGDETAILS:
                about_type.setText(aboutContent);
                ObjectList.addAll(mDataManager.queryDictionaryContentInfo(tableNames[13]));
                break;
        }
        for (int i = 0; i < ObjectList.size(); i++) {
            initDbData(ObjectList.get(i));
        }
    }

    public void initDbData(Object object) {
        String typeName = "";
        if (object instanceof DictionaryContentInfo) {
            DictionaryContentInfo dictionaryContentInfo = (DictionaryContentInfo) object;
            typeName = dictionaryContentInfo.getTypeName();
            attributeTypeBeanArrayList.add(new AttributeTypeBean(dictionaryContentInfo.getTypeName(), dictionaryContentInfo.getTypeNumber()));
        } else if (object instanceof DictionaryContentChildInfo) {
            DictionaryContentChildInfo dictionaryContentInfo = (DictionaryContentChildInfo) object;
            typeName = dictionaryContentInfo.getTypeName();
            attributeTypeBeanArrayList.add(new AttributeTypeBean(dictionaryContentInfo.getTypeName(), dictionaryContentInfo.getTypeNumber()));
        }
        if (typeName.equals("其他")){
            layout_view.setVisibility(View.VISIBLE);
        }else {
            layout_view.setVisibility(View.GONE);
        }
    }

}
