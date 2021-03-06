package cn.distantstar.srb.core.controller;


import cn.distantstar.common.result.Result;
import cn.distantstar.srb.base.utils.JwtUtils;
import cn.distantstar.srb.core.pojo.entity.TransFlow;
import cn.distantstar.srb.core.service.TransFlowService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 交易流水表 前端控制器
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Api(tags = "资金记录")
@RestController
@RequestMapping("/api/core/transFlow")
@Slf4j
public class TransFlowController {

    @Resource
    private TransFlowService transFlowService;

    @ApiOperation("获取列表")
    @GetMapping("/list")
    public Result<List<TransFlow>> list(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        List<TransFlow> list = transFlowService.selectByUserId(userId);
        return Result.ok(list);
    }
}

