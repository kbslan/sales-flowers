package com.dy.sales.flowers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dy.sales.flowers.entity.PackageFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.BusinessException;
import com.dy.sales.flowers.mapper.PackageFlowerRecordMapper;
import com.dy.sales.flowers.service.PackageFlowerRecordService;
import com.dy.sales.flowers.translator.PackageFlowRecordInsertTranslator;
import com.dy.sales.flowers.translator.PackageFlowerRecordUpdateTranslator;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordQuery;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordSave;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 包花记录 服务实现类
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
@Service
public class PackageFlowerRecordServiceImpl extends ServiceImpl<PackageFlowerRecordMapper, PackageFlowerRecord> implements PackageFlowerRecordService {
    @Resource
    private PackageFlowRecordInsertTranslator packageFlowRecordInsertTranslator;
    @Resource
    private PackageFlowerRecordUpdateTranslator packageFlowerRecordUpdateTranslator;

    @Override
    public Page<PackageFlowerRecord> pageQuery(PackageFlowerRecordQuery request) {
        LambdaQueryWrapper<PackageFlowerRecord> wrapper = Wrappers.<PackageFlowerRecord>lambdaQuery()
                .eq(Objects.nonNull(request.getId()), PackageFlowerRecord::getId, request.getId())
                .eq(Objects.nonNull(request.getPackageId()), PackageFlowerRecord::getPackageId, request.getPackageId())
                .eq(Objects.nonNull(request.getPickerId()), PackageFlowerRecord::getPickerId, request.getPickerId())
                .eq(Objects.nonNull(request.getCategoryId()), PackageFlowerRecord::getCategoryId, request.getCategoryId())
                .eq(Objects.nonNull(request.getSpecificationId()), PackageFlowerRecord::getSpecificationId, request.getSpecificationId())
                .eq(Objects.nonNull(request.getDamageReasonId()), PackageFlowerRecord::getDamageReasonId, request.getDamageReasonId())
                ;
        if (Objects.nonNull(request.getCreated()) && Objects.nonNull(request.getCreated().getMin()) && Objects.nonNull(request.getCreated().getMax())) {
            wrapper.between(PackageFlowerRecord::getCreated, request.getCreated().getMin(), request.getCreated().getMax());
        }
        if (Objects.isNull(request.getYn())) {
            wrapper.ne(PackageFlowerRecord::getYn, YNEnum.DELETED.getCode());
        } else {
            wrapper.eq(PackageFlowerRecord::getYn, request.getYn());
        }
        wrapper.orderByDesc(PackageFlowerRecord::getModified);
        return this.page(new Page<>(request.getPage(), request.getSize()), wrapper);
    }

    @Override
    public boolean deletes(List<Long> ids, User user) {
        if (CollectionUtils.isNotEmpty(ids)) {
            return this.updateBatchById(ids.stream().map(id -> {
                PackageFlowerRecord update = new PackageFlowerRecord();
                update.setId(id);
                update.setYn(YNEnum.DELETED.getCode());
                update.setModified(LocalDateTime.now());
                return update;
            }).collect(Collectors.toList()), 200);
        }
        return false;
    }

    @Override
    public boolean saveOption(PackageFlowerRecordSave request, User user) {
        return Objects.isNull(request.getId())
                ? this.save(packageFlowRecordInsertTranslator.apply(request, user))
                : this.updateById(packageFlowerRecordUpdateTranslator.apply(request, user));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<PackageFlowerRecordSave> request, User user) {
        if (CollectionUtils.isEmpty(request)) {
            return false;
        }

        request.forEach(item -> {
            boolean success = saveOption(item, user);
            if (!success) {
                throw new BusinessException(ResultCode.SYS_EXCEPTION);
            }
        });
        return true;
    }
}
