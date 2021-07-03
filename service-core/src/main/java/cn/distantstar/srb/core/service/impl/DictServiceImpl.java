package cn.distantstar.srb.core.service.impl;

import cn.distantstar.srb.core.pojo.dto.ExcelDictDTO;
import cn.distantstar.srb.core.pojo.entity.Dict;
import cn.distantstar.srb.core.mapper.DictMapper;
import cn.distantstar.srb.core.pojo.listener.ExcelDictDTOListener;
import cn.distantstar.srb.core.service.DictService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper))
                .sheet()
                .doRead();
        log.info("importData finished");
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        // 将数据从数据库中全部读取
        List<Dict> dictList = baseMapper.selectList(null);
        // 将List<Dict>转换成合适的List<ExcelDictDTO>
        ArrayList<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        dictList.forEach( dict -> {
                //如果有子节点，则是非叶子节点
                boolean hasChildren = this.hasChildren(dict.getId());
                dict.setHasChildren(hasChildren);
            }
        );
        return dictList;
    }

    /**
     * 将父id当做id去查询，看是否能找到数据
     * 能找到，则证明有子数据
     * 反之，则证明没有子数据
     * @param id 父id
     * @return 返回boolean
     */
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }


}
