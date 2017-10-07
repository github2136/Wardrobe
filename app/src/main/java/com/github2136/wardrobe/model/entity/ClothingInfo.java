package com.github2136.wardrobe.model.entity;

import com.github2136.sqliteutil.Column;
import com.github2136.sqliteutil.Table;

import java.util.Date;
import java.util.List;

/**
 * 服装类
 * Created by yubin on 2017/7/20.
 */

@Table
public class ClothingInfo {
    @Column
    private String ciId;//主键
    @Column
    private String ciType;//服装类型
    // @Column
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
}
