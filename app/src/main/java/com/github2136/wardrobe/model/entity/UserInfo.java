package com.github2136.wardrobe.model.entity;
/**
  *  Create by yb 2018/5/21
 **/
public class UserInfo {

    private String sessionToken;
    private String updatedAt;
    private String objectId;
    private String username;
    private String createdAt;
    private boolean emailVerified;
    private boolean mobilePhoneVerified;

    public String getSessionToken() { return sessionToken;}

    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken;}

    public String getUpdatedAt() { return updatedAt;}

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt;}

    public String getObjectId() { return objectId;}

    public void setObjectId(String objectId) { this.objectId = objectId;}

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getCreatedAt() { return createdAt;}

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt;}

    public boolean isEmailVerified() { return emailVerified;}

    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified;}

    public boolean isMobilePhoneVerified() { return mobilePhoneVerified;}

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) { this.mobilePhoneVerified = mobilePhoneVerified;}
}
