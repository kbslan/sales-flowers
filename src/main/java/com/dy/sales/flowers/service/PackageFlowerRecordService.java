package com.dy.sales.flowers.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.sales.flowers.entity.PackageFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordQuery;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordSave;

import java.util.List;

/**
 * <p>
 * 包花记录 服务类
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
public interface PackageFlowerRecordService extends IService<PackageFlowerRecord> {

    /**
     * 分页查询
     *
     * @param request 参数
     * @return 列表
     */
    Page<PackageFlowerRecord> pageQuery(PackageFlowerRecordQuery request);

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
    boolean save(PackageFlowerRecordSave request, User user);

    /**
     * 批量提报数据
     *
     * @param request 参数
     * @param user    操作人信息
     * @return 结果
     */
    boolean saveBatch(List<PackageFlowerRecordSave> request, User user);
}
