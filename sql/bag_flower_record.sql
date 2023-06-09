CREATE TABLE `bag_flower_record`
(
    `id`               BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `bag_id`           BIGINT(20)   NOT NULL COMMENT '包花人ID',
    `picker_id`        BIGINT(20)   NOT NULL COMMENT '采花人ID',
    `category_id`      BIGINT(20)   NOT NULL COMMENT '品种ID',
    `specification_id` BIGINT(20)   NOT NULL COMMENT '规格ID',
    `bag_amount`       BIGINT(20)   NOT NULL DEFAULT '0' COMMENT '包花数量',
    `damage_reason_id` BIGINT(20)   NULL     DEFAULT NULL COMMENT '损坏原因ID',
    `damage_amount`    BIGINT(20)   NULL     DEFAULT '0' COMMENT '损坏数量',
    `yn`               INT(11)      NOT NULL DEFAULT '0' COMMENT '状态 1: 审核通过, 0: 提报中, -1: 删除',
    `creator_id`       BIGINT(20)   NOT NULL DEFAULT '0' COMMENT '创建人ID',
    `creator_name`     VARCHAR(150) NOT NULL DEFAULT '0' COMMENT '创建人名称',
    `created`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier_id`      BIGINT(20)   NULL     DEFAULT NULL COMMENT '更新人ID',
    `modifier_name`    VARCHAR(150) NULL     DEFAULT NULL COMMENT '更新人名称',
    `modified`         DATETIME     NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
)
COMMENT ='包花记录'
COLLATE = 'utf8_general_ci'
ENGINE = InnoDB
;
