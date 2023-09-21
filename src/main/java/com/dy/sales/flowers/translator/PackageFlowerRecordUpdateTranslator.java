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
 * 包花记录编辑转换
 *
 * @author chao.lan
 */
@Component
public class PackageFlowerRecordUpdateTranslator implements BiFunction<PackageFlowerRecordSave, User, PackageFlowerRecord> {

    @Override
    public PackageFlowerRecord apply(PackageFlowerRecordSave request, User user) {
        Assert.notNull(request.getId(), "包花记录ID为空");
        Assert.notNull(request.getPackageId(), "包花人为空");
        Assert.notNull(request.getPickerId(), "采花人为空");
        Assert.notNull(request.getCategoryId(), "品种为空");
        Assert.notNull(request.getSpecificationId(), "规格为空");
        Assert.notNull(request.getPackageAmount(), "包花数量为空");

        if (Objects.nonNull(request.getDamageAmount()) && request.getDamageAmount()>0 && Objects.isNull(request.getDamageReasonId())) {
            throw new BusinessException(ResultCode.PARAM_EXCEPTION);
        }
        PackageFlowerRecord update = new PackageFlowerRecord();


        YNEnum ynEnum = YNEnum.get(request.getYn());
        if (Objects.isNull(ynEnum)) {
            throw new BusinessException(ResultCode.PARAM_EXCEPTION);
        }
        update.setId(request.getId());
        update.setPickerId(request.getPickerId());
        update.setCategoryId(request.getCategoryId());
        update.setSpecificationId(request.getSpecificationId());
        update.setDamageReasonId(request.getDamageReasonId());
        update.setPackageAmount(request.getPackageAmount());
        update.setDamageAmount(request.getDamageAmount());

        //删除状态编辑后，变为提报状态
        if (Objects.equals(ynEnum, YNEnum.DELETED)) {
            update.setYn(YNEnum.NO.getCode());
        } else {
            update.setYn(ynEnum.getCode());
        }

        update.setModifierId(user.getId());
        update.setModifierName(user.getName());
        update.setModified(LocalDateTime.now());

        return update;
    }
}
