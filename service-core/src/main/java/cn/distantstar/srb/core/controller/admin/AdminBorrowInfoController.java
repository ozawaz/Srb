package cn.distantstar.srb.core.controller.admin;

import cn.distantstar.common.result.Result;
import cn.distantstar.srb.core.pojo.entity.BorrowInfo;
import cn.distantstar.srb.core.pojo.vo.BorrowInfoApprovalVo;
import cn.distantstar.srb.core.service.BorrowInfoService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author distantstar
 */
@Api(tags = "借款管理")
@RestController
@RequestMapping("/admin/core/borrowInfo")
@Slf4j
public class AdminBorrowInfoController {

    private BorrowInfoService borrowInfoService;

    @Autowired
    public void setBorrowInfoService(BorrowInfoService borrowInfoService) {
        this.borrowInfoService = borrowInfoService;
    }

    @ApiOperation("借款信息列表")
    @GetMapping("/list")
    public Result<List<BorrowInfo>> list() {
        List<BorrowInfo> borrowInfoList = borrowInfoService.selectList();
        return Result.ok(borrowInfoList);
    }

    @ApiOperation("获取借款信息")
    @GetMapping("/show/{id}")
    public Result<Map<String, Object>> show(
            @ApiParam(value = "借款id", required = true)
            @PathVariable Long id) {
        Map<String, Object> borrowInfoDetail = borrowInfoService.getBorrowInfoDetail(id);
        return Result.ok(borrowInfoDetail);
    }

    @ApiOperation("审批借款信息")
    @PostMapping("/approval")
    public Result approval(@RequestBody BorrowInfoApprovalVo borrowInfoApprovalVO) {
        borrowInfoService.approval(borrowInfoApprovalVO);
        return Result.ok().message("审批完成");
    }
}
