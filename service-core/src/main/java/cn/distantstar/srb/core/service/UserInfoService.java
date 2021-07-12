package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.pojo.query.UserInfoQuery;
import cn.distantstar.srb.core.pojo.vo.LoginVo;
import cn.distantstar.srb.core.pojo.vo.RegisterVo;
import cn.distantstar.srb.core.pojo.vo.UserInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * 用户登录
     * @param loginVo 登录信息
     * @param ip ip
     * @return 返回查询到的用户信息
     */
    UserInfoVo login(LoginVo loginVo, String ip);

    /**
     * 判断手机号是否已经注册
     * @param mobile 手机号
     * @return 返回结果
     */
    boolean checkMobile(String mobile);

    /**
     * 获取会员分页列表
     * @param pageParam 参数
     * @param userInfoQuery 用户查询信息
     * @return 返回用户列表
     */
    IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    /**
     * 锁定和解锁
     * @param id 用户id
     * @param status 锁定状态
     */
    void lock(Long id, Integer status);
}
