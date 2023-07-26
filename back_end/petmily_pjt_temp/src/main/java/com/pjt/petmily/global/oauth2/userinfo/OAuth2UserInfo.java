package com.pjt.petmily.global.oauth2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getUserId();
//    public abstract String getNickname();
//    public abstract String getImgUrl();
}
