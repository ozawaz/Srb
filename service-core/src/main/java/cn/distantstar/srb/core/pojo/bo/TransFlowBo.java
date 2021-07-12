package cn.distantstar.srb.core.pojo.bo;

import cn.distantstar.srb.core.enums.TransTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author distantstar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransFlowBo {
    private String agentBillNo;
    private String bindCode;
    private BigDecimal amount;
    private TransTypeEnum transTypeEnum;
    private String memo;
}
