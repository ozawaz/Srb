package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.BorrowInfo;
import cn.distantstar.srb.core.pojo.entity.Lend;
import cn.distantstar.srb.core.pojo.vo.BorrowInfoApprovalVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface LendService extends IService<Lend> {

    /**
     * 创建标
     * @param borrowInfoApprovalVO 借款申请信息
     * @param borrowInfo 借款人信息
     */
    void createLend(BorrowInfoApprovalVo borrowInfoApprovalVO, BorrowInfo borrowInfo);

    /**
     * 获取标的列表
     * @return 返回标的列表
     */
    List<Lend> selectList();

    /**
     * 获取标的信息
     * @param id 标id
     * @return 返回信息
     */
    Map<String, Object> getLendDetail(Long id);
}
