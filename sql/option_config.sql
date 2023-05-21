CREATE TABLE `option_config`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `type`          INT(150)     NOT NULL COMMENT '类型， 1：采花人，2：品种，3：规格，4：报损原因',
    `label`         VARCHAR(150) NOT NULL DEFAULT '0' COMMENT '名称',
    `value`         VARCHAR(150) NOT NULL DEFAULT '0' COMMENT '值',
    `yn`            INT(11)      NOT NULL DEFAULT '0' COMMENT '状态 1: 启用, 0: 禁用',
    `creator_id`    BIGINT(20)   NOT NULL DEFAULT '0' COMMENT '创建人ID',
    `creator_name`  VARCHAR(150) NULL     DEFAULT NULL COMMENT '创建人名称',
    `created`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier_id`   BIGINT(20)   NOT NULL COMMENT '更新人ID',
    `modifier_name` VARCHAR(150) NULL     DEFAULT NULL COMMENT '更新人名称',
    `modified`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uq_type_lable` (`type`, `label`)
)
COMMENT ='选项配置'
COLLATE = 'utf8_general_ci'
ENGINE = InnoDB
;
