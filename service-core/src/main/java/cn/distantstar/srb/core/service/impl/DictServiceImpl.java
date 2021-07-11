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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

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
        // 首先判断redis中有没有相应的数据
        List<Dict> dictList = null;
        try {
            dictList = (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if(dictList != null){
                log.info("从redis中取值成功");
                return dictList;
            }
        } catch (Exception e) {
            // 此处不抛出异常，继续执行后面的代码。
            // 因为缓存中没有取到数据，就去数据库看看
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

        log.info("从数据库中取值");
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        dictList = baseMapper.selectList(wrapper);
        dictList.forEach( dict -> {
                //如果有子节点，则是非叶子节点
                boolean hasChildren = this.hasChildren(dict.getId());
                dict.setHasChildren(hasChildren);
            }
        );

        // 将数据存入redis
        try {
            redisTemplate.opsForValue().set("srb:core:dictList:" + parentId,
                    dictList, 5, TimeUnit.MINUTES);
            log.info("数据存入redis");
        } catch (Exception e) {
            //此处不抛出异常，继续执行后面的代码
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

        return dictList;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(dictQueryWrapper);
        return this.listByParentId(dict.getId());
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
