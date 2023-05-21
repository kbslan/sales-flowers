CREATE TABLE `user`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(150) NOT NULL COMMENT '昵称',
    `mobile`        VARCHAR(50)  NOT NULL COMMENT '电话号码',
    `password`      VARCHAR(100) NOT NULL COMMENT '密码',
    `admin`         INT(11)      NOT NULL DEFAULT '0' COMMENT '是否是管理员',
    `yn`            INT(11)      NOT NULL DEFAULT '1' COMMENT '状态 1: 启用, 0: 禁用',
    `creator_id`    BIGINT(20)   NULL     DEFAULT '0' COMMENT '创建人ID',
    `creator_name`  VARCHAR(150) NULL     DEFAULT NULL COMMENT '创建人名称',
    `created`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier_id`   BIGINT(20)   NULL     DEFAULT '0' COMMENT '更新人ID',
    `modifier_name` VARCHAR(150) NULL     DEFAULT NULL COMMENT '更新人名称',
    `modified`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `salt`          VARCHAR(50)  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uq_mobile_name` (`mobile`, `name`),
    UNIQUE INDEX `mobile` (`mobile`),
    INDEX `name` (`name`)
)
COMMENT ='账号'
COLLATE = 'utf8_general_ci'
ENGINE = InnoDB
;
