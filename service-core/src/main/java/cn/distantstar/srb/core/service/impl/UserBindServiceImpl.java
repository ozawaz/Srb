package cn.distantstar.srb.core.service.impl;

import cn.distantstar.common.exception.Assert;
import cn.distantstar.common.result.ResponseEnum;
import cn.distantstar.srb.core.enums.UserBindEnum;
import cn.distantstar.srb.core.hfb.FormHelper;
import cn.distantstar.srb.core.hfb.HfbConst;
import cn.distantstar.srb.core.hfb.RequestHelper;
import cn.distantstar.srb.core.mapper.UserInfoMapper;
import cn.distantstar.srb.core.pojo.entity.UserBind;
import cn.distantstar.srb.core.mapper.UserBindMapper;
import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.pojo.vo.UserBindVo;
import cn.distantstar.srb.core.service.UserBindService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public String commitBindUser(UserBindVo userBindVo, Long userId) {

        //查询身份证号码是否绑定
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper
                .eq("id_card", userBindVo.getIdCard())
                .ne("user_id", userId);
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        //USER_BIND_IDCARD_EXIST_ERROR(-301, "身份证号码已绑定"),
        Assert.isNull(userBind, ResponseEnum.USER_BIND_IDCARD_EXIST_ERROR);

        // 判断用户是否曾经绑定过
        userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", userId);
        userBind = baseMapper.selectOne(userBindQueryWrapper);

        // 如果没有绑定过，则
        if (userBind == null) {
            // 创建用户绑定记录
            userBind = new UserBind();
            BeanUtils.copyProperties(userBindVo, userBind);
            userBind.setId(userId);
            userBind.setStatus(UserBindEnum.NO_BIND.getStatus());
            baseMapper.insert(userBind);
        } else {
            // 如果已经绑定过，则进行数据更新
            BeanUtils.copyProperties(userBindVo, userBind);
            baseMapper.updateById(userBind);
        }


        // 组装自动表单的参数
        Map<String, Object> paramMap = new HashMap<>(12);
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentUserId", userId);
        paramMap.put("idCard",userBindVo.getIdCard());
        paramMap.put("personalName", userBindVo.getName());
        paramMap.put("bankType", userBindVo.getBankType());
        paramMap.put("bankNo", userBindVo.getBankNo());
        paramMap.put("mobile", userBindVo.getMobile());
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));
        
        
        // 返回动态表单字符串
        return FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
    }

    @Override
    public void notify(Map<String, Object> paramMap) {
        // 获取绑定码
        String bindCode = (String)paramMap.get("bindCode");
        // 会员id
        String agentUserId = (String)paramMap.get("agentUserId");

        // 根据user_id查询user_bind记录
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", agentUserId);

        // 更新用户绑定表
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        userBind.setBindCode(bindCode);
        userBind.setStatus(UserBindEnum.BIND_OK.getStatus());
        baseMapper.updateById(userBind);

        // 更新用户表
        UserInfo userInfo = userInfoMapper.selectById(agentUserId);
        userInfo.setBindCode(bindCode);
        userInfo.setName(userBind.getName());
        userInfo.setIdCard(userBind.getIdCard());
        userInfo.setBindStatus(UserBindEnum.BIND_OK.getStatus());
        userInfoMapper.updateById(userInfo);
    }
}
