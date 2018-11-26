package com.alizhezi.hooklogin;

import android.app.Application;

public class HookAppconlication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        HookUtil hookUtil=new HookUtil();


        hookUtil.hookStartActivity(this);


        hookUtil.hookActivityThread();
    }
}
