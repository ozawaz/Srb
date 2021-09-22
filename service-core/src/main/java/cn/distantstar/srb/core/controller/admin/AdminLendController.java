package cn.distantstar.srb.core.controller.admin;

import cn.distantstar.common.result.Result;
import cn.distantstar.srb.core.pojo.entity.Lend;
import cn.distantstar.srb.core.service.LendService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author distantstar
 */
@Api(tags = "标的管理")
@RestController
@RequestMapping("/admin/core/lend")
@Slf4j
public class AdminLendController {

    private LendService lendService;

    @Autowired
    public void setLendService(LendService lendService) {
        this.lendService = lendService;
    }

    @ApiOperation("标的列表")
    @GetMapping("/list")
    public Result<List<Lend>> list() {
        List<Lend> lendList = lendService.selectList();
        return Result.ok(lendList);
    }

    @ApiOperation("获取标的信息")
    @GetMapping("/show/{id}")
    public Result<Map<String, Object>> show(
            @ApiParam(value = "标的id", required = true)
            @PathVariable Long id) {
        Map<String, Object> result = lendService.getLendDetail(id);
        return Result.ok(result);
    }

    @ApiOperation("放款")
    @GetMapping("/makeLoan/{id}")
    public Result makeLoan(
            @ApiParam(value = "标的id", required = true)
            @PathVariable("id") Long id) {
        lendService.makeLoan(id);
        return Result.ok().message("放款成功");
    }
}
