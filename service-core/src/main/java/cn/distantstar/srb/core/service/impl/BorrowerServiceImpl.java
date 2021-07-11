package cn.distantstar.srb.core.service.impl;

import cn.distantstar.srb.core.enums.BorrowerStatusEnum;
import cn.distantstar.srb.core.mapper.BorrowerAttachMapper;
import cn.distantstar.srb.core.mapper.UserInfoMapper;
import cn.distantstar.srb.core.pojo.entity.Borrower;
import cn.distantstar.srb.core.mapper.BorrowerMapper;
import cn.distantstar.srb.core.pojo.entity.BorrowerAttach;
import cn.distantstar.srb.core.pojo.entity.UserInfo;
import cn.distantstar.srb.core.pojo.vo.BorrowerVo;
import cn.distantstar.srb.core.service.BorrowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService {

    @Resource
    private BorrowerAttachMapper borrowerAttachMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void saveBorrowerVoByUserId(BorrowerVo borrowerVO, Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);

        // 保存借款人信息
        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        // 认证中
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        baseMapper.insert(borrower);

        // 保存附件
        List<BorrowerAttach> borrowerAttachList = borrowerVO.getBorrowerAttachList();
        borrowerAttachList.forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });

        // 更新会员状态，更新为认证中
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoMapper.updateById(userInfo);
    }
}
