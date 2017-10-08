package com.github2136.wardrobe.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.github2136.sqliteutil.Column;
import com.github2136.sqliteutil.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服装类
 * Created by yubin on 2017/7/20.
 */

@Table
public class ClothingInfo implements Parcelable {
    @Column
    private String ciId;//主键
    @Column
    private String ciType;//服装类型
    @Column
    private String ciColor;//服装颜色
    @Column
    private String ciSeason;//季节
    @Column
    private String ciRemark;//备注
    @Column
    private String valid;//是否启用
    @Column
    private Date modifyDate;//修改时间
    @Column
    private String modifyer;//修改人
    @Column
    private Byte viewSeq;//排序
    private List<MediaFile> mediaFiles;//图片

    public String getCiId() {
        return ciId == null ? "" : ciId;
    }

    public void setCiId(String ciId) {
        this.ciId = ciId;
    }

    public String getCiType() {
        return ciType == null ? "" : ciType;
    }

    public void setCiType(String ciType) {
        this.ciType = ciType;
    }

    public String getCiSeason() {
        return ciSeason == null ? "" : ciSeason;
    }

    public void setCiSeason(String ciSeason) {
        this.ciSeason = ciSeason;
    }

    public String getCiRemark() {
        return ciRemark == null ? "" : ciRemark;
    }

    public void setCiRemark(String ciRemark) {
        this.ciRemark = ciRemark;
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

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public String getCiColor() {
        return ciColor == null ? "" : ciColor;
    }

    public void setCiColor(String ciColor) {
        this.ciColor = ciColor;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ciId);
        dest.writeString(this.ciType);
        dest.writeString(this.ciColor);
        dest.writeString(this.ciSeason);
        dest.writeString(this.ciRemark);
        dest.writeString(this.valid);
        dest.writeLong(this.modifyDate != null ? this.modifyDate.getTime() : -1);
        dest.writeString(this.modifyer);
        dest.writeValue(this.viewSeq);
        dest.writeList(this.mediaFiles);
    }

    public ClothingInfo() {}

    protected ClothingInfo(Parcel in) {
        this.ciId = in.readString();
        this.ciType = in.readString();
        this.ciColor = in.readString();
        this.ciSeason = in.readString();
        this.ciRemark = in.readString();
        this.valid = in.readString();
        long tmpModifyDate = in.readLong();
        this.modifyDate = tmpModifyDate == -1 ? null : new Date(tmpModifyDate);
        this.modifyer = in.readString();
        this.viewSeq = (Byte) in.readValue(Byte.class.getClassLoader());
        this.mediaFiles = new ArrayList<MediaFile>();
        in.readList(this.mediaFiles, MediaFile.class.getClassLoader());
    }

    public static final Creator<ClothingInfo> CREATOR = new Creator<ClothingInfo>() {
        @Override
        public ClothingInfo createFromParcel(Parcel source) {return new ClothingInfo(source);}

        @Override
        public ClothingInfo[] newArray(int size) {return new ClothingInfo[size];}
    };
}
