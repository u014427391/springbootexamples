package com.example.springboot.oauth2.service;

import com.example.springboot.oauth2.entity.dto.OauthClientDetailsDto;

public interface OAuthService {

    OauthClientDetailsDto loadOauthClientDetails(String clientId);

}
