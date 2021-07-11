package cn.distantstar.srb.core.controller.admin;

import cn.distantstar.common.result.Result;
import cn.distantstar.srb.core.pojo.entity.Borrower;
import cn.distantstar.srb.core.pojo.vo.BorrowerApprovalVo;
import cn.distantstar.srb.core.pojo.vo.BorrowerDetailVo;
import cn.distantstar.srb.core.service.BorrowerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author distantstar
 */
@Api(tags = "借款人管理")
@RestController
@RequestMapping("/admin/core/borrower")
@Slf4j
public class AdminBorrowerController {

    private BorrowerService borrowerService;

    @Autowired
    public void setBorrowerService(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @ApiOperation("获取借款人分页列表")
    @GetMapping("/list/{page}/{limit}")
    public Result<IPage<Borrower>> listPage(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(value = "查询关键字", required = false)
            @RequestParam String keyword) {

        Page<Borrower> pageParam = new Page<>(page, limit);
        IPage<Borrower> pageModel =  borrowerService.listPage(pageParam, keyword);
        return Result.ok(pageModel);
    }

    @ApiOperation("获取借款人信息")
    @GetMapping("/show/{id}")
    public Result<BorrowerDetailVo> show(
            @ApiParam(value = "借款人id", required = true)
            @PathVariable Long id) {
        BorrowerDetailVo borrowerDetailVO = borrowerService.getBorrowerDetailVoById(id);
        return Result.ok(borrowerDetailVO);
    }

    @ApiOperation("借款额度审批")
    @PostMapping("/approval")
    public Result approval(@RequestBody BorrowerApprovalVo borrowerApprovalVO){
        borrowerService.approval(borrowerApprovalVO);
        return Result.ok().message("审批完成");
    }
}
