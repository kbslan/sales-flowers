package com.dy.sales.flowers.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dy.sales.flowers.annotation.CurrentUser;
import com.dy.sales.flowers.entity.BagFlowerRecord;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.service.BagFlowerRecordService;
import com.dy.sales.flowers.vo.constant.PermissionConstants;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.BagFlowerRecordQuery;
import com.dy.sales.flowers.vo.response.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 包花记录 前端控制器
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
@RestController
@RequestMapping("/bag/flower/record")
public class BagFlowerRecordController {
    @Resource
    private BagFlowerRecordService bagFlowerRecordService;

    /**
     * 列表查询
     */
    @PostMapping("/page")
    public HttpResult<Page<BagFlowerRecord>> page(@RequestBody BagFlowerRecordQuery request, @CurrentUser User user) {
        return HttpResult.success(bagFlowerRecordService.pageQuery(request));
    }

    /**
     * 逻辑删除
     *
     * @param ids  ids(多个用逗号分隔)
     * @param user 当前用户信息
     * @return 结果
     */
    @GetMapping("/delete")
    public HttpResult<Boolean> delete(@RequestParam("ids") String ids, @CurrentUser User user) {
        if (StringUtils.isBlank(ids)) {
            return HttpResult.failed(ResultCode.PARAM_EXCEPTION);
        }
        boolean success = bagFlowerRecordService.deletes(Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList()), user);
        return success ? HttpResult.success() : HttpResult.failed(ResultCode.SYS_EXCEPTION);
    }


    /**
     * 新增、编辑
     *
     * @param request 参数
     * @param user    当前用户信息
     * @return 结果
     */
    @PostMapping("/save")
    public HttpResult<Boolean> save(@RequestBody BagFlowerRecordQuery request, @CurrentUser User user) {
        return HttpResult.success(bagFlowerRecordService.saveOption(request, user));
    }

    /**
     * 批量提报数据
     *
     * @param request 参数
     * @param user    当前用户信息
     * @return 结果
     */
    @PostMapping("/save/batch")
    public HttpResult<Boolean> saveBatch(@RequestBody List<BagFlowerRecordQuery> request, @CurrentUser User user) {
        return HttpResult.success(bagFlowerRecordService.saveBatch(request, user));
    }


    /**
     * 管理员审核包花记录
     *
     * @param request 参数
     * @param user    当前用户信息
     * @return 结果
     */
    @PostMapping("/audit")
    public HttpResult<Boolean> auditPass(@RequestBody BagFlowerRecordQuery request, @CurrentUser(permission = PermissionConstants.ADMIN) User user) {
        request.setYn(YNEnum.YES.getCode());
        return HttpResult.success(bagFlowerRecordService.saveOption(request, user));
    }

}
