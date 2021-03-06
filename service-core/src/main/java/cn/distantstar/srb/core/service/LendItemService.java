package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.LendItem;
import cn.distantstar.srb.core.pojo.vo.InvestVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借记录表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface LendItemService extends IService<LendItem> {

    /**
     * 会员投资提交数据
     * @param investVO 投标信息
     * @return 返回表单
     */
    String commitInvest(InvestVo investVO);

    /**
     * 会员投资异步回调
     * @param paramMap 参数
     */
    void notify(Map<String, Object> paramMap);

    /**
     * 获取投资列表信息
     * @param lendId 标id
     * @param status 状态
     * @return 返回列表信息
     */
    List<LendItem> selectByLendId(Long lendId, int status);

    /**
     * 获取投资列表信息
     * @param lendId 标id
     * @return 返回列表信息
     */
    List<LendItem> selectByLendId(Long lendId);
}
