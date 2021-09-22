package cn.distantstar.srb.core.service.impl;

import cn.distantstar.common.exception.Assert;
import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.common.util.MD5;
import cn.distantstar.srb.base.utils.JwtUtils;
import cn.distantstar.srb.core.mapper.UserAccountMapper;
import cn.distantstar.srb.core.mapper.UserLoginRecordMapper;
import cn.distantstar.srb.core.pojo.entity.UserAccount;
import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.mapper.UserInfoMapper;
import cn.distantstar.srb.core.pojo.entity.UserLoginRecord;
import cn.distantstar.srb.core.pojo.query.UserInfoQuery;
import cn.distantstar.srb.core.pojo.vo.LoginVo;
import cn.distantstar.srb.core.pojo.vo.RegisterVo;
import cn.distantstar.srb.core.pojo.vo.UserIndexVo;
import cn.distantstar.srb.core.pojo.vo.UserInfoVo;
import cn.distantstar.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;

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

    @Override
    public UserInfoVo login(LoginVo loginVo, String ip) {
        // 获取基本信息
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        Integer userType = loginVo.getUserType();

        // 获取会员
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        queryWrapper.eq("user_type", userType);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);

        // 用户不存在
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);

        // 校验密码
        // LOGIN_PASSWORD_ERROR(-209, "密码不正确"),
        Assert.equals(MD5.encrypt(password), userInfo.getPassword(), ResponseEnum.LOGIN_PASSWORD_ERROR);

        // 用户是否被禁用
        // LOGIN_DISABLED_ERROR(-210, "用户已被禁用"),
        Assert.equals(userInfo.getStatus(), UserInfo.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);

        // 记录登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecord.setIp(ip);
        userLoginRecordMapper.insert(userLoginRecord);

        // 生成token
        String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());

        // 组装UserInfoVo
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setToken(token);
        userInfoVo.setName(userInfo.getName());
        userInfoVo.setNickName(userInfo.getNickName());
        userInfoVo.setHeadImg(userInfo.getHeadImg());
        userInfoVo.setMobile(userInfo.getMobile());
        userInfoVo.setUserType(userType);

        return userInfoVo;
    }

    @Override
    public boolean checkMobile(String mobile) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery) {
        String mobile = userInfoQuery.getMobile();
        Integer status = userInfoQuery.getStatus();
        Integer userType = userInfoQuery.getUserType();

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();

        userInfoQueryWrapper
                .eq(StringUtils.isNotBlank(mobile), "mobile", mobile)
                .eq(status != null, "status", userInfoQuery.getStatus())
                .eq(userType != null, "user_type", userType);
        return baseMapper.selectPage(pageParam, userInfoQueryWrapper);
    }

    @Override
    public void lock(Long id, Integer status) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);
        baseMapper.updateById(userInfo);
    }

    @Override
    public UserIndexVo getIndexUserInfo(Long userId) {
        //用户信息
        UserInfo userInfo = baseMapper.selectById(userId);

        //账户信息
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", userId);
        UserAccount userAccount = userAccountMapper.selectOne(userAccountQueryWrapper);

        //登录信息
        QueryWrapper<UserLoginRecord> userLoginRecordQueryWrapper = new QueryWrapper<>();
        userLoginRecordQueryWrapper
                .eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 1");
        UserLoginRecord userLoginRecord = userLoginRecordMapper.selectOne(userLoginRecordQueryWrapper);

        //组装结果数据
        UserIndexVo userIndexVO = new UserIndexVo();
        userIndexVO.setUserId(userInfo.getId());
        userIndexVO.setUserType(userInfo.getUserType());
        userIndexVO.setName(userInfo.getName());
        userIndexVO.setNickName(userInfo.getNickName());
        userIndexVO.setHeadImg(userInfo.getHeadImg());
        userIndexVO.setBindStatus(userInfo.getBindStatus());
        userIndexVO.setAmount(userAccount.getAmount());
        userIndexVO.setFreezeAmount(userAccount.getFreezeAmount());
        userIndexVO.setLastLoginTime(userLoginRecord.getCreateTime());

        return userIndexVO;
    }
}
