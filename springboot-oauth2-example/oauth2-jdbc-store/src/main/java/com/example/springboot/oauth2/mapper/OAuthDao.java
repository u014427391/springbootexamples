package com.example.springboot.oauth2.mapper;

import com.example.springboot.oauth2.entity.dto.OauthClientDetailsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


public interface OAuthDao {

    @Select("select * from oauth_client_details where client_id =#{clientId}")
    OauthClientDetailsDto loadOauthClientDetailsByClientId(String clientId);

}
