package com.dy.sales.flowers.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.sales.flowers.entity.OptionConfig;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.vo.enums.OptionEnum;
import com.dy.sales.flowers.vo.request.ChangeStatusParams;
import com.dy.sales.flowers.vo.request.OptionQuery;

import java.util.List;

/**
 * <p>
 * 选项配置 服务类
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
public interface OptionConfigService extends IService<OptionConfig> {

    /**
     * 分页查询
     *
     * @param request 参数
     * @return 列表
     */
    Page<OptionConfig> pageQuery(OptionQuery request);

    /**
     * 状态变更
     *
     * @param request 参数
     * @param user    操作人信息
     * @return 结果
     */
    boolean changeYn(ChangeStatusParams request, User user);

    /**
     * 新增、编辑
     *
     * @param request 参数
     * @param user    操作人信息
     * @return 结果
     */
    boolean saveOption(OptionQuery request, User user);

    /**
     * 查询所有类型的配置
     *
     * @param type 类型
     * @return 结果
     */
    List<OptionConfig> list(OptionEnum type);

    /**
     * 查询所有类型的配置
     *
     * @param type 类型
     * @param yn   状态
     * @return 结果
     */
    List<OptionConfig> list(OptionEnum type, Integer yn);
}
