package cn.distantstar.srb.core;

import cn.distantstar.srb.core.mapper.DictMapper;
import cn.distantstar.srb.core.pojo.entity.Dict;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: distantstar
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@PropertySource(value={"classpath:application.yml"})
public class RedisTemplateTests {

    @Autowired
    private RedisTemplate<String, Object> template;
    @Resource
    private DictMapper dictMapper;

    @Test
    public void saveDict(){
        log.info("进入方法==================================================");
        Dict dict = dictMapper.selectById(1);
        log.info("dict方法查询完毕===========================================");
        //向数据库中存储string类型的键值对, 过期时间5分钟
        template.opsForValue().set("dict", dict, 5, TimeUnit.MINUTES);
    }
}
