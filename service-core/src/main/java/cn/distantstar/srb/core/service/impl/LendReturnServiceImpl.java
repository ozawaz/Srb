package cn.distantstar.srb.core.service.impl;

import cn.distantstar.srb.core.pojo.entity.LendReturn;
import cn.distantstar.srb.core.mapper.LendReturnMapper;
import cn.distantstar.srb.core.service.LendReturnService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 还款记录表 服务实现类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Service
public class LendReturnServiceImpl extends ServiceImpl<LendReturnMapper, LendReturn> implements LendReturnService {

    @Override
    public List<LendReturn> selectByLendId(Long lendId) {
        QueryWrapper<LendReturn> queryWrapper = new QueryWrapper();
        queryWrapper.eq("lend_id", lendId);
        List<LendReturn> lendReturnList = baseMapper.selectList(queryWrapper);
        return lendReturnList;
    }
}
