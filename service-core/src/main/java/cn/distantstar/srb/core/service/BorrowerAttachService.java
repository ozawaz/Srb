package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.entity.BorrowerAttach;
import cn.distantstar.srb.core.pojo.vo.BorrowerAttachVo;
import cn.distantstar.srb.core.pojo.vo.BorrowerDetailVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface BorrowerAttachService extends IService<BorrowerAttach> {

    /**
     * 根据借款人id获取附件
     * @param borrowerId 借款人id
     * @return 返回查询到的附件
     */
    List<BorrowerAttachVo> selectBorrowerAttachVoList(Long borrowerId);
}
