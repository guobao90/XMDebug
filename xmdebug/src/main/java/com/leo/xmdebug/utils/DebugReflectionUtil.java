package com.leo.xmdebug.utils;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class DebugReflectionUtil {
    public DebugReflectionUtil() {
    }

    @Nullable
    public static Activity getShowingActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke((Object)null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map)activitiesField.get(activityThread);
            Iterator var4 = activities.values().iterator();

            while(var4.hasNext()) {
                Object activityRecord = var4.next();
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity)activityField.get(activityRecord);
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return null;
    }
}
