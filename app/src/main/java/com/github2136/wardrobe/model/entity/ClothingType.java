package com.github2136.wardrobe.model.entity;

import com.github2136.sqliteutil.Column;
import com.github2136.sqliteutil.Table;

import java.util.Date;

/**
 * 服装类型
 * Created by yb on 2017/8/17.
 */
@Deprecated
@Table
public class ClothingType {
    @Column
    private String objectId;
    @Column
    private Date createdAt;
    @Column
    private Date updatedAt;
    /**********************************************************/
    @Column
    private String ctId;//类型主键
    @Column
    private String upCtId;//上级主键
    @Column
    private String ctName;//类型名称
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

    public String getCtId() {
        return ctId == null ? "" : ctId;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public String getUpCtId() {
        return upCtId == null ? "" : upCtId;
    }

    public void setUpCtId(String upCtId) {
        this.upCtId = upCtId;
    }

    public String getCtName() {
        return ctName == null ? "" : ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
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

}
