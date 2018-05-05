package com.example.administrator.landapplication.ui.activity.office;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.TaskContentBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.GlobalNetWorkManager;
import com.example.administrator.landapplication.model.db.entity.TaskListInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.network.TaskListNerWork;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.ui.adapter.AdapterStandardGeneral;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.ui.view.PullToRefreshLayout;
import com.example.administrator.landapplication.ui.view.PullableListView;
import com.example.administrator.landapplication.utils.LogUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/13.
 * 任务列表
 */

public class CurrentTaskActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.task_list)
    PullableListView task_list;
    @BindView(R.id.pull_refresh_layout)
    PullToRefreshLayout pull_refresh_layout;
    private ArrayList<Object> taskContentList;
    private AdapterStandardGeneral adapterStandardGeneral;
    private int taskType;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;
    private TaskListNerWork taskListNerWork;
    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case NetEventCode.NETWORK_TASK_LIST: //网络请求结果
                if (pullToRefreshLayout != null)
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                initTaskListContent();
                progressDialog.dismiss();
                break;
            case NetEventCode.NETWORK_ERROR:
                if (pullToRefreshLayout != null)
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                initTaskListContent();
                toastUtil.showToast(commonevent.getTemp_str());
                progressDialog.dismiss();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.online_task;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        progressDialog = showDialog.showProgressStatus(this, getString(R.string.progress));
        taskListNerWork = new TaskListNerWork(this, dataManager);
        Bundle extras = getIntent().getExtras();
        taskType = (int) extras.get("taskType");
    }

    @Override
    protected void initData() {
        initNetData(true);
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.tasks));
        img_btn_black.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        task_list.setOnItemClickListener(this);
        pull_refresh_layout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int taskStatus = ((TaskContentBean) taskContentList.get(i)).getTaskStatus();
        String taskId = ((TaskContentBean) taskContentList.get(i)).getTaskId();
        switch (taskStatus) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 9:
                Intent taskIntent = new Intent(this, TaskContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("taskType",String.valueOf(taskStatus));
                bundle.putString("taskId",taskId);
                taskIntent.putExtras(bundle);
                startActivity(taskIntent);
                break;
            case 5:
                startActivity(new Intent(this, MonitoringCenterActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, ChargeTaskActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        initNetData(false);
        this.pullToRefreshLayout = pullToRefreshLayout;
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    private void initNetData(boolean status) {
        if (status)
            progressDialog.ShowDiaLog();
        switch (taskType) {
            case 0: //新建
                taskListNerWork.getCurrentTaskListData(UrlList.NEWTASKMANAGER, taskType);
                break;
            case 1: //进行中
                taskListNerWork.getCurrentTaskListData(UrlList.ONLINETASKMANAGER, taskType);
                break;
        }
    }

    private void initTaskListContent() {
        List<TaskListInfo> taskListInfos = dataManager.queryTaskTypeListInfo(String.valueOf(taskType));
        taskContentList = new ArrayList<>();
        for (int i = 0; i < taskListInfos.size(); i++) {
            TaskListInfo taskListInfo = taskListInfos.get(i);
            taskContentList.add(new TaskContentBean(taskListInfo.getCountyLevel(), taskListInfo.getSamplePointNumber(), Integer.valueOf(taskListInfo.getTaskStatus()),taskListInfo.getTaskId()));
        }
        adapterStandardGeneral = new AdapterStandardGeneral(taskContentList, this);
        task_list.setAdapter(adapterStandardGeneral);

    }


}
