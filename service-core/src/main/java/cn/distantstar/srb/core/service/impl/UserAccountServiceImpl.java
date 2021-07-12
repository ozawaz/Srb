package cn.distantstar.srb.core.service.impl;

import cn.distantstar.common.exception.Assert;
import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.srb.core.enums.TransTypeEnum;
import cn.distantstar.srb.core.hfb.FormHelper;
import cn.distantstar.srb.core.hfb.HfbConst;
import cn.distantstar.srb.core.hfb.RequestHelper;
import cn.distantstar.srb.core.mapper.UserInfoMapper;
import cn.distantstar.srb.core.pojo.bo.TransFlowBo;
import cn.distantstar.srb.core.pojo.entity.UserAccount;
import cn.distantstar.srb.core.mapper.UserAccountMapper;
import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.service.TransFlowService;
import cn.distantstar.srb.core.service.UserAccountService;
import cn.distantstar.srb.core.util.LendNoUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Service
@Slf4j
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private TransFlowService transFlowService;

    @Override
    public String commitCharge(BigDecimal chargeAmt, Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        String bindCode = userInfo.getBindCode();
        //判断账户绑定状态
        Assert.notEmpty(bindCode, ResponseEnum.USER_NO_BIND_ERROR);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("chargeAmt", chargeAmt);
        paramMap.put("feeAmt", new BigDecimal("0"));
        paramMap.put("notifyUrl", HfbConst.RECHARGE_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.RECHARGE_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        //构建充值自动提交表单
        return FormHelper.buildForm(HfbConst.RECHARGE_URL, paramMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String notify(Map<String, Object> paramMap) {
        log.info("充值成功：" + JSONObject.toJSONString(paramMap));

        // 判断交易流水是否存在
        // 商户充值订单号
        String agentBillNo = (String)paramMap.get("agentBillNo");
        boolean isSave = transFlowService.isSaveTransFlow(agentBillNo);
        if(isSave){
            log.warn("幂等性返回");
            return "success";
        }

        // 充值人绑定协议号
        String bindCode = (String)paramMap.get("bindCode");
        // 充值金额
        String chargeAmt = (String)paramMap.get("chargeAmt");

        // 优化
        baseMapper.updateAccount(bindCode, new BigDecimal(chargeAmt), new BigDecimal(0));

        // 增加交易流水
        TransFlowBo transFlowBO = new TransFlowBo(
                agentBillNo,
                bindCode,
                new BigDecimal(chargeAmt),
                TransTypeEnum.RECHARGE,
                "充值");
        transFlowService.saveTransFlow(transFlowBO);

        return "success";
    }
}
