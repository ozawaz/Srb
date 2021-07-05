package cn.distantstar.srb.core.mapper;

import cn.distantstar.srb.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户基本信息 Mapper 接口
 * </p>
 *
 * @author Star
 * @since 2021-07-02
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
