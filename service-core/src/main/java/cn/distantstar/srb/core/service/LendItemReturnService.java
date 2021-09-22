package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.LendItemReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借回款记录表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface LendItemReturnService extends IService<LendItemReturn> {

    /**
     * 获取回款计划列表
     * @param lendId 标id
     * @param userId 用户id
     * @return 返回列表
     */
    List<LendItemReturn> selectByLendId(Long lendId, Long userId);

    /**
     * 根据还款id获取回款列表
     * @param lendReturnId 还款id
     * @return 返回回款列表
     */
    List<Map<String, Object>> addReturnDetail(Long lendReturnId);

    /**
     * 根据还款计划id获取对应的回款计划列表
     * @param lendReturnId 还款id
     * @return 返回计划列表
     */
    List<LendItemReturn> selectLendItemReturnList(Long lendReturnId);
}
