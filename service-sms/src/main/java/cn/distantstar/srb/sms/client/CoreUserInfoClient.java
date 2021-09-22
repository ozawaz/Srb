package cn.distantstar.srb.sms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author distantstar
 */
@FeignClient(value = "service-core")
public interface CoreUserInfoClient {

    /**
     * 判断手机号是否已经注册过
     * @param mobile 手机号
     * @return 返回结果
     */
    @GetMapping("/api/core/userInfo/checkMobile/{mobile}")
    boolean checkMobile(@PathVariable String mobile);
}
