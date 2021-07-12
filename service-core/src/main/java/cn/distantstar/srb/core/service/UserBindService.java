package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.UserBind;
import cn.distantstar.srb.core.pojo.vo.UserBindVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface UserBindService extends IService<UserBind> {

    /**
     * 账户绑定提交数据
     * @param userBindVo 绑定信息
     * @param userId 用户id
     * @return 返回动态表单字符串
     */
    String commitBindUser(UserBindVo userBindVo, Long userId);

    /**
     * 汇付宝异步通知尚融宝
     * @param paramMap 转换好的map
     */
    void notify(Map<String, Object> paramMap);

    /**
     * 根据投标用户id获取绑定code
     * @param investUserId 投标用户id
     * @return 返回绑定code
     */
    String getBindCodeByUserId(Long investUserId);
}
