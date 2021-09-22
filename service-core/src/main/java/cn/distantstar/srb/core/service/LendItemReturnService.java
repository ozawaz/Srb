package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.LendItemReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
