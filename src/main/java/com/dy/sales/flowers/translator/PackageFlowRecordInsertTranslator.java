package com.dy.sales.flowers.translator;

import com.dy.sales.flowers.entity.PackageFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.BusinessException;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordQuery;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordSave;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 包花记录新增转换
 * @author chao.lan
 */
@Component
public class PackageFlowRecordInsertTranslator implements BiFunction<PackageFlowerRecordSave, User, PackageFlowerRecord> {

    @Override
    public PackageFlowerRecord apply(PackageFlowerRecordSave request, User user) {
        Assert.notNull(request.getPickerId(), "采花人为空");
        Assert.notNull(request.getCategoryId(), "品种为空");
        Assert.notNull(request.getSpecificationId(), "规格为空");
        Assert.notNull(request.getPackageAmount(), "包花数量为空");

        if (Objects.nonNull(request.getDamageAmount()) && request.getDamageAmount()>0 && Objects.isNull(request.getDamageReasonId())) {
            throw new BusinessException(ResultCode.PARAM_EXCEPTION);
        }

        PackageFlowerRecord entity = new PackageFlowerRecord();
        entity.setPackageId(user.getId());
        entity.setPickerId(request.getPickerId());
        entity.setCategoryId(request.getCategoryId());
        entity.setSpecificationId(request.getSpecificationId());
        entity.setDamageReasonId(request.getDamageReasonId());
        entity.setPackageAmount(request.getPackageAmount());
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
