package com.dy.sales.flowers.web;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dy.sales.flowers.annotation.CurrentUser;
import com.dy.sales.flowers.entity.OptionConfig;
import com.dy.sales.flowers.entity.User;
import com.dy.sales.flowers.service.OptionConfigService;
import com.dy.sales.flowers.vo.constant.PermissionConstants;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.enums.YNEnum;
import com.dy.sales.flowers.vo.request.ChangeStatusParams;
import com.dy.sales.flowers.vo.request.OptionQuery;
import com.dy.sales.flowers.vo.response.HttpResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 选项配置 前端控制器
 * </p>
 *
 * @author chao.lan
 * @since 2023-05-21
 */
@RestController
@RequestMapping("/option/config")
public class OptionConfigController {

    @Resource
    private OptionConfigService optionConfigService;

    /**
     * 列表查询
     */
    @PostMapping("/page")
    public HttpResult<Page<OptionConfig>> page(@RequestBody OptionQuery request, @CurrentUser(permission = PermissionConstants.ADMIN) User user) {
        return HttpResult.success(optionConfigService.pageQuery(request));
    }

    /**
     * 状态变更
     *
     * @param request 请求参数
     * @param user    当前用户信息
     * @return 结果
     */
    @PostMapping("/yn")
    public HttpResult<Boolean> changeYn(@RequestBody ChangeStatusParams request, @CurrentUser(permission = PermissionConstants.ADMIN) User user) {
        if (CollectionUtils.isEmpty(request.getIds()) || Objects.isNull(request.getStatus()) || Objects.isNull(YNEnum.get(request.getStatus()))) {
            return HttpResult.failed(ResultCode.PARAM_EXCEPTION);
        }
        boolean success = optionConfigService.changeYn(request, user);
        return success ? HttpResult.success(success) : HttpResult.failed(ResultCode.SYS_EXCEPTION);
    }


    /**
     * 配置新增、编辑
     *
     * @param request 参数
     * @param user    当前用户信息
     * @return 结果
     */
    @PostMapping("/save")
    public HttpResult<Boolean> save(@RequestBody OptionQuery request, @CurrentUser(permission = PermissionConstants.ADMIN) User user) {
        return HttpResult.success(optionConfigService.saveOption(request, user));
    }

}
