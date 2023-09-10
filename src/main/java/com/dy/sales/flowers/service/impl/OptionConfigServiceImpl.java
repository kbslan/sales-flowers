package com.dy.sales.flowers.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dy.sales.flowers.entity.OptionConfig;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.mapper.OptionConfigMapper;
import com.dy.sales.flowers.service.OptionConfigService;
import com.dy.sales.flowers.translator.OptionConfigInsertTranslator;
import com.dy.sales.flowers.translator.OptionConfigUpdateTranslator;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.ChangeStatusParams;
import com.dy.sales.flowers.vo.request.OptionQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 选项配置 服务实现类
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
@Service
public class OptionConfigServiceImpl extends ServiceImpl<OptionConfigMapper, OptionConfig> implements OptionConfigService {

    @Resource
    private OptionConfigInsertTranslator optionConfigInsertTranslator;
    @Resource
    private OptionConfigUpdateTranslator optionConfigUpdateTranslator;

    @Override
    public Page<OptionConfig> pageQuery(OptionQuery request) {
        return this.page(new Page<>(request.getPage(), request.getSize()),
                Wrappers.<OptionConfig>lambdaQuery()
                        .eq(OptionConfig::getType, request.getType())
                        .like(StringUtils.isNotBlank(request.getLabel()), OptionConfig::getLabel, request.getLabel())
                        .eq(OptionConfig::getYn, Objects.isNull(request.getYn()) ? YNEnum.YES.getCode() : request.getYn())
                        .orderByDesc(Arrays.asList(OptionConfig::getType, OptionConfig::getModified))
        );
    }

    @Override
    public boolean changeYn(ChangeStatusParams request, User user) {
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            return this.update(
                    new OptionConfig()
                            .setYn(request.getStatus())
                            .setModified(LocalDateTime.now())
                            .setModifierId(user.getId())
                            .setModifierName(user.getName()),
                    Wrappers.<OptionConfig>lambdaQuery()
                            .in(OptionConfig::getId, request.getIds())
            );

        }
        return false;
    }

    @Override
    public boolean saveOption(OptionQuery request, User user) {
        return Objects.isNull(request.getId())
                ? this.save(optionConfigInsertTranslator.apply(request, user))
                : this.updateById(optionConfigUpdateTranslator.apply(request, user));
    }
}
