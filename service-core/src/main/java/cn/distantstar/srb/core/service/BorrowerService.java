package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.Borrower;
import cn.distantstar.srb.core.pojo.vo.BorrowerApprovalVo;
import cn.distantstar.srb.core.pojo.vo.BorrowerAttachVo;
import cn.distantstar.srb.core.pojo.vo.BorrowerDetailVo;
import cn.distantstar.srb.core.pojo.vo.BorrowerVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface BorrowerService extends IService<Borrower> {

    /**
     * 根据用户id保存借款人信息
     * @param borrowerVO 借款人信息
     * @param userId 用户id
     */
    void saveBorrowerVoByUserId(BorrowerVo borrowerVO, Long userId);

    /**
     * 通过userId获取借款人申请状态
     * @param userId 借款人id
     * @return 返回状态
     */
    Integer getStatusByUserId(Long userId);

    /**
     * 根据id获取借款人信息
     * @param id id
     * @return 返回查询到的信息
     */
    BorrowerDetailVo getBorrowerDetailVoById(Long id);

    /**
     * 获取借款人分页信息列表
     * @param pageParam 分页参数
     * @param keyword 关键字
     * @return 返回信息列表
     */
    IPage<Borrower> listPage(Page<Borrower> pageParam, String keyword);

    /**
     * 对借款人审批
     * @param borrowerApprovalVO 借款人审批信息
     */
    void approval(BorrowerApprovalVo borrowerApprovalVO);
}
