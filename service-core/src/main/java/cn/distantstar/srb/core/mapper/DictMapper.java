package cn.distantstar.srb.core.mapper;

import cn.distantstar.srb.core.pojo.dto.ExcelDictDTO;
import cn.distantstar.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 将数据批量插入到数据库中
     * @param list 将要插入的数据
     */
    void insertBatch(List<ExcelDictDTO> list);
}
