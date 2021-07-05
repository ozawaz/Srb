package cn.distantstar.srb.sms.service;

import java.util.Map;

/**
 * @author distantstar
 */
public interface SmsService {

    /**
     * 发送短信接口
     * @param mobile 将要发送的手机号码
     * @param templateCode 短音模板编号
     * @param param 发送参数
     */
    void send(String mobile, String templateCode, Map<String,Object> param);
}
