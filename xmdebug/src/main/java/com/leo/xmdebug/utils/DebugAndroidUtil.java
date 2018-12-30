package com.leo.xmdebug.utils;

import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class DebugAndroidUtil {
    public DebugAndroidUtil() {
    }

    public static boolean isRooted() {
        String buildTags = Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        } else {
            try {
                File file = new File("/system/app/Superuser.apk");
                if (file.exists()) {
                    return true;
                }
            } catch (Exception var2) {
                ;
            }

            return canExecuteCommand("/system/xbin/which su") || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su") || canExecuteCommand("busybox which su");
        }
    }

    private static boolean canExecuteCommand(String command) {
        Process process = null;

        boolean var4;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String info = in.readLine();
            if (info != null) {
                var4 = true;
                return var4;
            }

            var4 = false;
        } catch (Exception var8) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }

        }

        return var4;
    }
}