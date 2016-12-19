package org.github.dtsopensource.remote.test.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.github.dtsopensource.remote.test.dao.dataobject.DtsTestDO;
import org.github.dtsopensource.remote.test.dao.dataobject.DtsTestDOExample;

public interface DtsTestDOMapper {
    int insert(DtsTestDO record);

    int insertSelective(DtsTestDO record);

    List<DtsTestDO> selectByExample(DtsTestDOExample example);

    int updateByExampleSelective(@Param("record") DtsTestDO record, @Param("example") DtsTestDOExample example);

    int updateByExample(@Param("record") DtsTestDO record, @Param("example") DtsTestDOExample example);
}
