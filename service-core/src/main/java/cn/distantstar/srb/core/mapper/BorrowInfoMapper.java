package cn.distantstar.srb.core.mapper;

import cn.distantstar.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Mapper
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo> {

    /**
     * 获取申请信息
     * @return 返回申请信息
     */
    List<BorrowInfo> selectBorrowInfoList();
}
