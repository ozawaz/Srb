package cn.distantstar.srb.core.service;

import cn.distantstar.srb.core.pojo.dto.ExcelDictDTO;
import cn.distantstar.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
public interface DictService extends IService<Dict> {

    /**
     * 批量导入数据
     * @param inputStream 读取流
     */
    void importData(InputStream inputStream);

    /**
     * 批量导出数据
     * @return 返回导出的数据列表
     */
    List<ExcelDictDTO> listDictData();

    /**
     * 根据父id查找所有数据
     * @param parentId 父id
     * @return 返回所有数据
     */
    List<Dict> listByParentId(Long parentId);

    /**
     * 根据dictCode获取下级节点
     * @param dictCode dictCode
     * @return 返回查找到的节点
     */
    List<Dict> findByDictCode(String dictCode);
}
