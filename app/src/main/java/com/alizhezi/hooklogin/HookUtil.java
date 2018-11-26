package com.alizhezi.hooklogin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtil {

    private String TAG="HookUtil";

    private Context context;


    public  void hookStartActivity(Context context){

        this.context=context;


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


    public void hookActivityThread(){

        try {
            Class<?> activityThreadClass= Class.forName("android.app.ActivityThread");


            Field sCurrentActivityThread = activityThreadClass.getDeclaredField("sCurrentActivityThread");


            sCurrentActivityThread.setAccessible(true);

            Object activityThread= sCurrentActivityThread.get(null);


            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);

            Handler H = (Handler) mHField.get(activityThread);


            Field mCallBack = Handler.class.getDeclaredField("mCallback");
            mCallBack.setAccessible(true);

            mCallBack.set(H,new ActivityCallBack(H));


        } catch (Exception e) {
            e.printStackTrace();

            Log.e(TAG,"error:"+e.getMessage());
        }
    }


    class ActivityCallBack implements Handler.Callback{


        private Handler mHandler;

        public ActivityCallBack(Handler mHandler) {
            this.mHandler = mHandler;
        }

        @Override
        public boolean handleMessage(Message msg) {

            Log.e(TAG,"handleMessage:"+ msg.what);


           if (msg.what==100){


               Object obj = msg.obj;


               try {
                   Field intentField = obj.getClass().getDeclaredField("intent");


                   intentField.setAccessible(true);

                   Intent realyIntent= (Intent) intentField.get(obj);


                   Intent oldIntent=realyIntent.getParcelableExtra("oldIntent");

                   SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

                   boolean isLogin = sharedPreferences.getBoolean("isLogin", false);


                   if (isLogin){


                       realyIntent.setComponent(oldIntent.getComponent());
                   }else {


                       ComponentName componentName = new ComponentName(context, LoginActivity.class);

                       realyIntent.putExtra("extraIntent",oldIntent.getComponent().getClassName());

                       realyIntent.setComponent(componentName);

                   }


               } catch (Exception e) {
                   e.printStackTrace();
               }

           }




            mHandler.handleMessage(msg);
            return true;
        }
    }

    class StartActivityInvocationHandler implements InvocationHandler{

        private Object mIActivityManager;

        public StartActivityInvocationHandler(Object mIActivityManager) {
            this.mIActivityManager = mIActivityManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {





            if (method.getName().equals("startActivity")) {

                int index=0;

                Intent oldIntent=null;

                for (int i = 0; i < args.length; i++) {

                    if (args[i] instanceof Intent){

                        index=i;

                        oldIntent= (Intent) args[i];
                    }
                }

                Intent newIntent=new Intent();


                newIntent.setComponent(new ComponentName(context,ProxyActivity.class));


                newIntent.putExtra("oldIntent",oldIntent);


                args[index]=newIntent;


                Log.e(TAG,"invoke  method:"+method.getName());
            }


            return method.invoke(mIActivityManager,args);
        }
    }
}
