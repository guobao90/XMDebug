package com.leo.xmdebug.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class DebugActivityLifecycleCallBacksUtil {
    private static DebugActivityLifecycleCallBacksUtil.DebugActivityLifecycleCallbacks callbacks;
    private static boolean added;

    public DebugActivityLifecycleCallBacksUtil() {
    }

    public static void addCallbacks(Context context) {
        if (callbacks == null) {
            callbacks = new DebugActivityLifecycleCallBacksUtil.DebugActivityLifecycleCallbacks();
        }

        if (!added) {
            ((Application)context.getApplicationContext()).registerActivityLifecycleCallbacks(callbacks);
            added = true;
        }

    }

    public static void removeCallbacks(Context context) {
        if (callbacks != null && added) {
            ((Application)context.getApplicationContext()).unregisterActivityLifecycleCallbacks(callbacks);
            added = false;
        }

    }

    public static boolean isAdded() {
        return added;
    }

    private static class DebugActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private DebugActivityLifecycleCallbacks() {
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Toast.makeText(activity, activity.getComponentName().getClassName(), Toast.LENGTH_SHORT).show();
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }
}
