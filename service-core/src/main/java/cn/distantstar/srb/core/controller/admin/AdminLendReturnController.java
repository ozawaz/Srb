package cn.distantstar.srb.core.controller.admin;

import cn.distantstar.common.result.Result;
import cn.distantstar.srb.core.pojo.entity.LendReturn;
import cn.distantstar.srb.core.service.LendReturnService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author distantstar
 * @version 1.0
 * @date 2021
 * @description cn.distantstar.srb.core.controller.admin
 * @since JDK1.8
 */
@Api(tags = "还款记录")
@RestController
@RequestMapping("/admin/core/lendReturn")
@Slf4j
public class AdminLendReturnController {

    @Resource
    private LendReturnService lendReturnService;

    @ApiOperation("获取列表")
    @GetMapping("/list/{lendId}")
    public Result<List<LendReturn>> list(
            @ApiParam(value = "标的id", required = true)
            @PathVariable Long lendId) {
        List<LendReturn> list = lendReturnService.selectByLendId(lendId);
        return Result.ok(list);
    }
}
