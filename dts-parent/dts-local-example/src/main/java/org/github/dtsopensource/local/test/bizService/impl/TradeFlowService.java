package org.github.dtsopensource.local.test.bizService.impl;

import javax.annotation.Resource;

import org.github.dtsopensource.local.test.GenerateId;
import org.github.dtsopensource.local.test.bizService.ITradeFlowService;
import org.github.dtsopensource.local.test.dao.dataobject.DtsTestDO;
import org.github.dtsopensource.local.test.dao.mapper.DtsTestDOMapper;
import org.springframework.stereotype.Service;

/**
 * @author ligaofeng 2016年12月8日 下午9:50:02
 */
@Service
public class TradeFlowService implements ITradeFlowService {

    @Resource
    private DtsTestDOMapper dtsTestDOMapper;

    @Override
    public void saveTradeFlow(String flow) {
        DtsTestDO dtsTestDO = new DtsTestDO();
        dtsTestDO.setName(GenerateId.generateFlowId());
        dtsTestDO.setValue(flow);
        dtsTestDOMapper.insert(dtsTestDO);
    }

}
