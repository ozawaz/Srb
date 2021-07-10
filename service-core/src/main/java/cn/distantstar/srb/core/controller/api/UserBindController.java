package cn.distantstar.srb.core.controller.api;


import cn.distantstar.common.result.Result;
import cn.distantstar.srb.base.utils.JwtUtils;
import cn.distantstar.srb.core.hfb.RequestHelper;
import cn.distantstar.srb.core.pojo.vo.UserBindVo;
import cn.distantstar.srb.core.service.UserBindService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 前端控制器
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Api(tags = "会员账号绑定")
@RestController
@RequestMapping("/api/core/userBind")
@Slf4j
public class UserBindController {

    private UserBindService userBindService;

    @Autowired
    public void setUserBindService(UserBindService userBindService) {
        this.userBindService = userBindService;
    }

    @ApiOperation("账户绑定提交数据")
    @PostMapping("/auth/bind")
    public Result<String> bind(@RequestBody UserBindVo userBindVo, HttpServletRequest request) {
        // 从header中获取token
        String token = request.getHeader("token");
        // 从token中获取userId
        Long userId = JwtUtils.getUserId(token);
        // 根据userId进行账户绑定
        String formStr = userBindService.commitBindUser(userBindVo, userId);
        return Result.ok(formStr);
    }

    @ApiOperation("账户绑定异步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        // 汇付宝向尚融宝发起回调请求时携带的参数
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("用户账号绑定异步回调：" + JSON.toJSONString(paramMap));

        //校验签名
        if(!RequestHelper.isSignEquals(paramMap)) {
            log.error("用户账号绑定异步回调签名错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }

        //修改绑定状态
        userBindService.notify(paramMap);
        return "success";
    }
}

