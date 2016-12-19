package org.github.dtsopensource.remote.test.bizService.impl;

import javax.annotation.Resource;

import org.github.dtsopensource.remote.test.GenerateId;
import org.github.dtsopensource.remote.test.bizService.ITradeFlowService;
import org.github.dtsopensource.remote.test.dao.dataobject.DtsTestDO;
import org.github.dtsopensource.remote.test.dao.mapper.DtsTestDOMapper;
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
