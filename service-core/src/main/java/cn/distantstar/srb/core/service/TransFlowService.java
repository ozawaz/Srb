package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.bo.TransFlowBo;
import cn.distantstar.srb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface TransFlowService extends IService<TransFlow> {

    /**
     * 保存交易流水
     * @param transFlowBO 交易流水对象
     */
    void saveTransFlow(TransFlowBo transFlowBO);

    /**
     * 判断流水如果存在，则从业务方法中直接退出
     * @param agentBillNo 流水订单号
     * @return 返回结果
     */
    boolean isSaveTransFlow(String agentBillNo);
}
