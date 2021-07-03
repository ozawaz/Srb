package cn.distantstar.srb.core.controller.admin;


import cn.distantstar.common.exception.Assert;
import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.common.result.Result;
import cn.distantstar.srb.core.pojo.entity.IntegralGrade;
import cn.distantstar.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Api(tags = "积分等级管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    private IntegralGradeService integralGradeService;

    @Autowired
    public void setIntegralGradeService(IntegralGradeService integralGradeService) {
        this.integralGradeService = integralGradeService;
    }

    @ApiOperation("显示所有积分等级")
    @GetMapping("/list")
    public Result<List<IntegralGrade>> listAll(){
        List<IntegralGrade> list = integralGradeService.list();
        return Result.ok(list).message("返回积分等级列表成功");
    }

    @ApiOperation("根据id删除相关积分等级")
    @DeleteMapping("/remove/{id}")
    public Result removeById(
            @ApiParam(value = "积分id", example = "100")
            @PathVariable Long id){
        boolean b = integralGradeService.removeById(id);
        if (b) {
            return Result.ok().message("删除成功");
        } else {
            return Result.fail().message("删除失败");
        }
    }

    @ApiOperation("新增积分等级")
    @PostMapping("/save")
    public Result save(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade){

        // 如果借款额度为空就手动抛出一个自定义的异常！
        Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);

        boolean result = integralGradeService.save(integralGrade);
        if (result) {
            return Result.ok().message("保存成功");
        } else {
            return Result.fail().message("保存失败");
        }
    }

    @ApiOperation("根据id获取积分等级")
    @GetMapping("/get/{id}")
    public Result<IntegralGrade> getById(
            @ApiParam(value = "积分id", example = "1")
            @PathVariable Long id){
        IntegralGrade integralGrade = integralGradeService.getById(id);
        if(integralGrade != null){
            return Result.ok(integralGrade);
        }else{
            return Result.fail(integralGrade).message("数据不存在");
        }
    }

    @ApiOperation("更新积分等级")
    @PutMapping("/update")
    public Result updateById(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade){
        boolean result = integralGradeService.updateById(integralGrade);
        if(result){
            return Result.ok().message("修改成功");
        }else{
            return Result.fail().message("修改失败");
        }
    }
}

