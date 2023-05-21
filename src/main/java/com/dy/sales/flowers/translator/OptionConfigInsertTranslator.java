package com.dy.sales.flowers.translator;

import com.dy.sales.flowers.entity.OptionConfig;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.BusinessException;
import com.dy.sales.flowers.vo.enums.OptionEnum;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.OptionQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 配置项新增转换
 * @author chao.lan
 */
@Component
public class OptionConfigInsertTranslator implements BiFunction<OptionQuery, User, OptionConfig> {

    @Override
    public OptionConfig apply(OptionQuery request, User user) {
        Assert.notNull(request.getType(), "配置类型为空");
        Assert.notNull(request.getLabel(), "配置名称为空");
        Assert.notNull(request.getValue(), "配置值为空");

        OptionConfig entity = new OptionConfig();
        OptionEnum optionEnum = OptionEnum.get(request.getType());
        if (Objects.isNull(optionEnum)) {
            throw new BusinessException(ResultCode.PARAM_EXCEPTION);
        }
        entity.setType(optionEnum.getCode());
        entity.setLabel(request.getLabel());
        entity.setValue(request.getValue());
        entity.setYn(YNEnum.YES.getCode());
        entity.setCreatorId(user.getId());
        entity.setCreatorName(user.getName());
        entity.setCreated(LocalDateTime.now());
        entity.setModifierId(user.getId());
        entity.setModifierName(user.getName());
        entity.setModified(LocalDateTime.now());

        return entity;
    }
}
