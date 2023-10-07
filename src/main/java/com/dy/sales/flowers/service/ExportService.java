package com.dy.sales.flowers.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;

import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 * 导出服务
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/22 15:21
 */
public interface ExportService {

    /**
     * 动态表头
     */
    List<List<String>> head();

    /**
     * 查询数据
     *
     * @param request 查询参数
     * @return 数据
     */
    List<List<Object>> data(Object request);

    /**
     * 导出
     *
     * @param request   查询参数
     * @param outputStream 输出流
     */
    default void write(Object request, OutputStream outputStream) {
        EasyExcel.write(outputStream)
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(15))
                // 这里放入动态头
                .head(head())
                .sheet("sheet1")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(data(request));
    }
}
