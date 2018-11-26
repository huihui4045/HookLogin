package com.alizhezi.hooklogin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtil {


    public  void hookStartActivity(){


        try {
            //Android 8.0
            Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");

            Field iActivityManagerSingleton = activityManagerClass.getDeclaredField("IActivityManagerSingleton");

            iActivityManagerSingleton.setAccessible(true);

            Object mSingleton= iActivityManagerSingleton.get(null);


            Class<?> mSingletonClass = Class.forName("android.util.Singleton");

            Field mInstance = mSingletonClass.getDeclaredField("mInstance");

            mInstance.setAccessible(true );

            Object IActivityManager = mInstance.get(mSingleton);


            Class proxyClass = Class.forName("android.app.IActivityManager");

            StartActivityInvocationHandler handler=new StartActivityInvocationHandler(IActivityManager);

            /***
             * 代理對象
             */
            Object newIActivityManager=Proxy.newProxyInstance(Thread.currentThread().
                    getContextClassLoader(),new Class[]{proxyClass},handler);


            mInstance.set(mSingleton,newIActivityManager);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class StartActivityInvocationHandler implements InvocationHandler{

        private Object mIActivityManager;

        public StartActivityInvocationHandler(Object mIActivityManager) {
            this.mIActivityManager = mIActivityManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }
}
