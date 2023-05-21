package com.dy.sales.flowers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dy.sales.flowers.entity.BagFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.exception.BusinessException;
import com.dy.sales.flowers.mapper.BagFlowerRecordMapper;
import com.dy.sales.flowers.service.BagFlowerRecordService;
import com.dy.sales.flowers.translator.BagFlowRecordInsertTranslator;
import com.dy.sales.flowers.translator.BagFlowerRecordUpdateTranslator;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.BagFlowerRecordQuery;
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
public class BagFlowerRecordServiceImpl extends ServiceImpl<BagFlowerRecordMapper, BagFlowerRecord> implements BagFlowerRecordService {
    @Resource
    private BagFlowRecordInsertTranslator bagFlowRecordInsertTranslator;
    @Resource
    private BagFlowerRecordUpdateTranslator bagFlowerRecordUpdateTranslator;

    @Override
    public Page<BagFlowerRecord> pageQuery(BagFlowerRecordQuery request) {
        LambdaQueryWrapper<BagFlowerRecord> wrapper = Wrappers.<BagFlowerRecord>lambdaQuery()
                .eq(Objects.nonNull(request.getId()), BagFlowerRecord::getId, request.getId())
                .eq(Objects.nonNull(request.getBagId()), BagFlowerRecord::getBagId, request.getBagId())
                .eq(Objects.nonNull(request.getPickerId()), BagFlowerRecord::getPickerId, request.getPickerId())
                .eq(Objects.nonNull(request.getCategoryId()), BagFlowerRecord::getCategoryId, request.getCategoryId())
                .eq(Objects.nonNull(request.getSpecificationId()), BagFlowerRecord::getSpecificationId, request.getSpecificationId())
                .eq(Objects.nonNull(request.getDamageReasonId()), BagFlowerRecord::getDamageReasonId, request.getDamageReasonId())
                ;
        if (Objects.nonNull(request.getCreated()) && Objects.nonNull(request.getCreated().getMin()) && Objects.nonNull(request.getCreated().getMax())) {
            wrapper.between(BagFlowerRecord::getCreated, request.getCreated().getMin(), request.getCreated().getMax());
        }
        if (Objects.isNull(request.getYn())) {
            wrapper.ne(BagFlowerRecord::getYn, YNEnum.DELETED.getCode());
        } else {
            wrapper.eq(BagFlowerRecord::getYn, request.getYn());
        }
        return this.page(new Page<>(request.getPage(), request.getSize()), wrapper);
    }

    @Override
    public boolean deletes(List<Long> ids, User user) {
        if (CollectionUtils.isNotEmpty(ids)) {
            return this.updateBatchById(ids.stream().map(id -> {
                BagFlowerRecord update = new BagFlowerRecord();
                update.setId(id);
                update.setYn(YNEnum.DELETED.getCode());
                update.setModified(LocalDateTime.now());
                return update;
            }).collect(Collectors.toList()), 200);
        }
        return false;
    }

    @Override
    public boolean saveOption(BagFlowerRecordQuery request, User user) {
        return Objects.isNull(request.getId())
                ? this.save(bagFlowRecordInsertTranslator.apply(request, user))
                : this.updateById(bagFlowerRecordUpdateTranslator.apply(request, user));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<BagFlowerRecordQuery> request, User user) {
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
