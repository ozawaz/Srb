package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.LendReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface LendReturnService extends IService<LendReturn> {

    /**
     * 获取列表
     * @param lendId 标id
     * @return 返回列表
     */
    List<LendReturn> selectByLendId(Long lendId);

    /**
     * 还款
     * @param lendReturnId 还款标
     * @param userId 用户id
     * @return 自动提交表单
     */
    String commitReturn(Long lendReturnId, Long userId);

    /**
     * 还款异步回调
     * @param paramMap 参数
     */
    void notify(Map<String, Object> paramMap);
}
