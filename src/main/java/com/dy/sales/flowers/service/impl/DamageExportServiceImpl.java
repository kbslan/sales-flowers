package com.dy.sales.flowers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dy.sales.flowers.entity.OptionConfig;
import com.dy.sales.flowers.entity.PackageFlowerRecord;
import com.dy.sales.flowers.service.ExportService;
import com.dy.sales.flowers.service.OptionConfigService;
import com.dy.sales.flowers.service.PackageFlowerRecordService;
import com.dy.sales.flowers.vo.constant.CommonConstants;
import com.dy.sales.flowers.vo.enums.OptionEnum;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *     报损导出
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/22 15:22
 */
@Slf4j
@Service("damageExportService")
public class DamageExportServiceImpl implements ExportService {

    @Resource
    private PackageFlowerRecordService packageFlowerRecordService;
    @Resource
    private OptionConfigService optionConfigService;
    @Override
    public List<List<String>> head() {
        List<List<String>> head = new ArrayList<>();
        head.add(Collections.singletonList("日期"));
        head.add(Collections.singletonList("采花人编号"));
        head.add(Collections.singletonList("报损人编号"));
        head.add(Collections.singletonList("品种"));
        head.add(Collections.singletonList("报损原因"));
        head.add(Collections.singletonList("数量"));
        return head;
    }

    @Override
    public List<List<String>> data(Object request) {
        if (request instanceof PackageFlowerRecordQuery) {
            PackageFlowerRecordQuery query = (PackageFlowerRecordQuery) request;
            LambdaQueryWrapper<PackageFlowerRecord> queryWrapper = packageFlowerRecordService.buildQueryWrapper(query);
            //损坏原因
            Map<Long, OptionConfig> damageReasonMap = optionConfigService.list(OptionEnum.FLOWER_DAMAGE_REASON)
                    .stream().collect(Collectors.toMap(OptionConfig::getId, Function.identity()));
            //品种
            Map<Long, OptionConfig> categoryMap = optionConfigService.list(OptionEnum.FLOWER_CATEGORY)
                    .stream().collect(Collectors.toMap(OptionConfig::getId, Function.identity()));
            return packageFlowerRecordService.list(queryWrapper).stream().map(record -> {
                List<String> row = new ArrayList<>();
                row.add(record.getCreated().format(CommonConstants.YYYY_MM_DD));
                row.add(record.getPickerId().toString());
                row.add(record.getPackageId().toString());
                row.add(categoryMap.get(record.getCategoryId()).getLabel());
                row.add(Objects.isNull(record.getDamageReasonId()) ? StringUtils.EMPTY : damageReasonMap.get(record.getDamageReasonId()).getLabel());
                row.add(record.getDamageAmount().toString());
                return row;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
