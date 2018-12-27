package com.leo.xmdebug.detail.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.leo.xmdebug.detail.DebugInfoDetail;

import java.util.ArrayList;

public class DebugInfoDetailsNormalModel implements Parcelable {
    private String first;
    private String second;
    private ArrayList<DebugInfoDetail> next;
    private String nextTitle;
    private DebugInfoDetailsNormalModel.NormalInfoType type;
    public static final Creator<DebugInfoDetailsNormalModel> CREATOR = new Creator<DebugInfoDetailsNormalModel>() {
        public DebugInfoDetailsNormalModel createFromParcel(Parcel source) {
            return new DebugInfoDetailsNormalModel(source);
        }

        public DebugInfoDetailsNormalModel[] newArray(int size) {
            return new DebugInfoDetailsNormalModel[size];
        }
    };

    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return this.second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public ArrayList<DebugInfoDetail> getNext() {
        return this.next;
    }

    public void setNext(ArrayList<DebugInfoDetail> next) {
        this.next = next;
    }

    public DebugInfoDetailsNormalModel.NormalInfoType getType() {
        return this.type;
    }

    public void setType(DebugInfoDetailsNormalModel.NormalInfoType type) {
        this.type = type;
    }

    public String getNextTitle() {
        return this.nextTitle;
    }

    public void setNextTitle(String nextTitle) {
        this.nextTitle = nextTitle;
    }

    public DebugInfoDetailsNormalModel() {
        this.type = DebugInfoDetailsNormalModel.NormalInfoType.NORMAL;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first);
        dest.writeString(this.second);
        dest.writeTypedList(this.next);
        dest.writeString(this.nextTitle);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected DebugInfoDetailsNormalModel(Parcel in) {
        this.type = DebugInfoDetailsNormalModel.NormalInfoType.NORMAL;
        this.first = in.readString();
        this.second = in.readString();
        this.next = in.createTypedArrayList(DebugInfoDetail.CREATOR);
        this.nextTitle = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : DebugInfoDetailsNormalModel.NormalInfoType.values()[tmpType];
    }

    public static enum NormalInfoType {
        NORMAL,
        ACTIVITIES,
        PERMISSIONS,
        DATABASE;

        private NormalInfoType() {
        }
    }
}