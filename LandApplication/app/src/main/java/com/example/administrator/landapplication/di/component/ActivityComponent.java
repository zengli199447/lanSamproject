package com.example.administrator.landapplication.di.component;

import android.app.Activity;


import com.example.administrator.landapplication.di.moudle.ActivityModule;
import com.example.administrator.landapplication.di.scope.ActivityScope;
import com.example.administrator.landapplication.ui.activity.main.HomeActivity;
import com.example.administrator.landapplication.ui.activity.main.LoginActivity;
import com.example.administrator.landapplication.ui.activity.main.MainActivity;
import com.example.administrator.landapplication.ui.activity.main.PersonalActivity;
import com.example.administrator.landapplication.ui.activity.main.SettingActivity;
import com.example.administrator.landapplication.ui.activity.main.WelComeActivity;
import com.example.administrator.landapplication.ui.activity.office.ChangeRequestActivity;
import com.example.administrator.landapplication.ui.activity.office.ChargeTaskActivity;
import com.example.administrator.landapplication.ui.activity.office.ChargeTaskConfirmActivity;
import com.example.administrator.landapplication.ui.activity.office.ChargeTaskContentActivity;
import com.example.administrator.landapplication.ui.activity.office.CurrentTaskActivity;
import com.example.administrator.landapplication.ui.activity.office.DetailsSampleActivity;
import com.example.administrator.landapplication.ui.activity.tools.InspectionRoutePlanningActivity;
import com.example.administrator.landapplication.ui.activity.office.MonitoringCenterActivity;
import com.example.administrator.landapplication.ui.activity.office.MonitoringCenterListActivity;
import com.example.administrator.landapplication.ui.activity.office.SamplingDetailsActivity;
import com.example.administrator.landapplication.ui.activity.office.SceneSamplingActivity;
import com.example.administrator.landapplication.ui.activity.office.TaskContentActivity;
import com.example.administrator.landapplication.ui.activity.tools.BluetoothPairingActivity;
import com.example.administrator.landapplication.ui.activity.tools.LocationNavigationActivity;
import com.example.administrator.landapplication.ui.activity.tools.LocationScopeDetectionActivity;
import com.example.administrator.landapplication.ui.activity.tools.LocatorSettingActivity;
import com.example.administrator.landapplication.ui.activity.tools.QrCodeActivity;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(WelComeActivity welComeActivity);

    void inject(LoginActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(PersonalActivity personalActivity);

    void inject(SettingActivity settingActivity);

    void inject(LocationScopeDetectionActivity locationScopeDetectionActivity);

    void inject(LocationNavigationActivity locationNavigationActivity);

    void inject(CurrentTaskActivity onlineTaskActivity);

    void inject(TaskContentActivity taskContentActivity);

    void inject(ChargeTaskActivity chargeTaskActivity);

    void inject(ChargeTaskContentActivity chargeTaskContentActivity);

    void inject(ChargeTaskConfirmActivity chargeTaskConfirmActivity);

    void inject(QrCodeActivity qrCodeActivity);

    void inject(DetailsSampleActivity detailsSampleActivity);

    void inject(ChangeRequestActivity changeRequestActivity);

    void inject(SceneSamplingActivity sceneSamplingActivity);

    void inject(MonitoringCenterActivity monitoringCenterActivity);

    void inject(MonitoringCenterListActivity monitoringCenterListActivity);

    void inject(SamplingDetailsActivity samplingDetailsActivity);

    void inject(BluetoothPairingActivity bluetoothPairingActivity);

    void inject(LocatorSettingActivity locatorSettingActivity);

    void inject(InspectionRoutePlanningActivity inspectionRoutePlanningActivity);

}
