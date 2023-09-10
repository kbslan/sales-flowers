package com.dy.sales.flowers.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * mybatis plus 分页查询结果转换工具
 *
 * @author chao.lan
 */
public class PageUtils {

    /**
     * 分页对象转换, 目的：不直接暴露数据库实体对象到前端
     *
     * @param iPage      分页结果
     * @param translator 实体对象化转换方法
     * @param <T>        实体类
     * @param <R>        转换后的对象
     * @return 结果
     */
    public static <T, R> Page<R> convertToPage(IPage<T> iPage, Function<T, R> translator) {
        Page<R> page = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            page.setRecords(iPage.getRecords().stream().map(translator).collect(Collectors.toList()));
        }
        page.setOrders(iPage.orders());
        return page;
    }

    /**
     * 分页对象转换, 目的：不直接暴露数据库实体对象到前端
     *
     * @param iPage 分页结果
     * @param u 实体对象
     * @param translator 实体对象化转换方法
     * @param <T> 实体类
     * @param <U> 实体类
     * @param <R> 转换后的对象
     * @return 结果
     */
    public static <T, U, R> Page<R> convertToPage(IPage<T> iPage,  U u, BiFunction<T, U, R> translator) {
        Page<R> page = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            page.setRecords(iPage.getRecords().stream().map(item -> translator.apply(item, u)).collect(Collectors.toList()));
        }
        page.setOrders(iPage.orders());
        return page;
    }
}
