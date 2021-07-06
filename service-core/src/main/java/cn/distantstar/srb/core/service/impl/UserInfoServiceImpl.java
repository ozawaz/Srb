package cn.distantstar.srb.core.service.impl;

import cn.distantstar.common.exception.Assert;
import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.common.util.MD5;
import cn.distantstar.srb.core.mapper.UserAccountMapper;
import cn.distantstar.srb.core.pojo.entity.UserAccount;
import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.mapper.UserInfoMapper;
import cn.distantstar.srb.core.pojo.vo.RegisterVo;
import cn.distantstar.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Resource
    private UserAccountMapper userAccountMapper;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void register(RegisterVo registerVO) {

        // 判断用户手机号是否已经被注册过
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", registerVO.getMobile());
        Integer count = baseMapper.selectCount(wrapper);
        // MOBILE_EXIST_ERROR(-207, "手机号已被注册"),
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        // 插入用户信息记录：user_info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        baseMapper.insert(userInfo);

        // 插入用户账户记录：user_account
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);
    }
}
