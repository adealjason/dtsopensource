package org.github.dtsopensource.local.test;

import java.util.UUID;

/**
 * @author ligaofeng 2016年12月8日 下午9:02:58
 */
public class GenerateId {

    private GenerateId() {
    }

    /**
     * @return
     */
    public static String generateOrderId() {
        return "order_" + UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * @return
     */
    public static String generatePaymentId() {
        return "payment_" + UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * @return
     */
    public static String generateFlowId() {
        return "flow_" + UUID.randomUUID().toString().replaceAll("-", "");
    }
}
