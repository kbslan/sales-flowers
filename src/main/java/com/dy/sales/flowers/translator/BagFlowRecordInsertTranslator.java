package com.dy.sales.flowers.translator;

import com.dy.sales.flowers.entity.BagFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.BagFlowerRecordQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

/**
 * 包花记录新增转换
 * @author chao.lan
 */
@Component
public class BagFlowRecordInsertTranslator implements BiFunction<BagFlowerRecordQuery, User, BagFlowerRecord> {

    @Override
    public BagFlowerRecord apply(BagFlowerRecordQuery request, User user) {
        Assert.notNull(request.getPickerId(), "采花人为空");
        Assert.notNull(request.getCategoryId(), "品种为空");
        Assert.notNull(request.getSpecificationId(), "规格为空");
        Assert.notNull(request.getBagAmount(), "包花数量为空");

        BagFlowerRecord entity = new BagFlowerRecord();
        entity.setBagId(user.getId());
        entity.setPickerId(request.getPickerId());
        entity.setCategoryId(request.getCategoryId());
        entity.setSpecificationId(request.getSpecificationId());
        entity.setDamageReasonId(request.getDamageReasonId());
        entity.setBagAmount(request.getBagAmount());
        entity.setDamageAmount(request.getDamageAmount());
        //提报中
        entity.setYn(YNEnum.NO.getCode());
        entity.setCreatorId(user.getId());
        entity.setCreatorName(user.getName());
        entity.setCreated(LocalDateTime.now());
        entity.setModifierId(user.getId());
        entity.setModifierName(user.getName());
        entity.setModified(LocalDateTime.now());

        return entity;
    }
}
