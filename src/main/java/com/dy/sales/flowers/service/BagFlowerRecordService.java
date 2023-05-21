package com.dy.sales.flowers.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.sales.flowers.entity.BagFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.vo.request.BagFlowerRecordQuery;

import java.util.List;

/**
 * <p>
 * 包花记录 服务类
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
public interface BagFlowerRecordService extends IService<BagFlowerRecord> {

    /**
     * 分页查询
     *
     * @param request 参数
     * @return 列表
     */
    Page<BagFlowerRecord> pageQuery(BagFlowerRecordQuery request);

    /**
     * 逻辑删除
     *
     * @param ids  id列表
     * @param user 操作人信息
     * @return 结果
     */
    boolean deletes(List<Long> ids, User user);

    /**
     * 新增、编辑
     *
     * @param request 参数
     * @param user    操作人信息
     * @return 结果
     */
    boolean saveOption(BagFlowerRecordQuery request, User user);

    /**
     * 批量提报数据
     *
     * @param request 参数
     * @param user    操作人信息
     * @return 结果
     */
    boolean saveBatch(List<BagFlowerRecordQuery> request, User user);
}
