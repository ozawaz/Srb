package cn.distantstar.srb.core.service.impl;

import cn.distantstar.srb.core.pojo.entity.UserLoginRecord;
import cn.distantstar.srb.core.mapper.UserLoginRecordMapper;
import cn.distantstar.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
