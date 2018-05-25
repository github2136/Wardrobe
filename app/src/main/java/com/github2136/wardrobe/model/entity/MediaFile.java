package com.github2136.wardrobe.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.github2136.sqliteutil.Table;

/**
 * 图片文件
 * Created by yb on 2017/8/17.
 */
@Table
public class MediaFile implements Parcelable {
    private String url;
    private FileObjectBean fileObject;
    private String localPath;
    private String name;
    private String objectId;
    private boolean dirty;

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    public FileObjectBean getFileObject() { return fileObject;}

    public void setFileObject(FileObjectBean fileObject) { this.fileObject = fileObject;}

    public String getLocalPath() { return localPath;}

    public void setLocalPath(String localPath) { this.localPath = localPath;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getObjectId() { return objectId;}

    public void setObjectId(String objectId) { this.objectId = objectId;}

    public boolean isDirty() { return dirty;}

    public void setDirty(boolean dirty) { this.dirty = dirty;}

    public static class FileObjectBean implements Parcelable {

        private boolean fetchWhenSave;
        private String objectId;
        private String className;
        private boolean isDataReady;
        private boolean requestStatistic;

        public boolean isFetchWhenSave() { return fetchWhenSave;}

        public void setFetchWhenSave(boolean fetchWhenSave) { this.fetchWhenSave = fetchWhenSave;}

        public String getObjectId() { return objectId;}

        public void setObjectId(String objectId) { this.objectId = objectId;}

        public String getClassName() { return className;}

        public void setClassName(String className) { this.className = className;}

        public boolean isIsDataReady() { return isDataReady;}

        public void setIsDataReady(boolean isDataReady) { this.isDataReady = isDataReady;}

        public boolean isRequestStatistic() { return requestStatistic;}

        public void setRequestStatistic(boolean requestStatistic) { this.requestStatistic = requestStatistic;}

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.fetchWhenSave ? (byte) 1 : (byte) 0);
            dest.writeString(this.objectId);
            dest.writeString(this.className);
            dest.writeByte(this.isDataReady ? (byte) 1 : (byte) 0);
            dest.writeByte(this.requestStatistic ? (byte) 1 : (byte) 0);
        }

        public FileObjectBean() {}

        protected FileObjectBean(Parcel in) {
            this.fetchWhenSave = in.readByte() != 0;
            this.objectId = in.readString();
            this.className = in.readString();
            this.isDataReady = in.readByte() != 0;
            this.requestStatistic = in.readByte() != 0;
        }

        public static final Creator<FileObjectBean> CREATOR = new Creator<FileObjectBean>() {
            @Override
            public FileObjectBean createFromParcel(Parcel source) {return new FileObjectBean(source);}

            @Override
            public FileObjectBean[] newArray(int size) {return new FileObjectBean[size];}
        };
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.fileObject, flags);
        dest.writeString(this.localPath);
        dest.writeString(this.name);
        dest.writeString(this.objectId);
        dest.writeByte(this.dirty ? (byte) 1 : (byte) 0);
    }

    public MediaFile() {}

    protected MediaFile(Parcel in) {
        this.url = in.readString();
        this.fileObject = in.readParcelable(FileObjectBean.class.getClassLoader());
        this.localPath = in.readString();
        this.name = in.readString();
        this.objectId = in.readString();
        this.dirty = in.readByte() != 0;
    }

    public static final Creator<MediaFile> CREATOR = new Creator<MediaFile>() {
        @Override
        public MediaFile createFromParcel(Parcel source) {return new MediaFile(source);}

        @Override
        public MediaFile[] newArray(int size) {return new MediaFile[size];}
    };
}
