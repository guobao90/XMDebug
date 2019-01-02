package com.leo.xmdebug.detail;

import android.os.Parcel;
import android.os.Parcelable;

public class DebugInfoDetail implements Parcelable {
    private DebugInfoDetail.InfoType type;
    private String stringExtra;
    public static final Creator<DebugInfoDetail> CREATOR = new Creator<DebugInfoDetail>() {
        public DebugInfoDetail createFromParcel(Parcel source) {
            return new DebugInfoDetail(source);
        }

        public DebugInfoDetail[] newArray(int size) {
            return new DebugInfoDetail[size];
        }
    };

    public DebugInfoDetail(DebugInfoDetail.InfoType type) {
        this.type = type;
    }

    public DebugInfoDetail.InfoType getType() {
        return this.type;
    }

    public void setType(DebugInfoDetail.InfoType type) {
        this.type = type;
    }

    public String getStringExtra() {
        return this.stringExtra;
    }

    public void setStringExtra(String stringExtra) {
        this.stringExtra = stringExtra;
    }

    public DebugInfoDetail() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.stringExtra);
    }

    protected DebugInfoDetail(Parcel in) {
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : DebugInfoDetail.InfoType.values()[tmpType];
        this.stringExtra = in.readString();
    }

    public enum InfoType {
        APP_ABSTRACT,
        APP_NAME,
        PACKAGE_NAME,
        VERSION,
        VERSION_CODE,
        FIRST_INSTALL_TIME,
        LAST_UPDATE_TIME,
        ACTIVITIES_SUMMARY,
        ACTIVITIES,
        SERVICES_SUMMARY,
        SERVICES,
        RECEIVERS_SUMMARY,
        RECEIVERS,
        PROVIDERS_SUMMARY,
        PROVIDERS,
        PERMISSIONS_SUMMARY,
        PERMISSIONS,
        INSTALLED_APP_SUMMARY,
        INSTALLED_APP,
        DEVICE_ABSTRACT,
        ANDROID_VERSION,
        MODEL,
        RESOLUTION,
        ROOT,
        ABI,
        UUID,
        DEVICE_TOKEN,
        ACCOUNT_ABSTRACT,
        SHARED_PREFERENCE_LIST,
        SHARED_PREFERENCE_DETAIL,
        FILES,
        DATABASE_LIST,
        DATABASE_DETAILS,
        CRASH_LOG_LIST,
        CRASH_LOG_DETAIL;

        private InfoType() {
        }
    }
}