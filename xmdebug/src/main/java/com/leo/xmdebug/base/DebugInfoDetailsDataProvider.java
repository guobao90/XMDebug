package com.leo.xmdebug.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.leo.utils.AndroidUtils;
import com.leo.utils.DateUtils;
import com.leo.xmdebug.R;
import com.leo.xmdebug.detail.DebugInfoDetail;
import com.leo.xmdebug.detail.model.DebugInfoDetailsNormalModel;
import com.leo.xmdebug.home.model.DebugCrashLogDetailModel;
import com.leo.xmdebug.home.model.DebugDatabaseModel;
import com.leo.xmdebug.home.model.DebugInfoDetailsInstalledAppModel;
import com.leo.xmdebug.mt.model.DebugFilesMultiModel;
import com.leo.xmdebug.utils.DebugAndroidUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DebugInfoDetailsDataProvider {
    private Context context;
    private PackageManager packageManager;
    private String packageName;

    public DebugInfoDetailsDataProvider(Context context) {
        this.context = context;
        this.packageManager = context.getPackageManager();
        this.packageName = context.getPackageName();
    }

    @Nullable
    public List<Object> getDetail(DebugInfoDetail detail) {
        ArrayList list = new ArrayList();

        try {
            switch (detail.getType()) {
                case APP_ABSTRACT:
                    list.addAll(this.getAppAbstract());
                    break;
                case APP_NAME:
                    list.add(this.getAppName());
                    break;
                case PACKAGE_NAME:
                    list.add(this.getPackageName());
                    break;
                case VERSION:
                    list.add(this.getVersion());
                    break;
                case VERSION_CODE:
                    list.add(this.getVersionCode());
                    break;
                case FIRST_INSTALL_TIME:
                    list.add(this.getFirstInstallTime());
                    break;
                case LAST_UPDATE_TIME:
                    list.add(this.getLastUpdateTime());
                    break;
                case ACTIVITIES_SUMMARY:
                    list.add(this.getActivitiesSummary());
                    break;
                case ACTIVITIES:
                    list.addAll(this.getActivities());
                    break;
                case SERVICES_SUMMARY:
                    list.add(this.getServicesSummary());
                    break;
                case SERVICES:
                    list.addAll(this.getServices());
                    break;
                case RECEIVERS_SUMMARY:
                    list.add(this.getReceiversSummary());
                    break;
                case RECEIVERS:
                    list.addAll(this.getReceivers());
                    break;
                case PROVIDERS_SUMMARY:
                    list.add(this.getProvidersSummary());
                    break;
                case PROVIDERS:
                    list.addAll(this.getProviders());
                    break;
                case PERMISSIONS_SUMMARY:
                    list.add(this.getPermissionsSummary());
                    break;
                case PERMISSIONS:
                    list.addAll(this.getPermissions());
                    break;
                case INSTALLED_APP_SUMMARY:
                    list.add(this.getInstalledAppSummary());
                    break;
                case INSTALLED_APP:
                    list.addAll(this.getInstalledApp());
                    break;
                case DEVICE_ABSTRACT:
                    list.addAll(this.getDeviceAbstract());
                    break;
                case ANDROID_VERSION:
                    list.add(this.getBuildVersion());
                    break;
                case MODEL:
                    list.add(this.getDeviceModel());
                    break;
                case RESOLUTION:
                    list.add(this.getResolution());
                    break;
                case ROOT:
                    list.add(this.getRoot());
                    break;
                case ABI:
                    list.add(this.getABIs());
                    break;
                case SHARED_PREFERENCE_LIST:
                    list.addAll(this.getSharedPreferenceList());
                    break;
                case SHARED_PREFERENCE_DETAIL:
                    list.addAll(this.getSharedPreferenceDetails(detail.getStringExtra()));
                    break;
                case FILES:
                    list.addAll(this.getFiles(detail.getStringExtra()));
                    break;
                case DATABASE_LIST:
                    list.addAll(this.getDataBaseList());
                    break;
                case DATABASE_DETAILS:
                    list.add(this.getDataBaseDetails(detail.getStringExtra()));
                    break;
                case CRASH_LOG_LIST:
                    list.addAll(this.getCrashLogs());
                    break;
                case CRASH_LOG_DETAIL:
                    list.add(this.getCrashLogDetail(detail.getStringExtra()));
            }

            return list;
        } catch (Exception var4) {
            return null;
        }
    }

    private List<DebugInfoDetailsNormalModel> getAppAbstract() throws PackageManager.NameNotFoundException {
        List<DebugInfoDetailsNormalModel> abstracts = new ArrayList();
        abstracts.add(this.getAppName());
        abstracts.add(this.getPackageName());
        abstracts.add(this.getVersion());
        abstracts.add(this.getVersionCode());
        abstracts.add(this.getMarket());
        abstracts.add(this.getFirstInstallTime());
        abstracts.add(this.getLastUpdateTime());
        abstracts.add(this.getActivitiesSummary());
        abstracts.add(this.getServicesSummary());
        abstracts.add(this.getReceiversSummary());
        abstracts.add(this.getProvidersSummary());
        abstracts.add(this.getPermissionsSummary());
        abstracts.add(this.getInstalledAppSummary());
        return abstracts;
    }

    private DebugInfoDetailsNormalModel getAppName() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("应用名");
        model.setSecond(this.packageManager.getApplicationLabel(this.packageManager.getApplicationInfo(this.packageName, 0)).toString());
        return model;
    }

    private DebugInfoDetailsNormalModel getPackageName() {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("包名");
        model.setSecond(this.packageName);
        return model;
    }

    private DebugInfoDetailsNormalModel getVersion() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 0);
        model.setFirst("应用版本");
        model.setSecond(packageInfo.versionName);
        return model;
    }

    private DebugInfoDetailsNormalModel getVersionCode() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 0);
        model.setFirst("版本号");
        model.setSecond(String.valueOf(packageInfo.versionCode));
        return model;
    }

    private DebugInfoDetailsNormalModel getMarket() {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("渠道");
        model.setSecond("");
        return model;
    }

    private DebugInfoDetailsNormalModel getFirstInstallTime() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 0);
        model.setFirst("首次安装时间");
        model.setSecond(DateUtils.getTimeString(packageInfo.firstInstallTime / 1000L, "yyyy-MM-dd HH:mm:ss"));
        return model;
    }

    private DebugInfoDetailsNormalModel getLastUpdateTime() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 0);
        model.setFirst("最后更新时间");
        model.setSecond(DateUtils.getTimeString(packageInfo.lastUpdateTime / 1000L, "yyyy-MM-dd HH:mm:ss"));
        return model;
    }

    private DebugInfoDetailsNormalModel getActivitiesSummary() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("Activities");

        try {
            PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 1);
            model.setSecond(this.context.getString(R.string.cld_a, new Object[]{String.valueOf(packageInfo.activities.length)}));
            model.setNextTitle("Activities");
            ArrayList<DebugInfoDetail> list = new ArrayList();
            list.add(new DebugInfoDetail(DebugInfoDetail.InfoType.ACTIVITIES));
            model.setNext(list);
        } catch (Exception var4) {
            model.setSecond("数据太多，挂了_(:з)∠)_");
        }

        return model;
    }

    private List<DebugInfoDetailsNormalModel> getActivities() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 1);
        List<DebugInfoDetailsNormalModel> activities = new ArrayList();
        ActivityInfo[] var3 = packageInfo.activities;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ActivityInfo activityInfo = var3[var5];
            DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
            model.setFirst(activityInfo.name);
            model.setType(DebugInfoDetailsNormalModel.NormalInfoType.ACTIVITIES);
            activities.add(model);
        }

        return activities;
    }

    private DebugInfoDetailsNormalModel getServicesSummary() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 4);
        model.setFirst("Services");
        model.setSecond(this.context.getString(R.string.cld_a, new Object[]{String.valueOf(packageInfo.services != null ? packageInfo.services.length : 0)}));
        model.setNextTitle("Services");
        ArrayList<DebugInfoDetail> list = new ArrayList();
        list.add(new DebugInfoDetail(DebugInfoDetail.InfoType.SERVICES));
        model.setNext(list);
        return model;
    }

    private List<DebugInfoDetailsNormalModel> getServices() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 4);
        ArrayList<DebugInfoDetailsNormalModel> services = new ArrayList();
        ServiceInfo[] var3 = packageInfo.services;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ServiceInfo serviceInfo = var3[var5];
            DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
            model.setFirst(serviceInfo.name);
            services.add(model);
        }

        return services;
    }

    private DebugInfoDetailsNormalModel getReceiversSummary() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 2);
        model.setFirst("Receivers");
        model.setSecond(this.context.getString(R.string.cld_a, new Object[]{String.valueOf(packageInfo.receivers == null ? 0:packageInfo.receivers.length)}));
        model.setNextTitle("Receivers");
        ArrayList<DebugInfoDetail> list = new ArrayList();
        list.add(new DebugInfoDetail(DebugInfoDetail.InfoType.RECEIVERS));
        model.setNext(list);
        return model;
    }

    private List<DebugInfoDetailsNormalModel> getReceivers() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 2);
        ArrayList<DebugInfoDetailsNormalModel> receivers = new ArrayList();
        ActivityInfo[] var3 = packageInfo.receivers;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ActivityInfo activityInfo = var3[var5];
            DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
            model.setFirst(activityInfo.name);
            receivers.add(model);
        }

        return receivers;
    }

    private DebugInfoDetailsNormalModel getProvidersSummary() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 8);
        model.setFirst("Providers");
        model.setSecond(this.context.getString(R.string.cld_a, new Object[]{String.valueOf(packageInfo.providers == null ? 0 : packageInfo.providers.length)}));
        model.setNextTitle("Providers");
        ArrayList<DebugInfoDetail> list = new ArrayList();
        list.add(new DebugInfoDetail(DebugInfoDetail.InfoType.PROVIDERS));
        model.setNext(list);
        return model;
    }

    private List<DebugInfoDetailsNormalModel> getProviders() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 8);
        ArrayList<DebugInfoDetailsNormalModel> providers = new ArrayList();
        ProviderInfo[] var3 = packageInfo.providers;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ProviderInfo providerInfo = var3[var5];
            DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
            model.setFirst(providerInfo.name);
            providers.add(model);
        }

        return providers;
    }

    private DebugInfoDetailsNormalModel getPermissionsSummary() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 4096);
        model.setFirst("Permissions");
        model.setSecond(this.context.getString(R.string.cld_count, new Object[]{String.valueOf(packageInfo.requestedPermissions.length)}));
        model.setNextTitle("Permissions");
        ArrayList<DebugInfoDetail> list = new ArrayList();
        list.add(new DebugInfoDetail(DebugInfoDetail.InfoType.PERMISSIONS));
        model.setNext(list);
        return model;
    }

    private List<DebugInfoDetailsNormalModel> getPermissions() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 4096);
        ArrayList<DebugInfoDetailsNormalModel> permissions = new ArrayList();
        String[] var3 = packageInfo.requestedPermissions;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String permission = var3[var5];
            DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
            model.setFirst(permission);
            model.setType(DebugInfoDetailsNormalModel.NormalInfoType.PERMISSIONS);
            permissions.add(model);
        }

        return permissions;
    }

    private DebugInfoDetailsNormalModel getInstalledAppSummary() throws PackageManager.NameNotFoundException {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        List<PackageInfo> packages = this.packageManager.getInstalledPackages(0);
        int count = 0;
        Iterator var4 = packages.iterator();

        while (var4.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) var4.next();
            if ((packageInfo.applicationInfo.flags & 1) == 0) {
                ++count;
            }
        }

        model.setFirst("已安装应用");
        model.setSecond(this.context.getString(R.string.cld_a, new Object[]{String.valueOf(count)}));
        model.setNextTitle("已安装应用");
        ArrayList<DebugInfoDetail> list = new ArrayList();
        list.add(new DebugInfoDetail(DebugInfoDetail.InfoType.INSTALLED_APP));
        model.setNext(list);
        return model;
    }

    private List<DebugInfoDetailsInstalledAppModel> getInstalledApp() throws PackageManager.NameNotFoundException {
        List<PackageInfo> packages = this.packageManager.getInstalledPackages(0);
        List<DebugInfoDetailsInstalledAppModel> apps = new ArrayList();
        Iterator var3 = packages.iterator();

        while (var3.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) var3.next();
            if ((packageInfo.applicationInfo.flags & 1) == 0) {
                DebugInfoDetailsInstalledAppModel model = new DebugInfoDetailsInstalledAppModel();
                model.setAppIcon(packageInfo.applicationInfo.loadIcon(this.packageManager));
                model.setName(packageInfo.applicationInfo.loadLabel(this.packageManager).toString());
                model.setPackageName(packageInfo.packageName);
                model.setLaunchIntent(this.packageManager.getLaunchIntentForPackage(packageInfo.packageName));
                apps.add(model);
            }
        }

        return apps;
    }

    private List<DebugInfoDetailsNormalModel> getDeviceAbstract() {
        List<DebugInfoDetailsNormalModel> list = new ArrayList();
        list.add(this.getBuildVersion());
        list.add(this.getDeviceModel());
        list.add(this.getResolution());
        list.add(this.getRoot());
        list.add(this.getABIs());
        return list;
    }

    private DebugInfoDetailsNormalModel getBuildVersion() {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("Android版本");
        model.setSecond(Build.VERSION.RELEASE);
        return model;
    }

    private DebugInfoDetailsNormalModel getDeviceModel() {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("设备型号");
        String modelInfo = Build.MANUFACTURER + " ";
        modelInfo = modelInfo + Build.BRAND + " ";
        modelInfo = modelInfo + Build.MODEL;
        model.setSecond(modelInfo);
        return model;
    }

    private DebugInfoDetailsNormalModel getResolution() {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();
        String resolution = metrics.widthPixels + " x " + metrics.heightPixels + " (" + metrics.densityDpi + "dpi)";
        model.setFirst("分辨率");
        model.setSecond(resolution);
        return model;
    }

    private DebugInfoDetailsNormalModel getRoot() {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("Root状态");
        if (DebugAndroidUtil.isRooted()) {
            model.setSecond("已Root");
        } else {
            model.setSecond("未Root");
        }

        model.setSecond("未Root");
        return model;
    }

    private DebugInfoDetailsNormalModel getABIs() {
        DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
        model.setFirst("ABIs支持");
        String abis = "";
        if (Build.VERSION.SDK_INT >= 21) {
            String[] var3 = Build.SUPPORTED_ABIS;
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String abi = var3[var5];
                abis = abis + abi + "\n";
            }

            model.setSecond(abis.substring(0, abis.length() - 1));
        } else {
            abis = abis + Build.CPU_ABI;
            if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
                abis = abis + "\n" + Build.CPU_ABI2;
            }

            model.setSecond(abis);
        }

        return model;
    }


    private List<DebugInfoDetailsNormalModel> getSharedPreferenceList() {
        List<DebugInfoDetailsNormalModel> list = new ArrayList();
        File spfDir = new File(this.context.getFilesDir().getParentFile(), "shared_prefs");
        File[] var3 = spfDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            File spf = var3[var5];
            DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
            String name = spf.getName().substring(0, spf.getName().lastIndexOf("."));
            model.setFirst(name);
            model.setNextTitle(name);
            ArrayList<DebugInfoDetail> nextList = new ArrayList();
            DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.SHARED_PREFERENCE_DETAIL);
            detail.setStringExtra(spf.getName());
            nextList.add(detail);
            model.setNext(nextList);
            list.add(model);
        }

        return list;
    }

    private List<DebugInfoDetailsNormalModel> getSharedPreferenceDetails(String filename) {
        List<DebugInfoDetailsNormalModel> list = new ArrayList();
        SharedPreferences spf = this.context.getSharedPreferences(filename.substring(0, filename.lastIndexOf(".")), 0);

        DebugInfoDetailsNormalModel model;
        for (Iterator var4 = spf.getAll().entrySet().iterator(); var4.hasNext(); list.add(model)) {
            Map.Entry<String, ?> entry = (Map.Entry) var4.next();
            model = new DebugInfoDetailsNormalModel();
            model.setFirst((String) entry.getKey());
            if (!(entry.getValue() instanceof Set)) {
                model.setSecond(String.valueOf(entry.getValue()));
            } else {
                StringBuilder sb = new StringBuilder();
                Iterator var8 = ((Set) entry.getValue()).iterator();

                while (var8.hasNext()) {
                    String str = (String) var8.next();
                    sb.append(str);
                    sb.append("\n");
                }

                model.setSecond(sb.toString());
            }
        }

        return list;
    }

    private List<DebugFilesMultiModel> getFiles(String path) {
        List<DebugFilesMultiModel> list = new ArrayList();
        File file = new File(path);
        File[] var4 = file.listFiles();
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            File subFile = var4[var6];
            DebugFilesMultiModel model = new DebugFilesMultiModel();
            model.setFile(subFile);
            list.add(model);
        }

        return list;
    }

    private List<DebugInfoDetailsNormalModel> getDataBaseList() {
        List<DebugInfoDetailsNormalModel> list = new ArrayList();
        File databaseDir = new File(this.context.getFilesDir().getParentFile(), "databases");
        File[] var3 = databaseDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !name.endsWith("-journal");
            }
        });
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
            String name = file.getName();
            if (name.contains(".")) {
                name = file.getName().substring(0, file.getName().lastIndexOf("."));
            }

            model.setFirst(name);
            ArrayList<DebugInfoDetail> nextList = new ArrayList();
            DebugInfoDetail detail = new DebugInfoDetail(DebugInfoDetail.InfoType.DATABASE_DETAILS);
            detail.setStringExtra(file.getName());
            model.setNext(nextList);
            model.setType(DebugInfoDetailsNormalModel.NormalInfoType.DATABASE);
            nextList.add(detail);
            list.add(model);
        }

        return list;
    }

    private DebugDatabaseModel getDataBaseDetails(String databaseName) {
        DebugDatabaseModel model = new DebugDatabaseModel();
        model.setName(databaseName);
        ArrayList tables = new ArrayList();

        try {
            SQLiteDatabase database = SQLiteDatabase.openDatabase(this.context.getDatabasePath(databaseName).getAbsolutePath(), (SQLiteDatabase.CursorFactory) null, 1);
            Cursor tableCursor = database.rawQuery("select name from sqlite_master where type='table' order by name", (String[]) null);
            ArrayList tableNames = new ArrayList();

            while (tableCursor.moveToNext()) {
                tableNames.add(tableCursor.getString(0));
            }

            tableCursor.close();
            Iterator var7 = tableNames.iterator();

            while (var7.hasNext()) {
                String tableName = (String) var7.next();
                Cursor cursor = database.query(tableName, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                DebugDatabaseModel.Table table = new DebugDatabaseModel.Table();
                table.setName(tableName);
                ArrayList data = new ArrayList();

                while (cursor.moveToNext()) {
                    if (table.getColumns() == null || table.getColumns().length == 0) {
                        table.setColumns(cursor.getColumnNames());
                    }

                    String[] var12 = cursor.getColumnNames();
                    int var13 = var12.length;

                    for (int var14 = 0; var14 < var13; ++var14) {
                        String column = var12[var14];
                        switch (cursor.getType(cursor.getColumnIndex(column))) {
                            case 0:
                                data.add((Object) null);
                                break;
                            case 1:
                                data.add(cursor.getLong(cursor.getColumnIndex(column)));
                                break;
                            case 2:
                                data.add(cursor.getFloat(cursor.getColumnIndex(column)));
                                break;
                            case 3:
                                data.add(cursor.getString(cursor.getColumnIndex(column)));
                                break;
                            case 4:
                                data.add(cursor.getBlob(cursor.getColumnIndex(column)));
                        }
                    }
                }

                table.setData(data);
                tables.add(table);
                cursor.close();
            }

            model.setTables(tables);
        } catch (Exception var16) {
            var16.printStackTrace();
        }

        return model;
    }

    private List<DebugInfoDetailsNormalModel> getCrashLogs() {
        ArrayList list = new ArrayList();

        try {
            File logDir;
            if (Environment.getExternalStorageState().equals("mounted")) {
                logDir = new File(this.context.getExternalCacheDir(), "crashLog");
            } else {
                logDir = new File(this.context.getCacheDir(), "crashLog");
            }

            if (!logDir.exists() || !logDir.isDirectory()) {
                return list;
            }

            File[] var3 = logDir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return dir.isDirectory() && name.startsWith("crash_log_");
                }
            });
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                File logFile = var3[var5];
                DebugInfoDetailsNormalModel model = new DebugInfoDetailsNormalModel();
                String filename = logFile.getName();
                String time = filename.substring(filename.lastIndexOf("_") + 1);
                String timeFormat = DateUtils.getTimeString(Long.parseLong(time) / 1000L, "yyyy/MM/dd HH:mm:ss");
                model.setSecond(timeFormat);
                model.setFirst(logFile.getName());
                model.setNextTitle(timeFormat);
                ArrayList<DebugInfoDetail> nextList = new ArrayList();
                DebugInfoDetail info = new DebugInfoDetail(DebugInfoDetail.InfoType.CRASH_LOG_DETAIL);
                info.setStringExtra(logFile.getAbsolutePath());
                nextList.add(info);
                model.setNext(nextList);
                list.add(model);
            }

            Collections.sort(list, new Comparator<DebugInfoDetailsNormalModel>() {
                public int compare(DebugInfoDetailsNormalModel o1, DebugInfoDetailsNormalModel o2) {
                    return o2.getSecond().compareTo(o1.getSecond());
                }
            });
        } catch (Exception var13) {
            ;
        }

        return list;
    }

    private DebugCrashLogDetailModel getCrashLogDetail(String dirPath) {
        DebugCrashLogDetailModel model = new DebugCrashLogDetailModel();

        try {
            File stackTraceFile = new File(dirPath, "stackTrace.log");
            if (stackTraceFile.exists() && stackTraceFile.isFile()) {
                FileInputStream is = new FileInputStream(stackTraceFile);
                InputStreamReader reader = new InputStreamReader(is, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

                String time = dirPath.substring(dirPath.lastIndexOf("_") + 1);
                String timeFormat = DateUtils.getTimeString(Long.parseLong(time) / 1000L, "yyyy/MM/dd HH:mm:ss");
                model.setTime(timeFormat);
                model.setStackTrace(sb.toString());
            }

            File screenShotFile = new File(dirPath, "screenShot.png");
            if (screenShotFile.exists() && screenShotFile.isFile()) {
                model.setScreenShot(screenShotFile);
            }

            File dumpFile = new File(dirPath, "memoryDump.hprof");
            if (dumpFile.exists() && dumpFile.isFile()) {
                model.setDump(dumpFile);
            }
        } catch (Exception var11) {
            ;
        }

        return model;
    }
}