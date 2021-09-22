package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface UserAccountService extends IService<UserAccount> {

    /**
     * 充值
     * @param chargeAmt 充值金额
     * @param userId 用户id
     * @return 返回填充表单
     */
    String commitCharge(BigDecimal chargeAmt, Long userId);

    /**
     * 用户充值异步回调
     * @param paramMap 参数
     * @return 返回状态
     */
    String notify(Map<String, Object> paramMap);

    /**
     * 查询账户余额
     * @param userId 用户id
     * @return 返回余额
     */
    BigDecimal getAccount(Long userId);

    /**
     * 用户提现
     * @param fetchAmt 提现金额
     * @param userId 用户id
     * @return 返回结果
     */
    String commitWithdraw(BigDecimal fetchAmt, Long userId);

    /**
     * 用户提现异步回调
     * @param paramMap 参数列表
     */
    void notifyWithdraw(Map<String, Object> paramMap);
}
