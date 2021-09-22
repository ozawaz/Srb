package cn.distantstar.srb.sms.controller.api;

import cn.distantstar.common.exception.Assert;
import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.common.result.Result;
import cn.distantstar.common.util.RandomUtils;
import cn.distantstar.common.util.RegexValidateUtils;
import cn.distantstar.srb.sms.client.CoreUserInfoClient;
import cn.distantstar.srb.sms.service.SmsService;
import cn.distantstar.srb.sms.utils.SmsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: distantstar
 */
@Api(tags = "短信管理")
@RestController
@RequestMapping("/api/sms")
@Slf4j
public class ApiSmsController {

    private SmsService smsService;
    private RedisTemplate<String, Object> redisTemplate;

//    @Resource
//    private CoreUserInfoClient coreUserInfoClient;

    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    @Autowired
//    public void setCoreUserInfoClient(CoreUserInfoClient coreUserInfoClient) {
//        this.coreUserInfoClient = coreUserInfoClient;
//    }

    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public Result send(@ApiParam(value = "手机号", required = true)
                       @PathVariable String mobile) {

        // MOBILE_NULL_ERROR(-202, "手机号不能为空"),
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        // MOBILE_ERROR(-203, "手机号不正确"),
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        //手机号是否注册
//        boolean result = coreUserInfoClient.checkMobile(mobile);
//        System.out.println("result = " + result);
//        Assert.isTrue(!result, ResponseEnum.MOBILE_EXIST_ERROR);

        // 生成验证码
        String code = RandomUtils.getFourBitRandom();
        // 组装短信模板参数
        Map<String,Object> param = new HashMap<>(1);
        param.put("code", code);
        // 发送短信
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, param);

        //将验证码存入redis
        redisTemplate.opsForValue().set("srb:sms:code:" + mobile,
                code, 5, TimeUnit.MINUTES);

        return Result.ok().message("短信发送成功");
    }
}
