package org.github.dtsopensource.local.test.application;

import org.github.dtsopensource.local.test.PurchaseContext;

/**
 * @author ligaofeng 2016年12月8日 下午8:30:32
 */
public interface IPurchaseService {

    /**
     * 购买服务
     * 
     * @param context
     * @param response
     */
    public String puchase(PurchaseContext context);
}
