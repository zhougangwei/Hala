package com.hala.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by oneki on 2017/5/16.
 */

public class ActivityManagerUtil {

    private static Stack<Activity> activityStack;

    private static ActivityManagerUtil instance;

    public static ActivityManagerUtil  getActivityManager() {
        if (instance == null) {
            instance = new ActivityManagerUtil();
        }
        return instance;
    }

    public void popActivityStack(Activity activity) {

        if ( activity!=null) {

            activityStack.remove(activity);
            activity.finish();
            activity = null;

        }
    }

    public void pushActivity2Stack(Activity activity) {

        if (activityStack == null) {

            activityStack = new Stack<Activity>();

        }

        activityStack.add(activity);

    }

    public Activity getCurrentActivity() {

        Activity activity = null;
        if (activityStack!=null&&!activityStack.isEmpty()) {

            activity = activityStack.lastElement();

        }

        return activity;

    }

    public void popAllActivityFromStack() {

        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                break;
            }
            popActivityStack(activity);
        }

    }
}
