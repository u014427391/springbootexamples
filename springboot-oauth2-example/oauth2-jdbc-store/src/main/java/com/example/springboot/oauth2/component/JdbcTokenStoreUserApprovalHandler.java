package com.example.springboot.oauth2.component;

import com.example.springboot.oauth2.entity.OauthClientDetails;
import com.example.springboot.oauth2.entity.dto.OauthClientDetailsDto;
import com.example.springboot.oauth2.service.OAuthService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  自定义TokenStoreUserApprovalHandler
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/06/15 14:48  修改内容:
 * </pre>
 */
//@Component
public class JdbcTokenStoreUserApprovalHandler extends TokenStoreUserApprovalHandler {

    Logger LOG = LoggerFactory.getLogger(JdbcTokenStoreUserApprovalHandler.class);

    @Autowired
    OAuthService oAuthService;

    @Override
    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        if (super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        }
        if (!userAuthentication.isAuthenticated()) {
            return false;
        }
        String clientId = authorizationRequest.getClientId();
        LOG.info("clientId:[{}]",clientId);
        OauthClientDetailsDto clientDetails = oAuthService.loadOauthClientDetails(clientId);
        return clientDetails != null;
    }
}
