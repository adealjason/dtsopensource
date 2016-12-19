package org.github.dtsopensource.remote.test;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author ligaofeng 2016年12月8日 下午8:32:05
 */
@Data
public class PurchaseContext implements Serializable {

    private static final long serialVersionUID = -5005501890585728538L;

    //商品名称
    private String            productName;

    //商品价格
    private BigDecimal        orderAmount;

    //账户余额
    private BigDecimal        currentAmount;
}
