package com.github2136.wardrobe.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVFile;
import com.github2136.sqliteutil.Column;
import com.github2136.sqliteutil.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服装类
 * Created by yb on 2017/7/20.
 */

@Table
public class ClothingInfo implements Parcelable {
    @Column(primaryKey = true)
    private String objectId;
    @Column
    private Date createdAt;
    @Column
    private Date updatedAt;
    /****************************************/
    @Column
    private String userId;//所属用户
    @Column
    private String ciType;//服装类型
    @Column
    private String ciColor;//服装颜色
    @Column
    private String ciSeason;//季节
    @Column
    private String ciRemark;//备注
    @Column
    private List<MediaFile> ciPicture;//图片
    @Column
    private Boolean valid;//是否启用
    @Column
    private Byte viewSeq;//排序

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCiType() {
        return ciType;
    }

    public void setCiType(String ciType) {
        this.ciType = ciType;
    }

    public String getCiColor() {
        return ciColor;
    }

    public void setCiColor(String ciColor) {
        this.ciColor = ciColor;
    }

    public String getCiSeason() {
        return ciSeason;
    }

    public void setCiSeason(String ciSeason) {
        this.ciSeason = ciSeason;
    }

    public String getCiRemark() {
        return ciRemark;
    }

    public void setCiRemark(String ciRemark) {
        this.ciRemark = ciRemark;
    }

    public List<MediaFile> getCiPicture() {
        return ciPicture;
    }

    public void setCiPicture(List<MediaFile> ciPicture) {
        this.ciPicture = ciPicture;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
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
        dest.writeString(this.objectId);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeString(this.userId);
        dest.writeString(this.ciType);
        dest.writeString(this.ciColor);
        dest.writeString(this.ciSeason);
        dest.writeString(this.ciRemark);
        dest.writeTypedList(this.ciPicture);
        dest.writeValue(this.valid);
        dest.writeValue(this.viewSeq);
    }

    public ClothingInfo() {}

    protected ClothingInfo(Parcel in) {
        this.objectId = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.userId = in.readString();
        this.ciType = in.readString();
        this.ciColor = in.readString();
        this.ciSeason = in.readString();
        this.ciRemark = in.readString();
        this.ciPicture = in.createTypedArrayList(MediaFile.CREATOR);
        this.valid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.viewSeq = (Byte) in.readValue(Byte.class.getClassLoader());
    }

    public static final Creator<ClothingInfo> CREATOR = new Creator<ClothingInfo>() {
        @Override
        public ClothingInfo createFromParcel(Parcel source) {return new ClothingInfo(source);}

        @Override
        public ClothingInfo[] newArray(int size) {return new ClothingInfo[size];}
    };
}
