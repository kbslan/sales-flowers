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
 * 配置项编辑转换
 * @author chao.lan
 */
@Component
public class OptionConfigUpdateTranslator implements BiFunction<OptionQuery, User, OptionConfig> {

    @Override
    public OptionConfig apply(OptionQuery request, User user) {
        Assert.notNull(request.getId(), "配置ID为空");
        Assert.notNull(request.getType(), "配置类型为空");
        Assert.notNull(request.getLabel(), "配置名称为空");
        Assert.notNull(request.getValue(), "配置值为空");

        OptionConfig optionConfig = new OptionConfig();
        OptionEnum optionEnum = OptionEnum.get(request.getType());
        if (Objects.isNull(optionEnum)) {
            throw new BusinessException(ResultCode.PARAM_EXCEPTION);
        }
        YNEnum ynEnum = YNEnum.get(request.getYn());
        if (Objects.isNull(ynEnum)) {
            throw new BusinessException(ResultCode.PARAM_EXCEPTION);
        }
        optionConfig.setId(request.getId());
        optionConfig.setType(optionEnum.getCode());
        optionConfig.setLabel(request.getLabel());
        optionConfig.setValue(request.getValue());
        optionConfig.setYn(ynEnum.getCode());
        optionConfig.setModifierId(user.getId());
        optionConfig.setModifierName(user.getName());
        optionConfig.setModified(LocalDateTime.now());

        return optionConfig;
    }
}
