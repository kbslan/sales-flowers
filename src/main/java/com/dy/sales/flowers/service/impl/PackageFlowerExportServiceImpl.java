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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 包花数量导出
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/22 15:28
 */
@Slf4j
@Service("packageFlowerExportService")
public class PackageFlowerExportServiceImpl implements ExportService {

    @Resource
    private OptionConfigService optionConfigService;
    @Resource
    private PackageFlowerRecordService packageFlowerRecordService;

    public Map<Long, String> getSpecifications() {
        return optionConfigService.list(OptionEnum.FLOWER_SPECIFICATION)
                .stream().collect(Collectors.toMap(OptionConfig::getId, OptionConfig::getLabel));
    }

    @Override
    public List<List<String>> head() {
        List<List<String>> head = new ArrayList<>();
        head.add(Collections.singletonList("日期"));
//        head.add(Collections.singletonList("包花人编号"));
        head.add(Collections.singletonList("包花人名称"));
//        head.add(Collections.singletonList("采花人编号"));
        head.add(Collections.singletonList("采花人名称"));
//        head.add(Collections.singletonList("品种编码"));
        head.add(Collections.singletonList("品种名称"));
        getSpecifications().forEach((id, label) -> head.add(Collections.singletonList(label)));
        head.add(Collections.singletonList("合计"));
        return head;
    }

    @Override
    public List<List<String>> data(Object request) {
        if (request instanceof PackageFlowerRecordQuery) {
            PackageFlowerRecordQuery query = (PackageFlowerRecordQuery) request;
            LambdaQueryWrapper<PackageFlowerRecord> queryWrapper = packageFlowerRecordService.buildQueryWrapper(query);
            //采花人
            Map<Long, OptionConfig> pickerMap = optionConfigService.list(OptionEnum.FLOWER_PICKER)
                    .stream().collect(Collectors.toMap(OptionConfig::getId, Function.identity()));
            //品种
            Map<Long, OptionConfig> categoryMap = optionConfigService.list(OptionEnum.FLOWER_CATEGORY)
                    .stream().collect(Collectors.toMap(OptionConfig::getId, Function.identity()));

            return packageFlowerRecordService.list(queryWrapper).stream().map(record -> {
                List<String> row = new ArrayList<>();
                //日期
                row.add(record.getCreated().format(CommonConstants.YYYY_MM_DD_A));
                //包花人
//                row.add(record.getPackageId().toString());
                row.add(record.getCreatorName());
                //采花人
//                row.add(record.getPickerId().toString());
                row.add(pickerMap.get(record.getPickerId()).getLabel());
//                row.add(record.getCategoryId().toString());
                row.add(categoryMap.get(record.getCategoryId()).getLabel());
                getSpecifications().forEach((id, label) -> {
                    if (record.getSpecificationId().equals(id)) {
                        row.add(record.getPackageAmount().toString());
                    } else {
                        row.add(StringUtils.EMPTY);
                    }
                });
                row.add(record.getPackageAmount().toString());
                return row;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
