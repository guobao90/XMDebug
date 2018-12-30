package com.leo.xmdebug.mt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class DebugFilesMultiModel implements Parcelable {
    private File file;
    public static final Creator<DebugFilesMultiModel> CREATOR = new Creator<DebugFilesMultiModel>() {
        public DebugFilesMultiModel createFromParcel(Parcel source) {
            return new DebugFilesMultiModel(source);
        }

        public DebugFilesMultiModel[] newArray(int size) {
            return new DebugFilesMultiModel[size];
        }
    };

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.file);
    }

    public DebugFilesMultiModel() {
    }

    protected DebugFilesMultiModel(Parcel in) {
        this.file = (File)in.readSerializable();
    }
}
