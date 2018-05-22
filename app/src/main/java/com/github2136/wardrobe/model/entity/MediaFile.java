package com.github2136.wardrobe.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.github2136.sqliteutil.Column;
import com.github2136.sqliteutil.Table;

import java.util.Date;

/**
 * 图片文件
 * Created by yb on 2017/8/17.
 */
@Table
public class MediaFile implements Parcelable {
    @Column(primaryKey = true)
    private String fmId;//文件主键
    @Column
    private String fmPath;//文件路径
    @Column
    private String ciId;//所属服装
    @Column
    private String valid;//是否启用
    @Column
    private Date modifyDate;//修改时间
    @Column
    private String modifyer;//修改人
    @Column
    private Byte viewSeq;//排序

    public String getFmId() {
        return fmId == null ? "" : fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    public String getFmPath() {
        return fmPath == null ? "" : fmPath;
    }

    public void setFmPath(String fmPath) {
        this.fmPath = fmPath;
    }

    public String getCiId() {
        return ciId == null ? "" : ciId;
    }

    public void setCiId(String ciId) {
        this.ciId = ciId;
    }

    public String getValid() {
        return valid == null ? "" : valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyer() {
        return modifyer == null ? "" : modifyer;
    }

    public void setModifyer(String modifyer) {
        this.modifyer = modifyer;
    }

    public Byte getViewSeq() {
        return viewSeq;
    }

    public void setViewSeq(Byte viewSeq) {
        this.viewSeq = viewSeq;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fmId);
        dest.writeString(this.fmPath);
        dest.writeString(this.ciId);
        dest.writeString(this.valid);
        dest.writeLong(this.modifyDate != null ? this.modifyDate.getTime() : -1);
        dest.writeString(this.modifyer);
        dest.writeValue(this.viewSeq);
    }

    public MediaFile() {}

    protected MediaFile(Parcel in) {
        this.fmId = in.readString();
        this.fmPath = in.readString();
        this.ciId = in.readString();
        this.valid = in.readString();
        long tmpModifyDate = in.readLong();
        this.modifyDate = tmpModifyDate == -1 ? null : new Date(tmpModifyDate);
        this.modifyer = in.readString();
        this.viewSeq = (Byte) in.readValue(Byte.class.getClassLoader());
    }

    public static final Creator<MediaFile> CREATOR = new Creator<MediaFile>() {
        @Override
        public MediaFile createFromParcel(Parcel source) {return new MediaFile(source);}

        @Override
        public MediaFile[] newArray(int size) {return new MediaFile[size];}
    };
}
