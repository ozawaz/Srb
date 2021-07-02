package cn.distantstar.srb.core.service.impl;

import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.mapper.UserInfoMapper;
import cn.distantstar.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
