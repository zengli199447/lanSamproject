package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseDialog;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.utils.SystemUtil;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/3/21.
 */

public class SuperInputDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private int status;
    private String title;
    private String hint;
    @BindView(R.id.super_edit)
    EditText super_edit;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.empty_input_view)
    View empty_input_view;
    @BindView(R.id.input_type)
    TextView input_type;

    protected SuperInputDialog(Context context, @StyleRes int themeResId, int status, String title, String hint) {
        super(context, themeResId);
        this.context = context;
        this.status = status;
        this.title = title;
        this.hint = hint;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_input_super;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        btn_commit.setText("确认");
        input_type.setText(title);
        super_edit.setHint(hint);
    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        empty_input_view.setOnClickListener(this);
        super_edit.addTextChangedListener(TextChangedListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_commit:
                RxBus.getDefault().post(new CommonEvent(status, super_edit.getText().toString()));
                dismiss();
                break;
            case R.id.empty_input_view:
                super_edit.setText("");
                break;
        }

    }

    private String beforeText;
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

}
