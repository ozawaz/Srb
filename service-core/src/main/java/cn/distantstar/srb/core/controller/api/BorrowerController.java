package cn.distantstar.srb.core.controller.api;


import cn.distantstar.common.result.Result;
import cn.distantstar.srb.base.utils.JwtUtils;
import cn.distantstar.srb.core.pojo.vo.BorrowerVo;
import cn.distantstar.srb.core.service.BorrowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Api(tags = "借款人")
@RestController
@RequestMapping("/api/core/borrower")
@Slf4j
public class BorrowerController {

    private BorrowerService borrowerService;

    @Autowired
    public void setBorrowerService(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @ApiOperation("保存借款人信息")
    @PostMapping("/auth/save")
    public Result save(@RequestBody BorrowerVo borrowerVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowerService.saveBorrowerVoByUserId(borrowerVO, userId);
        return Result.ok().message("信息提交成功");
    }

    @ApiOperation("获取借款人认证状态")
    @GetMapping("/auth/getBorrowerStatus")
    public Result<Integer> getBorrowerStatus(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status = borrowerService.getStatusByUserId(userId);
        return Result.ok(status);
    }
}

