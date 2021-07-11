package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.Borrower;
import cn.distantstar.srb.core.pojo.vo.BorrowerVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
