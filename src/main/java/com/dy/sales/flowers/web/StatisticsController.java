package com.dy.sales.flowers.web;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dy.sales.flowers.annotation.CurrentUser;
import com.dy.sales.flowers.entity.OptionConfig;
import com.dy.sales.flowers.entity.PackageFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.service.OptionConfigService;
import com.dy.sales.flowers.service.PackageFlowerRecordService;
import com.dy.sales.flowers.vo.enums.OptionEnum;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.response.BarStatisticsVo;
import com.dy.sales.flowers.vo.response.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 *     数据看板统计
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/26 22:33
 */
@RestController
@RequestMapping("/api/statics")
public class StatisticsController {

    @Resource
    private PackageFlowerRecordService packageFlowerRecordService;
    @Resource
    private OptionConfigService optionConfigService;

    /**
     * 柱状图数据统计
     *
     * @param user 当前用户信息
     * @return 结果
     */
    @GetMapping("/bar")
    public HttpResult<BarStatisticsVo> bar(@CurrentUser User user) {
        BarStatisticsVo result = new BarStatisticsVo();
        Map<Long, String> categoryMap = optionConfigService.list(OptionEnum.FLOWER_CATEGORY, YNEnum.YES.getCode())
                .stream().collect(Collectors.toMap(OptionConfig::getId, OptionConfig::getLabel));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(30);
        // 统计时间范围
        result.setStart(start);
        result.setEnd(now);

        //查询数据
        List<PackageFlowerRecord> list = packageFlowerRecordService.list(
                Wrappers.lambdaQuery(PackageFlowerRecord.class)
                        .eq(PackageFlowerRecord::getYn, YNEnum.YES.getCode())
                        .between(PackageFlowerRecord::getCreated, start, now)
        );
        AtomicLong flowerAmount = new AtomicLong(0L);
        AtomicLong damageAmount = new AtomicLong(0L);
        list.stream().collect(Collectors.groupingBy(PackageFlowerRecord::getCategoryId))
                .forEach((categoryId, flowers) -> {
                    //品种
                    result.getCategories().add(categoryMap.get(categoryId));
                    //包花数量
                    long packageSum = flowers.stream().mapToLong(PackageFlowerRecord::getPackageAmount).sum();
                    flowerAmount.addAndGet(packageSum);
                    result.getPackageAmounts().add(packageSum);
                    //损坏数量
                    long damageSum = flowers.stream().mapToLong(PackageFlowerRecord::getDamageAmount).sum();
                    damageAmount.addAndGet(damageSum);
                    result.getDamageAmounts().add(damageSum * -1);
                });
        // 品种数量
        result.setCategoryAmount(result.getCategories().size());
        // 包花数量/损坏数量
        result.setFlowerPerDamage(String.format("%s/%s", flowerAmount.get(), damageAmount.get()));
        // 报损原因数量
        result.setDamageReasonAmount(list.stream().map(PackageFlowerRecord::getDamageReasonId).distinct().count());
        return HttpResult.success(result);
    }

}
