package com.leo.xmdebug.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DebugDatabaseModel implements Parcelable {
    private String name;
    private List<Table> tables;
    public static final Creator<DebugDatabaseModel> CREATOR = new Creator<DebugDatabaseModel>() {
        public DebugDatabaseModel createFromParcel(Parcel source) {
            return new DebugDatabaseModel(source);
        }

        public DebugDatabaseModel[] newArray(int size) {
            return new DebugDatabaseModel[size];
        }
    };

    public List<DebugDatabaseModel.Table> getTables() {
        return this.tables;
    }

    public void setTables(List<DebugDatabaseModel.Table> tables) {
        this.tables = tables;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DebugDatabaseModel() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.tables);
    }

    protected DebugDatabaseModel(Parcel in) {
        this.name = in.readString();
        this.tables = in.createTypedArrayList(DebugDatabaseModel.Table.CREATOR);
    }

    public static class Table implements Parcelable {
        private String name;
        private String[] columns;
        private List<Serializable> data;
        public static final Creator<DebugDatabaseModel.Table> CREATOR = new Creator<DebugDatabaseModel.Table>() {
            public DebugDatabaseModel.Table createFromParcel(Parcel source) {
                return new DebugDatabaseModel.Table(source);
            }

            public DebugDatabaseModel.Table[] newArray(int size) {
                return new DebugDatabaseModel.Table[size];
            }
        };

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getColumns() {
            return this.columns;
        }

        public void setColumns(String[] columns) {
            this.columns = columns;
        }

        public List<Serializable> getData() {
            return this.data;
        }

        public void setData(List<Serializable> data) {
            this.data = data;
        }

        public Table() {
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeStringArray(this.columns);
            dest.writeList(this.data);
        }

        protected Table(Parcel in) {
            this.name = in.readString();
            this.columns = in.createStringArray();
            this.data = new ArrayList();
            in.readList(this.data, Serializable.class.getClassLoader());
        }
    }
}