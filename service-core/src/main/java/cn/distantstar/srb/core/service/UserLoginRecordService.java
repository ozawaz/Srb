package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {

    /**
     * 获取会员登录日志列表
     * @param userId 会员id
     * @return 返回日志列表
     */
    List<UserLoginRecord> listTop50(Long userId);
}
