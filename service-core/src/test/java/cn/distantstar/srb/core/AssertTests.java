package cn.distantstar.srb.core;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * @author: distantstar
 */
public class AssertTests {

    @Test
    public void test1() {
        Object object = null;
        if (object == null) {
            throw new IllegalArgumentException("参数错误");
        }
    }

    @Test
    public void test2() {
        Object object = null;
        Assert.notNull(object, "参数错误");
    }
}
