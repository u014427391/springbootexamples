DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    `id` BIGINT(20) NOT NULL COMMENT '主键ID',
    `name` VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    `age` INT(11) NULL DEFAULT NULL COMMENT '年龄',
    `email` VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `is_deleted` INT(2) DEFAULT 0 COMMENT '是否删除标识,0:否,1:是',
    PRIMARY KEY (id)
);
