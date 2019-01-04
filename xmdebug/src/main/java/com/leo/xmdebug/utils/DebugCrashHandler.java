package com.leo.xmdebug.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Debug;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DebugCrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String CRASH_LOG_DIR_NAME = "crashLog";
    public static final String CRASH_LOG_PREFIX = "crash_log_";
    public static final String FILE_NAME_STACK_TRACE = "stackTrace.log";
    public static final String FILE_NAME_SCREEN_SHOT = "screenShot.png";
    public static final String FILE_NAME_MEMORY_DUMP = "memoryDump.hprof";
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Context applicationContext;

    private DebugCrashHandler() {
    }

    private void init(Context context) {
        this.applicationContext = context;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static void setCrashHandler(Context applicationContext) {
        (new DebugCrashHandler()).init(applicationContext);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        this.saveExceptionInfo(t, e);
        this.defaultHandler.uncaughtException(t, e);
    }

    private void saveExceptionInfo(Thread t, Throwable e) {
        File dir = this.createDir();
        this.saveStackTrace(dir, t, e);
        this.screenShot(dir);
        this.dump(dir);
    }

    @Nullable
    private File createDir() {
        String dirName = "crash_log_" + System.currentTimeMillis();
        File logDir;
        if (Environment.getExternalStorageState().equals("mounted")) {
            logDir = new File(this.applicationContext.getExternalCacheDir(), "crashLog");
        } else {
            logDir = new File(this.applicationContext.getCacheDir(), "crashLog");
        }

        File dir = new File(logDir, dirName);
        return !dir.exists() && !dir.mkdirs() ? null : dir;
    }

    private void saveStackTrace(File dir, Thread t, Throwable e) {
        if (dir != null) {
            try {
                File logFile = new File(dir, "stackTrace.log");
                if (!logFile.exists() && !logFile.createNewFile()) {
                    return;
                }

                PrintStream printStream = new PrintStream(logFile);
                e.printStackTrace(printStream);

                for(Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
                    cause.printStackTrace(printStream);
                }

                printStream.close();
            } catch (Exception var7) {
                ;
            }

        }
    }

    private void screenShot(File dir) {
        if (dir != null) {
            File screenShotFile = new File(dir, "screenShot.png");
            if (DebugAndroidUtil.isRooted()) {
                this.screenShotWithRoot(screenShotFile);
            } else {
                this.screenShotWithoutRoot(screenShotFile);
            }

        }
    }

    private void screenShotWithoutRoot(File file) {
        Activity activity = DebugReflectionUtil.getShowingActivity();
        if (activity != null) {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();
            if (bitmap != null) {
                try {
                    FileOutputStream os = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
                    os.close();
                } catch (Exception var6) {
                    var6.printStackTrace();
                }
            }

        }
    }

    private void screenShotWithRoot(File file) {
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.write(("/system/bin/screencap -p " + file.getAbsolutePath()).getBytes());
            os.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            if (process != null) {
                process.exitValue();
            }

        }

    }

    private void dump(File dir) {
        if (dir != null) {
            File dumpFile = new File(dir, "memoryDump.hprof");

            try {
                Debug.dumpHprofData(dumpFile.getAbsolutePath());
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }
    }
}