package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.BorrowInfo;
import cn.distantstar.srb.core.pojo.vo.BorrowInfoApprovalVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface BorrowInfoService extends IService<BorrowInfo> {

    /**
     * 获取借款额度
     * @param userId 用户id
     * @return 返回借款额度
     */
    BigDecimal getBorrowAmount(Long userId);

    /**
     * 提交借款申请
     * @param borrowInfo 借款信息
     * @param userId 用户id
     */
    void saveBorrowInfo(BorrowInfo borrowInfo, Long userId);

    /**
     * 获取借款申请审批状态
     * @param userId 用户id
     * @return 返回状态
     */
    Integer getStatusByUserId(Long userId);

    /**
     * 获取申请信息
     * @return 返回申请信息
     */
    List<BorrowInfo> selectList();

    /**
     * 获取借款信息
     * @param id id
     * @return 返回借款信息
     */
    Map<String, Object> getBorrowInfoDetail(Long id);

    /**
     * 审批借款信息
     * @param borrowInfoApprovalVO 申请信息
     */
    void approval(BorrowInfoApprovalVo borrowInfoApprovalVO);
}
