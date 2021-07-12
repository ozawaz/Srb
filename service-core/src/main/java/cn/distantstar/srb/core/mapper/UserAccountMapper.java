package cn.distantstar.srb.core.mapper;

import cn.distantstar.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * 更新账户
     * @param bindCode 绑定码
     * @param amount 充值金额
     * @param freezeAmount 流水
     */
    void updateAccount(@Param("bindCode")String bindCode,
                       @Param("amount")BigDecimal amount,
                       @Param("freezeAmount")BigDecimal freezeAmount);
}
