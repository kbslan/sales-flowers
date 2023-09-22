package com.dy.sales.flowers.web;

import com.dy.sales.flowers.annotation.CurrentUser;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.service.ExportService;
import com.dy.sales.flowers.vo.constant.PermissionConstants;
import com.dy.sales.flowers.vo.request.PackageFlowerRecordQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>
 * 导出服务
 * </p>
 *
 * @author chao.lan
 * @version 1.0.0
 * @since 2023/9/22 22:24
 */
@RestController
@RequestMapping("/api/export")
public class ExportController {
    @Resource(name = "damageExportService")
    private ExportService damageExportService;
    @Resource(name = "packageFlowerExportService")
    private ExportService packageFlowerExportService;

    /**
     * 报损记录导出
     *
     * @param request  查询条件
     * @param response 响应
     * @param user 用户信息
     * @throws IOException IO异常
     */
    @PostMapping("/damage")
    public void damageExport(@RequestBody PackageFlowerRecordQuery request,
                             HttpServletResponse response,
                             @CurrentUser(permission = PermissionConstants.ADMIN) User user)
            throws IOException {
        String fileName = "报损导出.xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String file = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + file);
        damageExportService.write(request, response.getOutputStream());
    }

    /**
     * 包花数量导出
     *
     * @param request  查询条件
     * @param response 响应
     * @param user 用户信息
     * @throws IOException IO异常
     */
    @PostMapping("/packageFlower")
    public void packageFlowerExport(@RequestBody PackageFlowerRecordQuery request,
                                    HttpServletResponse response,
                                    @CurrentUser(permission = PermissionConstants.ADMIN) User user)
            throws IOException {
        String fileName = "包花数量导出.xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String file = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + file);
        packageFlowerExportService.write(request, response.getOutputStream());
    }
}
