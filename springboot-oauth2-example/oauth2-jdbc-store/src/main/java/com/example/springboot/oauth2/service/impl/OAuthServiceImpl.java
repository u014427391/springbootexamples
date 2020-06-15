package com.example.springboot.oauth2.service.impl;

import com.example.springboot.oauth2.entity.dto.OauthClientDetailsDto;
import com.example.springboot.oauth2.mapper.OAuthDao;
import com.example.springboot.oauth2.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    OAuthDao oAuthDao;

    @Override
    @Transactional(rollbackFor = Exception.class,readOnly = true)
    public OauthClientDetailsDto loadOauthClientDetails(String clientId) {
        return oAuthDao.loadOauthClientDetailsByClientId(clientId);
    }


}
