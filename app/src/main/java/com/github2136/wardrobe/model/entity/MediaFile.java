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
    private String name;
    private String objectId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.objectId);
    }

    public MediaFile() {}

    protected MediaFile(Parcel in) {
        this.url = in.readString();
        this.name = in.readString();
        this.objectId = in.readString();
    }

    public static final Creator<MediaFile> CREATOR = new Creator<MediaFile>() {
        @Override
        public MediaFile createFromParcel(Parcel source) {return new MediaFile(source);}

        @Override
        public MediaFile[] newArray(int size) {return new MediaFile[size];}
    };
}
