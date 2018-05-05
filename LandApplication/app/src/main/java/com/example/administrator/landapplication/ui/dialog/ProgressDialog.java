package com.example.administrator.landapplication.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.landapplication.R;


/**
 * Created by Administrator on 2018/3/19.
 */

public class ProgressDialog extends AlertDialog {
    private Context context;
    private ImageView imageView;
    private TextView textView;
    private AnimationDrawable animationDrawable;
    private String content;


    protected ProgressDialog(Context context, @StyleRes int themeResId, String content) {
        super(context, themeResId);
        this.context = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        imageView = (ImageView) findViewById(R.id.iv_loading);
        textView = (TextView) findViewById(R.id.tv_loading);
        try {
            textView.setText(content);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.progress_white_animlist);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void ShowDiaLog() {
//        SystemUtil.windowToDark(context);
        show();
    }

    public void setProgress(String changeContent){
        textView.setText(changeContent);
    }

}
