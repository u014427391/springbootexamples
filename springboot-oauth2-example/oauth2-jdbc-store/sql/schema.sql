/** used in tests that use MYSQL **/

CREATE TABLE oauth_client_details (
  client_id VARCHAR(128) PRIMARY KEY,
  resource_ids VARCHAR(128),
  client_secret VARCHAR(128),
  scope VARCHAR(128),
  authorized_grant_types VARCHAR(128),
  web_server_redirect_uri VARCHAR(128),
  authorities VARCHAR(128),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  ENABLE TINYINT(1) DEFAULT '1',
  autoapprove VARCHAR(128)
);

CREATE TABLE oauth_client_token (
  token_id VARCHAR(128),
  token BLOB,
  authentication_id VARCHAR(128) PRIMARY KEY,
  user_name VARCHAR(128),
  client_id VARCHAR(128)
);

CREATE TABLE oauth_access_token (
  token_id VARCHAR(128),
  token BLOB,
  authentication_id VARCHAR(128) PRIMARY KEY,
  user_name VARCHAR(128),
  client_id VARCHAR(128),
  authentication BLOB,
  refresh_token VARCHAR(128)
);


CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(128),
  token BLOB,
  authentication BLOB
);


CREATE TABLE oauth_code (
  CODE VARCHAR(128), authentication BLOB
);

CREATE TABLE oauth_approvals (
	userId VARCHAR(128),
	clientId VARCHAR(128),
	scope VARCHAR(128),
	STATUS VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- # customized oauth_client_details table
CREATE TABLE ClientDetails (
  appId VARCHAR(128) PRIMARY KEY,
  resourceIds VARCHAR(128),
  appSecret VARCHAR(128),
  scope VARCHAR(128),
  grantTypes VARCHAR(128),
  redirectUrl VARCHAR(128),
  authorities VARCHAR(128),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(128)
);


insert into `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) values('cms',NULL,'{noop}secret','all','authorization_code','http://localhost:8084/cms/login',NULL,'60','60',NULL,'true');
insert into `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) values('oa',NULL,'{noop}secret','all','authorization_code','http://localhost:8082/oa/login',NULL,'60','60',NULL,'true');



/** 业务数据**/

 -- # 用户表
CREATE TABLE USER (
  id INT(11) NOT NULL AUTO_INCREMENT,
  email VARCHAR(255),
  PASSWORD VARCHAR(255) NOT NULL,
  phone VARCHAR(255),
  username VARCHAR(255) NOT NULL UNIQUE,
  last_login_time DATETIME ,
  PRIMARY KEY  (id)
)
INSERT INTO USER (email,PASSWORD,phone,username,last_login_time) VALUES ('123456@qq.com','11','15522288822','admin',SYSDATE());

 -- # 用户权限表
CREATE TABLE user_privilege (
  user_id INT(11),
  privilege VARCHAR(255),
  KEY idx_user_id (user_id)
)
INSERT INTO user_privilege(user_id,privilege) VALUES(1,'ROLE_ADMIN');

