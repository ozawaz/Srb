package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.pojo.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户注册
     * @param registerVO 注册信息
     */
    void register(RegisterVo registerVO);
}
