package cn.distantstar.srb.core.controller.api;


import cn.distantstar.common.exception.Assert;
import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.common.result.Result;
import cn.distantstar.common.util.RegexValidateUtils;
import cn.distantstar.srb.base.utils.JwtUtils;
import cn.distantstar.srb.core.pojo.vo.LoginVo;
import cn.distantstar.srb.core.pojo.vo.RegisterVo;
import cn.distantstar.srb.core.pojo.vo.UserInfoVo;
import cn.distantstar.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Api(tags = "会员接口")
@RestController
@RequestMapping("/api/core/userInfo")
@Slf4j
public class UserInfoController {

    private UserInfoService userInfoService;
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVO){

        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();

        //MOBILE_NULL_ERROR(-202, "手机号不能为空"),
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        //MOBILE_ERROR(-203, "手机号不正确"),
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);
        //PASSWORD_NULL_ERROR(-204, "密码不能为空"),
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);
        //CODE_NULL_ERROR(-205, "验证码不能为空"),
        Assert.notEmpty(code, ResponseEnum.CODE_NULL_ERROR);

        //校验验证码
        String codeGen = (String)redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
        //CODE_ERROR(-206, "验证码不正确"),
        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

        //注册
        userInfoService.register(registerVO);

        return Result.ok().message("注册成功");
    }

    @ApiOperation("会员登录")
    @PostMapping("/login")
    public Result<UserInfoVo> login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        String ip = request.getRemoteAddr();
        UserInfoVo userInfoVo = userInfoService.login(loginVo, ip);

        return Result.ok(userInfoVo);
    }

    @ApiOperation("校验令牌")
    @GetMapping("/checkToken")
    public Result checkToken(HttpServletRequest request) {
        // 从请求中获取token
        String token = request.getHeader("token");
        // 校验token
        boolean result = JwtUtils.checkToken(token);

        if (result) {
            return Result.ok();
        } else {
            return Result.build(ResponseEnum.LOGIN_AUTH_ERROR.getCode(),
                    ResponseEnum.LOGIN_AUTH_ERROR.getMessage());
        }
    }

    @ApiOperation("校验手机号是否注册")
    @GetMapping("/checkMobile/{mobile}")
    public boolean checkMobile(@PathVariable String mobile){
        return userInfoService.checkMobile(mobile);
    }
}

