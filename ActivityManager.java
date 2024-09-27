package com.tra.loginscreen;

import android.app.Activity;
import android.app.Application;
import java.util.LinkedList;


public class ActivityManager {
    private static ActivityManager instance;

    //用來儲存Activity的LinkedList
    private LinkedList<Activity> activityList = new LinkedList<Activity>();

    //此類別無法使用普通的方法實例化

    private ActivityManager(){

        super();

    }
    //用來取得唯一實例物件的方法

    public static ActivityManager getInstance(){

        if (instance == null){

            instance = new ActivityManager();
        }

        return instance;
    }

    //用來加入要管理的Activity的方法

    public void addActivity(Activity activity){

        activityList.add(activity);

    }

    //用來關掉所有被管理的Activity的方法

    public void closeAllActivity(){

        for (Activity activity : activityList){

            //fininsh()將Activity推向後台，移出了Activity推疊，資源並沒有被
            //釋放，不會觸發onDestory()，但按Back鍵也會回到原Activity

            activity.finish();
        }

        System.exit(0);
    }

}
